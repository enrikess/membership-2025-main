package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Concesionario extends BeanBase {
    private static final long serialVersionUID = -3029980911883750112L;

    private Integer idConcesionario;
    private String nombreConcesionario;
    private Integer estadoConcesionario;
    private Direccion direccion;
}
