package com.backapi.backend.service;

import com.backapi.backend.model.dto.ChangePasswordDTO;
import com.backapi.backend.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserByEmail(String email);

    boolean checkUserPassword(String rawPassword, String passwordFromDb);

    void changeUserKey(String email, UserDTO user);

    void addNewUser(UserDTO userDTO);

    List<UserDTO> getUsers();
}
