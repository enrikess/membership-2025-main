package com.promotick.lafabril.dao.facturacion;

import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.UtilEnum;

import java.util.Date;
import java.util.List;

public interface PeriodoParticipanteDao {
    MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta);

    MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta, Date fecha);

    List<MetaParticipante> obtenerMetaParticipanteNuevo(Integer idParticipante, Integer mes);
}
