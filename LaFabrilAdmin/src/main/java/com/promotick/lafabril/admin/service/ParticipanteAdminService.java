package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import com.promotick.lafabril.model.reporte.ReporteParticipante;
import com.promotick.lafabril.model.util.FiltroParticipante;
import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.*;

import java.util.Date;
import java.util.List;

public interface ParticipanteAdminService {
    Participante obtenerParticipantePorNroDocumento(String nroDocumento);

    List<Participante> participantesListar(FiltroParticipante filtroParticipante);

    Integer participantesContar(FiltroParticipante filtroParticipante);

    Integer actualizarEstadoParticipante(Participante participante);

    Participante obtenerByID(Integer idParticipante);

    Integer actualizarParticipante(Participante participante);

    List<Participante> listarParticipanteEmail(Integer tipoEmail);

    Integer periodoParticipanteEnvioEmail(Participante participante);

    Integer periodoParticipanteEnvioEmail(PeriodoParticipante periodoParticipante);

    List<Participante> listarCumpleanos();

    List<Participante> listarParticipantesFestividades();

    Participante obtenerParticipante(String nroDocumento);

    List<ReporteParticipante> reporteParticipantes(FiltroParticipante filtroParticipante);

    MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta);

    List<CategoriaParticipante> listarCategoriaParticipante();

    Integer actualizarEstadoParticipanteCanje(Participante participante);

    ParticipanteMeta participanteMetaObtenerByAvance(Integer idParticipante, Date fechaOperacion);

    Integer participanteAprobar(Participante participante);

    Integer participanteAprobarByDocumento(Participante participante);

    Participante participanteAprobarMailBienvenidaObtener(Integer idParticipante);

    List<Broker> listarBroker();

    List<Regional> listarRegional();
}
