package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.facturacion.PeriodoParticipante;

import java.util.List;

public interface PeriodoAdminService {
    Integer validacionPeriodo();

    List<PeriodoParticipante> cierrePeriodo();

    List<PeriodoParticipante> recalculoMetas();
}
