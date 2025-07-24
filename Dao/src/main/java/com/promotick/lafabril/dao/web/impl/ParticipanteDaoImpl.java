package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.web.definition.*;
import com.promotick.lafabril.model.web.*;
import com.promotick.configuration.utils.dao.DaoParameter;
import com.promotick.lafabril.dao.admin.definition.ReporteParticipanteExcelDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.ParticipanteDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import com.promotick.lafabril.model.reporte.ReporteParticipante;
import com.promotick.lafabril.model.util.FiltroParticipante;
import com.promotick.lafabril.model.util.FiltroParticipanteTransaccion;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class ParticipanteDaoImpl extends DaoGenerator implements ParticipanteDao {

    private ParticipanteDaoDefinition participanteDaoDefinition;
    private ParticipanteTransaccionDaoDefinition participanteTransaccionDaoDefinition;
    private ReporteParticipanteExcelDaoDefinition reporteParticipanteExcelDaoDefinition;
    private CategoriaParticipanteDaoDefinition categoriaParticipanteDaoDefinition;
    private ParticipanteMetaDaoDefinition participanteMetaDaoDefinition;
    private BrokerDaoDefinition brokerDaoDefinition;
    private RegionalDaoDefinition regionalDaoDefinition;

    @Autowired
    public ParticipanteDaoImpl(JdbcTemplate jdbcTemplate, ParticipanteDaoDefinition participanteDaoDefinition, ParticipanteTransaccionDaoDefinition participanteTransaccionDaoDefinition, ReporteParticipanteExcelDaoDefinition reporteParticipanteExcelDaoDefinition, CategoriaParticipanteDaoDefinition categoriaParticipanteDaoDefinition, ParticipanteMetaDaoDefinition participanteMetaDaoDefinition, BrokerDaoDefinition brokerDaoDefinition, RegionalDaoDefinition regionalDaoDefinition) {
        super(jdbcTemplate);
        this.participanteDaoDefinition = participanteDaoDefinition;
        this.participanteTransaccionDaoDefinition = participanteTransaccionDaoDefinition;
        this.reporteParticipanteExcelDaoDefinition = reporteParticipanteExcelDaoDefinition;
        this.categoriaParticipanteDaoDefinition = categoriaParticipanteDaoDefinition;
        this.participanteMetaDaoDefinition = participanteMetaDaoDefinition;
        this.brokerDaoDefinition = brokerDaoDefinition;
        this.regionalDaoDefinition = regionalDaoDefinition;
    }

    @Override
    public Participante obtenerParticipantePorNroDocumento(String nroDocumento) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_OBTENER_POR_NRO_DOCUMENTO)
                .addParameter("var_nro_documento", Types.VARCHAR, nroDocumento)
                .setDaoDefinition(participanteDaoDefinition)
                .build(Participante.class);
    }

    @Override
    public List<Participante> participantesListar(FiltroParticipante filtroParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, filtroParticipante.getIdCatalogo())
                .addParameter("var_orden", Types.INTEGER, filtroParticipante.getOrden())
                .addParameter("var_buscar", Types.VARCHAR, filtroParticipante.getBuscar())
                .addParameter("var_inicio", Types.INTEGER, filtroParticipante.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroParticipante.getLength())
                .setDaoDefinition(participanteDaoDefinition)
                .build();
    }

    @Override
    public Integer participantesContar(FiltroParticipante filtroParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_CONTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, filtroParticipante.getIdCatalogo())
                .addParameter("var_orden", Types.INTEGER, filtroParticipante.getOrden())
                .addParameter("var_buscar", Types.VARCHAR, filtroParticipante.getBuscar())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarEstadoParticipante(Participante participante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_ACTUALIZAR_ESTADO)
                .addParameter("var_participante", Types.INTEGER, participante.getIdParticipante())
                .addParameter("var_estado_participante", Types.INTEGER, participante.getEstadoParticipante())
                .addParameter("var_usuario_actualizacion", Types.VARCHAR, participante.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarEstadoParticipanteCanje(Participante participante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_ACTUALIZAR_ESTADO_CANJE)
                .addParameter("var_participante", Types.INTEGER, participante.getIdParticipante())
                .addParameter("var_estado_participante", Types.INTEGER, participante.getEstadoParticipante())
                .addParameter("var_usuario_actualizacion", Types.VARCHAR, participante.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registroParticipante(Participante participante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_REGISTRO)
                .addParameter("var_nombres_participante", Types.VARCHAR, participante.getNombresParticipante())
                .addParameter("var_appaterno_participante", Types.VARCHAR, participante.getAppaternoParticipante())
                .addParameter("var_apmaterno_participante", Types.VARCHAR, participante.getApmaternoParticipante())
                .addParameter("var_nro_documento", Types.VARCHAR, participante.getNroDocumento())
                .addParameter("var_email_participante", Types.VARCHAR, participante.getEmailParticipante())
                .addParameter("var_movil_participante", Types.VARCHAR, participante.getMovilParticipante())
                .addParameter("var_codpais", Types.VARCHAR, participante.getDireccion().getUbigeo().getCodpais())
                .addParameter("var_coddep", Types.VARCHAR, participante.getDireccion().getUbigeo().getCoddep())
                .addParameter("var_codprov", Types.VARCHAR, participante.getDireccion().getUbigeo().getCodprov())
                .addParameter("var_coddist", Types.VARCHAR, participante.getDireccion().getUbigeo().getCoddist())
                .addParameter("var_ciudad", Types.VARCHAR, participante.getCiudad())
                .addParameter("var_acepta_tyc", Types.BOOLEAN, participante.getAceptaTyc())
                .addParameter("var_acepta_uso_datos", Types.BOOLEAN, participante.getAceptaUsoDatos())
                .addParameter("var_usuario_creacion", Types.VARCHAR, participante.getNroDocumento())
                .addParameter("var_clave_participante", Types.VARCHAR, participante.getClaveParticipante())
                .addParameter("var_id_petshop", Types.INTEGER, participante.getPetshop().getIdPetshop())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Participante> topBrokerListar(Integer idParticipante, Integer idCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_TOP_BROKER)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .setDaoDefinition(participanteDaoDefinition)
                .build();
    }

    @Override
    public Integer participanteActualizarFoto(Integer idParticipante, String foto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_ACTUALIZAR_FOTO)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_foto", Types.VARCHAR, foto)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public ParticipanteMeta participanteMetaObtener(Integer idParticipante, Integer tipoMeta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_META_LISTAR)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_id_tipo_meta", Types.INTEGER, tipoMeta)
                .setDaoDefinition(participanteMetaDaoDefinition)
                .build(ParticipanteMeta.class);
    }

    @Override
    public ParticipanteMeta participanteMetaObtenerByAvance(Integer idParticipante, Date fechaOperacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_META_OBTENER_BY_AVANCE)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_fecha_operacion", Types.DATE, fechaOperacion)
                .setDaoDefinition(participanteMetaDaoDefinition)
                .build(ParticipanteMeta.class);
    }

    @Override
    public Integer registrarFacturaParticipante(String filename, Integer idParticipante) {
        return DaoBuilder
                .getInstance(this)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_REGISTRAR_FACTURA)
                .addParameter(new DaoParameter("var_id_participante", Types.INTEGER, idParticipante))
                .addParameter(new DaoParameter("var_filename", Types.VARCHAR, filename))
                .setReturnDaoParameter(new DaoParameter("resultado", Types.INTEGER))
                .build(Integer.class);
    }

    @Override
    public Integer participanteAprobar(Participante participante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_APROBAR)
                .addParameter("var_id_participante", Types.INTEGER, participante.getIdParticipante())
                .addParameter("var_aprobacion", Types.BOOLEAN, participante.getAprobar())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer participanteAprobarByDocumento(Participante participante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_APROBAR_DOCUMENTO)
                .addParameter("var_nro_documento", Types.VARCHAR, participante.getNroDocumento())
                .addParameter("var_aprobacion", Types.BOOLEAN, participante.getAprobar())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Participante participanteAprobarMailBienvenidaObtener(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_ACTIVACION_INDIVIDAL_OBTENER)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(participanteDaoDefinition)
                .build(Participante.class);
    }

    @Override
    public List<Broker> listarBroker() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_BROKER_LISTAR)
                .setDaoDefinition(brokerDaoDefinition)
                .build();
    }

    @Override
    public List<Regional> listarRegional() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REGIONAL_LISTAR)
                .setDaoDefinition(regionalDaoDefinition)
                .build();
    }

    @Override
    public Participante obtenerByID(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_OBTENER)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(participanteDaoDefinition)
                .build(Participante.class);
    }

    @Override
    public Integer actualizarParticipante(Participante participante) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTES_ACTUALIZAR)
                .addParameter("var_accion", Types.INTEGER, participante.getAccion())
                .addParameter("var_id_participante", Types.INTEGER, participante.getIdParticipante())
                .addParameter("var_id_catalogo", Types.INTEGER, participante.getCatalogo() == null ? null : participante.getCatalogo().getIdCatalogo())
                .addParameter("var_nro_documento", Types.VARCHAR, participante.getNroDocumento())
                .addParameter("var_nombres_participante", Types.VARCHAR, participante.getNombresParticipante())
                .addParameter("var_appaterno_participante", Types.VARCHAR, participante.getAppaternoParticipante())
                .addParameter("var_apmaterno_participante", Types.VARCHAR, participante.getApmaternoParticipante())
                .addParameter("var_email", Types.VARCHAR, participante.getEmailParticipante())
                .addParameter("var_telefono", Types.VARCHAR, participante.getTelefonoParticipante())
                .addParameter("var_movil", Types.VARCHAR, participante.getMovilParticipante())
                .addParameter("var_usuario_creacion", Types.VARCHAR, participante.getAuditoria() == null ? null : participante.getAuditoria().getUsuarioCreacion())
                .addParameter("var_clave", Types.VARCHAR, participante.getClaveParticipante())
                .addParameter("var_direccion", Types.VARCHAR, participante.getDireccion() == null ? null : participante.getDireccion().getDireccionCalle())
                .addParameter("var_referencia", Types.VARCHAR, participante.getDireccion() == null ? null : participante.getDireccion().getReferencia())
                .addParameter("var_token", Types.VARCHAR, participante.getTransaccionToken() == null ? null : participante.getTransaccionToken().getToken())
                .addParameter("var_fecha_nacimiento", Types.DATE, participante.getFechaNacimiento())
                .addParameter("var_tipo_direccion", Types.INTEGER, participante.getDireccion() == null ? null : (participante.getDireccion().getTipo()))
                .addParameter("var_id_direccion", Types.INTEGER, participante.getDireccion() == null ? null : (participante.getDireccion().getIdDireccion()))
                .addParameter("var_id_zona", Types.INTEGER, participante.getDireccion() == null ? null : (participante.getDireccion().getZona() == null ? null : participante.getDireccion().getZona().getIdZona()))
                .addParameter("var_id_tipo_vivienda", Types.INTEGER, participante.getDireccion() == null ? null : (participante.getDireccion().getTipoVivienda() == null ? null : participante.getDireccion().getTipoVivienda().getIdTipoVivienda()))
                .addParameter("var_codpais", Types.VARCHAR, participante.getDireccion() == null ? null : (participante.getDireccion().getUbigeo() == null ? null : participante.getDireccion().getUbigeo().getCodpais()))
                .addParameter("var_coddep", Types.VARCHAR, participante.getDireccion() == null ? null : (participante.getDireccion().getUbigeo() == null ? null : participante.getDireccion().getUbigeo().getCoddep()))
                .addParameter("var_codprov", Types.VARCHAR, participante.getDireccion() == null ? null : (participante.getDireccion().getUbigeo() == null ? null : participante.getDireccion().getUbigeo().getCodprov()))
                .addParameter("var_coddist", Types.VARCHAR, participante.getDireccion() == null ? null : (participante.getDireccion().getUbigeo() == null ? null : participante.getDireccion().getUbigeo().getCoddist()))
                .addParameter("var_cedula", Types.VARCHAR, participante.getCedula())
                .addParameter("var_clasificacion", Types.VARCHAR, participante.getClasificacion())
                .addParameter("var_cod_cliente", Types.VARCHAR, participante.getCodCliente())
                .addParameter("var_genero", Types.VARCHAR, participante.getGenero())
                .addParameter("var_estado_participante", Types.INTEGER, participante.getEstadoParticipante())
                .addParameter("var_id_tipo_documento", Types.INTEGER, participante.getTipoDocumento() != null ? participante.getTipoDocumento().getIdTipoDocumento() : null)
                .addParameter("var_email_enviado", Types.BOOLEAN, participante.getEmailEnviado())
                .addParameter("var_email_observacion", Types.VARCHAR, participante.getEmailObservacion())
                .addParameter("var_cliente", Types.VARCHAR, participante.getCliente())
                .addParameter("var_madre_padre", Types.VARCHAR, participante.getMadrePadre())
                .addParameter("var_estado_civil", Types.VARCHAR, participante.getEstadoCivil())
                .addParameter("var_nro_hijos", Types.INTEGER, participante.getNroHijos())
                .addParameter("var_id_concesionario", Types.INTEGER, participante.getConcesionario() != null ? participante.getConcesionario().getIdConcesionario() : null)
                .addParameter("var_id_tipo_participante", Types.INTEGER, participante.getIdTipoParticipante())
                .addParameter("var_vendedor", Types.VARCHAR, participante.getNombreVendedor())
                .addParameter("var_id_categoria", Types.INTEGER, participante.getIdCategoriaParticipante())
//                .addParameter("var_id_subtipo_participante", Types.INTEGER, participante.getSubtipoParticipante() != null ? participante.getSubtipoParticipante().getIdSubtipoParticipante() : null)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarParticipantePerfil(Participante participante) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_ACTUALIZAR_PERFIL)
                .addParameter("var_id_participante", Types.INTEGER, participante.getIdParticipante())
                .addParameter("var_telefono", Types.VARCHAR, participante.getTelefonoParticipante())
                .addParameter("var_movil", Types.VARCHAR, participante.getMovilParticipante())
                .addParameter("var_email", Types.VARCHAR, participante.getEmailParticipante())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Participante loginParticipante(Integer idTipoDocumento, String usuario) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_LOGIN_PARTICIPANTE)
                .addParameter("var_tipo_documento", Types.INTEGER, idTipoDocumento)
                .addParameter("var_usuario", Types.VARCHAR, usuario)
                .setDaoDefinition(participanteDaoDefinition)
                .build(Participante.class);
    }

    @Override
    public Integer puntosDisponiblesParticipante(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_DISPONIBLES)
                .addParameter("var_idparticipante", Types.INTEGER, idParticipante)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);

    }

    @Override
    public Integer puntosAcumuladosParticipante(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_ACUMULADOS)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_mes", Types.INTEGER, 0)
                .addParameter("var_anio", Types.INTEGER, 0)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer puntosCanjeadosParticipante(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_CANJEADOS)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_mes", Types.INTEGER, 0)
                .addParameter("var_anio", Types.INTEGER, 0)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer puntosPosiblesParticipante(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_POSIBLES)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_mes", Types.INTEGER, 0)
                .addParameter("var_anio", Types.INTEGER, 0)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Participante obtenerParticipanteByEmail(String email) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_OBTENER_EMAIL)
                .addParameter("var_email", Types.VARCHAR, email)
                .setDaoDefinition(participanteDaoDefinition)
                .build(Participante.class);
    }

    @Override
    public Date ultimaActualizacionParticipante(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_ULTIMA_ACTUALIZACION)
                .addParameter("var_idparticipante", Types.INTEGER, idParticipante)
                .setReturnDaoParameter("resultado", Types.DATE)
                .build(Date.class);
    }

    @Override
    public List<ParticipanteTransaccion> obtenerMisPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_TRANSACCION_LISTAR)
                .addParameter("var_id_tipo", Types.INTEGER, filtroParticipanteTransaccion.getTipo())
                .addParameter("var_id_participante", Types.INTEGER, filtroParticipanteTransaccion.getParticipante().getIdParticipante())
                .addParameter("var_fecha_inicial", Types.VARCHAR, filtroParticipanteTransaccion.getFinicio())
                .addParameter("var_fecha_final", Types.VARCHAR, filtroParticipanteTransaccion.getFfin())
                .addParameter("var_tamanio", Types.INTEGER, filtroParticipanteTransaccion.getLength())
                .addParameter("var_inicio", Types.INTEGER, filtroParticipanteTransaccion.getStart())
                .setDaoDefinition(participanteTransaccionDaoDefinition)
                .build();
    }

    @Override
    public Integer contarMisPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_TRANSACCION_CONTAR)
                .addParameter("var_id_tipo", Types.INTEGER, filtroParticipanteTransaccion.getTipo())
                .addParameter("var_id_participante", Types.INTEGER, filtroParticipanteTransaccion.getParticipante().getIdParticipante())
                .addParameter("var_fecha_inicial", Types.VARCHAR, filtroParticipanteTransaccion.getFinicio())
                .addParameter("var_fecha_final", Types.VARCHAR, filtroParticipanteTransaccion.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarDatosParticipante(Participante participante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_ACTUALIZAR_DATOS)
                .addParameter("var_id_participante", Types.INTEGER, participante.getIdParticipante())
                .addParameter("var_fecha_nacimiento", Types.DATE, participante.getFechaNacimiento())
                .addParameter("var_telefono_participante", Types.VARCHAR, participante.getTelefonoParticipante())
                .addParameter("var_movil_participante", Types.VARCHAR, participante.getMovilParticipante())
                .addParameter("var_email_participante", Types.VARCHAR, participante.getEmailParticipante())
                .addParameter("var_codpais", Types.VARCHAR, participante.getDireccion().getUbigeo().getCodpais())
                .addParameter("var_coddep", Types.VARCHAR, participante.getDireccion().getUbigeo().getCoddep())
                .addParameter("var_codprov", Types.VARCHAR, participante.getDireccion().getUbigeo().getCodprov())
                .addParameter("var_coddist", Types.VARCHAR, participante.getDireccion().getUbigeo().getCoddist())
                .addParameter("var_genero", Types.VARCHAR, participante.getGenero())
                .addParameter("var_acepta_tyc", Types.BOOLEAN, participante.getAceptaTyc())
                .addParameter("var_acepta_uso_datos", Types.BOOLEAN, participante.getAceptaUsoDatos())
                .addParameter("var_acepta_comunicacion", Types.BOOLEAN, participante.getAceptaComunicacion())
                .addParameter("var_usuario_creacion", Types.VARCHAR, participante.getAuditoria().getUsuarioCreacion())
                .addParameter("var_estado_civil", Types.VARCHAR, participante.getEstadoCivil())
                .addParameter("var_nro_hijos", Types.INTEGER, participante.getNroHijos())
                .addParameter("var_id_region", Types.INTEGER, participante.getRegion().getIdRegion())
                .addParameter("var_ciudad", Types.VARCHAR, participante.getCiudad())
                .addParameter("var_fecha_aniversario_local", Types.DATE, participante.getFechaAniversarioLocal())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer puntosAcumuladosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_ACUMULADOS_POR_FECHA)
                .addParameter("var_id_participante", Types.INTEGER, filtroParticipanteTransaccion.getParticipante().getIdParticipante())
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroParticipanteTransaccion.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroParticipanteTransaccion.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer puntosCanjeadosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_CANJEADOS_POR_FECHA)
                .addParameter("var_id_participante", Types.INTEGER, filtroParticipanteTransaccion.getParticipante().getIdParticipante())
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroParticipanteTransaccion.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroParticipanteTransaccion.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer puntosPosiblesParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_POSIBLES_POR_FECHA)
                .addParameter("var_id_participante", Types.INTEGER, filtroParticipanteTransaccion.getParticipante().getIdParticipante())
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroParticipanteTransaccion.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroParticipanteTransaccion.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer puntosDescargadosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_DESCARGADOS_POR_FECHA)
                .addParameter("var_id_participante", Types.INTEGER, filtroParticipanteTransaccion.getParticipante().getIdParticipante())
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroParticipanteTransaccion.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroParticipanteTransaccion.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Participante> listarParticipanteEmail(Integer tipoEmail) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PARTICIPANTE_EMAILS_LISTAR)
                .addParameter("var_tipo", Types.INTEGER, tipoEmail)
                .setDaoDefinition(participanteDaoDefinition)
                .build();
    }

    @Override
    public Integer periodoParticipanteEnvioEmail(Participante participante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PERIODO_PARTICIPANTE_ENVIO_EMAIL)
                .addParameter("var_id_participante", Types.INTEGER, participante.getIdParticipante())
                .addParameter("var_email_enviado", Types.BOOLEAN, participante.getEmailEnviado())
                .addParameter("var_email_observacion", Types.VARCHAR, participante.getEmailObservacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer periodoParticipanteEnvioEmail(PeriodoParticipante periodoParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PERIODO_PARTICIPANTE_ENVIO_EMAIL_INDEPENDIENTE)
                .addParameter("var_id_periodo_participante", Types.INTEGER, periodoParticipante.getIdPeriodoParticipante())
                .addParameter("var_email_enviado", Types.BOOLEAN, periodoParticipante.getEmailEnviado())
                .addParameter("var_email_observacion", Types.VARCHAR, periodoParticipante.getEmailObservacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Participante> listarCumpleanos() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CUMPLEADOS_LISTAR)
                .setDaoDefinition(participanteDaoDefinition)
                .build();
    }

    @Override
    public Integer puntosVencidosParticipante(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_PUNTOS_VENCIDOS_POR_FECHA)
                .addParameter("var_id_participante", Types.INTEGER, filtroParticipanteTransaccion.getParticipante().getIdParticipante())
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroParticipanteTransaccion.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroParticipanteTransaccion.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Participante> listarParticipantesFestividades() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_LISTAR_FESTIVIDADES_PARTICIPANTES)
                .setDaoDefinition(participanteDaoDefinition)
                .build();
    }


    @Override
    public Integer obtenerParticipante(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTE_PORCENTAJE)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteParticipante> reporteParticipantes(FiltroParticipante filtroParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTE_REPORTE_EXCEL)
                .setDaoDefinition(reporteParticipanteExcelDaoDefinition)
                .build();
    }

    @Override
    public List<CategoriaParticipante> listarCategoriaParticipante() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATEGORIA_PARTICIPANTE_LISTAR)
                .setDaoDefinition(categoriaParticipanteDaoDefinition)
                .build();
    }
}
