package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CampaniaDaoDefinition extends DaoDefinition<Campania> {
    public CampaniaDaoDefinition() {
        super(Campania.class);
    }

    @Override
    public Campania mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Campania campania = super.mapRow(rs, rowNumber);
        return campania;
    }
}
