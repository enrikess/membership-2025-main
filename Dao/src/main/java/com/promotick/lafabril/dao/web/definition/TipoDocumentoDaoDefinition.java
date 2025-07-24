package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.TipoDocumento;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TipoDocumentoDaoDefinition extends DaoDefinition<TipoDocumento> {

    public TipoDocumentoDaoDefinition() {
        super(TipoDocumento.class);
    }

    @Override
    public TipoDocumento mapRow(ResultSet rs, int rowNumber) throws SQLException {
        TipoDocumento tipoDocumento = super.mapRow(rs, rowNumber);
        return tipoDocumento;
    }
}
