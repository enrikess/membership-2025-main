package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.DireccionDao;
import com.promotick.lafabril.dao.web.definition.ParticipanteDireccionDaoDefinition;
import com.promotick.lafabril.dao.web.definition.TipoViviendaDaoDefinition;
import com.promotick.lafabril.dao.web.definition.ZonaDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.web.definition.DireccionDaoDefinition;
import com.promotick.lafabril.model.web.Direccion;
import com.promotick.lafabril.model.web.ParticipanteDireccion;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Zona;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class DireccionDaoImpl extends DaoGenerator implements DireccionDao {

    private final DireccionDaoDefinition direccionDaoDefinition;
    private final ZonaDaoDefinition zonaDaoDefinition;
    private final TipoViviendaDaoDefinition tipoViviendaDaoDefinition;
    private final ParticipanteDireccionDaoDefinition participanteDireccionDaoDefinition;

    @Autowired
    public DireccionDaoImpl(JdbcTemplate jdbcTemplate, DireccionDaoDefinition direccionDaoDefinition, ZonaDaoDefinition zonaDaoDefinition, TipoViviendaDaoDefinition tipoViviendaDaoDefinition, ParticipanteDireccionDaoDefinition participanteDireccionDaoDefinition) {
        super(jdbcTemplate);
        this.direccionDaoDefinition = direccionDaoDefinition;
        this.zonaDaoDefinition = zonaDaoDefinition;
        this.tipoViviendaDaoDefinition = tipoViviendaDaoDefinition;
        this.participanteDireccionDaoDefinition = participanteDireccionDaoDefinition;
    }

    @Override
    public Direccion direccionParticipante(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_DIRECCION)
                .addParameter("var_idparticipante", Types.INTEGER, idParticipante)
                .setDaoDefinition(direccionDaoDefinition)
                .build(Direccion.class);
    }

    @Override
    public Integer registrarDireccion(Direccion direccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_DIRECCION_REGISTRAR)
                .addParameter("var_id_pais", Types.VARCHAR, direccion.getUbigeo().getCodpais())
                .addParameter("var_coddep", Types.VARCHAR, direccion.getUbigeo().getCoddep())
                .addParameter("var_codprov", Types.VARCHAR, direccion.getUbigeo().getCodprov())
                .addParameter("var_coddis", Types.VARCHAR, direccion.getUbigeo().getCoddist())
                .addParameter("var_direccion", Types.VARCHAR, direccion.getDireccionCalle())
                .addParameter("var_referencia", Types.VARCHAR, direccion.getReferencia())
                .addParameter("var_id_zona", Types.INTEGER, direccion.getZona()==null?1:direccion.getZona().getIdZona())
                .addParameter("var_id_tipo_vivienda", Types.INTEGER, direccion.getTipoVivienda()==null?1:direccion.getTipoVivienda().getIdTipoVivienda())
                .addParameter("var_usuario", Types.VARCHAR, direccion.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Zona> listarZonas() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_ZONAS_LISTAR)
                .setDaoDefinition(zonaDaoDefinition)
                .build();
    }

    @Override
    public List<TipoVivienda> listarTipoViviendas() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_TIPO_VIVIENDA_LISTAR)
                .setDaoDefinition(tipoViviendaDaoDefinition)
                .build();
    }

    @Override
    public Integer registroDireccionCarga(Direccion direccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_DIRECCIONES_CARGAR)
                .addParameter("var_nro_documento", Types.VARCHAR, direccion.getNroDocumento())
                .addParameter("var_direccion_calle", Types.VARCHAR, direccion.getDireccionCalle())
                .addParameter("var_referencia", Types.VARCHAR, direccion.getReferencia())
                .addParameter("var_codubigeo", Types.VARCHAR, direccion.getUbigeo().getCodubigeo())
                .addParameter("var_id_tipo_vivienda", Types.INTEGER, direccion.getTipoVivienda().getIdTipoVivienda())
                .addParameter("var_id_zona", Types.INTEGER, direccion.getZona().getIdZona())
                .addParameter("var_usuario_creacion", Types.VARCHAR, direccion.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ParticipanteDireccion> listarDireccionesParticipante(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_DIRECCIONES_PARTICIPANTE_LISTAR)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(participanteDireccionDaoDefinition)
                .build();
    }

    @Override
    public Direccion direccionObtener(Integer idDireccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_DIRECCION_OBTENER)
                .addParameter("var_id_participante", Types.INTEGER, idDireccion)
                .setDaoDefinition(direccionDaoDefinition)
                .build(Direccion.class);
    }

    @Override
    public Integer registrarDireccionParticipante(ParticipanteDireccion participanteDireccion) {
        Direccion direccion = participanteDireccion.getDireccion();
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_DIRECCION_PARTICIPANTE_REGISTRAR)
                .addParameter("var_id_participante_direccion", Types.INTEGER, participanteDireccion.getIdParticipanteDireccion())
                .addParameter("var_id_participante", Types.INTEGER, participanteDireccion.getIdParticipante())
                .addParameter("var_tag_direccion", Types.VARCHAR, participanteDireccion.getTagDireccion())
                .addParameter("var_id_pais", Types.VARCHAR, direccion.getUbigeo().getCodpais())
                .addParameter("var_coddep", Types.VARCHAR, direccion.getUbigeo().getCoddep())
                .addParameter("var_codprov", Types.VARCHAR, direccion.getUbigeo().getCodprov())
                .addParameter("var_coddis", Types.VARCHAR, direccion.getUbigeo().getCoddist())
                .addParameter("var_direccion", Types.VARCHAR, direccion.getDireccionCalle())
                .addParameter("var_referencia", Types.VARCHAR, direccion.getReferencia())
                .addParameter("var_id_zona", Types.INTEGER, direccion.getZona()==null?1:direccion.getZona().getIdZona())
                .addParameter("var_id_tipo_vivienda", Types.INTEGER, direccion.getTipoVivienda()==null?1:direccion.getTipoVivienda().getIdTipoVivienda())
                .addParameter("var_usuario", Types.VARCHAR, direccion.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer eliminarDireccionParticipante(Integer idParticipante, Integer idParticipanteDireccion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_DIRECCIONES_PARTICIPANTE_ELIMINAR)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_id_participante_direccion", Types.INTEGER, idParticipanteDireccion)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
