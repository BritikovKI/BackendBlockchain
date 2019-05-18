package com.backapi.backend.service.Impl;

import com.backapi.backend.dao.UserDAO;
import com.backapi.backend.dao.VotingDAO;
import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.model.dto.UserVoteDTO;
import com.backapi.backend.model.dto.VotingDTO;
import com.backapi.backend.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingServiceImpl implements VotingService {

    private final VotingDAO votingDAO;
    private final UserDAO userDAO;

    @Autowired
    public VotingServiceImpl(VotingDAO votingDAO, UserDAO userDAO) {
        this.votingDAO = votingDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void create(VotingDTO votingDTO) {
        votingDAO.create(votingDTO);
    }

    @Override
    public void change(VotingDTO votingDTO) {
        votingDAO.change(votingDTO);
    }

    @Override
    public void delete(VotingDTO votingDTO) {
        votingDAO.delete(votingDTO);
    }

    @Override
    public VotingDTO get(UserDTO user, Integer id) {
        return votingDAO.get(user, id);
    }

    @Override
    public List<VotingDTO> getVotesByUser(Integer userId) {
        return votingDAO.getVotesByUser(userId);
    }

    @Override
    public List<VotingDTO> getAll(Integer userId) {
        return votingDAO.getAll(userId);
    }

    @Override
    public void addUser(UserVoteDTO userDTO, String user) {
        votingDAO.addUser(userDTO, user);
    }

    @Override
    public void vote(Integer id, UserDTO user) {
        votingDAO.vote(id, user);
    }

    @Override
    public List<UserDTO> getVoters(Integer id) {
        return userDAO.getUserByVote(id);
    }
}
