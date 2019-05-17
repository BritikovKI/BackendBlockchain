package com.backapi.backend.dao;

import com.backapi.backend.model.dto.ChangePasswordDTO;
import com.backapi.backend.model.dto.UserDTO;

import java.util.List;

public interface UserDAO {
    UserDTO getUserByEmail(String email);


    void addNewUser( UserDTO userDTO);

    void changeUserKey(String email, UserDTO encode);

    List<UserDTO> get();

    List<UserDTO> getUserByVote(Integer id);
}
