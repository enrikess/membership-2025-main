package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.dao.facturacion.PeriodoParticipanteDao;
import com.promotick.lafabril.dao.web.ParticipanteDao;
import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import com.promotick.lafabril.model.reporte.ReporteParticipante;
import com.promotick.lafabril.model.util.FiltroParticipante;
import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ParticipanteAdminServiceImpl implements ParticipanteAdminService {

    private ParticipanteDao participanteDao;

    private PeriodoParticipanteDao periodoParticipanteDao;

    @Autowired
    public ParticipanteAdminServiceImpl(ParticipanteDao participanteDao,PeriodoParticipanteDao periodoParticipanteDao) {
        this.participanteDao = participanteDao;
        this.periodoParticipanteDao = periodoParticipanteDao;
    }

    @Override
    public Participante obtenerParticipantePorNroDocumento(String nroDocumento) {
        return participanteDao.obtenerParticipantePorNroDocumento(nroDocumento);
    }

    @Override
    public List<Participante> participantesListar(FiltroParticipante filtroParticipante) {
        return participanteDao.participantesListar(filtroParticipante);
    }

    @Override
    public Integer participantesContar(FiltroParticipante filtroParticipante) {
        return participanteDao.participantesContar(filtroParticipante);
    }

    @Override
    @Transactional
    public Integer actualizarEstadoParticipante(Participante participante) {
        return participanteDao.actualizarEstadoParticipante(participante);
    }

    @Override
    public Participante obtenerByID(Integer idParticipante) {
        return participanteDao.obtenerByID(idParticipante);
    }

    @Override
    @Transactional
    public Integer actualizarParticipante(Participante participante) {
        return participanteDao.actualizarParticipante(participante);
    }

    @Override
    public List<Participante> listarParticipanteEmail(Integer tipoEmail) {
        return participanteDao.listarParticipanteEmail(tipoEmail);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer periodoParticipanteEnvioEmail(Participante participante) {
        return participanteDao.periodoParticipanteEnvioEmail(participante);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer periodoParticipanteEnvioEmail(PeriodoParticipante periodoParticipante) {
        return participanteDao.periodoParticipanteEnvioEmail(periodoParticipante);
    }

    @Override
    public List<Participante> listarCumpleanos() {
        return participanteDao.listarCumpleanos();
    }

    @Override
    public List<Participante> listarParticipantesFestividades() {
        return participanteDao.listarParticipantesFestividades();
    }

    @Override
    public Participante obtenerParticipante(String nroDocumento) {
        return participanteDao.loginParticipante(0, nroDocumento);
    }

    @Override
    public List<ReporteParticipante> reporteParticipantes(FiltroParticipante filtroParticipante) {
        return participanteDao.reporteParticipantes(filtroParticipante);
    }

    @Override
    public MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta) {
        return periodoParticipanteDao.obtenerMetaParticipante(idParticipante,tipoMeta);
    }

    @Override
    public List<CategoriaParticipante> listarCategoriaParticipante() {
        return participanteDao.listarCategoriaParticipante();
    }

    @Override
    @Transactional
    public Integer actualizarEstadoParticipanteCanje(Participante participante) {
        return participanteDao.actualizarEstadoParticipanteCanje(participante);
    }

    @Override
    public ParticipanteMeta participanteMetaObtenerByAvance(Integer idParticipante, Date fechaOperacion) {
        return participanteDao.participanteMetaObtenerByAvance(idParticipante, fechaOperacion);
    }

    @Override
    @Transactional
    public Integer participanteAprobar(Participante participante) {
        return participanteDao.participanteAprobar(participante);
    }

    @Override
    @Transactional
    public Integer participanteAprobarByDocumento(Participante participante) {
        return participanteDao.participanteAprobarByDocumento(participante);
    }

    @Override
    public Participante participanteAprobarMailBienvenidaObtener(Integer idParticipante) {
        return participanteDao.participanteAprobarMailBienvenidaObtener(idParticipante);
    }

    @Override
    public List<Broker> listarBroker() {
        return participanteDao.listarBroker();
    }

    @Override
    public List<Regional> listarRegional() {
        return participanteDao.listarRegional();
    }
}
