package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.AccionSellout;
import com.promotick.lafabril.model.web.MetaAvanceTrimestral;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("AccionSelloutDaoDefinition")
public class AccionSelloutDaoDefinition extends DaoDefinition<AccionSellout> {

	public AccionSelloutDaoDefinition() {
		super(AccionSellout.class);
	}

	@Override
	public AccionSellout mapRow(ResultSet rs, int rowNumber) throws SQLException {
		return super.mapRow(rs, rowNumber);
	}
}
