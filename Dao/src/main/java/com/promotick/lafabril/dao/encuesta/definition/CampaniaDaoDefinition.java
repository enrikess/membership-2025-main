package com.promotick.lafabril.dao.encuesta.definition;

import com.promotick.lafabril.model.encuesta.Campania;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Component("CampaniaDaoDefinitionMod")
public class CampaniaDaoDefinition extends DaoDefinition<Campania> {

    private final EncuestaDaoDefinition encuestaDaoDefinition;

    public CampaniaDaoDefinition(EncuestaDaoDefinition encuestaDaoDefinition) {
        super(Campania.class);
        this.encuestaDaoDefinition = encuestaDaoDefinition;
    }

    @Override
    public Campania mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Campania campania = super.mapRow(rs, rowNumber);
        if (Objects.nonNull(campania)) {
            campania.setEncuesta(encuestaDaoDefinition.mapRow(rs, rowNumber));
        }
        return campania;
    }
}
