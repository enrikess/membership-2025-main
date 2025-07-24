package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.TransaccionTokenDao;
import com.promotick.lafabril.model.web.TransaccionToken;
import com.promotick.lafabril.web.service.TransaccionTokenWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransaccionTokenWebServiceImpl implements TransaccionTokenWebService {

    private TransaccionTokenDao transaccionTokenDao;

    @Autowired
    public TransaccionTokenWebServiceImpl(TransaccionTokenDao transaccionTokenDao) {
        this.transaccionTokenDao = transaccionTokenDao;
    }

    @Override
    @Transactional
    public Integer guardarTransaccionToken(TransaccionToken transaccionToken) {
        return transaccionTokenDao.guardarTransaccionToken(transaccionToken);
    }

    @Override
    public Boolean existeTransaccionToken(String token) {
        return transaccionTokenDao.existeTransaccionToken(token);
    }
}
