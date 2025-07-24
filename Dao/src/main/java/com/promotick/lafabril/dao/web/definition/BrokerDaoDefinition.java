package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Broker;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class BrokerDaoDefinition extends DaoDefinition<Broker> {
    public BrokerDaoDefinition() {
        super(Broker.class);
    }

    @Override
    public Broker mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Broker broker = super.mapRow(rs, rowNumber);
        return broker;
    }
}
