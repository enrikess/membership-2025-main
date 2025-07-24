package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CatalogoDaoDefinition extends DaoDefinition<Catalogo> {
    public CatalogoDaoDefinition() {
        super(Catalogo.class);
    }

    @Override
    public Catalogo mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Catalogo catalogo = super.mapRow(rs, rowNumber);
        return catalogo;
    }
}
