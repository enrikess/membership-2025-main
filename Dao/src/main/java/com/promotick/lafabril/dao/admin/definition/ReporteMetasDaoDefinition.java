package com.promotick.lafabril.dao.admin.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.reporte.ReporteMetas;
import com.promotick.lafabril.model.reporte.ReporteVisita;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReporteMetasDaoDefinition extends DaoDefinition<ReporteMetas> {

    public ReporteMetasDaoDefinition() {
        super(ReporteMetas.class);
    }

    @Override
    public ReporteMetas mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return super.mapRow(rs, rowNumber);
    }

}
