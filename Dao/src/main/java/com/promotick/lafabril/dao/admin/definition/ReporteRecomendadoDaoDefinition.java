package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteRecomendado;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ReporteRecomendadoDaoDefinition extends DaoDefinition<ReporteRecomendado> {


    public ReporteRecomendadoDaoDefinition() {
        super(ReporteRecomendado.class);
    }

    @Override
    public ReporteRecomendado mapRow(ResultSet rs,int rowNumber) throws SQLException {
        ReporteRecomendado  reporteRecomendado = super.mapRow(rs, rowNumber);
        return reporteRecomendado;
    }

}
