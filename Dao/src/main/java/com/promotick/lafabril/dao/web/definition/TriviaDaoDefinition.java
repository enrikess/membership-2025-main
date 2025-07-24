package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Trivia;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TriviaDaoDefinition extends DaoDefinition<Trivia> {

	public TriviaDaoDefinition(){
		super(Trivia.class);
	}

	@Override
	public Trivia mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Trivia trivia = super.mapRow(rs, rowNumber);
		return trivia;
	}

}


