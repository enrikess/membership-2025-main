package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Mensaje;

public interface MensajeDao {
    Mensaje obtenerMensajeXTipo(Integer tipo);
}
