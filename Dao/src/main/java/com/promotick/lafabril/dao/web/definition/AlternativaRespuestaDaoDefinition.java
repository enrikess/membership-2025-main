package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.AlternativaRespuesta;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AlternativaRespuestaDaoDefinition extends DaoDefinition<AlternativaRespuesta> {

	public AlternativaRespuestaDaoDefinition(){
		super(AlternativaRespuesta.class);
	}

	@Override
	public AlternativaRespuesta mapRow(ResultSet rs, int rowNumber) throws SQLException {
		AlternativaRespuesta alternativaRespuesta = super.mapRow(rs, rowNumber);
		return alternativaRespuesta;
	}

}


