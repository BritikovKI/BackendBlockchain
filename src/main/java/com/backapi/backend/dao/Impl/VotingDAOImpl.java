package com.backapi.backend.dao.Impl;

import com.backapi.backend.dao.VotingDAO;
import com.backapi.backend.mapper.VotingMapper;
import com.backapi.backend.model.dto.VotingDTO;
import org.springframework.jdbc.core.JdbcTemplate;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VotingDAOImpl implements VotingDAO {

    private final JdbcTemplate jdbc;
    private final VotingMapper votingMapper;

    @Autowired
    public VotingDAOImpl(JdbcTemplate jdbc,
                       VotingMapper votingMapper) {
        this.jdbc = jdbc;
        this.votingMapper = votingMapper;
    }

    @Override
    public void create(VotingDTO votingDTO) {
            final String sql = "INSERT INTO voting(name, description, creator_id) VALUES (?,?,?);";
            jdbc.update(sql,votingDTO.getName(),votingDTO.getDescription(),votingDTO.getUser_id());
    }

    @Override
    public void change(VotingDTO votingDTO) {
        final String sql = "UPDATE voting SET name=?, description=? WHERE id = ?;";
        jdbc.update(sql,votingDTO.getName(),votingDTO.getDescription(),votingDTO.getId());

    }

    @Override
    public void delete(VotingDTO votingDTO) {
        final String sql = "DELETE FROM voting WHERE id = ?;";
        jdbc.update(sql,votingDTO.getId());
    }

    @Override
    public VotingDTO get(Integer id) {
        final String sql = "SELECT * FROM voting WHERE id=?;";
        return jdbc.queryForObject(sql,votingMapper,id);
    }

    @Override
    public List<VotingDTO> getVotesByUser(Integer userId) {

        final String sql = "SELECT * FROM voting WHERE creator_id=?;";
        return jdbc.query(sql,votingMapper,userId);
    }

    @Override
    public List<VotingDTO> getAll() {
        final String sql = "SELECT * FROM voting;";
        return jdbc.query(sql,votingMapper);
    }

//    public static class UserMapper implements RowMapper<UserDTO> {
//        @Override
//        public UserDTO mapRow(ResultSet resultSet, int i) throws SQLException {
//            UserDTO votingDTO = new UserDTO();
//            votingDTO.setEmail(resultSet.getString("email"));
//            votingDTO.setPassword(resultSet.getString("password"));
//            votingDTO.setId(resultSet.getInt("id"));
//            return votingDTO;
//        }
//    }
}
