package com.promotick.lafabril.admin.controller.excel.form;

import com.promotick.lafabril.model.util.descarga.FormCargaAcciones;
import com.promotick.lafabril.model.util.descarga.FormCargaMetas;
import com.promotick.lafabril.model.util.form.CargaAcciones;
import com.promotick.lafabril.model.util.form.CargaMetas;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultCargaAcciones implements Serializable {
	
	private static final long serialVersionUID = -5469670983973733613L;
    private Integer registrosCorrectos = 0;
    private Integer registrosError = 0;
    private Integer registrosTotal = 0;
    private List<FormCargaAcciones> listaErrores;
    private List<CargaAcciones> cargaAccionesProcesos;
}
