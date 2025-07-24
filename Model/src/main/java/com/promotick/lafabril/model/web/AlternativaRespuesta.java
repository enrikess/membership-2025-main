package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class AlternativaRespuesta extends BeanBase {

	private static final long serialVersionUID = 3198986267248047773L;

	private Integer idRespuesta;
	private String descripcionRespuesta;
	private Integer acertoRespuesta;
	private Integer orden;
	private Integer idTriviaMundial;


}
