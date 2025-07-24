package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class PollaParticipante extends BeanBase {

	private static final long serialVersionUID = 3298916267248047773L;

	private Integer idParticipante;
	private Integer idPaisMundial;
	private Integer idPaisMundial1;
	private Integer idPaisMundial2;
	private Integer idGrupoMundial;
	private String nombrePais;
	private String resultadoCodigo;
	private String usuarioCreacion;

}
