package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class PaisMundial extends BeanBase {

	private static final long serialVersionUID = 3198986267248047773L;

	private Integer idPaisMundial;
	private String nombrePaisMundial;
	private Integer idGrupoMundial;
	private String imagenPaisMundial;
	private String nombreGrupoMundial;
	private String codigoGrupoMundial;
	private String imagenMPaisMundial;
	private String mapaPaisMundial;


}
