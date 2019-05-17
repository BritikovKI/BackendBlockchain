package com.backapi.backend.controller;


import com.backapi.backend.model.UserStatus;
import com.backapi.backend.model.dto.VariantDTO;
import com.backapi.backend.service.UserService;
import com.backapi.backend.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/variant")
@EnableJdbcHttpSession
public class VariantController {


    private VariantService variantService;
    private UserService userService;

    @Autowired
    public VariantController(VariantService variantService, UserService userService) {
        this.variantService = variantService;
        this.userService = userService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity create(HttpSession session, @RequestBody VariantDTO variantDTO) {


        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            variantService.create(variantDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserStatus.SUCCESSFULLY_CREATED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(UserStatus.NOT_UNIQUE_FIELDS_IN_REQUEST);
        }
    }

    @PostMapping(path = "/delete")
    public ResponseEntity delete(HttpSession session, @RequestBody VariantDTO variantDTO) {


        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            variantService.delete(variantDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserStatus.SUCCESSFULLY_CREATED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(UserStatus.NOT_UNIQUE_FIELDS_IN_REQUEST);
        }
    }

    @PostMapping(path = "/change")
    public ResponseEntity change(HttpSession session, @RequestBody VariantDTO variantDTO) {


        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            variantService.change(variantDTO);
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
                    .body(variantService.get(id));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        }
    }







    @GetMapping(path = "/get/voting/{id}")
    public ResponseEntity getByVote(HttpSession session, @PathVariable Integer votingId) {
        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(variantService.getVariantsByVote(votingId));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        }
    }

}
