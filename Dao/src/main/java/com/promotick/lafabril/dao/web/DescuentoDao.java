package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Descuento;

import java.util.List;

public interface DescuentoDao {

    List<Descuento> listarDescuentos();

    List<Descuento> listarDescuentos(Integer idDescuento, Integer orden);

    Integer actualizarDescuento(Descuento descuento);

    Integer actualizarEstadoDescuento(Integer idDescuento, Integer estado);

}
