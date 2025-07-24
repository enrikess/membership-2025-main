package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.CategoriaDao;
import com.promotick.lafabril.model.web.Categoria;
import com.promotick.lafabril.web.service.CategoriaWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaWebServiceImpl implements CategoriaWebService {

    private CategoriaDao categoriaDao;

    @Autowired
    public CategoriaWebServiceImpl(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
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
    public Categoria obtenerCategoria(Integer idCatalogo, Integer idCategoria) {
        return categoriaDao.obtenerCategoria(idCatalogo, idCategoria);
    }
}
