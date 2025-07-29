package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.LogDao;
import com.promotick.lafabril.dao.web.definition.LogDaoDefinition;
import com.promotick.lafabril.model.web.Log;
import com.promotick.lafabril.web.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public void guardarLog(Log log) {
        logDao.guardarLog(log);
    }
}