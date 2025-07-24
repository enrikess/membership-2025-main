package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.dao.web.CatalogoDao;
import com.promotick.lafabril.model.web.Catalogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogoAdminServiceImpl implements CatalogoAdminService {

    private CatalogoDao catalogoDao;

    @Autowired
    public CatalogoAdminServiceImpl(CatalogoDao catalogoDao) {
        this.catalogoDao = catalogoDao;
    }

    @Override
    public List<Catalogo> listarCatalogos() {
        return catalogoDao.listarCatalogos();
    }

    @Override
    public List<Catalogo> listarCatalogos(Integer idCatalogo, Integer orden) {
        return catalogoDao.listarCatalogos(idCatalogo, orden);
    }

    @Override
    @Transactional
    public Integer actualizarCatalogo(Catalogo catalogo) {
        return catalogoDao.actualizarCatalogo(catalogo);
    }

    @Override
    @Transactional
    public Integer actualizarEstadoCatalogo(Integer idCatalogo, Integer estado, Integer tipo) {
        return catalogoDao.actualizarEstadoCatalogo(idCatalogo, estado, tipo);
    }

    @Override
    public List<Catalogo> listarCatalogoMultiselectSubcategoria(Integer idSubcategoria, Integer tipo) {
        return catalogoDao.listarCatalogoMultiselectSubcategoria(idSubcategoria, tipo);
    }

    @Override
    @Transactional
    public Integer actualizarPopupCatalogo(Catalogo catalogo) {
        return catalogoDao.actualizarPopupCatalogo(catalogo);
    }

    @Override
    @Transactional
    public Integer actualizarBannerFlotanteCatalogo(Catalogo catalogo) {
        return catalogoDao.actualizarBannerFlotanteCatalogo(catalogo);
    }
}
