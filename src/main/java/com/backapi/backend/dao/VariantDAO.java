package com.backapi.backend.dao;

import com.backapi.backend.model.dto.VariantDTO;

import java.util.List;

public interface VariantDAO {
    void create(VariantDTO variantDTO);
    void change(VariantDTO variantDTO);
    void delete(VariantDTO variantDTO);
    VariantDTO get(Integer id);
    List<VariantDTO> getVariantsByVote(Integer voteId);
}
