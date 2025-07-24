package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class CambiarClave implements Serializable {

    private static final long serialVersionUID = -3936935800397209321L;
    private String clave;
    private String token;

}
