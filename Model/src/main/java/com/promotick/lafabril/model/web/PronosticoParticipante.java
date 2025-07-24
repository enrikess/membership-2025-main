package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class PronosticoParticipante extends BeanBase {

	private static final long serialVersionUID = 1L;
 	private Integer idParticipante;
 	private Integer idPartidoMundial;
	private Integer idPaisMundial1;
	private Integer idPaisMundial2;
	private Integer scorePais1;
	private Integer scorePais2;
 	private String usuarioCreacion;


}
