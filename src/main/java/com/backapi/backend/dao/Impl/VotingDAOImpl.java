package com.backapi.backend.dao.Impl;

import com.backapi.backend.dao.VotingDAO;
import com.backapi.backend.mapper.VariantMapper;
import com.backapi.backend.mapper.VotingMapper;
import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.model.dto.UserVoteDTO;
import com.backapi.backend.model.dto.VotingDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class VotingDAOImpl implements VotingDAO {

    private final JdbcTemplate jdbc;
    private final VotingMapper votingMapper;
    private final VariantMapper variantMapper;

    @Autowired
    public VotingDAOImpl(JdbcTemplate jdbc,
                       VotingMapper votingMapper,
                         VariantMapper variantMapper) {
        this.jdbc = jdbc;
        this.votingMapper = votingMapper;
        this.variantMapper = variantMapper;
    }

    @Override
    @Transactional
    public void create(VotingDTO votingDTO) {
            String sql = "INSERT INTO voting(name, description, creator_id, block_key) VALUES (?,?,?,?) RETURNING id;";
            Integer id = jdbc.queryForObject(sql,Integer.class,votingDTO.getName(),votingDTO.getDescription(),votingDTO.getUser_id(), votingDTO.getBlockKey());
            votingDTO.setId(id);
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
    public VotingDTO get(UserDTO user, Integer id) {
        final String sql = "SELECT * FROM voting WHERE id=?;";
        VotingDTO res = jdbc.queryForObject(sql,votingMapper,id);
        try {
            res.setVoted(jdbc.queryForObject("SELECT voted FROM user_vote WHERE user_id=? AND vote_id=? ;", Boolean.class, user.getId(), res.getId()));
        } catch (NullPointerException | EmptyResultDataAccessException exp) {
            res.setVoted(null);
        }
        res.setVariants(jdbc.query("SELECT * FROM variant WHERE voting_id=?;", variantMapper, res.getId()));
        return res;
    }

    @Override
    public List<VotingDTO> getVotesByUser(Integer userId) {

        final String sql = "SELECT * FROM voting WHERE creator_id=?;";
        List<VotingDTO> res = jdbc.query(sql,votingMapper,userId);
        for (VotingDTO re : res) {
            re.setVariants(jdbc.query("SELECT * FROM variant WHERE voting_id=?;", variantMapper, re.getId()));
        }
        return res;
    }

    @Override
    public List<VotingDTO> getAll(Integer userId) {
        final String sql = "SELECT * FROM voting JOIN user_vote" +
                " ON user_vote.vote_id = voting.id AND user_id=?;";
        List<VotingDTO> res = jdbc.query(sql,votingMapper, userId);
        for (VotingDTO re : res) {
            re.setVariants(jdbc.query("SELECT * FROM variant WHERE voting_id=?;", variantMapper, re.getId()));
        }
        return res;
    }



    @Override
    public void addUser(UserVoteDTO userDTO, String user) {
        final String sql = "INSERT INTO user_vote(user_id, vote_id) VALUES (?,?);";
        jdbc.update(sql, userDTO.getUser_id(), userDTO.getVote_id());
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
