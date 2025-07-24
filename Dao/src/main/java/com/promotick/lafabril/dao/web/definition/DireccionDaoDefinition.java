package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Direccion;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DireccionDaoDefinition extends DaoDefinition<Direccion> {

    private UbigeoDaoDefinition ubigeoDaoDefinition;
    private ZonaDaoDefinition zonaDaoDefinition;
    private TipoViviendaDaoDefinition tipoViviendaDaoDefinition;

    @Autowired
    public DireccionDaoDefinition(UbigeoDaoDefinition ubigeoDaoDefinition, ZonaDaoDefinition zonaDaoDefinition, TipoViviendaDaoDefinition tipoViviendaDaoDefinition) {
        super(Direccion.class);
        this.ubigeoDaoDefinition = ubigeoDaoDefinition;
        this.zonaDaoDefinition = zonaDaoDefinition;
        this.tipoViviendaDaoDefinition = tipoViviendaDaoDefinition;
    }

    @Override
    public Direccion mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Direccion direccion = super.mapRow(rs, rowNumber);
        if (direccion != null) {
            direccion.setUbigeo(ubigeoDaoDefinition.mapRow(rs, rowNumber));
            direccion.setZona(zonaDaoDefinition.mapRow(rs, rowNumber));
            direccion.setTipoVivienda(tipoViviendaDaoDefinition.mapRow(rs, rowNumber));
        }
        return direccion;
    }
}
