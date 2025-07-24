package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.web.Direccion;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Zona;

import java.util.List;

public interface DireccionAdminService {
    List<Zona> listarZonas();

    List<TipoVivienda> listarTipoViviendas();

    Integer registroDireccionCarga(Direccion direccion);
}
