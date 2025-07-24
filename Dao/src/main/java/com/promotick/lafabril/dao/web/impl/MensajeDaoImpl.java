package com.promotick.lafabril.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.MensajeDao;
import com.promotick.lafabril.dao.web.definition.MensajeDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class MensajeDaoImpl extends DaoGenerator implements MensajeDao {

    private MensajeDaoDefinition mensajeDaoDefinition;

    @Autowired
    public MensajeDaoImpl(JdbcTemplate jdbcTemplate, MensajeDaoDefinition mensajeDaoDefinition) {
        super(jdbcTemplate);
        this.mensajeDaoDefinition = mensajeDaoDefinition;
    }

    @Override
    public Mensaje obtenerMensajeXTipo(Integer tipo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MENSAJE_OBTENER)
                .addParameter("var_tipo_carga", Types.INTEGER, tipo)
                .setDaoDefinition(mensajeDaoDefinition)
                .build(Mensaje.class);
    }
}
