package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Broker;
import com.promotick.lafabril.model.web.Regional;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RegionalDaoDefinition extends DaoDefinition<Regional> {
    public RegionalDaoDefinition() {
        super(Regional.class);
    }

    @Override
    public Regional mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Regional regional = super.mapRow(rs, rowNumber);
        return regional;
    }
}
