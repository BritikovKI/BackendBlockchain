package com.backapi.backend.model.dto;


import lombok.Data;

@Data
public class UserVoteDTO {
    private Integer id;
    private Integer user_id;
    private Integer vote_id;
    private Boolean voted;

}
