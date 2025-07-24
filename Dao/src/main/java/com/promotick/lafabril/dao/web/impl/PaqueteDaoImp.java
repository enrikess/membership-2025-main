package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.PaqueteDao;
import com.promotick.lafabril.dao.web.definition.PaqueteDaoDefinition;
import com.promotick.lafabril.dao.web.definition.PaymentDaoDefinition;
import com.promotick.lafabril.dao.web.definition.TxPasarelaDaoDefinition;
import com.promotick.lafabril.model.web.Payment;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.configuration.utils.dao.DaoParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class PaqueteDaoImp extends DaoGenerator implements PaqueteDao {

	private PaqueteDaoDefinition paqueteDaoDefinition;
	private PaymentDaoDefinition paymentDaoDefinition;
	private TxPasarelaDaoDefinition txPasarelaDaoDefinition;

	@Autowired
	public PaqueteDaoImp(JdbcTemplate jdbcTemplate, PaqueteDaoDefinition paqueteDaoDefinition, PaymentDaoDefinition paymentDaoDefinition, TxPasarelaDaoDefinition txPasarelaDaoDefinition) {
		super(jdbcTemplate);
		this.paqueteDaoDefinition = paqueteDaoDefinition;
		this.paymentDaoDefinition = paymentDaoDefinition;
		this.txPasarelaDaoDefinition = txPasarelaDaoDefinition;
	}


	@Override
	public Integer registrarPayment(Payment payment) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_PAYMENT_REGISTRAR)
				.addParameter(new DaoParameter("var_idParticipante", Types.INTEGER, payment.getIdParticipante()))
				.addParameter(new DaoParameter("var_application_code", Types.VARCHAR,payment.getApplication_code() ))
				.addParameter(new DaoParameter("var_uid", Types.VARCHAR,payment.getUid()))
				.addParameter(new DaoParameter("var_auth_timestamp", Types.VARCHAR, payment.getAuth_timestamp()))
				.addParameter(new DaoParameter("var_dev_reference", Types.VARCHAR, payment.getDev_reference()))
				.addParameter(new DaoParameter("var_product_description", Types.VARCHAR, payment.getProduct_description()))
				.addParameter(new DaoParameter("var_product_code", Types.VARCHAR, payment.getProduct_code()))
				.addParameter(new DaoParameter("var_product_amount", Types.VARCHAR, payment.getProduct_amount()))
				.addParameter(new DaoParameter("var_success_url", Types.VARCHAR, payment.getSuccess_url()))
				.addParameter(new DaoParameter("var_failure_url", Types.VARCHAR, payment.getFailure_url()))
				.addParameter(new DaoParameter("var_review_url", Types.VARCHAR,payment.getReview_url()))
				.addParameter(new DaoParameter("var_application_key", Types.VARCHAR,payment.getApplication_key()))
				.addParameter(new DaoParameter("var_url", Types.VARCHAR, payment.getUrl()))
				.addParameter(new DaoParameter("var_puntos", Types.INTEGER,payment.getPuntos()))
				.addParameter(new DaoParameter("var_plaintext", Types.VARCHAR, payment.getPlain_text()))
				.setReturnDaoParameter(new DaoParameter("resultado", Types.INTEGER))
						.build(Integer.class);
	}

	@Override
	public Integer actualizarPayment(Payment payment) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_PAYMENT_ACTUALIZAR)
				.addParameter(new DaoParameter("var_dev_reference", Types.VARCHAR, payment.getDev_reference()))
				.addParameter(new DaoParameter("var_date", Types.VARCHAR,payment.getDate()))
				.addParameter(new DaoParameter("var_paid_date", Types.VARCHAR,payment.getPaid_date()))
				.addParameter(new DaoParameter("var_user_id", Types.VARCHAR, payment.getUser_id()))
				.addParameter(new DaoParameter("var_transaction_id", Types.VARCHAR, payment.getTransaction_id()))
				.addParameter(new DaoParameter("var_recurrent_transaction_id", Types.VARCHAR, payment.getRecurrent_transaction_id()))
				.addParameter(new DaoParameter("var_product_id", Types.VARCHAR, payment.getProduct_id()))
				.addParameter(new DaoParameter("var_token", Types.VARCHAR, payment.getToken()))
				.addParameter(new DaoParameter("var_stoken", Types.VARCHAR, payment.getStoken()))
				.addParameter(new DaoParameter("var_currency", Types.VARCHAR, payment.getCurrency()))
				.addParameter(new DaoParameter("var_gross_value", Types.VARCHAR,payment.getGross_value()))
				.addParameter(new DaoParameter("var_num_coins", Types.VARCHAR,payment.getNum_coins()))
				.addParameter(new DaoParameter("var_carrier", Types.VARCHAR, payment.getCarrier()))
				.addParameter(new DaoParameter("var_payment_method", Types.VARCHAR,payment.getPayment_method()))
				.addParameter(new DaoParameter("var_status", Types.VARCHAR, payment.getStatus()))
				.addParameter(new DaoParameter("var_test_mode", Types.VARCHAR, payment.getTest_mode()))
				.addParameter(new DaoParameter("var_buyer_first_name", Types.VARCHAR, payment.getBuyer_first_name()))
				.addParameter(new DaoParameter("var_buyer_last_name", Types.VARCHAR, payment.getBuyer_last_name()))
				.addParameter(new DaoParameter("var_buyer_phone", Types.VARCHAR, payment.getBuyer_phone()))
				.addParameter(new DaoParameter("var_buyer_ip", Types.VARCHAR, payment.getBuyer_ip()))
				.addParameter(new DaoParameter("var_buyer_email", Types.VARCHAR, payment.getBuyer_email()))
				.addParameter(new DaoParameter("var_usd_amount", Types.VARCHAR, payment.getUsd_amount()))
				.addParameter(new DaoParameter("var_total_net_value", Types.VARCHAR, payment.getTotal_net_value()))
				.addParameter(new DaoParameter("var_status_detail", Types.VARCHAR, payment.getStatus_detail()))
				.addParameter(new DaoParameter("var_bank_name", Types.VARCHAR, payment.getBank_name()))
				.addParameter(new DaoParameter("var_authorization_code", Types.VARCHAR, payment.getAuthorization_code()))
				.setReturnDaoParameter(new DaoParameter("resultado", Types.INTEGER))
				.build(Integer.class);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Payment obtenerPayment(String devReference) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_PAYMENT_OBTENER)
				.addParameter(new DaoParameter("var_dev_reference", Types.VARCHAR, devReference))
				.setDaoDefinition(paymentDaoDefinition)
				.build(Payment.class);
	}


}
