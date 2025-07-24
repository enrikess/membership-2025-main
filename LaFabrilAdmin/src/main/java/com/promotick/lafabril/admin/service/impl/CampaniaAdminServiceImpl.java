package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CampaniaAdminService;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.dao.web.CampaniaDao;
import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Catalogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CampaniaAdminServiceImpl implements CampaniaAdminService {

    private CampaniaDao campaniaDao;

    @Autowired
    public CampaniaAdminServiceImpl(CampaniaDao campaniaDao) {
        this.campaniaDao = campaniaDao;
    }

    @Override
    public List<Campania> listarCampanias() {
        return campaniaDao.listarCampanias();
    }

    @Override
    public List<Campania> listarCampanias(Integer idCatalogo, Integer orden) {
        return campaniaDao.listarCampanias(idCatalogo, orden);
    }

    @Override
    @Transactional
    public Integer actualizarCampania(Campania campania) {
        return campaniaDao.actualizarCampania(campania);
    }

    @Override
    @Transactional
    public Integer actualizarEstadoCampania(Integer idCatalogo, Integer estado) {
        return campaniaDao.actualizarEstadoCampania(idCatalogo, estado);
    }


}
