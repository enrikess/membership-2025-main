package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Payment;


public interface PaqueteService {

	Integer registrarPayment(Payment payment);
	Integer actualizarPayment(Payment payment);
	Payment obtenerPayment(String devReference);
}
