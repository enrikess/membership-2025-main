package com.promotick.lafabril.admin.controller.excel.form;


import com.promotick.lafabril.model.util.descarga.FormCargaProductos;
import com.promotick.lafabril.model.web.Producto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultCargaProductos implements Serializable {
	
	private static final long serialVersionUID = -5469670983973733613L;
    private Integer registrosCorrectos = 0;
    private Integer registrosError = 0;
    private Integer totalRegistros = 0;
    private List<FormCargaProductos> listaErrores;
    private List<Producto> cargaProductoProcesos;
}
