package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.admin.RolUrl;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RolUrlDaoDefinition extends DaoDefinition<RolUrl> {

    public RolUrlDaoDefinition() {
        super(RolUrl.class);
    }

    @Override
    public RolUrl mapRow(ResultSet rs, int rowNumber) throws SQLException {
        RolUrl urlRol = super.mapRow(rs, rowNumber);
        return urlRol;
    }

}
