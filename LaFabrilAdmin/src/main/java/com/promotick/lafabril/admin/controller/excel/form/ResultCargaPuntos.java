package com.promotick.lafabril.admin.controller.excel.form;

import com.promotick.lafabril.model.util.descarga.FormCargaPuntos;
import com.promotick.lafabril.model.util.form.CargaPuntos;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultCargaPuntos implements Serializable {
	
	private static final long serialVersionUID = -5469670983973733613L;
    private Integer registrosCorrectos = 0;
    private Integer registrosError = 0;
    private Integer registrosTotal = 0;
    private List<FormCargaPuntos> listaErrores;
    private List<CargaPuntos> cargaPuntosProcesos;
}
