package com.promotick.membership.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.membership.dao.utils.ConstantesWebDAO;
import com.promotick.membership.dao.web.LogDao;
import com.promotick.membership.model.web.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class LogDaoImpl extends DaoGenerator implements LogDao {


    @Autowired
    public LogDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer guardarLog(Log log) {
        try {
            return DaoBuilder.getInstance(this)
                    .setLogger(ConstantesWebDAO.LOGGER)
                    .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                    .setProcedureName(ConstantesWebDAO.SP_LOG_GUARDAR)
                    .addParameter("var_usuario", Types.VARCHAR, log.getUsuario())
                    .addParameter("var_accion", Types.VARCHAR, log.getAccion())
                    .addParameter("var_detalle", Types.VARCHAR, log.getDetalle())
                    .addParameter("var_fecha", Types.TIMESTAMP, java.sql.Timestamp.valueOf(log.getFecha()))
                    .addParameter("var_header_json", Types.VARCHAR, log.getHeaderJson())
                    .addParameter("var_body_json", Types.VARCHAR, log.getBodyJson())
                    .addParameter("var_ip", Types.VARCHAR, log.getIp())
                    .addParameter("var_ruta", Types.VARCHAR, log.getRuta())
                    .setReturnDaoParameter("resultado", Types.INTEGER)
                    .build(Integer.class);
        } catch (Exception e) {
            System.err.println("⚠️ Error con stored procedure, usando función SQL: " + e.getMessage());
            String sql = "SELECT public.fn_log_guardar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return jdbcTemplate.queryForObject(sql, Integer.class,
                    log.getUsuario(),
                    log.getAccion(),
                    log.getDetalle(),
                    java.sql.Timestamp.valueOf(log.getFecha()),
                    log.getHeaderJson(),
                    log.getBodyJson(),
                    log.getIp(),
                    log.getRuta(),
                    "", // request vacío
                    ""  // response vacío
            );
        }


//        String sql = "SELECT public.fn_log_guardar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        return jdbcTemplate.queryForObject(sql, Integer.class,
//                log.getUsuario(),
//                log.getAccion(),
//                log.getDetalle(),
//                java.sql.Timestamp.valueOf(log.getFecha()),
//                log.getHeaderJson(),
//                log.getBodyJson(),
//                log.getIp(),
//                log.getRuta(),
//                log.getRequest(),
//                log.getResponse()
//        );
    }
}