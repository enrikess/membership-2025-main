package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.TipoParticipanteAdminService;
import com.promotick.lafabril.dao.web.TipoParticipanteDao;
import com.promotick.lafabril.model.web.TipoParticipante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoParticipanteAdminServiceImpl implements TipoParticipanteAdminService {

    private TipoParticipanteDao tipoParticipanteDao;

    @Autowired
    public TipoParticipanteAdminServiceImpl(TipoParticipanteDao tipoParticipanteDao) {
        this.tipoParticipanteDao = tipoParticipanteDao;
    }

    @Override
    public List<TipoParticipante> listarTipoParticipantes() {
        return tipoParticipanteDao.listarTipoParticipantes();
    }
}
