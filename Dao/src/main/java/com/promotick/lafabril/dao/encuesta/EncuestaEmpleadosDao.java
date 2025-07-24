package com.promotick.lafabril.dao.encuesta;

import com.promotick.lafabril.model.encuesta.*;
import com.promotick.lafabril.model.reporte.ReporteEncuestaEmpleados;
import com.promotick.lafabril.model.util.FiltroReporteEncuestaEmpleados;
import com.promotick.configuration.utils.dao.DaoResult;

import java.util.List;
import java.util.Optional;

public interface EncuestaEmpleadosDao {
    List<ParticipanteData> participanteDataListar();

    Optional<Campania> campaniaObtener(Integer idParticipante);

    List<Pregunta> preguntasByEncuesta(Integer idEncuesta);

    DaoResult participanteEncuestaGuardar(ParticipanteEncuesta participanteEncuesta);

    DaoResult participanteEncuestaDetalleGuardar(ParticipanteEncuestaDetalle participanteEncuestaDetalle);

    List<ReporteEncuestaEmpleados> reporteEncuestaEmpleadosListar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados);

    Integer reporteEncuestaEmpleadosContar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados);
}
