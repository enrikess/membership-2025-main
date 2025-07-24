package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.PeriodoAdminService;
import com.promotick.lafabril.dao.facturacion.PeriodoDao;
import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeriodoAdminServiceImpl implements PeriodoAdminService {

    private PeriodoDao periodoDao;

    @Autowired
    public PeriodoAdminServiceImpl(PeriodoDao periodoDao) {
        this.periodoDao = periodoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer validacionPeriodo() {
        return periodoDao.validacionPeriodo();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public List<PeriodoParticipante> cierrePeriodo() {
        return periodoDao.cierrePeriodo();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public List<PeriodoParticipante> recalculoMetas() {
        return periodoDao.recalculoMetas();
    }
}
