package com.backapi.backend.mapper;


import com.backapi.backend.model.dto.VariantDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VariantMapper implements RowMapper<VariantDTO> {
    @Override
    public VariantDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        VariantDTO variantDTO = new VariantDTO();
        variantDTO.setName(resultSet.getString("name"));
        variantDTO.setDescription(resultSet.getString("description"));
        variantDTO.setId(resultSet.getInt("id"));
        variantDTO.setVoting_id(resultSet.getInt("voting_id"));
        return variantDTO;
    }
}
