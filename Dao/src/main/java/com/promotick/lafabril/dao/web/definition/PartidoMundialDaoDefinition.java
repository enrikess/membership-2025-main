package com.promotick.lafabril.dao.web.definition;


import com.promotick.lafabril.model.web.PartidoMundial;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PartidoMundialDaoDefinition extends DaoDefinition<PartidoMundial> {

	public PartidoMundialDaoDefinition(){
		super(PartidoMundial.class);
	}

	@Override
	public PartidoMundial mapRow(ResultSet rs, int rowNumber) throws SQLException {
		PartidoMundial partidoMundial = super.mapRow(rs, rowNumber);
		return partidoMundial;
	}

}


