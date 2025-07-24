package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.UtilEnum;

import java.util.List;

public interface PeriodoParticipanteWebService {
    MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta);

    List<MetaParticipante> obtenerMetaParticipanteNuevo(Integer idParticipante, Integer mes);
}
