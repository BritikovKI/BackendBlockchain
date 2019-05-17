package com.backapi.backend.service;

import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.model.dto.VariantDTO;

import java.util.List;

public interface VariantService {
    void create(VariantDTO variantDTO);
    void change(VariantDTO variantDTO);
    void delete(VariantDTO variantDTO);
    VariantDTO get(Integer id);
    List<VariantDTO> getVariantsByVote(Integer voteId);


}
