package com.backapi.backend.dao.Impl;
import com.backapi.backend.dao.UserDAO;
import com.backapi.backend.mapper.UserMapper;
import com.backapi.backend.model.dto.ChangePasswordDTO;
import com.backapi.backend.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbc;

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbc,
                       UserMapper userMapper,PasswordEncoder passwordEncoder) {
        this.jdbc = jdbc;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        final String sql = "SELECT * FROM users WHERE lower(email) = lower(?)";
        try {
            return jdbc.queryForObject(sql, userMapper, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public void addNewUser( UserDTO userDTO) {
        final String sql = "INSERT INTO users(email, password,public_key) VALUES (?, ?,?)";
        jdbc.update(sql, userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getPublicKey()) ;

    }


    @Override
    @Transactional
    public void changeUserKey(String email,UserDTO user) {
        final String sql = "UPDATE users SET public_key=? WHERE lower(users.email) = lower(?);";
        jdbc.update(sql, user.getPublicKey(), email);
        jdbc.update("DELETE FROM user_vote WHERE user_id = ?", user.getId());
        jdbc.update("DELETE FROM voting WHERE creator_id = ?", user.getId());
    }

    @Override
    public List<UserDTO> get() {
        final String sql = "SELECT * FROM users;";
        return jdbc.query(sql, userMapper);
    }

    @Override
    public List<UserDTO> getUserByVote(Integer id) {
        final String sql = "SELECT * FROM users JOIN" +
                " (SELECT user_id, vote_id FROM user_vote) AS uv " +
                " ON uv.vote_id = ? AND user_id = id;";
        return jdbc.query(sql,userMapper,id);
    }

//    public static class UserMapper implements RowMapper<UserDTO> {
//        @Override
//        public UserDTO mapRow(ResultSet resultSet, int i) throws SQLException {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setEmail(resultSet.getString("email"));
//            userDTO.setPassword(resultSet.getString("password"));
//            userDTO.setId(resultSet.getInt("id"));
//            return userDTO;
//        }
//    }
}
