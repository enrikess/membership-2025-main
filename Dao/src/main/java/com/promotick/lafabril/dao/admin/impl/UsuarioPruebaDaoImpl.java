package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.UsuarioPruebaDao;
import com.promotick.lafabril.dao.admin.definition.UsuarioPruebaDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class UsuarioPruebaDaoImpl extends DaoGenerator implements UsuarioPruebaDao {

    private UsuarioPruebaDaoDefinition usuarioPruebaDaoDefinition;

    @Autowired
    public UsuarioPruebaDaoImpl(JdbcTemplate jdbcTemplate, UsuarioPruebaDaoDefinition usuarioPruebaDaoDefinition) {
        super(jdbcTemplate);
        this.usuarioPruebaDaoDefinition = usuarioPruebaDaoDefinition;
    }

    @Override
    public List<UsuarioPrueba> listarUsuariosPrueba(String nroDocumento, Integer orden) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_USUARIO_PRUEBA_LISTAR)
                .addParameter("var_nro_documento", Types.VARCHAR, nroDocumento)
                .addParameter("var_orden", Types.INTEGER, orden)
                .setDaoDefinition(usuarioPruebaDaoDefinition)
                .build();
    }

    @Override
    public Integer registrarUsuarioPrueba(UsuarioPrueba usuarioPrueba) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_USUARIO_PRUEBA_REGISTRAR)
                .addParameter("var_id_participante", Types.INTEGER, usuarioPrueba.getParticipante().getIdParticipante())
                .addParameter("var_estado_usuario_prueba", Types.INTEGER, usuarioPrueba.getEstadoUsuarioPrueba())
                .addParameter("var_usuario_registro", Types.VARCHAR, usuarioPrueba.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }


    @Override
    public Integer actualizarEstadoUsuarioPrueba(UsuarioPrueba usuarioPrueba) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_USUARIO_PRUEBA_ACTUALIZAR_ESTADO)
                .addParameter("var_id_usuario_prueba", Types.INTEGER, usuarioPrueba.getIdUsuarioPrueba())
                .addParameter("var_estado_usuario_prueba", Types.INTEGER, usuarioPrueba.getEstadoUsuarioPrueba())
                .addParameter("var_usuario_actualizacion", Types.VARCHAR, usuarioPrueba.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarParticipantePrueba(Participante participante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTE_PRUEBA_REGISTRAR)
                .addParameter("var_id_catalogo", Types.INTEGER, participante.getCatalogo().getIdCatalogo())
                .addParameter("var_nombre", Types.VARCHAR, participante.getNombresParticipante())
                .addParameter("var_appaterno", Types.VARCHAR, participante.getAppaternoParticipante())
                .addParameter("var_apmaterno", Types.VARCHAR, participante.getApmaternoParticipante())
                .addParameter("var_id_tipo_documento", Types.INTEGER, participante.getTipoDocumento().getIdTipoDocumento())
                .addParameter("var_nro_doc", Types.VARCHAR, participante.getNroDocumento())
                .addParameter("var_usuario_creacion", Types.VARCHAR, participante.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public String obtenerUsuariosPrueba() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTE_PRUEBA_OBTENER)
                .setReturnDaoParameter("resultado", Types.VARCHAR)
                .build(String.class);
    }
}
