package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CategoriaIntegrationAdminService;
import com.promotick.lafabril.dao.admin.CategoriaIntegrationDao;
import com.promotick.lafabril.model.web.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaIntegrationAdminServiceImpl implements CategoriaIntegrationAdminService {

    private CategoriaIntegrationDao categoriaIntegrationDao;

    @Autowired
    public CategoriaIntegrationAdminServiceImpl(CategoriaIntegrationDao categoriaIntegrationDao) {
        this.categoriaIntegrationDao = categoriaIntegrationDao;
    }

    @Override
    @Transactional
    public Integer actualizarCategoria(Categoria categoria) {
        return categoriaIntegrationDao.actualizarCategoria(categoria);
}
}
