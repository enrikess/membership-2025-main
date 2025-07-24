package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.util.FiltroParticipanteTransaccion;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.ParticipanteMeta;
import com.promotick.lafabril.model.web.ParticipanteTransaccion;

import java.util.Date;
import java.util.List;

public interface ParticipanteWebService {
    Participante loginParticipante(Integer idTipoDocumento, String usuario);

    Integer actualizarParticipante(Participante participante);

    Integer actualizarParticipantePerfil(Participante participante);

    Participante obtenerParticipanteByEmail(String email);

    Integer puntosDisponiblesParticipante(Integer idParticipante);

    Integer puntosAcumuladosParticipante(Integer idParticipante);

    Integer puntosCanjeadosParticipante(Integer idParticipante);

    Integer puntosPosiblesParticipante(Integer idParticipante);

    Date ultimaActualizacionParticipante(Integer idParticipante);

    List<ParticipanteTransaccion> obtenerMisPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer contarMisPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer actualizarDatosParticipante(Participante participante);

    Integer puntosAcumuladosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer puntosCanjeadosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer puntosPosiblesParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer puntosVencidosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer puntosDescargadosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer obtenerParticipante(Integer idParticipante);

    Integer registroParticipante(Participante participante);

    List<Participante> topBrokerListar(Integer idParticipante, Integer idCatalogo);

    Integer participanteActualizarFoto(Integer idParticipante, String foto);

    ParticipanteMeta participanteMetaObtener(Integer idParticipante, Integer tipoMeta);

    Integer registrarFacturaParticipante(String filename, Integer idParticipante);
}
