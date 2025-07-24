package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.MensajeDao;
import com.promotick.lafabril.model.web.Mensaje;
import com.promotick.lafabril.web.service.MensajeWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeWebServiceImpl implements MensajeWebService {

    private MensajeDao mensajeDao;

    @Autowired
    public MensajeWebServiceImpl(MensajeDao mensajeDao) {
        this.mensajeDao = mensajeDao;
    }

    @Override
    public Mensaje obtenerMensajeXTipo(Integer tipo) {
        return mensajeDao.obtenerMensajeXTipo(tipo);
    }
}
