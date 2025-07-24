package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.TipoDescuento;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TipoDescuentoDaoDefinition extends DaoDefinition<TipoDescuento> {
    public TipoDescuentoDaoDefinition() {
        super(TipoDescuento.class);
    }

    @Override
    public TipoDescuento mapRow(ResultSet rs, int rowNumber) throws SQLException {
        TipoDescuento tipoDescuento = super.mapRow(rs, rowNumber);
        return tipoDescuento;
    }
}
