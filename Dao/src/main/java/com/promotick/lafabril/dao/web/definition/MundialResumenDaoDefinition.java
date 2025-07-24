package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.MundialResumen;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MundialResumenDaoDefinition extends DaoDefinition<MundialResumen> {

	public MundialResumenDaoDefinition(){
		super(MundialResumen.class);
	}

	@Override
	public MundialResumen mapRow(ResultSet rs, int rowNumber) throws SQLException {
		MundialResumen mundialResumen = super.mapRow(rs, rowNumber);
		return mundialResumen;
	}

}


