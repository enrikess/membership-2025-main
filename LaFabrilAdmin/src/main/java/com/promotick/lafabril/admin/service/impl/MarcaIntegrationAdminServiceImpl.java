package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.MarcaIntegrationAdminService;
import com.promotick.lafabril.dao.admin.MarcaIntegrationDao;
import com.promotick.lafabril.model.web.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarcaIntegrationAdminServiceImpl implements MarcaIntegrationAdminService {

    private MarcaIntegrationDao marcaIntegrationDao;

    @Autowired
    public MarcaIntegrationAdminServiceImpl(MarcaIntegrationDao marcaIntegrationDao) {
        this.marcaIntegrationDao = marcaIntegrationDao;
    }

    @Override
    @Transactional
    public Integer actualizarMarca(Marca marca) {
        return marcaIntegrationDao.actualizarMarca(marca);
    }
}
