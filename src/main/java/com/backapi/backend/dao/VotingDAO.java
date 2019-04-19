package com.backapi.backend.dao;

import com.backapi.backend.model.dto.VotingDTO;

import java.util.List;

public interface VotingDAO {

    void create(VotingDTO votingDTO);
    void change(VotingDTO votingDTO);
    void delete(VotingDTO votingDTO);
    VotingDTO get(Integer id);
    List<VotingDTO> getVotesByUser(Integer userId);
    List<VotingDTO> getAll();
}
