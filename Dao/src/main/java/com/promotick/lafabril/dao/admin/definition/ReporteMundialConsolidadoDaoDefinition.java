package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteMundialConsolidado;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class ReporteMundialConsolidadoDaoDefinition extends DaoDefinition<ReporteMundialConsolidado> {

    public ReporteMundialConsolidadoDaoDefinition() {
        super(ReporteMundialConsolidado.class);
    }

//    @Override
//    public ReporteMundialConsolidado mapRow(ResultSet rs, int rowNumber) throws SQLException {
//        ReporteMundialConsolidado reporteMundialConsolidado = super.mapRow(rs, rowNumber);
//        return reporteMundialConsolidado;
//    }
}