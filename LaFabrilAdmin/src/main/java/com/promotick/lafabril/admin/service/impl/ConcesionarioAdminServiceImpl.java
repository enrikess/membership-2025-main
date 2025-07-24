package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.ConcesionarioAdminService;
import com.promotick.lafabril.dao.admin.ConcesionarioDao;
import com.promotick.lafabril.model.web.Concesionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcesionarioAdminServiceImpl implements ConcesionarioAdminService {

    private ConcesionarioDao concesionarioDao;

    @Autowired
    public ConcesionarioAdminServiceImpl(ConcesionarioDao concesionarioDao) {
        this.concesionarioDao = concesionarioDao;
    }

    @Override
    public List<Concesionario> listarConcesionarios() {
        return concesionarioDao.listarConcesionarios();
    }
}
