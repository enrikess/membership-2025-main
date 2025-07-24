package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CampaniaIntegrationAdminService;
import com.promotick.lafabril.dao.admin.CampaniaIntegrationDao;
import com.promotick.lafabril.model.web.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CampaniaIntegrationAdminServiceImpl implements CampaniaIntegrationAdminService {

    private CampaniaIntegrationDao campaniaIntegrationDao;

    @Autowired
    public CampaniaIntegrationAdminServiceImpl(CampaniaIntegrationDao campaniaIntegrationDao){
        this.campaniaIntegrationDao = campaniaIntegrationDao;
    }

    @Override
    @Transactional
    public Integer actualizarCampania(Producto producto) {
        return campaniaIntegrationDao.actualizarCampania(producto);
    }
}
