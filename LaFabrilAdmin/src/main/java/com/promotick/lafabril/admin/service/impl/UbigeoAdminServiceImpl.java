package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.UbigeoAdminService;
import com.promotick.lafabril.dao.web.UbigeoDao;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Ubigeo;
import com.promotick.lafabril.model.web.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbigeoAdminServiceImpl implements UbigeoAdminService {

    private UbigeoDao ubigeoDao;

    @Autowired
    public UbigeoAdminServiceImpl(UbigeoDao ubigeoDao) {
        this.ubigeoDao = ubigeoDao;
    }

    @Override
    public Ubigeo obtenerUbigeoID(Integer idUbigeo) {
        return ubigeoDao.obtenerUbigeoID(idUbigeo);
    }

    @Override
    public List<Ubigeo> listarProvincias(String codPais, String codDepartamento) {
        return ubigeoDao.listarProvincias(codPais, codDepartamento);
    }

    @Override
    public List<Ubigeo> listarDistritos(String codPais, String codDepartamento, String codProvincia) {
        return ubigeoDao.listarDistritos(codPais, codDepartamento, codProvincia);
    }

    @Override
    public List<Ubigeo> listarUbigeos(String codpais) {
        return ubigeoDao.listarUbigeos(codpais);
    }

    @Override
    public List<Zona> listarZonas() {
        return ubigeoDao.listarZonas();
    }

    @Override
    public List<TipoVivienda> listarTipoVivienda() {
        return ubigeoDao.listarTipoVivienda();
    }
}
