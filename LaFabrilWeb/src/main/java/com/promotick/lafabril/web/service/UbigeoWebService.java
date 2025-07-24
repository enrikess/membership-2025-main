package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Ubigeo;

import java.util.List;

public interface UbigeoWebService {
    List<Ubigeo> listarDepartamentos(String codPais);

    List<Ubigeo> listarProvincias(String codPais, String codDepartamento);

    List<Ubigeo> listarDistritos(String codPais, String codDepartamento, String codProvincia);

    Ubigeo obtenerUbigeoID(Integer idUbigeo);
}
