package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Catalogo;

import java.util.List;

public interface CampaniaAdminService {

    List<Campania> listarCampanias();

    List<Campania> listarCampanias(Integer idCampania, Integer orden);

    Integer actualizarCampania(Campania campania);

    Integer actualizarEstadoCampania(Integer idCampania, Integer estado);

}
