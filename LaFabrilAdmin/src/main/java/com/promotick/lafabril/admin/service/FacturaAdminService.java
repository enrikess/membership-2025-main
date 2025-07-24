package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.facturacion.Factura;
import com.promotick.lafabril.model.facturacion.ParticipanteAvance;
import com.promotick.lafabril.model.util.FiltroReportePuntosAcreditados;
import com.promotick.lafabril.model.util.FiltroReporteVentas;
import com.promotick.lafabril.model.util.FiltroTyc;

import java.util.List;

public interface FacturaAdminService {
    List<ParticipanteAvance> reporteVentasListar(FiltroReporteVentas filtroReporteVentas);

    Integer reporteVentasContar(FiltroReporteVentas filtroReporteVentas);

    List<Factura> reportePuntosAcreditadosListar(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados);

    Integer reportePuntosAcreditadosContar(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados);

    Integer facturaEnvioEmail(Integer idParticipante, Boolean emailEnviado, String emailObservacion);

    Integer cargaManualEnvioEmail(Integer idParticipante, Boolean emailEnviado, String emailObservacion);
    Integer reporteTycActualizar(FiltroTyc filtroTyc);
}
