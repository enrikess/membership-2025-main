package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.RazonSocial;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RazonSocialDaoDefinition extends DaoDefinition<RazonSocial> {

    public RazonSocialDaoDefinition() {
        super(RazonSocial.class);
    }

    @Override
    public RazonSocial mapRow(ResultSet rs, int rowNumber) throws SQLException {
        RazonSocial razonSocial = super.mapRow(rs, rowNumber);
        return razonSocial;
    }
}
