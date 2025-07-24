package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.RecomendadoDao;
import com.promotick.lafabril.model.web.Recomendado;
import com.promotick.lafabril.web.service.RecomendadoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecomendadoWebServiceImpl implements RecomendadoWebService {
    private RecomendadoDao recomendadoDao;

    @Autowired
    public RecomendadoWebServiceImpl(RecomendadoDao recomendadoDao){
        this.recomendadoDao = recomendadoDao;
    }


    @Override
    public List<Recomendado> listarRecomendados(Integer idParticipante) {
        return recomendadoDao.listarRecomendados(idParticipante);
    }

    @Override
    @Transactional
    public Integer registrarRecomendado(Recomendado recomendado) {
        return recomendadoDao.registrarRecomendado(recomendado);
    }
}
