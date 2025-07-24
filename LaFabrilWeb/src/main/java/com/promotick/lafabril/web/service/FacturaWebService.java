package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.facturacion.Factura;
import com.promotick.lafabril.model.util.FacturaAgrupado;
import com.promotick.lafabril.model.util.FiltroFacturaParticipante;
import com.promotick.lafabril.model.util.MetaFacturacion;

import java.util.List;

public interface FacturaWebService {
    List<FacturaAgrupado> listarFacturacionAgrupada(Integer idParticipante, Integer anio);

    MetaFacturacion obtenerMetaFacturacionParticipante(Integer idParticipante, Integer anio, Integer trimestre);

    List<Factura> listarFacturasParticipante(FiltroFacturaParticipante filtroFacturaParticipante);

    Integer contarFacturasParticipante(FiltroFacturaParticipante filtroFacturaParticipante);
}
