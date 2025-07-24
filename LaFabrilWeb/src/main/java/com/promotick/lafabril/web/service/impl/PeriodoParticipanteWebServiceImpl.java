package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.facturacion.PeriodoParticipanteDao;
import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.web.service.PeriodoParticipanteWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PeriodoParticipanteWebServiceImpl implements PeriodoParticipanteWebService {

    private PeriodoParticipanteDao periodoParticipanteDao;

    @Autowired
    public PeriodoParticipanteWebServiceImpl(PeriodoParticipanteDao periodoParticipanteDao) {
        this.periodoParticipanteDao = periodoParticipanteDao;
    }

    @Override
    public MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta) {
        return periodoParticipanteDao.obtenerMetaParticipante(idParticipante, tipoMeta);
    }

    @Override
    public List<MetaParticipante> obtenerMetaParticipanteNuevo(Integer idParticipante, Integer mes) {
        return periodoParticipanteDao.obtenerMetaParticipanteNuevo(idParticipante, mes);
    }
}
