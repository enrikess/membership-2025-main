package com.promotick.lafabril.model.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class Dashboard implements Serializable {

    private static final long serialVersionUID = 6501717352891499911L;

    private Integer cantidadParticipantes;
    private Integer cantidadCanjes;
    private Integer cantidadVisitas;

}
