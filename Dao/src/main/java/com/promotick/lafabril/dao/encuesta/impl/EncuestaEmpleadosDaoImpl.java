package com.promotick.lafabril.dao.encuesta.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.admin.definition.ReporteEncuestaEmpleadosDaoDefinition;
import com.promotick.lafabril.dao.encuesta.EncuestaEmpleadosDao;
import com.promotick.lafabril.dao.encuesta.definition.CampaniaDaoDefinition;
import com.promotick.lafabril.dao.encuesta.definition.ParticipanteDataDaoDefinition;
import com.promotick.lafabril.dao.encuesta.definition.PreguntaDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesEncuestaDAO;
import com.promotick.lafabril.model.encuesta.*;
import com.promotick.lafabril.model.reporte.ReporteEncuestaEmpleados;
import com.promotick.lafabril.model.util.FiltroReporteEncuestaEmpleados;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.configuration.utils.dao.DaoResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class EncuestaEmpleadosDaoImpl extends DaoGenerator implements EncuestaEmpleadosDao {

    private final ParticipanteDataDaoDefinition participanteDataDaoDefinition;
    private final CampaniaDaoDefinition campaniaDaoDefinition;
    private final PreguntaDaoDefinition preguntaDaoDefinition;
    private final ReporteEncuestaEmpleadosDaoDefinition reporteEncuestaEmpleadosDaoDefinition;

    public EncuestaEmpleadosDaoImpl(JdbcTemplate jdbcTemplate, ParticipanteDataDaoDefinition participanteDataDaoDefinition, CampaniaDaoDefinition campaniaDaoDefinition, PreguntaDaoDefinition preguntaDaoDefinition, ReporteEncuestaEmpleadosDaoDefinition reporteEncuestaEmpleadosDaoDefinition) {
        super(jdbcTemplate);
        this.participanteDataDaoDefinition = participanteDataDaoDefinition;
        this.campaniaDaoDefinition = campaniaDaoDefinition;
        this.preguntaDaoDefinition = preguntaDaoDefinition;
        this.reporteEncuestaEmpleadosDaoDefinition = reporteEncuestaEmpleadosDaoDefinition;
    }

    @Override
    public List<ParticipanteData> participanteDataListar() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesEncuestaDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesEncuestaDAO.SP_PARTICIPANTE_DATA_LISTAR)
                .setDaoDefinition(participanteDataDaoDefinition)
                .build();
    }

    @Override
    public Optional<Campania> campaniaObtener(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesEncuestaDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesEncuestaDAO.SP_ENCUESTA_OBTENER)
                .addParameter("var_id_partipante", Types.INTEGER, idParticipante)
                .setDaoDefinition(campaniaDaoDefinition)
                .buildOptional(Campania.class);
    }

    @Override
    public List<Pregunta> preguntasByEncuesta(Integer idEncuesta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesEncuestaDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesEncuestaDAO.SP_PREGUNTAS_BY_ENCUESTA)
                .addParameter("var_id_encuesta", Types.INTEGER, idEncuesta)
                .setDaoDefinition(preguntaDaoDefinition)
                .build();
    }

    @Override
    public DaoResult participanteEncuestaGuardar(ParticipanteEncuesta participanteEncuesta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesEncuestaDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesEncuestaDAO.SP_PARTICIPANTE_ENCUESTA_GUARDAR)
                .addParameter("var_id_participante", Types.INTEGER, participanteEncuesta.getIdParticipante())
                .addParameter("var_id_encuesta", Types.INTEGER, participanteEncuesta.getIdEncuesta())
                .addParameter("var_id_campania", Types.INTEGER, participanteEncuesta.getIdCampania())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(DaoResult.class);
    }

    @Override
    public DaoResult participanteEncuestaDetalleGuardar(ParticipanteEncuestaDetalle participanteEncuestaDetalle) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesEncuestaDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesEncuestaDAO.SP_PARTICIPANTE_ENCUESTA_DETALLE_GUARDAR)
                .addParameter("var_id_participante_encuesta", Types.INTEGER, participanteEncuestaDetalle.getIdParticipanteEncuesta())
                .addParameter("var_texto_pregunta", Types.VARCHAR, participanteEncuestaDetalle.getTextoPregunta())
                .addParameter("var_texto_respuesta", Types.VARCHAR, participanteEncuestaDetalle.getTextoRespuesta())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(DaoResult.class);
    }

    @Override
    public List<ReporteEncuestaEmpleados> reporteEncuestaEmpleadosListar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesEncuestaDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesEncuestaDAO.SP_PARTICIPANTE_ENCUESTA_REPORTE_LISTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroReporteEncuestaEmpleados.getBuscar())
                .addParameter("var_finicio", Types.VARCHAR, filtroReporteEncuestaEmpleados.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReporteEncuestaEmpleados.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroReporteEncuestaEmpleados.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroReporteEncuestaEmpleados.getLength())
                .setDaoDefinition(reporteEncuestaEmpleadosDaoDefinition)
                .build();
    }

    @Override
    public Integer reporteEncuestaEmpleadosContar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesEncuestaDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesEncuestaDAO.SP_PARTICIPANTE_ENCUESTA_REPORTE_CONTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroReporteEncuestaEmpleados.getBuscar())
                .addParameter("var_finicio", Types.VARCHAR, filtroReporteEncuestaEmpleados.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReporteEncuestaEmpleados.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
