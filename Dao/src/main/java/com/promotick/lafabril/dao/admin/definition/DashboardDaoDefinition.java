package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.admin.Dashboard;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DashboardDaoDefinition extends DaoDefinition<Dashboard> {

    public DashboardDaoDefinition() {
        super(Dashboard.class);
    }

    @Override
    public Dashboard mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Dashboard dashboard = super.mapRow(rs, rowNumber);
        return dashboard;
    }

}