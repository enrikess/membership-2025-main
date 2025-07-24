package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.UbigeoDao;
import com.promotick.lafabril.model.web.Ubigeo;
import com.promotick.lafabril.web.service.UbigeoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbigeoWebServiceImpl implements UbigeoWebService {

    private UbigeoDao ubigeoDao;

    @Autowired
    public UbigeoWebServiceImpl(UbigeoDao ubigeoDao) {
        this.ubigeoDao = ubigeoDao;
    }

    @Override
    public List<Ubigeo> listarDepartamentos(String codPais) {
        return ubigeoDao.listarDepartamentos(codPais);
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
    public Ubigeo obtenerUbigeoID(Integer idUbigeo) {
        return ubigeoDao.obtenerUbigeoID(idUbigeo);
    }
}
