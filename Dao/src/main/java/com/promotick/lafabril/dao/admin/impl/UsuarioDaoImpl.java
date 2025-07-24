package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.UsuarioDao;
import com.promotick.lafabril.dao.admin.definition.UsuarioDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class UsuarioDaoImpl extends DaoGenerator implements UsuarioDao {

    private UsuarioDaoDefinition usuarioDaoDefinition;

    @Autowired
    public UsuarioDaoImpl(JdbcTemplate jdbcTemplate, UsuarioDaoDefinition usuarioDaoDefinition) {
        super(jdbcTemplate);
        this.usuarioDaoDefinition = usuarioDaoDefinition;
    }

    @Override
    public Usuario autenticar(Usuario usuario) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_USUARIO_AUTENTICAR)
                .addParameter("var_usuario", Types.VARCHAR, usuario.getUsuario())
                .setDaoDefinition(usuarioDaoDefinition)
                .build(Usuario.class);
    }

    @Override
    public List<Usuario> listarUsuarios(Integer idUsuario, Integer orden) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_USUARIO_LISTAR)
                .addParameter("var_id_usuario", Types.INTEGER, idUsuario)
                .addParameter("var_orden", Types.INTEGER, orden)
                .setDaoDefinition(usuarioDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarUsuario(Usuario usuario) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_USUARIO_ACTUALIZAR)
                .addParameter("var_accion", Types.INTEGER, usuario.getAccion())
                .addParameter("var_id_usuario", Types.INTEGER, usuario.getIdUsuario())
                .addParameter("var_nombres_usuario", Types.VARCHAR, usuario.getNombresUsuario())
                .addParameter("var_apellidopat_usuario", Types.VARCHAR, usuario.getApellidoPaterno())
                .addParameter("var_apellidomat_usuario", Types.VARCHAR, usuario.getApellidoMaterno())
                .addParameter("var_correo_usuario", Types.VARCHAR, usuario.getCorreoUsuario())
                .addParameter("var_usuario", Types.VARCHAR, usuario.getUsuario())
                .addParameter("var_clave", Types.VARCHAR, usuario.getClave())
                .addParameter("var_id_rol", Types.INTEGER, usuario.getRol() != null ? usuario.getRol().getIdRol() : null)
                .addParameter("var_estado_usuario", Types.INTEGER, usuario.getEstadoUsuario())
                .addParameter("var_usuario_creacion", Types.VARCHAR, usuario.getAuditoria().getUsuarioCreacion())
                .addParameter("var_usuario_actualizacion", Types.VARCHAR, usuario.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

}
