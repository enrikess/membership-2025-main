package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.web.ConfiguracionDao;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.FiltroAcelerador;
import com.promotick.lafabril.model.util.FiltroBanner;
import com.promotick.lafabril.model.web.Acelerador;
import com.promotick.lafabril.model.web.RazonSocial;
import com.promotick.lafabril.model.web.Region;
import com.promotick.lafabril.model.web.TipoDocumento;
import com.promotick.lafabril.web.service.ConfiguracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfiguracionServiceImpl implements ConfiguracionService {

    private ConfiguracionDao configuracionDao;

    @Autowired
    public ConfiguracionServiceImpl(ConfiguracionDao configuracionDao) {
        this.configuracionDao = configuracionDao;
    }

    @Override
    public ConfiguracionWeb obtenerConfiguracionWeb() {
        return configuracionDao.obtenerConfiguracionWeb();
    }

    @Override
    public List<TipoDocumento> listarTipoDocumentos() {
        return configuracionDao.listarTipoDocumentos();
    }

    @Override
    public List<RazonSocial> listarRazonSocial() {
        return configuracionDao.obtenerRazonesSociales(ConstantesCommon.ZERO_VALUE);
    }

    @Override
    public Integer bannerFiltroContar(FiltroBanner filtroBanner) {
        return configuracionDao.bannerFiltroContar(filtroBanner);
    }

    @Override
    public List<ConfiguracionBanner> bannerFiltroListar(FiltroBanner filtroBanner) {
        return configuracionDao.bannerFiltroListar(filtroBanner);
    }

    @Override
    @Transactional
    public Integer registroBanner(ConfiguracionBanner configuracionBanner) {
        return configuracionDao.registroBanner(configuracionBanner);
    }

    @Override
    public ConfiguracionBanner obtenerBanner(Integer idConfiguracionBanner) {
        return configuracionDao.obtenerBanner(idConfiguracionBanner);
    }

    @Override
    @Transactional
    public Integer actualizarDatosVencimiento(ConfiguracionWeb configuracionWeb) {
        return configuracionDao.actualizarDatosVencimiento(configuracionWeb);
    }

    @Override
    public List<Region> obtenerRegiones() {
        return configuracionDao.obtenerRegiones();
    }

    @Override
    public List<Acelerador> aceleradorListar(FiltroAcelerador filtroAcelerador) {
        return configuracionDao.aceleradorListar(filtroAcelerador);
    }

    @Override
    @Transactional
    public Integer registroAcelerador(FiltroAcelerador filtroAcelerador) {
        return configuracionDao.registroAcelerador(filtroAcelerador);
    }
}
