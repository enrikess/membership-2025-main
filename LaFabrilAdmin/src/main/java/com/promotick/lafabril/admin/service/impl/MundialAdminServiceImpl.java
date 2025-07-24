package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.MensajeAdminService;
import com.promotick.lafabril.admin.service.MundialAdminService;
import com.promotick.lafabril.dao.web.MensajeDao;
import com.promotick.lafabril.dao.web.MundialDao;
import com.promotick.lafabril.model.web.Mensaje;
import com.promotick.lafabril.model.web.PartidoMundial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MundialAdminServiceImpl implements MundialAdminService {

    private MundialDao mundialDao;

    @Autowired
    public MundialAdminServiceImpl(MundialDao mundialDao) {
        this.mundialDao = mundialDao;
    }

    @Override
    public List<PartidoMundial> listarFechasPartido() {
        return mundialDao.listarFechasPartido();
    }
}
