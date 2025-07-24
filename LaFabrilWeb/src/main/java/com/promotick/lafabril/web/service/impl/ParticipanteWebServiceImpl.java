package com.promotick.lafabril.web.service.impl;

import com.promotick.configuration.services.PromotickResourceService;
import com.promotick.lafabril.dao.web.ParticipanteDao;
import com.promotick.lafabril.model.util.FiltroParticipanteTransaccion;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.ParticipanteMeta;
import com.promotick.lafabril.model.web.ParticipanteTransaccion;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipanteWebServiceImpl implements ParticipanteWebService {

    private final ParticipanteDao participanteDao;
    private final PromotickResourceService promotickResourceService;

    @Override
    public Participante loginParticipante(Integer idTipoDocumento, String usuario) {
        return participanteDao.loginParticipante(idTipoDocumento, usuario);
    }

    @Override
    @Transactional
    public Integer actualizarParticipante(Participante participante) {
        return participanteDao.actualizarParticipante(participante);
    }

    @Override
    @Transactional
    public Integer actualizarParticipantePerfil(Participante participante) {
        return participanteDao.actualizarParticipantePerfil(participante);
    }

    @Override
    public Participante obtenerParticipanteByEmail(String email) {
        return participanteDao.obtenerParticipanteByEmail(email);
    }

    @Override
    public Integer puntosDisponiblesParticipante(Integer idParticipante) {
        return participanteDao.puntosDisponiblesParticipante(idParticipante);
    }

    @Override
    public Integer puntosAcumuladosParticipante(Integer idParticipante) {
        return participanteDao.puntosAcumuladosParticipante(idParticipante);
    }

    @Override
    public Integer puntosCanjeadosParticipante(Integer idParticipante) {
        return participanteDao.puntosCanjeadosParticipante(idParticipante);
    }

    @Override
    public Integer puntosPosiblesParticipante(Integer idParticipante) {
        return participanteDao.puntosPosiblesParticipante(idParticipante);
    }

    @Override
    public Date ultimaActualizacionParticipante(Integer idParticipante) {
        return participanteDao.ultimaActualizacionParticipante(idParticipante);
    }

    @Override
    public List<ParticipanteTransaccion> obtenerMisPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.obtenerMisPuntos(filtroParticipanteTransaccion);
    }

    @Override
    public Integer contarMisPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.contarMisPuntos(filtroParticipanteTransaccion);
    }

    @Override
    @Transactional
    public Integer actualizarDatosParticipante(Participante participante) {
        return participanteDao.actualizarDatosParticipante(participante);
    }

    @Override
    public Integer puntosAcumuladosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.puntosAcumuladosParticipante(filtroParticipanteTransaccion);
    }

    @Override
    public Integer puntosCanjeadosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.puntosCanjeadosParticipante(filtroParticipanteTransaccion);
    }

    @Override
    public Integer puntosPosiblesParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.puntosPosiblesParticipante(filtroParticipanteTransaccion);
    }

    @Override
    public Integer puntosVencidosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.puntosVencidosParticipante(filtroParticipanteTransaccion);
    }

    @Override
    public Integer puntosDescargadosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.puntosDescargadosParticipante(filtroParticipanteTransaccion);
    }

    @Override
    public Integer obtenerParticipante(Integer idParticipante) {
        return participanteDao.obtenerParticipante(idParticipante);
    }

    @Override
    @Transactional
    public Integer registroParticipante(Participante participante) {
        return participanteDao.registroParticipante(participante);
    }

    @Override
    public List<Participante> topBrokerListar(Integer idParticipante, Integer idCatalogo) {
        List<Participante> lista = participanteDao.topBrokerListar(idParticipante, idCatalogo);
        if (lista != null) {
            lista.forEach(p -> {
                if (StringUtils.isEmpty(p.getFoto())) {
                    p.setFoto(promotickResourceService.web("img/fotos/person1.png"));
                } else {
                    p.setFoto(promotickResourceService.web("img/fotos/" + p.getFoto()));
                }
            });
        }
        return lista;
    }

    @Override
    @Transactional
    public Integer participanteActualizarFoto(Integer idParticipante, String foto) {
        return participanteDao.participanteActualizarFoto(idParticipante, foto);
    }

    @Override
    public ParticipanteMeta participanteMetaObtener(Integer idParticipante, Integer tipoMeta) {
        return participanteDao.participanteMetaObtener(idParticipante, tipoMeta);
    }

    @Override
    @Transactional
    public Integer registrarFacturaParticipante(String filename, Integer idParticipante) {
        return participanteDao.registrarFacturaParticipante(filename, idParticipante);
    }
}
