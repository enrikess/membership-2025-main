package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.MetaAvanceTrimestral;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("MetaAvanceTrimestralDaoDefinition")
public class MetaAvanceTrimestralDaoDefinition extends DaoDefinition<MetaAvanceTrimestral> {

	public MetaAvanceTrimestralDaoDefinition() {
		super(MetaAvanceTrimestral.class);
	}

	@Override
	public MetaAvanceTrimestral mapRow(ResultSet rs, int rowNumber) throws SQLException {
		return super.mapRow(rs, rowNumber);
	}
}
