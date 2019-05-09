package com.backapi.backend.service.Impl;

import com.backapi.backend.dao.UserDAO;
import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

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
    public void changeUserKey(String email, UserDTO user) {
        userDAO.changeUserKey(email, user);
        ProcessBuilder pb = new ProcessBuilder("src/main/resources/scripts/transactMoney.sh", user.getPublicKey());
        try {
            Process p = pb.start();
        } catch (IOException ex){
            return;
        }


    }

    @Override
    public void addNewUser( UserDTO userDTO) {
        userDAO.addNewUser( userDTO);
        if(userDTO.getPublicKey()!=null) {
            ProcessBuilder pb = new ProcessBuilder("src/main/resources/scripts/transactMoney.sh", userDTO.getPublicKey());
            try {
                Process p = pb.start();
            } catch (IOException ex){
                return;
            }
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userDAO.get();
    }
}
