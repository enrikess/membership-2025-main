package com.promotick.lafabril.dao.facturacion.impl;

import com.promotick.lafabril.dao.facturacion.definition.PeriodoParticipanteDaoDefinition;
import com.promotick.lafabril.dao.facturacion.PeriodoDao;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class PeriodoDaoImpl extends DaoGenerator implements PeriodoDao {

    private PeriodoParticipanteDaoDefinition periodoParticipanteDaoDefinition;

    @Autowired
    public PeriodoDaoImpl(JdbcTemplate jdbcTemplate, PeriodoParticipanteDaoDefinition periodoParticipanteDaoDefinition) {
        super(jdbcTemplate);
        this.periodoParticipanteDaoDefinition = periodoParticipanteDaoDefinition;
    }

    @Override
    public Integer validacionPeriodo() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PERIODO_VALIDACION)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<PeriodoParticipante> cierrePeriodo() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PERIODO_CIERRE)
                .setDaoDefinition(periodoParticipanteDaoDefinition)
                .build();
    }

    @Override
    public List<PeriodoParticipante> recalculoMetas() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PERIODO_RECALCULO_METAS)
                .setDaoDefinition(periodoParticipanteDaoDefinition)
                .build();
    }
}
