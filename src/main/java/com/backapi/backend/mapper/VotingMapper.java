package com.backapi.backend.mapper;


import com.backapi.backend.model.dto.VotingDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VotingMapper implements RowMapper<VotingDTO> {
    @Override
    public VotingDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        VotingDTO votingDTO = new VotingDTO();
        votingDTO.setName(resultSet.getString("name"));
        votingDTO.setDescription(resultSet.getString("description"));
        votingDTO.setId(resultSet.getInt("id"));
        return votingDTO;
    }
}
