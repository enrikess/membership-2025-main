package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MetaParticipante implements Serializable {
    private static final long serialVersionUID = 6170601193225683292L;
    private String anio;
    private Date fechaInicio;
    private Date fechaFin;
    private Double periodoMeta;
    private Double totalVenta;
    private Boolean isMetaCumplida;
    private Integer teFalta;
    private Integer montoPeriodo;
    private Integer montoAcumulado;
    private Integer metaMensual;

    public Integer getTeFalta(){
        if(metaMensual != null && montoAcumulado != null){
            if(metaMensual > montoAcumulado) {
                return metaMensual - montoAcumulado;
            }else{
                return 0;
            }
        }else{
            return teFalta;
        }
    }
}
