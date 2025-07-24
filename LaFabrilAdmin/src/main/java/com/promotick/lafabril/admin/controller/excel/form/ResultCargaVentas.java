package com.promotick.lafabril.admin.controller.excel.form;

import com.promotick.lafabril.model.util.descarga.FormCargaVentas;
import com.promotick.lafabril.model.util.form.CargaVentas;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultCargaVentas implements Serializable {
	
	private static final long serialVersionUID = -5469670983973733613L;
    private Integer registrosCorrectos = 0;
    private Integer registrosError = 0;
    private Integer registrosTotal = 0;
    private List<FormCargaVentas> listaErrores;
    private List<CargaVentas> cargaVentasProcesos;
}
