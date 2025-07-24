package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.MundialRanking;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MundialRankingDaoDefinition extends DaoDefinition<MundialRanking> {

	public MundialRankingDaoDefinition(){
		super(MundialRanking.class);
	}

	@Override
	public MundialRanking mapRow(ResultSet rs, int rowNumber) throws SQLException {
		MundialRanking mundialRanking = super.mapRow(rs, rowNumber);
		return mundialRanking;
	}

}


