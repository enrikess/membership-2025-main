package com.promotick.lafabril.dao.web.definition;


import com.promotick.lafabril.model.web.PollaParticipante;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PollaParticipanteDaoDefinition extends DaoDefinition<PollaParticipante> {

	public PollaParticipanteDaoDefinition(){
		super(PollaParticipante.class);
	}

	@Override
	public PollaParticipante mapRow(ResultSet rs, int rowNumber) throws SQLException {
		PollaParticipante pollaParticipante = super.mapRow(rs, rowNumber);
		return pollaParticipante;
	}

}


