package com.promotick.lafabril.dao.facturacion;

import com.promotick.lafabril.model.facturacion.PeriodoParticipante;

import java.util.List;

public interface PeriodoDao {
    Integer validacionPeriodo();

    List<PeriodoParticipante> cierrePeriodo();

    List<PeriodoParticipante> recalculoMetas();
}
