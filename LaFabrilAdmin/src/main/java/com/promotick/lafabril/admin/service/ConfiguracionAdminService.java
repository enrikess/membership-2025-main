package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.FiltroAcelerador;
import com.promotick.lafabril.model.util.FiltroBanner;
import com.promotick.lafabril.model.web.Acelerador;
import com.promotick.lafabril.model.web.RazonSocial;
import com.promotick.lafabril.model.web.Region;
import com.promotick.lafabril.model.web.TipoDocumento;

import java.util.List;

public interface ConfiguracionAdminService {
    ConfiguracionWeb obtenerConfiguracionWeb();

    List<TipoDocumento> listarTipoDocumentos();

    List<RazonSocial> listarRazonSocial();

    Integer bannerFiltroContar(FiltroBanner filtroBanner);

    List<ConfiguracionBanner> bannerFiltroListar(FiltroBanner filtroBanner);

    Integer registroBanner(ConfiguracionBanner configuracionBanner);

    ConfiguracionBanner obtenerBanner(Integer idConfiguracionBanner);

    Integer actualizarDatosVencimiento(ConfiguracionWeb configuracionWeb);

    List<Region> obtenerRegiones();

    List<Acelerador> aceleradorListar(FiltroAcelerador filtroAcelerador);

    Integer registroAcelerador(FiltroAcelerador filtroAcelerador);
}
