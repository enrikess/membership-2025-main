package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;

public class TipoDescuento extends BeanBase {

	private static final long serialVersionUID = 2437670342998222001L;
	
	private Integer idTipoDescuento;
	private String nombreTipoDescuento;
	private String descripcionTipodescuento;
	private Integer estadoTipodescuento;
	public Integer getIdTipoDescuento() {
		return idTipoDescuento;
	}
	public TipoDescuento setIdTipoDescuento(Integer idTipoDescuento) {
		this.idTipoDescuento = idTipoDescuento;
		return this;
	}

	public String getNombreTipoDescuento() {
		return nombreTipoDescuento;
	}
	public void setNombreTipoDescuento(String nombreTipoDescuento) {
		this.nombreTipoDescuento = nombreTipoDescuento;
	}
	public String getDescripcionTipodescuento() {
		return descripcionTipodescuento;
	}
	public void setDescripcionTipodescuento(String descripcionTipodescuento) {
		this.descripcionTipodescuento = descripcionTipodescuento;
	}
	public Integer getEstadoTipodescuento() {
		return estadoTipodescuento;
	}
	public void setEstadoTipodescuento(Integer estadoTipodescuento) {
		this.estadoTipodescuento = estadoTipodescuento;
	}
	
}
