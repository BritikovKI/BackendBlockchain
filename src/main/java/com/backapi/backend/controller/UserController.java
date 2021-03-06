package com.backapi.backend.controller;

import com.backapi.backend.model.UserStatus;
import com.backapi.backend.model.dto.ChangePasswordDTO;
import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@EnableJdbcHttpSession
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/info")
    public ResponseEntity getUser(HttpSession session) {

        logger.info("info session - ", session.getId());

        Object sessionAttribute = session.getAttribute("user");
        if (sessionAttribute == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(UserStatus.ACCESS_ERROR);
        }

        String email = sessionAttribute.toString();

        UserDTO user = userService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity register(HttpSession session, @RequestBody UserDTO user) {

        logger.info("register session - ", session.getId());

        if (session.getAttribute("user") != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ALREADY_AUTHENTICATED);
        }

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.EMPTY_FIELDS_IN_REQUEST);
        }

        try {
            userService.addNewUser(user);
            sessionAuth(session, user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserStatus.SUCCESSFULLY_CREATED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(UserStatus.NOT_UNIQUE_FIELDS_IN_REQUEST);
        }
    }

    @PostMapping(path = "/auth")
    public ResponseEntity auth(HttpSession httpSession, @RequestBody UserDTO userDTO) {

        if (httpSession.getAttribute("user") != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ALREADY_AUTHENTICATED);
        }

        UserDTO userFromDb = userService.getUserByEmail(userDTO.getEmail());
        if (userFromDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        }

        boolean userIsValid = userService.checkUserPassword(
                userDTO.getPassword(),
                userFromDb.getPassword());

        if (!userIsValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UserStatus.WRONG_CREDENTIALS);
        }

        sessionAuth(httpSession, userDTO.getEmail());
        return ResponseEntity.ok(UserStatus.SUCCESSFULLY_AUTHED);
    }

    @PostMapping(path = "/change")
    public ResponseEntity change(HttpSession httpSession, @RequestBody UserDTO userDTO) {

        if (httpSession.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ALREADY_AUTHENTICATED);
        }
        if (httpSession.getAttribute("user").toString() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(UserStatus.NOT_FOUND);
        }

        try {
            userService.changeUserKey(
                    httpSession.getAttribute("user").toString(),
                    userDTO);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UserStatus.WRONG_CREDENTIALS);
        }

        return ResponseEntity.ok(UserStatus.SUCCESSFULLY_AUTHED);
    }

    @GetMapping(path = "/get")
    public ResponseEntity get(HttpSession httpSession ) {

        Object userSession = httpSession.getAttribute("user");
        if (userSession == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(UserStatus.ACCESS_ERROR);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());

    }


    @PostMapping(path = "/logout")
    public ResponseEntity logout(HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(UserStatus.ACCESS_ERROR);
        }
        httpSession.invalidate();
        return ResponseEntity.ok(UserStatus.SUCCESSFULLY_LOGGED_OUT);
    }


    private void sessionAuth(HttpSession session, String email){
        session.setAttribute("user", email);
        session.setMaxInactiveInterval(7*24*60*60);
    }
}
