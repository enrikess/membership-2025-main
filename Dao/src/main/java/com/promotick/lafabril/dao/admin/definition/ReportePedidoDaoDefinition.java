package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReportePedido;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ReportePedidoDaoDefinition extends DaoDefinition<ReportePedido> {


    public ReportePedidoDaoDefinition() {
        super(ReportePedido.class);
    }

    @Override
    public ReportePedido mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ReportePedido reportePedido = super.mapRow(rs, rowNumber);
        return reportePedido;
    }

}
