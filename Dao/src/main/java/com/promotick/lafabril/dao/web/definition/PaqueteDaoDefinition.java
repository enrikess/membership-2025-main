package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Paquete;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PaqueteDaoDefinition extends DaoDefinition<Paquete> {

	public PaqueteDaoDefinition() {
		super(Paquete.class);
	}

	@Override
	public Paquete mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Paquete paquete = super.mapRow(rs, rowNumber);
		return paquete;
	}

}