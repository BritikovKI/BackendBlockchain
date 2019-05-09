package com.backapi.backend.model.dto;


import lombok.Data;


@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String password;
    private String publicKey;
}
