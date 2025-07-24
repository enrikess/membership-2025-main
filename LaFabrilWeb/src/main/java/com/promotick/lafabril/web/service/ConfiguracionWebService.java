package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.web.RazonSocial;
import com.promotick.lafabril.model.web.Region;
import com.promotick.lafabril.model.web.TipoDocumento;
import com.promotick.lafabril.model.web.TipoParticipante;

import java.util.List;

public interface ConfiguracionWebService {
    List<ConfiguracionBanner> listarBanner(Integer idConfiguracionBanner, Integer idCatalogo, Integer estadoConfigBanner, Integer tipoBanner);

    ConfiguracionWeb obtenerConfiguracionWeb();

    List<TipoDocumento> obtenerTipoDocumentos();

    List<RazonSocial> obtenerRazonesSociales(Integer idCatalogo);

    List<TipoParticipante> obtenerTipoParticipante();

    List<Region> obtenerRegiones();
}
