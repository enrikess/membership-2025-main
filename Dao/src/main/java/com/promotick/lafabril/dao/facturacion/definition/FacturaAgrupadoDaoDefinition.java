package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.model.util.FacturaAgrupado;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FacturaAgrupadoDaoDefinition extends DaoDefinition<FacturaAgrupado> {
    public FacturaAgrupadoDaoDefinition() {
        super(FacturaAgrupado.class);
    }

    @Override
    public FacturaAgrupado mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return super.mapRow(rs, rowNumber);
    }
}
