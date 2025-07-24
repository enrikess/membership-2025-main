package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.MensajeAdminService;
import com.promotick.lafabril.dao.web.MensajeDao;
import com.promotick.lafabril.model.web.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeAdminServiceImpl implements MensajeAdminService {

    private MensajeDao mensajeDao;

    @Autowired
    public MensajeAdminServiceImpl(MensajeDao mensajeDao) {
        this.mensajeDao = mensajeDao;
    }

    @Override
    public Mensaje obtenerMensajeXTipo(Integer tipo) {
        return mensajeDao.obtenerMensajeXTipo(tipo);
    }
}
