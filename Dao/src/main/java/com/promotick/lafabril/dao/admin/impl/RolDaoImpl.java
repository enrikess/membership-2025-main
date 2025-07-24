package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.RolDao;
import com.promotick.lafabril.dao.admin.definition.RolDaoDefinition;
import com.promotick.lafabril.dao.admin.definition.RolUrlDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Rol;
import com.promotick.lafabril.model.admin.RolUrl;
import com.promotick.lafabril.model.util.FiltroRoles;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class RolDaoImpl extends DaoGenerator implements RolDao {

    private RolUrlDaoDefinition rolUrlDaoDefinition;
    private RolDaoDefinition rolDaoDefinition;

    @Autowired
    public RolDaoImpl(JdbcTemplate jdbcTemplate, RolUrlDaoDefinition rolUrlDaoDefinition, RolDaoDefinition rolDaoDefinition) {
        super(jdbcTemplate);
        this.rolUrlDaoDefinition = rolUrlDaoDefinition;
        this.rolDaoDefinition = rolDaoDefinition;
    }

    @Override
    public List<Rol> obtenerRoles(FiltroRoles filtroRoles) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_ROL_LISTAR)
                .addParameter("var_id_rol", Types.INTEGER, filtroRoles.getIdRol())
                .addParameter("var_estado_rol", Types.INTEGER, filtroRoles.getEstadoRol())
                .addParameter("var_tamanio", Types.INTEGER, filtroRoles.getLength())
                .addParameter("var_inicio", Types.INTEGER, filtroRoles.getStart())
                .setDaoDefinition(rolDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarRol(Rol rol) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_ROL_ACTUALZAR)
                .addParameter("var_accion", Types.INTEGER, rol.getAccion())
                .addParameter("var_id_rol", Types.INTEGER, rol.getIdRol())
                .addParameter("var_nombre_rol", Types.VARCHAR, rol.getNombreRol())
                .addParameter("var_descripcion_rol", Types.VARCHAR, rol.getDescripcionRol())
                .addParameter("var_estado_rol", Types.INTEGER, rol.getEstadoRol())
                .addParameter("var_ids_menus", Types.VARCHAR, rol.getIdMenus())
                .addParameter("var_usuario_creacion", Types.VARCHAR, rol.getAuditoria().getUsuarioCreacion())
                .addParameter("var_usuario_actualizacion", Types.VARCHAR, rol.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer contarRol() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_ROL_CONTAR)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<RolUrl> obtenerRolesUrl() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_ROL_URL_LISTAR)
                .setDaoDefinition(rolUrlDaoDefinition)
                .build();
    }

}
