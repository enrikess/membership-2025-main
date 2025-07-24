package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Cotizacion;
import com.promotick.lafabril.model.web.Pasajero;

public interface CotizacionDao {
    Integer registrarCotizacion(Cotizacion cotizacion);

    void registrarPasajeros(Pasajero pasajero, Integer cotizacionId);
}
