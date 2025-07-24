package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteVisita;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReporteVisitasDaoDefinition extends DaoDefinition<ReporteVisita> {

    public ReporteVisitasDaoDefinition() {
        super(ReporteVisita.class);
    }

    @Override
    public ReporteVisita mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ReporteVisita reporteVisita = super.mapRow(rs, rowNumber);
        return reporteVisita;
    }

}
