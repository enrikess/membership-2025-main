package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CargaProcesoAdminService;
import com.promotick.lafabril.dao.facturacion.CargaProcesoDao;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargaProcesoAdminServiceImpl implements CargaProcesoAdminService {

    private CargaProcesoDao cargaProcesoDao;

    @Autowired
    public CargaProcesoAdminServiceImpl(CargaProcesoDao cargaProcesoDao) {
        this.cargaProcesoDao = cargaProcesoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer registrarCargaProceso(CargaProceso cargaProceso) {
        return cargaProcesoDao.registrarCargaProceso(cargaProceso);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer actualizarCargaProceso(CargaProceso cargaProceso) {
        return cargaProcesoDao.actualizarCargaProceso(cargaProceso);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public List<FormBulkParticipantes> bulkParticipantesProcesar(CargaProceso cargaProceso) {
        return cargaProcesoDao.bulkParticipantesProcesar(cargaProceso);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public List<FormBulkFacturas> bulkFacturasProcesar(CargaProceso cargaProceso) {
        return cargaProcesoDao.bulkFacturasProcesar(cargaProceso);
    }
}
