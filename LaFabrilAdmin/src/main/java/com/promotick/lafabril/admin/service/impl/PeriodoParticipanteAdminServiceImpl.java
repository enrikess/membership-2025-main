package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.PeriodoParticipanteAdminService;
import com.promotick.lafabril.dao.facturacion.PeriodoParticipanteDao;
import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.UtilEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PeriodoParticipanteAdminServiceImpl implements PeriodoParticipanteAdminService {

    private PeriodoParticipanteDao periodoParticipanteDao;

    @Autowired
    public PeriodoParticipanteAdminServiceImpl(PeriodoParticipanteDao periodoParticipanteDao) {
        this.periodoParticipanteDao = periodoParticipanteDao;
    }

    @Override
    public MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta) {
        return periodoParticipanteDao.obtenerMetaParticipante(idParticipante, tipoMeta);
    }

    @Override
    public MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta, Date fecha) {
        return periodoParticipanteDao.obtenerMetaParticipante(idParticipante, tipoMeta, fecha);
    }
}
