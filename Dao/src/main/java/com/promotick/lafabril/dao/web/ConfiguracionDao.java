package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.FiltroAcelerador;
import com.promotick.lafabril.model.util.FiltroBanner;
import com.promotick.lafabril.model.web.Acelerador;
import com.promotick.lafabril.model.web.RazonSocial;
import com.promotick.lafabril.model.web.Region;
import com.promotick.lafabril.model.web.TipoDocumento;

import java.util.List;

public interface ConfiguracionDao {
    List<ConfiguracionBanner> listarBanner(Integer idConfiguracionBanner, Integer idCatalogo, Integer estadoConfigBanner, Integer tipoBanner);

    List<TipoDocumento> listarTipoDocumentos();

    ConfiguracionWeb obtenerConfiguracionWeb();

    Integer bannerFiltroContar(FiltroBanner filtroBanner);

    List<ConfiguracionBanner> bannerFiltroListar(FiltroBanner filtroBanner);

    Integer registroBanner(ConfiguracionBanner configuracionBanner);

    ConfiguracionBanner obtenerBanner(Integer idConfiguracionBanner);

    List<RazonSocial> obtenerRazonesSociales(Integer idCatalogo);

    List<Region> obtenerRegiones();

    Integer actualizarDatosVencimiento(ConfiguracionWeb configuracionWeb);

    List<Acelerador> aceleradorListar(FiltroAcelerador filtroAcelerador);

    Integer registroAcelerador(FiltroAcelerador filtroAcelerador);
}
