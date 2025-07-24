package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.List;

@Data
public class Pasajero extends BeanBase {
    private static final long serialVersionUID = 5664403732779590642L;

    private Integer idPasajero;
    private String nombrePasajero;
    private String apellidoPasajero;
    private TipoDocumento tipoDocumento;
    private String nroDocumento;
    private String fechaNacimiento;
    private Integer terceraEdad;
    private Integer discapacitado;

//    private String nombres;
//    private String apellidos;
//    private Integer tipoDocumento;
//    private String nroDocumento;
//    private String fechaNacimiento;
//    private boolean discapacidad;
//    private boolean edadMayor65;

}
