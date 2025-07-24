package com.promotick.lafabril.admin.controller.excel.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promotick.lafabril.model.util.descarga.FormCargaPdf;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResultCargaPdf implements Serializable {

	private static final long serialVersionUID = -5469670983973733613L;
	private String exception;
    private Integer registrosCorrectos;
    private Integer registrosError;
    private Integer registrosTotal;

    @JsonIgnore
    private List<FormCargaPdf> listaErrores;

    public ResultCargaPdf() {
        this.registrosCorrectos = 0;
        this.registrosError = 0;
        this.registrosTotal = 0;
        this.listaErrores = new ArrayList<>();
    }
}
