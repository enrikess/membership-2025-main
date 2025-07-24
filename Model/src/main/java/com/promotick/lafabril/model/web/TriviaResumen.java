package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class TriviaResumen extends BeanBase {

	private static final long serialVersionUID = 7833663451723299166L;
 	private Integer cantidadAcertoTrivia;
 	private Integer cantidadAcertoTotal;
 	private Integer cantidadErradoTrivia;
 	private Integer cantidadErradoTotal;
	private Integer cantidadPuntosTrivia;
	private Integer cantidadPuntosTotal;

}
