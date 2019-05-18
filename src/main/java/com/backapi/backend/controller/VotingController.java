package com.backapi.backend.controller;


import com.backapi.backend.model.UserStatus;
import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.model.dto.UserVoteDTO;
import com.backapi.backend.model.dto.VotingDTO;
import com.backapi.backend.service.UserService;
import com.backapi.backend.service.VariantService;
import com.backapi.backend.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/voting")
@EnableJdbcHttpSession
public class VotingController {


    private VotingService votingService;
    private UserService userService;
    private VariantService variantService;

    @Autowired
    public VotingController(VotingService votingService, VariantService variantService, UserService userService) {
        this.votingService = votingService;
        this.userService = userService;
        this.variantService = variantService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity create(HttpSession session, @RequestBody VotingDTO votingDTO) {


        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            votingDTO.setUser_id(userService.getUserByEmail(session.getAttribute("user").toString()).getId());
            votingService.create(votingDTO);
            for (int i =0;i<votingDTO.getVariants().size();i++) {
                votingDTO.getVariants().get(i).setVoting_id(votingDTO.getId());
                variantService.create(votingDTO.getVariants().get(i));
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserStatus.SUCCESSFULLY_CREATED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(UserStatus.NOT_UNIQUE_FIELDS_IN_REQUEST);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity change(HttpSession session, @RequestBody UserVoteDTO userDTO) {


        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }
        UserDTO user = userService.getUserByEmail(session.getAttribute("user").toString());
        if(user.getId() == votingService.get(user,userDTO.getVote_id()).getUser_id()) {
            try {
                votingService.addUser(userDTO, session.getAttribute("user").toString());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(UserStatus.SUCCESSFULLY_CHANGED);
            } catch (DuplicateKeyException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(UserStatus.NOT_UNIQUE_FIELDS_IN_REQUEST);
            }
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }
    }

    @PostMapping(path = "/change")
    public ResponseEntity change(HttpSession session, @RequestBody VotingDTO votingDTO) {


        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            votingDTO.setUser_id(userService.getUserByEmail(session.getAttribute("user").toString()).getId());
            votingService.change(votingDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(UserStatus.SUCCESSFULLY_CHANGED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(UserStatus.NOT_UNIQUE_FIELDS_IN_REQUEST);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity get(HttpSession session, @PathVariable Integer id) {


        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        UserDTO user = userService.getUserByEmail(session.getAttribute("user").toString());
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(votingService.get(user,id));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        }
    }


    @GetMapping(path = "/get/")
    public ResponseEntity getAll(HttpSession session) {
            if (session.getAttribute("user") == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(UserStatus.ACCESS_ERROR);
            }

            try {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(votingService.getAll(userService.getUserByEmail(session.getAttribute("user").toString()).getId()));
            } catch (DuplicateKeyException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(UserStatus.NOT_FOUND);
            }
    }


    @GetMapping(path = "/get/{id}/users")
    public ResponseEntity getUsers(HttpSession session, @PathVariable Integer id) {


        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(votingService.getVoters(id));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/get/user")
    public ResponseEntity getUserPolls(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(votingService.getVotesByUser(userService.getUserByEmail(session.getAttribute("user").toString()).getId()));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        }
    }
}
