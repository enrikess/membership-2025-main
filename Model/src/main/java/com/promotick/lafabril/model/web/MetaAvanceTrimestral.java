package com.promotick.lafabril.model.web;

import java.io.Serializable;

public class MetaAvanceTrimestral implements Serializable {

	private static final long serialVersionUID = 6399566770807987009L;

	private Integer valorMeta;
	private Double valorPago;
	private Integer puntosPremio;
	private Double porcentajeRebate;
	private Double avance;

	public Integer getValorMeta() {
		return valorMeta;
	}

	public void setValorMeta(Integer valorMeta) {
		this.valorMeta = valorMeta;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Integer getPuntosPremio() {
		return puntosPremio;
	}

	public void setPuntosPremio(Integer puntosPremio) {
		this.puntosPremio = puntosPremio;
	}

	public Double getPorcentajeRebate() {
		return porcentajeRebate;
	}

	public void setPorcentajeRebate(Double porcentajeRebate) {
		this.porcentajeRebate = porcentajeRebate;
	}

	public Double getAvance() {
		return avance;
	}

	public void setAvance(Double avance) {
		this.avance = avance;
	}
}
