package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.PaqueteDao;
import com.promotick.lafabril.model.web.Payment;
import com.promotick.lafabril.web.service.PaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "PaqueteService")
public class PaqueteServiceImpl implements PaqueteService {
	
	@Autowired
	PaqueteDao paqueteDao;


	@Transactional
	@Override
	public Integer registrarPayment(Payment payment) {
		return paqueteDao.registrarPayment(payment);
	}
	
	@Transactional
	@Override
	public Integer actualizarPayment(Payment payment) {
		return paqueteDao.actualizarPayment(payment);
	}

	@Override
	public Payment obtenerPayment(String devReference) {
		return paqueteDao.obtenerPayment(devReference);
	}


}
