package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Marca;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MarcaDaoDefinition extends DaoDefinition<Marca> {

    public MarcaDaoDefinition() {
        super(Marca.class);
    }

    @Override
    public Marca mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Marca marca = super.mapRow(rs, rowNumber);
        return marca;
    }
}
