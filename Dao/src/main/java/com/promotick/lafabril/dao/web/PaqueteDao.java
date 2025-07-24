package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Payment;

public interface PaqueteDao {
	Integer registrarPayment(Payment payment);
	Integer actualizarPayment(Payment payment);
	Payment obtenerPayment(String devReference);
}


