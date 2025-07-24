package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.facturacion.FacturaDao;
import com.promotick.lafabril.model.facturacion.Factura;
import com.promotick.lafabril.model.util.FacturaAgrupado;
import com.promotick.lafabril.model.util.FiltroFacturaParticipante;
import com.promotick.lafabril.model.util.MetaFacturacion;
import com.promotick.lafabril.web.service.FacturaWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaWebServiceImpl implements FacturaWebService {

    private FacturaDao facturaDao;

    @Autowired
    public FacturaWebServiceImpl(FacturaDao facturaDao) {
        this.facturaDao = facturaDao;
    }

    @Override
    public List<FacturaAgrupado> listarFacturacionAgrupada(Integer idParticipante, Integer anio) {
        return facturaDao.listarFacturacionAgrupada(idParticipante, anio);
    }

    @Override
    public MetaFacturacion obtenerMetaFacturacionParticipante(Integer idParticipante, Integer anio, Integer trimestre) {
        return facturaDao.obtenerMetaFacturacionParticipante(idParticipante, anio, trimestre);
    }

    @Override
    public List<Factura> listarFacturasParticipante(FiltroFacturaParticipante filtroFacturaParticipante) {
        return facturaDao.listarFacturasParticipante(filtroFacturaParticipante);
    }

    @Override
    public Integer contarFacturasParticipante(FiltroFacturaParticipante filtroFacturaParticipante) {
        return facturaDao.contarFacturasParticipante(filtroFacturaParticipante);
    }
}
