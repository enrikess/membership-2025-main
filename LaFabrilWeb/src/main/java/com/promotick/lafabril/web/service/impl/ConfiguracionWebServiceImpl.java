package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.ConfiguracionDao;
import com.promotick.lafabril.dao.web.TipoParticipanteDao;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.web.RazonSocial;
import com.promotick.lafabril.model.web.Region;
import com.promotick.lafabril.model.web.TipoDocumento;
import com.promotick.lafabril.model.web.TipoParticipante;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfiguracionWebServiceImpl implements ConfiguracionWebService {

    private ConfiguracionDao configuracionDao;

    private TipoParticipanteDao tipoParticipanteDao;

    @Autowired
    public ConfiguracionWebServiceImpl(ConfiguracionDao configuracionDao,TipoParticipanteDao tipoParticipanteDao) {
        this.configuracionDao = configuracionDao;
        this.tipoParticipanteDao = tipoParticipanteDao;
    }

    @Override
    public List<ConfiguracionBanner> listarBanner(Integer idConfiguracionBanner, Integer idCatalogo, Integer estadoConfigBanner, Integer tipoBanner) {
        return configuracionDao.listarBanner(idConfiguracionBanner, idCatalogo, estadoConfigBanner, tipoBanner);
    }

    @Override
    public ConfiguracionWeb obtenerConfiguracionWeb() {
        return configuracionDao.obtenerConfiguracionWeb();
    }

    @Override
    public List<TipoDocumento> obtenerTipoDocumentos() {
        return configuracionDao.listarTipoDocumentos();
    }

    @Override
    public List<RazonSocial> obtenerRazonesSociales(Integer idCatalogo) {
        return configuracionDao.obtenerRazonesSociales(idCatalogo);
    }

    @Override
    public List<TipoParticipante> obtenerTipoParticipante() {
        return tipoParticipanteDao.listarTipoParticipantes();
    }

    @Override
    public List<Region> obtenerRegiones() {
        return configuracionDao.obtenerRegiones();
    }
}
