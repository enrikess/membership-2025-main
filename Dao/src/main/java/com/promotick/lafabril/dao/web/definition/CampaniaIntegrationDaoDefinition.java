package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Producto;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CampaniaIntegrationDaoDefinition extends DaoDefinition<Producto> {
    public CampaniaIntegrationDaoDefinition() {
        super(Producto.class);
    }

    @Override
    public Producto mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Producto producto = super.mapRow(rs, rowNumber);
        return producto;
    }

}
