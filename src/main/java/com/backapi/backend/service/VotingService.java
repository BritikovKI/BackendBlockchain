package com.backapi.backend.service;

import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.model.dto.UserVoteDTO;
import com.backapi.backend.model.dto.VotingDTO;

import java.util.List;

public interface VotingService {
    void create(VotingDTO votingDTO);
    void change(VotingDTO votingDTO);
    void delete(VotingDTO votingDTO);
    VotingDTO get(UserDTO user,Integer id);
    List<VotingDTO> getVotesByUser(Integer userId);
    List<VotingDTO> getAll(Integer userId);
    List<UserDTO> getVoters(Integer id);
    void addUser(UserVoteDTO userDTO, String user);

    void vote(Integer id, UserDTO user);
}
