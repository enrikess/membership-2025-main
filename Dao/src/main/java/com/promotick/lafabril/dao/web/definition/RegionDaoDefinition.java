package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Region;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RegionDaoDefinition extends DaoDefinition<Region> {

    public RegionDaoDefinition() {
        super(Region.class);
    }

    @Override
    public Region mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Region region = super.mapRow(rs, rowNumber);
        return region;
    }
}
