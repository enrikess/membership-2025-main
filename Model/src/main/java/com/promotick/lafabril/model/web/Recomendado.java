package com.promotick.lafabril.model.web;

import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.Date;

@Data
public class Recomendado extends BeanBase {

    private static final long serialVersionUID = 611195349255474373L;

    private Integer idRecomendado;
    private Participante participante;
    private String nroDocumentoRecomendado;
    private String nombresRecomendado;
    private String appaternoRecomendado;
    private String apmaternoRecomendado;
    private String telefonoRecomendado;
    private String celularRecomendado;
    private String emailRecomendado;
    private String tiempoCompra;
    private String financiamiento;
    private String observacionRecomendado;
    private Date fechaRegistro;
    private Integer estadoRecomendado;
    private String ciudad;
    private String provincia;



    //temp
    private String fechaRegistroString;
    private String apellidosRecomendado;
    private String estadoRecomendadoString;



    public String getFechaRegistroString() {
        if( fechaRegistro != null ){
            fechaRegistroString = UtilCommon.dateFormat(fechaRegistro, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaRegistroString;
    }

    public String getEstadoRecomendadoString() {
        switch (estadoRecomendado){
            case 0:
                return "Negado";
            case 1:
                return "Aprobado";
            default:
                return "Registrado";
        }
    }



}
