package com.backapi.backend.service;

import com.backapi.backend.model.dto.UserDTO;

public interface UserService {

    UserDTO getUserByEmail(String email);

    void addUserAndCreateSchool(UserDTO userDTO);

    boolean checkUserPassword(String rawPassword, String passwordFromDb);

    void changeUserPassword(String email, String password);

    void addNewUser(String toString, UserDTO userDTO);
}
