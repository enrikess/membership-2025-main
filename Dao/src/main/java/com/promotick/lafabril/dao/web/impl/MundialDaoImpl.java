package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.MundialDao;
import com.promotick.lafabril.dao.web.RecomendadoDao;
import com.promotick.lafabril.dao.web.definition.*;
import com.promotick.lafabril.model.web.*;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class MundialDaoImpl extends DaoGenerator implements MundialDao {

    private MundialResumenDaoDefinition mundialResumenDaoDefinition;
    private MundialRankingDaoDefinition mundialRankingDaoDefinition;
    private ConfiguracionMundialDaoDefinition configuracionMundialDaoDefinition;
    private PartidoMundialDaoDefinition partidoMundialDaoDefinition;

    public MundialDaoImpl(JdbcTemplate jdbcTemplate, MundialResumenDaoDefinition mundialResumenDaoDefinition, MundialRankingDaoDefinition mundialRankingDaoDefinition, ConfiguracionMundialDaoDefinition configuracionMundialDaoDefinition, PartidoMundialDaoDefinition partidoMundialDaoDefinition) {
        super(jdbcTemplate);
        this.mundialResumenDaoDefinition = mundialResumenDaoDefinition;
        this.mundialRankingDaoDefinition = mundialRankingDaoDefinition;
        this.configuracionMundialDaoDefinition = configuracionMundialDaoDefinition;
        this.partidoMundialDaoDefinition = partidoMundialDaoDefinition;
    }

    @Override
    public MundialResumen obtenerResumenMundial(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MUNDIAL_RESUMEN_OBTENER)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(mundialResumenDaoDefinition)
                .build(MundialResumen.class);

    }

    @Override
    public List<MundialRanking> obtenerRankingMundial() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MUNDIAL_RANKING_OBTENER)
                .setDaoDefinition(mundialRankingDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarCategoriaMundial() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MUNDIAL_CRON_ACTUALIZAR_CATEGORIA_PARTICIPANTE)
                .setReturnDaoParameter("resultado",Types.INTEGER)
                .setDaoDefinition(mundialRankingDaoDefinition)
                .build(Integer.class);
    }

    @Override
    public ConfiguracionMundial obtenerConfiguracionMundial() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MUNDIAL_CONFIGURACION_OBTENER)
                .setDaoDefinition(configuracionMundialDaoDefinition)
                .build(ConfiguracionMundial.class);
    }

    @Override
    public List<PartidoMundial> listarFechasPartido() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_FECHAS_LISTAR)
                .setDaoDefinition(partidoMundialDaoDefinition)
                .build();
    }
}
