package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.SubtipoParticipanteAdminService;
import com.promotick.lafabril.dao.web.SubtipoParticipanteDao;
import com.promotick.lafabril.model.web.SubtipoParticipante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtipoParticipanteAdminServiceImpl implements SubtipoParticipanteAdminService {

    private SubtipoParticipanteDao subtipoParticipanteDao;

    @Autowired
    public SubtipoParticipanteAdminServiceImpl(SubtipoParticipanteDao subtipoParticipanteDao) {
        this.subtipoParticipanteDao = subtipoParticipanteDao;
    }

    @Override
    public List<SubtipoParticipante> listarSubtiposParticipante() {
        return subtipoParticipanteDao.listarSubtiposParticipante();
    }
}
