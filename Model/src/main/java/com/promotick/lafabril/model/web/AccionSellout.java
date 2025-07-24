package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccionSellout extends BeanBase implements Serializable {

	private static final long serialVersionUID = 6399566770807987009L;

	private Integer idLafabrilProducto;
	private Integer idCarga;
	private String nombreProducto;
	private String imagenLogo;
	private Integer estadoProducto;
	private String paginaWeb;
	private Integer orden;
	private Date fechaProducto;
	private String accionProducto;
	private String descripcion;
	private Integer cantidadProducto;
	private String tipoUnidad;
}
