package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.facturacion.ParticipanteAvance;
import com.promotick.lafabril.model.util.FacturaAgrupado;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ParticipanteAvanceDaoDefinition extends DaoDefinition<ParticipanteAvance> {
    public ParticipanteAvanceDaoDefinition() {
        super(ParticipanteAvance.class);
    }

    @Override
    public ParticipanteAvance mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return super.mapRow(rs, rowNumber);
    }
}
