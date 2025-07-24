package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import com.promotick.lafabril.model.reporte.ReporteParticipante;
import com.promotick.lafabril.model.util.FiltroParticipante;
import com.promotick.lafabril.model.util.FiltroParticipanteTransaccion;
import com.promotick.lafabril.model.web.*;

import java.util.Date;
import java.util.List;

public interface ParticipanteDao {
    Participante obtenerParticipantePorNroDocumento(String nroDocumento);

    List<Participante> participantesListar(FiltroParticipante filtroParticipante);

    Integer participantesContar(FiltroParticipante filtroParticipante);

    Integer actualizarEstadoParticipante(Participante participante);

    Participante obtenerByID(Integer idParticipante);

    Integer actualizarParticipante(Participante participante);

    Integer actualizarParticipantePerfil(Participante participante);

    Participante loginParticipante(Integer idTipoDocumento, String usuario);

    Integer puntosDisponiblesParticipante(Integer idParticipante);

    Integer puntosAcumuladosParticipante(Integer idParticipante);

    Integer puntosCanjeadosParticipante(Integer idParticipante);

    Integer puntosPosiblesParticipante(Integer idParticipante);

    Participante obtenerParticipanteByEmail(String email);

    Date ultimaActualizacionParticipante(Integer idParticipante);

    List<ParticipanteTransaccion> obtenerMisPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer contarMisPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer actualizarDatosParticipante(Participante participante);

    Integer puntosAcumuladosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer puntosCanjeadosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer puntosPosiblesParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    Integer puntosDescargadosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    List<Participante> listarParticipanteEmail(Integer tipoEmail);

    Integer periodoParticipanteEnvioEmail(Participante participante);

    Integer periodoParticipanteEnvioEmail(PeriodoParticipante periodoParticipante);

    List<Participante> listarCumpleanos();

    Integer puntosVencidosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion);

    List<Participante> listarParticipantesFestividades();

    Integer obtenerParticipante(Integer idParticipante);

    List<ReporteParticipante> reporteParticipantes(FiltroParticipante filtroParticipante);

    List<CategoriaParticipante> listarCategoriaParticipante();

    Integer actualizarEstadoParticipanteCanje(Participante participante);

    Integer registroParticipante(Participante participante);

    List<Participante> topBrokerListar(Integer idParticipante, Integer idCatalogo);

    Integer participanteActualizarFoto(Integer idParticipante, String foto);

    ParticipanteMeta participanteMetaObtener(Integer idParticipante, Integer tipoMeta);

    ParticipanteMeta participanteMetaObtenerByAvance(Integer idParticipante, Date fechaOperacion);

    Integer registrarFacturaParticipante(String filename, Integer idParticipante);

    Integer participanteAprobar(Participante participante);

    Integer participanteAprobarByDocumento(Participante participante);

    Participante participanteAprobarMailBienvenidaObtener(Integer idParticipante);

    List<Broker> listarBroker();

    List<Regional> listarRegional();
}
