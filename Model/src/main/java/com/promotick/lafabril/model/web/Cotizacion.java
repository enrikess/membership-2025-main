package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.List;

@Data
public class Cotizacion extends BeanBase {
    private static final long serialVersionUID = -4289465173212558105L;

    private Integer idCotizacion;
    private String origen;
    private String destino;
    private Integer tipoBoleto;
    private String fechaIda;
    private String fechaVuelta;
    private Integer adultos;
    private Integer boys;
    private Integer bebes;
    private Integer boy1;
    private Integer boy2;
    private Integer boy3;
    private Integer boy4;
    private Integer bebe1;
    private Integer bebe2;
    private Integer bebe3;
    private Integer bebe4;
    private String clase;
    private Integer mayores;
//    private Integer terceraEdad;
//    private Integer discapacitado;
    private Integer edadMayor;

    private List<Pasajero> listaPasajeros;
    private String correo;
    private String telefono;
}
