package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.dao.admin.CargaExcelDao;
import com.promotick.lafabril.model.util.FiltroFaq;
import com.promotick.lafabril.model.util.FiltroTyc;
import com.promotick.lafabril.model.util.form.CargaAcciones;
import com.promotick.lafabril.model.util.form.CargaMetas;
import com.promotick.lafabril.model.util.form.CargaPuntos;
import com.promotick.lafabril.model.util.form.CargaVentas;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Recomendado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CargaExcelAdminServiceImpl implements CargaExcelAdminService {

    private CargaExcelDao cargaExcelDao;

    @Autowired
    public CargaExcelAdminServiceImpl(CargaExcelDao cargaExcelDao) {
        this.cargaExcelDao = cargaExcelDao;
    }

    @Override
    @Transactional
    public Integer registrarCargaPuntos(CargaPuntos cargaPuntos) {
        return cargaExcelDao.registrarCargaPuntos(cargaPuntos);
    }

    @Override
    @Transactional
    public Integer registrarCargaParticipante(Participante participante) {
        return cargaExcelDao.registrarCargaParticipante(participante);
    }

    @Override
    @Transactional
    public Integer registrarCargaMetas(CargaMetas cargaMetas) {
        return cargaExcelDao.registrarCargaMetas(cargaMetas);
    }

    @Override
    @Transactional
    public Integer registrarCargaVentas(CargaVentas cargaVentas) {
        return cargaExcelDao.registrarCargaVentas(cargaVentas);
    }

    @Override
    @Transactional
    public Integer registrarCargaParticipanteEstado(Participante participante) {
        return cargaExcelDao.registrarCargaParticipanteEstado(participante);
    }

    @Override
    @Transactional
    public Integer registrarCargaRecomendadoEstado(Recomendado recomendado) {
        return cargaExcelDao.registrarCargaRecomendadoEstado(recomendado);
    }

    @Override
    @Transactional
    public Integer actualizarTyc(FiltroTyc filtroTyc) {
        return cargaExcelDao.actualizarTyc(filtroTyc);
    }

    @Override
    @Transactional
    public Integer actualizarFaq(FiltroFaq filtroFaq) {
        return cargaExcelDao.actualizarFaq(filtroFaq);
    }

    @Override
    @Transactional
    public Integer registrarAccionesMetas(CargaAcciones cargaAcciones) {
        return cargaExcelDao.registrarAccionesMetas(cargaAcciones);
    }
}
