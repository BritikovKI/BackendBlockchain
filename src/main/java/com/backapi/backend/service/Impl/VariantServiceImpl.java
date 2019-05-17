package com.backapi.backend.service.Impl;

import com.backapi.backend.dao.UserDAO;
import com.backapi.backend.dao.VariantDAO;
import com.backapi.backend.model.dto.UserDTO;
import com.backapi.backend.model.dto.VariantDTO;
import com.backapi.backend.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VariantServiceImpl implements VariantService {

    private final VariantDAO variantDAO;
    private final UserDAO userDAO;

    @Autowired
    public VariantServiceImpl(VariantDAO variantDAO, UserDAO userDAO) {
        this.variantDAO = variantDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void create(VariantDTO variantDTO) {
        variantDAO.create(variantDTO);
    }

    @Override
    public void change(VariantDTO variantDTO) {
        variantDAO.change(variantDTO);
    }

    @Override
    public void delete(VariantDTO variantDTO) {
        variantDAO.delete(variantDTO);
    }

    @Override
    public VariantDTO get(Integer id) {
        return variantDAO.get(id);
    }

    @Override
    public List<VariantDTO> getVariantsByVote(Integer voteId) {
        return variantDAO.getVariantsByVote(voteId);
    }


}
