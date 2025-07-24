package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.EncuestaDao;
import com.promotick.lafabril.model.web.Encuesta;
import com.promotick.lafabril.model.web.EncuestaDetalle;
import com.promotick.lafabril.web.service.EncuestaWebService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EncuestaWebServiceImpl implements EncuestaWebService {

    private final EncuestaDao encuestaDao;

    @Override
    public void registrarEncuestaRegular(Encuesta encuesta) {
        this.registrarEncuestaProceso(encuesta);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {Exception.class, RuntimeException.class})
    public void registrarEncuestaTransactional(Encuesta encuesta) {
        this.registrarEncuestaProceso(encuesta);
    }

    public void registrarEncuestaProceso(Encuesta encuesta) {
        if (encuesta == null || encuesta.getDetalles() == null || encuesta.getDetalles().isEmpty()) {
            throw new RuntimeException("La encuesta esta incompleta");
        }
        Integer encuestaId = encuestaDao.registrarEncuesta(encuesta);
        if (encuestaId == null) {
            throw new RuntimeException("No se pudo registrar la encuesta");
        }
        for (EncuestaDetalle encuestaDetalle : encuesta.getDetalles()) {
            encuestaDetalle.setEncuesta(new Encuesta().setIdEncuesta(encuestaId));
            Integer detalleId = encuestaDao.registrarEncuestaDetalle(encuestaDetalle);
            if (detalleId == null) {
                throw new RuntimeException("No se pudo registrar la pregunta de la encuesta");
            }
        }
    }

    @Override
    public Integer encuestaPedidoObtener(Integer idParticipante) {
        return encuestaDao.encuestaPedidoObtener(idParticipante);
    }

    @Transactional
    @Override
    public Integer omitirEncuesta(Integer idPedido) {
        return  encuestaDao.omitirEncuesta(idPedido);
    }
}
