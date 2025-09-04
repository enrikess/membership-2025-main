package com.promotick.membership.web.service;

import com.promotick.membership.model.DetalleMisionDto;
import com.promotick.membership.model.Mision;
import com.promotick.membership.model.MisionDto;

import java.util.List;

public interface MisionService {
    List<MisionDto> obtenerMisiones();
    DetalleMisionDto obtenerMisionesPorId(long idMision);
    String registrarMisionRecompensa(long idMision,long idRecompensa);
}
