package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.ProcesoAdminService;
import com.promotick.lafabril.dao.facturacion.ProcesoDao;
import com.promotick.lafabril.model.facturacion.Proceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcesoAdminServiceImpl implements ProcesoAdminService {

    private ProcesoDao procesoDao;

    @Autowired
    public ProcesoAdminServiceImpl(ProcesoDao procesoDao) {
        this.procesoDao = procesoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer registrarProceso(String tipoProceso) {
        return procesoDao.registrarProceso(tipoProceso);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer finalizarProceso(Proceso proceso) {
        return procesoDao.finalizarProceso(proceso);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Boolean procesoEnEjecucion() {
        return procesoDao.procesoEnEjecucion();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer procesoVencimientoPuntos() {
        return procesoDao.procesoVencimientoPuntos();
    }

    @Override
    @Transactional
    public void initRestaurarProductos() {
        procesoDao.initRestaurarProductos();
    }
}
