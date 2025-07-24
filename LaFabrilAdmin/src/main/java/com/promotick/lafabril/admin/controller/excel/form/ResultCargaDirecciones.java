package com.promotick.lafabril.admin.controller.excel.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promotick.lafabril.model.util.descarga.FormCargaDirecciones;
import com.promotick.lafabril.model.web.Direccion;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultCargaDirecciones implements Serializable {

    private static final long serialVersionUID = -3960356784155942726L;
    private Integer registrosTotal;
    private Integer registrosCorrectos;
    private Integer registrosError;
    @JsonIgnore
    private List<FormCargaDirecciones> listaErrores;
    @JsonIgnore
    private List<Direccion> cargaDirecciones;
}
