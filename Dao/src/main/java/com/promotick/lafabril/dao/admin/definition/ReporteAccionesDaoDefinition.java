package com.promotick.lafabril.dao.admin.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.reporte.ReporteAcciones;
import com.promotick.lafabril.model.reporte.ReporteMetas;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReporteAccionesDaoDefinition extends DaoDefinition<ReporteAcciones> {

    public ReporteAccionesDaoDefinition() {
        super(ReporteAcciones.class);
    }

    @Override
    public ReporteAcciones mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return super.mapRow(rs, rowNumber);
    }

}
