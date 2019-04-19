package com.backapi.backend.dao;

import com.backapi.backend.model.dto.ChangePasswordDTO;
import com.backapi.backend.model.dto.UserDTO;

public interface UserDAO {
    UserDTO getUserByEmail(String email);


    void addNewUser( UserDTO userDTO);

    void changePassword(String email, ChangePasswordDTO encode);

}
