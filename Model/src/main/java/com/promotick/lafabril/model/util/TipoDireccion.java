package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TipoDireccion implements Serializable {

    private static final long serialVersionUID = 3879693223936209243L;

    private Integer id;
    private String nombre;

}
