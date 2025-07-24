package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.ContactoDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Contacto;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class ContactoDaoImpl extends DaoGenerator implements ContactoDao {

    @Autowired
    public ContactoDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer guardarContacto(Contacto contacto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CONTACTO_REGISTRAR)
                .addParameter("var_nombres", Types.VARCHAR, contacto.getNombres())
                .addParameter("var_apellidos", Types.VARCHAR, contacto.getApellidos())
                .addParameter("var_email", Types.VARCHAR, contacto.getEmail())
                .addParameter("var_telefono", Types.VARCHAR, contacto.getTelefono())
                .addParameter("var_mensaje", Types.VARCHAR, contacto.getMensaje())
                .addParameter("var_usuario_creacion", Types.VARCHAR, contacto.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

}
