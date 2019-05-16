package com.backapi.backend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class VotingDTO {
    private int id;
    private String name;
    private String description;
    private int user_id;
    private List<VariantDTO> variants;
    private String blockKey;
}