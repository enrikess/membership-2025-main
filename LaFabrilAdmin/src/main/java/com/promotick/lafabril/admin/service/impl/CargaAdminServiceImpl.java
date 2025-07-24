package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CargaAdminService;
import com.promotick.lafabril.dao.facturacion.CargaDao;
import com.promotick.lafabril.model.facturacion.Carga;
import com.promotick.lafabril.model.web.CargaWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CargaAdminServiceImpl implements CargaAdminService {

    private CargaDao cargaDao;

    @Autowired
    public CargaAdminServiceImpl(CargaDao cargaDao) {
        this.cargaDao = cargaDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer registroCarga(Carga carga) {
        return cargaDao.registroCarga(carga);
    }

    @Override
    public boolean existenciaCarga(String nombreArchivo) {
        return cargaDao.existenciaCarga(nombreArchivo);
    }

    @Override
    @Transactional
    public Integer registroCargaWeb(CargaWeb cargaWeb) {
        return cargaDao.registroCargaWeb(cargaWeb);
    }

    @Override
    @Transactional
    public Integer actualizarCargaWeb(CargaWeb cargaWeb) {
        return cargaDao.actualizarCargaWeb(cargaWeb);
    }
}
