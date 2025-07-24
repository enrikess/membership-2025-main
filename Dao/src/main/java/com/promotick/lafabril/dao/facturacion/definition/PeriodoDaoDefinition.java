package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.model.facturacion.Periodo;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PeriodoDaoDefinition extends DaoDefinition<Periodo> {

    private TipoPeriodoDaoDefinition tipoPeriodoDaoDefinition;

    public PeriodoDaoDefinition(TipoPeriodoDaoDefinition tipoPeriodoDaoDefinition) {
        super(Periodo.class);
        this.tipoPeriodoDaoDefinition = tipoPeriodoDaoDefinition;
    }

    @Override
    public Periodo mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Periodo periodo = super.mapRow(rs, rowNumber);
        if (periodo != null) {
            periodo.setTipoPeriodo(tipoPeriodoDaoDefinition.mapRow(rs, rowNumber));
        }
        return periodo;
    }
}
