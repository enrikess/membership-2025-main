package com.promotick.lafabril.dao.web.definition;


import com.promotick.lafabril.model.web.PaisMundial;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PaisMundialDaoDefinition extends DaoDefinition<PaisMundial> {

	public PaisMundialDaoDefinition(){
		super(PaisMundial.class);
	}

	@Override
	public PaisMundial mapRow(ResultSet rs, int rowNumber) throws SQLException {
		PaisMundial paisMundial = super.mapRow(rs, rowNumber);
		return paisMundial;
	}

}


