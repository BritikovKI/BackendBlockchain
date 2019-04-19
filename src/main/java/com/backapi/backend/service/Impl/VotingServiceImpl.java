package com.backapi.backend.service.Impl;

import com.backapi.backend.dao.VotingDAO;
import com.backapi.backend.model.dto.VotingDTO;
import com.backapi.backend.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingServiceImpl implements VotingService {

    private final VotingDAO votingDAO;

    @Autowired
    public VotingServiceImpl(VotingDAO votingDAO) {
        this.votingDAO = votingDAO;
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
    public VotingDTO get(Integer id) {
        return votingDAO.get(id);
    }

    @Override
    public List<VotingDTO> getVotesByUser(Integer userId) {
        return votingDAO.getVotesByUser(userId);
    }

    @Override
    public List<VotingDTO> getAll() {
        return votingDAO.getAll();
    }
}
