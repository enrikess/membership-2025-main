package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.DireccionAdminService;
import com.promotick.lafabril.dao.web.DireccionDao;
import com.promotick.lafabril.model.web.Direccion;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Zona;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DireccionAdminServiceImpl implements DireccionAdminService {

    private DireccionDao direccionDao;

    public DireccionAdminServiceImpl(DireccionDao direccionDao) {
        this.direccionDao = direccionDao;
    }

    @Override
    public List<Zona> listarZonas() {
        return direccionDao.listarZonas();
    }

    @Override
    public List<TipoVivienda> listarTipoViviendas() {
        return direccionDao.listarTipoViviendas();
    }

    @Override
    @Transactional
    public Integer registroDireccionCarga(Direccion direccion) {
        return direccionDao.registroDireccionCarga(direccion);
    }
}
