package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.MultiDireccionCustom;
import com.promotick.lafabril.model.web.ParticipanteDireccion;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Zona;

import java.util.List;

public interface DireccionWebService {

    List<Zona> listarZonas();

    List<TipoVivienda> listarTipoViviendas();

    MultiDireccionCustom direccionesParticipante(Integer idParticipante, Integer idDireccion);

    List<ParticipanteDireccion> listarDireccionesParticipante(Integer idParticipante);

    Integer registrarDireccionParticipante(ParticipanteDireccion direccion);

    Integer eliminarDireccionParticipante(Integer idParticipante, Integer idParticipanteDireccion);
}
