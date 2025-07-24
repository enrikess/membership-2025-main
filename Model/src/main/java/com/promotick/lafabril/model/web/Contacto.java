package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Contacto extends BeanBase {

    private static final long serialVersionUID = 7833663451720299166L;

    private Integer idContacto;
    private String nombres;
    private String apellidos;
    private String email;
    private String mensaje;
    private String telefono;
    private String nroDocumento;
    private String celular;

}
