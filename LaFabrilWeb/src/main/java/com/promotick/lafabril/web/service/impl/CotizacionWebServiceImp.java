package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.CotizacionDao;
import com.promotick.lafabril.model.web.Cotizacion;
import com.promotick.lafabril.model.web.Pasajero;
import com.promotick.lafabril.web.service.CotizacionWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CotizacionWebServiceImp implements CotizacionWebService {

    private CotizacionDao cotizacionDao;

    @Autowired
    public CotizacionWebServiceImp(CotizacionDao cotizacionDao) {
        this.cotizacionDao = cotizacionDao;
    }

    @Override
    @Transactional
    public Integer registrarCotizacion(Cotizacion cotizacion) {
        Integer cotizacionId = cotizacionDao.registrarCotizacion(cotizacion);
        if (cotizacionId!=null && cotizacionId>0) {
            for (Pasajero pasajero : cotizacion.getListaPasajeros()) {
                cotizacionDao.registrarPasajeros(pasajero, cotizacionId);
            }
        }
        return cotizacionId;
//        return cotizacionDao.registrarCotizacion(cotizacion);
    }
}
