package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CategoriaAdminService;
import com.promotick.lafabril.dao.web.CategoriaDao;
import com.promotick.lafabril.model.reporte.ReporteCategoria;
import com.promotick.lafabril.model.util.FiltroCategoria;
import com.promotick.lafabril.model.web.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaAdminServiceImpl implements CategoriaAdminService {

    private CategoriaDao categoriaDao;

    @Autowired
    public CategoriaAdminServiceImpl(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    @Override
    public List<Categoria> obtenerCategoriasAdmin(FiltroCategoria filtroCategoria) {
        return categoriaDao.obtenerCategoriasAdmin(filtroCategoria);
    }

    @Override
    @Transactional
    public Integer registroCategoria(Categoria categoria) {
        return categoriaDao.registroCategoria(categoria);
    }

    @Override
    public List<Categoria> obtenerSubcategorias(Integer idCategoria) {
        return categoriaDao.obtenerSubcategorias(idCategoria);
    }

    @Override
    @Transactional
    public Integer actualizacionOrdenSubcategoria(String cadena) {
        return categoriaDao.actualizacionOrdenSubcategoria(cadena);
    }

    @Override
    @Transactional
    public Integer actualizacionSubcategoria(Categoria categoria) {
        return categoriaDao.actualizacionSubcategoria(categoria);
    }

    @Override
    public List<Categoria> listarSubcategoria(Integer idSubcategoria) {
        return categoriaDao.listarSubCategoria(idSubcategoria);
    }

    @Override
    public List<Categoria> obtenerCategoriasGeneral(Integer idCatalogo) {
        List<Categoria> categorias = categoriaDao.obtenerCategoriasGeneral(idCatalogo);
        for (Categoria categoria : categorias) {
            categoria.setCategoriasHijas(categoriaDao.listarCategoriasHijas(idCatalogo, categoria.getIdCategoria()));
        }
        return categorias;
    }

    @Override
    public List<ReporteCategoria> reporteCategorias(FiltroCategoria filtroCategoria) {
        return categoriaDao.reporteCategorias(filtroCategoria);
    }
}
