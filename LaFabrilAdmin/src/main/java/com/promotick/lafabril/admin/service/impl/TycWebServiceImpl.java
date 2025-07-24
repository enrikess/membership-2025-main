package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.TycWebService;
import com.promotick.lafabril.dao.web.TycDao;
import com.promotick.lafabril.model.web.Tyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TycWebServiceImpl implements TycWebService {

    private TycDao tycDao;

    @Autowired
    public TycWebServiceImpl(TycDao tycDao) {
        this.tycDao = tycDao;
    }

    @Override
    public List<Tyc> listarTyc(Integer idTyc, Integer idCatalogo, Integer estadoTyc) {
        return tycDao.listarTyc(idTyc, idCatalogo, estadoTyc);
    }
}
