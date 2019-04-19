package com.backapi.backend.model.dto;


import lombok.Data;

@Data
public class VariantDTO {
    private int id;
    private String name;
    private String description;
    private int voting_id;
}
