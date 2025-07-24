package com.promotick.lafabril.model.util;

import com.promotick.lafabril.model.web.Participante;
import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroFacturaParticipante implements Serializable {

    private static final long serialVersionUID = -1531530842380238889L;
    private Integer start;
    private Integer length;
    private Participante participante;
    private String nroFactura;
}
