package com.backapi.backend.service.Impl;

import com.backapi.backend.dao.UserDAO;
import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }



    @Override
    public boolean checkUserPassword(String rawPassword, String passwordFromDb) {
        return passwordEncoder.matches(rawPassword, passwordFromDb);
    }

    @Override
    public void changeUserPassword(String email, String password) {
        userDAO.changePassword(email, passwordEncoder.encode(password));
    }

    @Override
    public void addNewUser( UserDTO userDTO) {
        userDAO.addNewUser( userDTO);
    }
}
