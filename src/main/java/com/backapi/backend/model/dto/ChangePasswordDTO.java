package com.backapi.backend.model.dto;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String key;
}
