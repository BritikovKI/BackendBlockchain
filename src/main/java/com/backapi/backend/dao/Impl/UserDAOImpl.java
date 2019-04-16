package com.backapi.backend.dao.Impl;
import com.backapi.backend.dao.UserDAO;
import com.backapi.backend.mapper.UserMapper;
import com.backapi.backend.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbc;

    private final UserMapper userMapper;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbc,
                       UserMapper userMapper) {
        this.jdbc = jdbc;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        final String sql = "SELECT * FROM user WHERE lower(email) = lower(?)";
        try {
            return jdbc.queryForObject(sql, userMapper, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer addUser(UserDTO userDTO) {
        final String sql = "INSERT INTO user(email, password) VALUES (?, ?) RETURNING id";
        return jdbc.queryForObject(sql, Integer.class, userDTO.getEmail(), userDTO.getPassword());
    }

    @Override
    public void addNewUser( UserDTO userDTO) {
        final String sql = "INSERT INTO user(email, password) VALUES (?, ?)";
        jdbc.update(sql, userDTO.getEmail(), userDTO.getPassword()) ;

    }

    @Override
    public Integer getUserIdByEmail(String email) {
        try {
            return jdbc.queryForObject("SELECT id FROM user WHERE lower(email) = lower(?)", Integer.class, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public void changePassword(String email, String password) {
        final String sql = "UPDATE user SET password = ? WHERE lower(user.email) = lower(?);";
        jdbc.update(sql, password, email);
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
