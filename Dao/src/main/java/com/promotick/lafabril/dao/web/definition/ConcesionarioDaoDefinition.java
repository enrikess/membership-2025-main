package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Concesionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ConcesionarioDaoDefinition extends DaoDefinition<Concesionario> {

    private DireccionDaoDefinition direccionDaoDefinition;

    @Autowired
    public ConcesionarioDaoDefinition(DireccionDaoDefinition direccionDaoDefinition) {
        super(Concesionario.class);
        this.direccionDaoDefinition = direccionDaoDefinition;
    }

    @Override
    public Concesionario mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Concesionario concesionario = super.mapRow(rs, rowNumber);
        if (concesionario != null) {
            concesionario.setDireccion(direccionDaoDefinition.mapRow(rs, rowNumber));
        }
        return concesionario;
    }
}
