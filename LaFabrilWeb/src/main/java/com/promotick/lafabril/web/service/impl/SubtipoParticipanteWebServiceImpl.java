package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.SubtipoParticipanteDao;
import com.promotick.lafabril.model.web.SubtipoParticipante;
import com.promotick.lafabril.web.service.SubtipoParticipanteWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtipoParticipanteWebServiceImpl implements SubtipoParticipanteWebService {

    private SubtipoParticipanteDao subtipoParticipanteDao;

    @Autowired
    public SubtipoParticipanteWebServiceImpl(SubtipoParticipanteDao subtipoParticipanteDao) {
        this.subtipoParticipanteDao = subtipoParticipanteDao;
    }

    @Override
    public List<SubtipoParticipante> listarSubtiposParticipante() {
        return subtipoParticipanteDao.listarSubtiposParticipante();
    }
}
