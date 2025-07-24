package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.admin.Rol;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RolDaoDefinition extends DaoDefinition<Rol> {

    public RolDaoDefinition() {
        super(Rol.class);
    }

    @Override
    public Rol mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Rol rol = super.mapRow(rs, rowNumber);
        return rol;
    }

}
