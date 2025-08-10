package com.promotick.membership.web.service;

import com.promotick.membership.model.Promocion;

import java.util.List;


public interface PromocionService {
    List<Promocion> obtenerPromociones(String token);
    List<Promocion> obtenerPromociones(List<String> palabras);
}
