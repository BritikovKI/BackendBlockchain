package com.backapi.backend.service;

import com.backapi.backend.model.dto.UserDTO;

public interface UserService {

    UserDTO getUserByEmail(String email);

    boolean checkUserPassword(String rawPassword, String passwordFromDb);

    void changeUserPassword(String email, String password);

    void addNewUser(UserDTO userDTO);
}
