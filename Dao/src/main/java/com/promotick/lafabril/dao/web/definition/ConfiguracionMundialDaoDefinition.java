package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.ConfiguracionMundial;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ConfiguracionMundialDaoDefinition extends DaoDefinition<ConfiguracionMundial> {

	public ConfiguracionMundialDaoDefinition(){
		super(ConfiguracionMundial.class);
	}

	@Override
	public ConfiguracionMundial mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ConfiguracionMundial configuracionMundial = super.mapRow(rs, rowNumber);
		return configuracionMundial;
	}

}


