package com.promotick.lafabril.dao.facturacion;

import com.promotick.lafabril.model.facturacion.Factura;
import com.promotick.lafabril.model.facturacion.ParticipanteAvance;
import com.promotick.lafabril.model.util.*;

import java.util.List;

public interface FacturaDao {
    List<FacturaAgrupado> listarFacturacionAgrupada(Integer idParticipante, Integer anio);

    MetaFacturacion obtenerMetaFacturacionParticipante(Integer idParticipante, Integer anio, Integer trimestre);

    List<Factura> listarFacturasParticipante(FiltroFacturaParticipante filtroFacturaParticipante);

    Integer contarFacturasParticipante(FiltroFacturaParticipante filtroFacturaParticipante);

    List<ParticipanteAvance> reporteVentasListar(FiltroReporteVentas filtroReporteVentas);

    Integer reporteVentasContar(FiltroReporteVentas filtroReporteVentas);

    List<Factura> reportePuntosAcreditadosListar(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados);

    Integer reportePuntosAcreditadosContar(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados);

    Integer facturaEnvioEmail(Integer idParticipante, Boolean emailEnviado, String emailObservacion);

    Integer cargaManualEnvioEmail(Integer idParticipante, Boolean emailEnviado, String emailObservacion);
    Integer reporteTycActualizar(FiltroTyc filtroTyc);
}
