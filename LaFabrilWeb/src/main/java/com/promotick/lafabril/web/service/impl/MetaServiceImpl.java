package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.MetaDao;
import com.promotick.lafabril.model.web.AccionSellout;
import com.promotick.lafabril.model.web.MetaAvanceTrimestral;
import com.promotick.lafabril.web.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MetaServiceImpl implements MetaService {

    @Autowired
    MetaDao metaDao;

    @Override
    public List<MetaAvanceTrimestral> obtenerMetaAvanceParticipanteTrimestral(Integer idParticipante, Integer anio, Integer trimestre) {
        return metaDao.obtenerMetaAvanceParticipanteTrimestral(idParticipante, anio, trimestre);
    }

    @Override
    public List<AccionSellout> obtenerAccionesSelloutParticipante(Integer idParticipante, Integer anio, Integer trimestre) {
        return metaDao.obtenerAccionesSelloutParticipante(idParticipante, anio, trimestre);
    }
}
