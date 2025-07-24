package com.promotick.lafabril.model.util;

import com.promotick.lafabril.model.web.Participante;
import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroRecomendados implements Serializable {

    private static final long serialVersionUID = -3630838136574764530L;

    private String finicio;
    private String ffin;
    private Integer length;
    private Integer start;

    private Participante participante;


}
