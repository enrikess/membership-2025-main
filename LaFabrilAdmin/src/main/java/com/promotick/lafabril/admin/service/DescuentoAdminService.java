package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Descuento;

import java.util.List;

public interface DescuentoAdminService {

    List<Descuento> listarDescuentos();

    List<Descuento> listarDescuentos(Integer idDescuento, Integer orden);

    Integer actualizarDescuento(Descuento descuento);

    Integer actualizarEstadoDescuento(Integer idDescuento, Integer estado);

}
