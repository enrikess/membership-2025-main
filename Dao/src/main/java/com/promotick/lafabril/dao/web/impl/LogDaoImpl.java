package com.promotick.lafabril.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.LogDao;
import com.promotick.lafabril.dao.web.definition.LogDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LogDaoImpl extends DaoGenerator implements LogDao {

    private LogDaoDefinition logDaoDefinition;

    @Autowired
    public LogDaoImpl(JdbcTemplate jdbcTemplate, LogDaoDefinition logDaoDefinition) {
        super(jdbcTemplate);
        this.logDaoDefinition = logDaoDefinition;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer guardarLog(Log log) {
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
            log.getRequest(),
            log.getResponse()
        );
    }
}