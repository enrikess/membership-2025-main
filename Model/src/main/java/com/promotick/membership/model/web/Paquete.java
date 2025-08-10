package com.promotick.membership.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class Paquete implements Serializable {

	private static final long serialVersionUID = -3618968045075148782L;

	private Integer idPaquete;
	private String nombrePaquete;
	private String prefijo;
	private Double monto;
	private Integer puntos;
	private String descripcion;

	
}
