package com.backapi.backend.mapper;


import com.backapi.backend.model.dto.UserDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<UserDTO> {
    @Override
    public UserDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(resultSet.getString("email"));
        userDTO.setPassword(resultSet.getString("password"));
        userDTO.setId(resultSet.getInt("id"));
        userDTO.setPublicKey(resultSet.getString("public_key"));
        return userDTO;
    }
}