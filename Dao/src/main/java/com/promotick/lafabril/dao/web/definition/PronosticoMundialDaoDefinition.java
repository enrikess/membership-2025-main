package com.promotick.lafabril.dao.web.definition;


import com.promotick.lafabril.model.web.PronosticoParticipante;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PronosticoMundialDaoDefinition extends DaoDefinition<PronosticoParticipante> {

	public PronosticoMundialDaoDefinition(){
		super(PronosticoParticipante.class);
	}

	@Override
	public PronosticoParticipante mapRow(ResultSet rs, int rowNumber) throws SQLException {
		PronosticoParticipante pronosticoParticipante = super.mapRow(rs, rowNumber);
		return pronosticoParticipante;
	}

}


