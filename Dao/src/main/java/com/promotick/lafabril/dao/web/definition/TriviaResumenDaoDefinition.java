package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.TriviaResumen;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TriviaResumenDaoDefinition extends DaoDefinition<TriviaResumen> {

	public TriviaResumenDaoDefinition(){
		super(TriviaResumen.class);
	}

	@Override
	public TriviaResumen mapRow(ResultSet rs, int rowNumber) throws SQLException {
		TriviaResumen triviaResumen = super.mapRow(rs, rowNumber);
		return triviaResumen;
	}

}


