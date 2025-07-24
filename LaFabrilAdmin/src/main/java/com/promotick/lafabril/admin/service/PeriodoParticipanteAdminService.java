package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.UtilEnum;

import java.util.Date;

public interface PeriodoParticipanteAdminService {
    MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta);

    MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta, Date fecha);
}
