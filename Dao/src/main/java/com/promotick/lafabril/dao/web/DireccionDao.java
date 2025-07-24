package com.promotick.lafabril.dao.web;


import com.promotick.lafabril.model.web.Direccion;
import com.promotick.lafabril.model.web.ParticipanteDireccion;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Zona;

import java.util.List;

public interface DireccionDao {
    Direccion direccionParticipante(Integer idParticipante);

    Integer registrarDireccion(Direccion direccion);

    List<Zona> listarZonas();

    List<TipoVivienda> listarTipoViviendas();

    Integer registroDireccionCarga(Direccion direccion);

    List<ParticipanteDireccion> listarDireccionesParticipante(Integer idParticipante);

    Direccion direccionObtener(Integer idDireccion);

    Integer registrarDireccionParticipante(ParticipanteDireccion direccion);

    Integer eliminarDireccionParticipante(Integer idParticipante, Integer idParticipanteDireccion);

}
