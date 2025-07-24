package com.promotick.lafabril.model.web;


import lombok.Data;

import java.io.Serializable;

@Data
public class Payment implements Serializable {

	private static final long serialVersionUID = -1973065172438882054L;
	private String application_code;
	private String uid;
	private String auth_timestamp;
	private String dev_reference;
	private String product_description;
	private String product_code;
	private String product_amount;
	private String success_url;
	private String failure_url;
	private String review_url;
	private String application_key;
	private Integer idParticipante;
	private String date;
	private String paid_date;
	private String user_id;
	private String transaction_id;
	private String recurrent_transaction_id;
	private String product_id;
	private String token;
	private String stoken;
	private String currency;
	private String gross_value;
	private String num_coins;
	private String carrier;
	private String payment_method;
	private String status;
	private String test_mode;
	private String buyer_first_name;
	private String buyer_last_name;
	private String buyer_phone;
	private String buyer_ip;
	private String buyer_email;
	private String usd_amount;
	private String total_net_value;
	private String status_detail;
	private String bank_name;
	private String url;
	private Integer puntos;
	private String plain_text;
	private String authorization_code;
	private String application_mode;
	private String message;
	private String transaction_reference;
	private String iva;
	private String montoMasIva;
	private String ivaPorcetaje;

	private Integer puntosParticipante;
	private Integer puntosSubTotal;

}
