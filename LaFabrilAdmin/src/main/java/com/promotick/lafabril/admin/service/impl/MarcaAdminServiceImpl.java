package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.MarcaAdminService;
import com.promotick.lafabril.dao.web.MarcaDao;
import com.promotick.lafabril.model.reporte.ReporteMarca;
import com.promotick.lafabril.model.util.FiltroMarca;
import com.promotick.lafabril.model.web.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaAdminServiceImpl implements MarcaAdminService {

    private MarcaDao marcaDao;

    @Autowired
    public MarcaAdminServiceImpl(MarcaDao marcaDao) {
        this.marcaDao = marcaDao;
    }

    @Override
    public List<Marca> listarMarcas(FiltroMarca filtroMarca) {
        return marcaDao.listarMarcas(filtroMarca);
    }

    @Override
    @Transactional
    public Integer actualizarMarca(Marca marca) {
        return marcaDao.actualizarMarca(marca);
    }

    @Override
    @Transactional
    public Integer registroMarca(Marca marca) {
        return marcaDao.registroMarca(marca);
    }

    @Override
    public Integer contarMarcas() {
        return marcaDao.contarMarcas();
    }

    @Override
    public List<Marca> listarMarcasFiltro(FiltroMarca filtroMarca) {
        return marcaDao.listarMarcasFiltro(filtroMarca);
    }

    @Override
    public Integer contarMarcasFiltro(FiltroMarca filtroMarca) {
        return marcaDao.contarMarcasFiltro(filtroMarca);
    }

    @Override
    public List<ReporteMarca> reporteMarcas(FiltroMarca filtroMarca) {
        return marcaDao.reporteMarcas(filtroMarca);
    }

}
