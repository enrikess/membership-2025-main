package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.FacturaAdminService;
import com.promotick.lafabril.dao.facturacion.FacturaDao;
import com.promotick.lafabril.model.facturacion.Factura;
import com.promotick.lafabril.model.facturacion.ParticipanteAvance;
import com.promotick.lafabril.model.util.FiltroReportePuntosAcreditados;
import com.promotick.lafabril.model.util.FiltroReporteVentas;
import com.promotick.lafabril.model.util.FiltroTyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacturaAdminServiceImpl implements FacturaAdminService {

    private FacturaDao facturaDao;

    @Autowired
    public FacturaAdminServiceImpl(FacturaDao facturaDao) {
        this.facturaDao = facturaDao;
    }

    @Override
    public List<ParticipanteAvance> reporteVentasListar(FiltroReporteVentas filtroReporteVentas) {
        return facturaDao.reporteVentasListar(filtroReporteVentas);
    }

    @Override
    public Integer reporteVentasContar(FiltroReporteVentas filtroReporteVentas) {
        return facturaDao.reporteVentasContar(filtroReporteVentas);
    }

    @Override
    public List<Factura> reportePuntosAcreditadosListar(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados) {
        return facturaDao.reportePuntosAcreditadosListar(filtroReportePuntosAcreditados);
    }

    @Override
    public Integer reportePuntosAcreditadosContar(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados) {
        return facturaDao.reportePuntosAcreditadosContar(filtroReportePuntosAcreditados);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer facturaEnvioEmail(Integer idParticipante, Boolean emailEnviado, String emailObservacion) {
        return facturaDao.facturaEnvioEmail(idParticipante, emailEnviado, emailObservacion);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer cargaManualEnvioEmail(Integer idParticipante, Boolean emailEnviado, String emailObservacion) {
        return facturaDao.cargaManualEnvioEmail(idParticipante, emailEnviado, emailObservacion);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer reporteTycActualizar(FiltroTyc filtroTyc) {
        return facturaDao.reporteTycActualizar(filtroTyc);
    }
}
