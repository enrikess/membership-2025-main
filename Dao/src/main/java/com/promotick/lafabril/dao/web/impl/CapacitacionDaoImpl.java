package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.web.definition.*;
import com.promotick.lafabril.model.web.*;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.CapacitacionDao;
import com.promotick.lafabril.dao.web.definition.*;
import com.promotick.lafabril.model.util.FiltroCapacitacion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class CapacitacionDaoImpl extends DaoGenerator implements CapacitacionDao {

    private final CapacitacionDaoDefinition capacitacionDaoDefinition;
    private final CapacitacionRecursoDaoDefinition capacitacionRecursoDaoDefinition;
    private final CapacitacionPreguntaDaoDefinition capacitacionPreguntaDaoDefinition;
    private final CapacitacionRespuestaDaoDefinition capacitacionRespuestaDaoDefinition;
    private final CapacitacionParticipanteDaoDefinition capacitacionParticipanteDaoDefinition;
    private final CapacitacionParticipanteDetalleDaoDefinition capacitacionParticipanteDetalleDaoDefinition;

    public CapacitacionDaoImpl(JdbcTemplate jdbcTemplate, CapacitacionDaoDefinition capacitacionDaoDefinition, CapacitacionRecursoDaoDefinition capacitacionRecursoDaoDefinition, CapacitacionPreguntaDaoDefinition capacitacionPreguntaDaoDefinition, CapacitacionRespuestaDaoDefinition capacitacionRespuestaDaoDefinition, CapacitacionParticipanteDaoDefinition capacitacionParticipanteDaoDefinition, CapacitacionParticipanteDetalleDaoDefinition capacitacionParticipanteDetalleDaoDefinition) {
        super(jdbcTemplate);
        this.capacitacionDaoDefinition = capacitacionDaoDefinition;
        this.capacitacionRecursoDaoDefinition = capacitacionRecursoDaoDefinition;
        this.capacitacionPreguntaDaoDefinition = capacitacionPreguntaDaoDefinition;
        this.capacitacionRespuestaDaoDefinition = capacitacionRespuestaDaoDefinition;
        this.capacitacionParticipanteDaoDefinition = capacitacionParticipanteDaoDefinition;
        this.capacitacionParticipanteDetalleDaoDefinition = capacitacionParticipanteDetalleDaoDefinition;
    }

    @Override
    public List<Capacitacion> capacitacionesListar(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_LISTAR)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(capacitacionDaoDefinition)
                .build();
    }

    @Override
    public Capacitacion capacitacionObtener(Integer idParticipante, Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_OBTENER)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .setDaoDefinition(capacitacionDaoDefinition)
                .build(Capacitacion.class);
    }

    @Override
    public List<CapacitacionRecurso> capacitacionRecursosListar(Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_RECURSOS_LISTAR)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .setDaoDefinition(capacitacionRecursoDaoDefinition)
                .build();
    }

    @Override
    public List<CapacitacionPregunta> capacitacionPreguntasListar(Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_PREGUNTAS_LISTAR)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .setDaoDefinition(capacitacionPreguntaDaoDefinition)
                .build();
    }

    @Override
    public List<CapacitacionRespuesta> capacitacionRespuestasListar(Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_RESPUESTAS_LISTAR)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .setDaoDefinition(capacitacionRespuestaDaoDefinition)
                .build();
    }

    @Override
    public Integer capacitacionParticipanteMantenimiento(CapacitacionParticipante capacitacionParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_PARTICIPANTE_MANTENIMIENTO)
                .addParameter("var_id_capacitacion_participante", Types.INTEGER, capacitacionParticipante.getIdCapacitacionParticipante())
                .addParameter("var_id_capacitacion", Types.INTEGER, capacitacionParticipante.getIdCapacitacion())
                .addParameter("var_id_participante", Types.INTEGER, capacitacionParticipante.getIdParticipante())
                .addParameter("var_cantidad_preguntas", Types.INTEGER, capacitacionParticipante.getCantidadPreguntas())
                .addParameter("var_cantidad_correctas", Types.INTEGER, capacitacionParticipante.getCantidadCorrectas())
                .addParameter("var_cantidad_erroneas", Types.INTEGER, capacitacionParticipante.getCantidadErroneas())
                .addParameter("var_calificacion", Types.INTEGER, capacitacionParticipante.getCalificacion())
                .addParameter("var_preguntas_raw", Types.VARCHAR, capacitacionParticipante.getPreguntasRaw())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer capacitacionParticipanteDetalleRegistrar(CapacitacionParticipanteDetalle capacitacionParticipanteDetalle) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_PARTICIPANTE_DETALLE_REGISTRAR)
                .addParameter("var_id_capacitacion_participante_pregunta", Types.INTEGER, capacitacionParticipanteDetalle.getIdCapacitacionParticipantePregunta())
                .addParameter("var_id_capacitacion_respuesta", Types.INTEGER, capacitacionParticipanteDetalle.getCapacitacionRespuesta().getIdCapacitacionRespuesta())
                .addParameter("var_es_correcta", Types.BOOLEAN, capacitacionParticipanteDetalle.getEsCorrecta())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public CapacitacionParticipante capacitacionParticipanteObtener(Integer idParticipante, Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_PARTICIPANTE_OBTENER)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(capacitacionParticipanteDaoDefinition)
                .build(CapacitacionParticipante.class);
    }

    @Override
    public List<CapacitacionParticipanteDetalle> capacitacionParticipanteDetalleListar(Integer idParticipante, Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_PARTICIPANTE_DETALLE_LISTAR)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(capacitacionParticipanteDetalleDaoDefinition)
                .build();
    }

    @Override
    public Integer guardarCapacitacionParticipantePregunta(CapacitacionParticipantePregunta capacitacionParticipantePregunta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CAPACITACION_PARTICIPANTE_PREGUNTA_REGISTRAR)
                .addParameter("var_id_capacitacion_participante_pregunta", Types.INTEGER, capacitacionParticipantePregunta.getIdCapacitacionParticipantePregunta())
                .addParameter("var_id_capacitacion_participante", Types.INTEGER, capacitacionParticipantePregunta.getIdCapacitacionParticipante())
                .addParameter("var_id_capacitacion_pregunta", Types.INTEGER, capacitacionParticipantePregunta.getIdCapacitacionPregunta())
                .addParameter("var_resultado_pregunta", Types.BOOLEAN, capacitacionParticipantePregunta.getResultadoPregunta())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Capacitacion> capacitacionesFiltroListar(FiltroCapacitacion filtroCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_LISTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroCapacitacion.getBuscar())
                .addParameter("var_inicio", Types.INTEGER, filtroCapacitacion.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroCapacitacion.getLength())
                .setDaoDefinition(capacitacionDaoDefinition)
                .build();
    }

    @Override
    public Integer capacitacionesFiltroContar(FiltroCapacitacion filtroCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_CONTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroCapacitacion.getBuscar())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Capacitacion capacitacionAdminObtener(Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_OBTENER)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .setDaoDefinition(capacitacionDaoDefinition)
                .build(Capacitacion.class);
    }

    @Override
    public Integer capacitacionAdminEstadoCambiar(Capacitacion capacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_ESTADO_CAMBIAR)
                .addParameter("var_id_capacitacion", Types.INTEGER, capacitacion.getIdCapacitacion())
                .addParameter("var_estado_capacitacion", Types.BOOLEAN, capacitacion.getEstadoCapacitacion())
                .addParameter("var_id_usuario_actualizacion", Types.INTEGER, capacitacion.getAuditoria().getIdUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer capacitacionAdminMantenimiento(Capacitacion capacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_MANTENIMIENTO)
                .addParameter("var_id_capacitacion", Types.INTEGER, capacitacion.getIdCapacitacion())
                .addParameter("var_nombre_capacitacion", Types.VARCHAR, capacitacion.getNombreCapacitacion())
                .addParameter("var_descripcion_capacitacion", Types.VARCHAR, capacitacion.getDescripcionCapacitacion())
                .addParameter("var_fecha_inicio", Types.VARCHAR, capacitacion.getFechaInicioString())
                .addParameter("var_fecha_fin", Types.VARCHAR, capacitacion.getFechaFinString())
                .addParameter("var_imagen_uno", Types.VARCHAR, capacitacion.getImagenUno())
                .addParameter("var_imagen_detalle", Types.VARCHAR, capacitacion.getImagenDetalle())
                .addParameter("var_id_usuario_creacion", Types.INTEGER, capacitacion.getAuditoria().getIdUsuarioCreacion())
                .addParameter("var_estado_cuestionario", Types.BOOLEAN, capacitacion.getEstadoCuestionario())
                .addParameter("var_id_catalogo", Types.INTEGER, capacitacion.getIdCatalogo())
                .addParameter("var_puntos_capacitacion", Types.INTEGER, capacitacion.getPuntosCapacitacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public CapacitacionRecurso capacitacionRecursosAdminObtener(Integer idCapacitacionRecurso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RECURSOS_OBTENER)
                .addParameter("var_id_capacitacion_recurso", Types.INTEGER, idCapacitacionRecurso)
                .setDaoDefinition(capacitacionRecursoDaoDefinition)
                .build(CapacitacionRecurso.class);
    }

    @Override
    public void capacitacionRecursosAdminOrdenar(String lista) {
        DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RECURSOS_ORDENAR)
                .addParameter("var_lista", Types.VARCHAR, lista)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer capacitacionRecursosAdminEliminar(Integer idCapacitacionRecurso, Integer idUsuario) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RECURSOS_ELIMINAR)
                .addParameter("var_id_capacitacion_recurso", Types.INTEGER, idCapacitacionRecurso)
                .addParameter("var_id_usuario_actualizacion", Types.INTEGER, idUsuario)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer capacitacionRecursosAdminActivar(Integer idCapacitacionRecurso, Integer idUsuario) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RECURSOS_ACTIVAR)
                .addParameter("var_id_capacitacion_recurso", Types.INTEGER, idCapacitacionRecurso)
                .addParameter("var_id_usuario_actualizacion", Types.INTEGER, idUsuario)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<CapacitacionRecurso> capacitacionRecursosAdminListarTodos(Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RECURSOS_LISTAR_TODOS)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .setDaoDefinition(capacitacionRecursoDaoDefinition)
                .build();
    }

    @Override
    public Integer capacitacionRecursoAdminMantenimiento(CapacitacionRecurso capacitacionRecurso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RECURSOS_MANTENIMIENTO)
                .addParameter("var_id_capacitacion_recurso", Types.INTEGER, capacitacionRecurso.getIdCapacitacionRecurso())
                .addParameter("var_id_capacitacion", Types.INTEGER, capacitacionRecurso.getIdCapacitacion())
                .addParameter("var_id_tipo_recurso", Types.INTEGER, capacitacionRecurso.getIdTipoRecurso())
                .addParameter("var_contenido", Types.VARCHAR, capacitacionRecurso.getContenido())
                .addParameter("var_nombre_capacitacion_recurso", Types.VARCHAR, capacitacionRecurso.getNombreCapacitacionRecurso())
                .addParameter("var_id_usuario_creacion", Types.INTEGER, capacitacionRecurso.getAuditoria().getIdUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<CapacitacionPregunta> capacitacionPreguntasAdminListar(Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_PREGUNTAS_LISTAR)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .setDaoDefinition(capacitacionPreguntaDaoDefinition)
                .build();
    }

    @Override
    public List<CapacitacionRespuesta> capacitacionRespuestasAdminListar(Integer idCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RESPUESTAS_LISTAR)
                .addParameter("var_id_capacitacion", Types.INTEGER, idCapacitacion)
                .setDaoDefinition(capacitacionRespuestaDaoDefinition)
                .build();
    }

    @Override
    public Integer capacitacionAdminPreguntasEstadoCambiar(CapacitacionPregunta capacitacionPregunta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_PREGUNTAS_ESTADO_CAMBIAR)
                .addParameter("var_id_capacitacion_pregunta", Types.INTEGER, capacitacionPregunta.getIdCapacitacionPregunta())
                .addParameter("var_estado_capacitacion_pregunta", Types.BOOLEAN, capacitacionPregunta.getEstadoCapacitacionPregunta())
                .addParameter("var_id_usuario_actualizacion", Types.INTEGER, capacitacionPregunta.getAuditoria().getIdUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public void capacitacionPreguntasAdminOrdenar(String lista) {
        DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_PREGUNTAS_ORDENAR)
                .addParameter("var_lista", Types.VARCHAR, lista)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer capacitacionAdminPreguntaActualizar(CapacitacionPregunta capacitacionPregunta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_PREGUNTAS_ACTUALIZAR)
                .addParameter("var_id_capacitacion_pregunta", Types.INTEGER, capacitacionPregunta.getIdCapacitacionPregunta())
                .addParameter("var_pregunta", Types.VARCHAR, capacitacionPregunta.getPregunta())
                .addParameter("var_id_usuario_actualizacion", Types.INTEGER, capacitacionPregunta.getAuditoria().getIdUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public CapacitacionPregunta capacitacionPreguntasAdminObtener(Integer idCapacitacionPregunta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_PREGUNTAS_OBTENER)
                .addParameter("var_id_capacitacion_pregunta", Types.INTEGER, idCapacitacionPregunta)
                .setDaoDefinition(capacitacionPreguntaDaoDefinition)
                .build(CapacitacionPregunta.class);
    }

    @Override
    public List<CapacitacionRespuesta> capacitacionRespuestasAdminListarByPregunta(Integer idCapacitacionPregunta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RESPUESTAS_OBTENER_BY_PREGUNTA)
                .addParameter("var_id_capacitacion_pregunta", Types.INTEGER, idCapacitacionPregunta)
                .setDaoDefinition(capacitacionRespuestaDaoDefinition)
                .build();
    }

    @Override
    public Integer capacitacionRespuestaAdminMantenimiento(CapacitacionRespuesta capacitacionRespuesta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RESPUESTA_MANTENIMIENTO)
                .addParameter("var_id_capacitacion_respuesta", Types.INTEGER, capacitacionRespuesta.getIdCapacitacionRespuesta())
                .addParameter("var_id_capacitacion", Types.INTEGER, capacitacionRespuesta.getIdCapacitacion())
                .addParameter("var_id_capacitacion_pregunta", Types.INTEGER, capacitacionRespuesta.getIdCapacitacionPregunta())
                .addParameter("var_id_tipo_pregunta", Types.INTEGER, capacitacionRespuesta.getIdTipoPregunta())
                .addParameter("var_respuesta", Types.VARCHAR, capacitacionRespuesta.getRespuesta())
                .addParameter("var_es_correcta", Types.BOOLEAN, capacitacionRespuesta.getEsCorrecta())
                .addParameter("var_id_usuario_creacion", Types.INTEGER, capacitacionRespuesta.getAuditoria().getIdUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public void capacitacionRespuestasAdminOrdenar(String lista) {
        DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_RESPUESTAS_ORDENAR)
                .addParameter("var_lista", Types.VARCHAR, lista)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer capacitacionPreguntaAdminNuevo(CapacitacionPregunta capacitacionPregunta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_ADMIN_PREGUNTA_NUEVO)
                .addParameter("var_id_capacitacion", Types.INTEGER, capacitacionPregunta.getIdCapacitacion())
                .addParameter("var_id_tipo_pregunta", Types.INTEGER, capacitacionPregunta.getIdTipoPregunta())
                .addParameter("var_pregunta", Types.VARCHAR, capacitacionPregunta.getPregunta())
                .addParameter("var_id_usuario_creacion", Types.INTEGER, capacitacionPregunta.getAuditoria().getIdUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarPuntosCapacitacionParticipante(CapacitacionParticipante capacitacionParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_PARTICIPANTE_REGISTRAR_PUNTOS)
                .addParameter("var_id_capacitacion", Types.INTEGER, capacitacionParticipante.getIdCapacitacion())
                .addParameter("var_id_participante", Types.INTEGER, capacitacionParticipante.getIdParticipante())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
