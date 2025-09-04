package com.promotick.membership.web.service.impl;

import com.promotick.membership.dao.web.IntervaloMisionesDao;
import com.promotick.membership.model.web.IntervaloMisiones;
import com.promotick.membership.web.service.IntervaloMisionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntervaloMisionesServiceImpl implements IntervaloMisionesService {

    @Autowired
    private IntervaloMisionesDao intervaloMisionesDao;

    @Override
    public IntervaloMisiones obtenerIntervaloMisiones() {
        return intervaloMisionesDao.obtenerIntervaloMisiones();
    }
}