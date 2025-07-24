package com.promotick.lafabril.admin.controller.excel.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promotick.lafabril.model.util.descarga.FormCargaParticipanteEstado;
import com.promotick.lafabril.model.web.Participante;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultCargaParticipantesEstado implements Serializable {

    private static final long serialVersionUID = -2368837444026740550L;
    private Integer registrosTotal;
    private Integer registrosCorrectos;
    private Integer registrosError;
    @JsonIgnore
    private List<FormCargaParticipanteEstado> listaErrores;
    @JsonIgnore
    private List<Participante> cargaParticipantes;
}
