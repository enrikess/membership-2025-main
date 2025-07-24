package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.TransaccionTokenDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.TransaccionToken;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class TransaccionTokenDaoImpl extends DaoGenerator implements TransaccionTokenDao {

    @Autowired
    public TransaccionTokenDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer guardarTransaccionToken(TransaccionToken transaccionToken) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_TRANSACCION_TOKEN_REGISTRAR)
                .addParameter("var_id_entidad", Types.INTEGER, transaccionToken.getIdEntidad())
                .addParameter("var_tipo_transaccion", Types.INTEGER, transaccionToken.getTipoTransaccionToken())
                .addParameter("var_usuario_creacion", Types.VARCHAR, transaccionToken.getAuditoria().getUsuarioCreacion())
                .addParameter("var_token", Types.VARCHAR, transaccionToken.getToken())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Boolean existeTransaccionToken(String token) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_TRANSACCION_TOKEN_VALIDAR)
                .addParameter("var_token", Types.VARCHAR, token)
                .setReturnDaoParameter("resultado", Types.BOOLEAN)
                .build(Boolean.class);
    }
}
