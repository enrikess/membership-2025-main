package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Ubigeo;
import com.promotick.lafabril.model.web.Zona;

import java.util.List;

public interface UbigeoAdminService {
    Ubigeo obtenerUbigeoID(Integer idUbigeo);

    List<Ubigeo> listarProvincias(String codPais, String codDepartamento);

    List<Ubigeo> listarDistritos(String codPais, String codDepartamento, String codProvincia);

    List<Ubigeo> listarUbigeos(String codpais);

    List<Zona> listarZonas();

    List<TipoVivienda> listarTipoVivienda();
}
