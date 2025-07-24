package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.DashboardDao;
import com.promotick.lafabril.dao.admin.definition.DashboardDaoDefinition;
import com.promotick.lafabril.dao.admin.definition.GraficoVisitasDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Dashboard;
import com.promotick.lafabril.model.admin.GraficoVisitas;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;


@Repository
public class DashboardDaoImpl extends DaoGenerator implements DashboardDao {

    private DashboardDaoDefinition dashboardDaoDefinition;
    private GraficoVisitasDaoDefinition graficoVisitasDaoDefinition;

    @Autowired
    public DashboardDaoImpl(JdbcTemplate jdbcTemplate, DashboardDaoDefinition dashboardDaoDefinition, GraficoVisitasDaoDefinition graficoVisitasDaoDefinition) {
        super(jdbcTemplate);
        this.dashboardDaoDefinition = dashboardDaoDefinition;
        this.graficoVisitasDaoDefinition = graficoVisitasDaoDefinition;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Dashboard obtenerDashboard() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_DASHBOARD_OBTENER)
                .setDaoDefinition(dashboardDaoDefinition)
                .build(Dashboard.class);
    }

    @Override
    public GraficoVisitas graficoVisitas(String fecha) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_GRAFICO_VISITAS)
                .addParameter("var_fecha", Types.VARCHAR, fecha)
                .setDaoDefinition(graficoVisitasDaoDefinition)
                .build(GraficoVisitas.class);
    }

}
