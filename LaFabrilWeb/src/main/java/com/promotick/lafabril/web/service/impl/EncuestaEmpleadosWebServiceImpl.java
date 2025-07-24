package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.encuesta.EncuestaEmpleadosDao;
import com.promotick.lafabril.model.encuesta.*;
import com.promotick.lafabril.web.service.EncuestaEmpleadosWebService;
import com.promotick.configuration.utils.dao.DaoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EncuestaEmpleadosWebServiceImpl implements EncuestaEmpleadosWebService {

    private final EncuestaEmpleadosDao encuestaEmpleadosDao;

    @Override
    public List<ParticipanteData> participanteDataListar() {
        return encuestaEmpleadosDao.participanteDataListar();
    }

    @Override
    public Optional<Campania> campaniaObtener(Integer idParticipante) {
        return encuestaEmpleadosDao.campaniaObtener(idParticipante);
    }

    @Override
    public List<Pregunta> preguntasByEncuesta(Integer idEncuesta) {
        return encuestaEmpleadosDao.preguntasByEncuesta(idEncuesta);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {Exception.class, RuntimeException.class})
    public void participanteEncuestaGuardar(ParticipanteEncuesta participanteEncuesta) {
        DaoResult resultEncuesta = encuestaEmpleadosDao.participanteEncuestaGuardar(participanteEncuesta);
        if (!resultEncuesta.isSuccess()) {
            throw new RuntimeException("Error al guardar el encuesta");
        }

        for (ParticipanteEncuestaDetalle detalle : participanteEncuesta.getDetalles()) {
            detalle.setIdParticipanteEncuesta(resultEncuesta.getResult());
            DaoResult resultDetalle = encuestaEmpleadosDao.participanteEncuestaDetalleGuardar(detalle);
            if (!resultDetalle.isSuccess()) {
                throw new RuntimeException("Error al guardar el detalle de la encuesta");
            }
        }
    }
}
