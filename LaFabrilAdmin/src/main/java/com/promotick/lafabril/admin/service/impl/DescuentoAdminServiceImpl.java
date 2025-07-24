package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CampaniaAdminService;
import com.promotick.lafabril.admin.service.DescuentoAdminService;
import com.promotick.lafabril.dao.web.CampaniaDao;
import com.promotick.lafabril.dao.web.DescuentoDao;
import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Descuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DescuentoAdminServiceImpl implements DescuentoAdminService {

    private DescuentoDao descuentoDao;

    @Autowired
    public DescuentoAdminServiceImpl(DescuentoDao descuentoDao) {
        this.descuentoDao = descuentoDao;
    }

    @Override
    public List<Descuento> listarDescuentos() {
        return descuentoDao.listarDescuentos();
    }

    @Override
    public List<Descuento> listarDescuentos(Integer idDescuento, Integer orden) {
        return descuentoDao.listarDescuentos(idDescuento, orden);
    }

    @Override
    @Transactional
    public Integer actualizarDescuento(Descuento descuento) {
        return descuentoDao.actualizarDescuento(descuento);
    }

    @Override
    @Transactional
    public Integer actualizarEstadoDescuento(Integer idDescuento, Integer estado) {
        return descuentoDao.actualizarEstadoDescuento(idDescuento, estado);
    }


}
