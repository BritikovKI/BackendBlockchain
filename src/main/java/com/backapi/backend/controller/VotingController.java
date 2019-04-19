package com.backapi.backend.controller;


import com.backapi.backend.model.UserStatus;
import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.model.dto.VotingDTO;
import com.backapi.backend.service.UserService;
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

    @Autowired
    public VotingController(VotingService votingService, UserService userService) {
        this.votingService = votingService;
        this.userService = userService;
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
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserStatus.SUCCESSFULLY_CREATED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(UserStatus.NOT_UNIQUE_FIELDS_IN_REQUEST);
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

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(votingService.get(id));
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
                        .body(votingService.getAll());
            } catch (DuplicateKeyException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(UserStatus.NOT_FOUND);
            }
    }


    @GetMapping(path = "/get/user/{id}")
    public ResponseEntity getAll(HttpSession session, @PathVariable Integer userId) {
        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(votingService.getVotesByUser(userId));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        }
    }


}
