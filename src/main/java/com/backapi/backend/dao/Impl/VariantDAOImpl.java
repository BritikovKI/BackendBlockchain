package com.backapi.backend.dao.Impl;

import com.backapi.backend.dao.VariantDAO;
import com.backapi.backend.mapper.VariantMapper;
import com.backapi.backend.model.dto.VariantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VariantDAOImpl implements VariantDAO {

    private final JdbcTemplate jdbc;
    private final VariantMapper variantMapper;

    @Autowired
    public VariantDAOImpl(JdbcTemplate jdbc,
                         VariantMapper variantMapper) {
        this.jdbc = jdbc;
        this.variantMapper = variantMapper;
    }

    @Override
    public void create(VariantDTO variantDTO) {
        final String sql = "INSERT INTO variant(name, description,voting_id) VALUES (?,?,?);";
        jdbc.update(sql, variantDTO.getName(),variantDTO.getDescription(),variantDTO.getVoting_id());
    }

    @Override
    public void change(VariantDTO variantDTO) {
        final String sql = "UPDATE variant SET name = ?, description = ? WHERE id = ?;";
        jdbc.update(sql,variantDTO.getName(),variantDTO.getDescription());
    }

    @Override
    public void delete(VariantDTO variantDTO) {
        final String sql = "DELETE FROM variant WHERE id = ?;";
        jdbc.update(sql,variantDTO.getId());

    }

    @Override
    public VariantDTO get(Integer id) {
        final String sql = "SELECT * FROM variant WHERE id=?;";
        return jdbc.queryForObject(sql,variantMapper,id);
    }

    @Override
    public List<VariantDTO> getVariantsByVote(Integer voteId) {

        final String sql = "SELECT * FROM variant WHERE voting_id=?;";
        return jdbc.query(sql,variantMapper,voteId);
    }
}
