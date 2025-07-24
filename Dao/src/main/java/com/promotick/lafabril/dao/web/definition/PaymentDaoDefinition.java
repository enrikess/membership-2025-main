package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Payment;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class PaymentDaoDefinition extends DaoDefinition<Payment> {

	public PaymentDaoDefinition() {
		super(Payment.class);	
	}

	@Override
	public Payment mapRow(ResultSet rs, int rowNumber) throws SQLException {		
		Payment payment = super.mapRow(rs, rowNumber);
		return payment;
	}	
}
