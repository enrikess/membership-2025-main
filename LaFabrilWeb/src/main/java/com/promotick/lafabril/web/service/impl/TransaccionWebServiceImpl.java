package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.TransaccionDao;
import com.promotick.lafabril.model.web.Transaccion;
import com.promotick.lafabril.web.service.TransaccionWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@EnableAsync
public class TransaccionWebServiceImpl implements TransaccionWebService {

    private TransaccionDao transaccionDao;

    @Autowired
    public TransaccionWebServiceImpl(TransaccionDao transaccionDao) {
        this.transaccionDao = transaccionDao;

    }

    @Async
    @Override
    @Transactional
    public void guardarTransaccionWeb(Transaccion transaccion) {
        try {
            transaccionDao.guardarTransaccionWeb(transaccion);
        } catch (Exception e) {
            log.error("Error al guardar visita", e);
        }
    }
}
