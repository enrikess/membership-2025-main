package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.List;

@Data
public class TriviaParticipante extends BeanBase {

	private static final long serialVersionUID = 7833663451723299166L;
 	private Integer idParticipante;
 	private Integer idTriviaMundial;
	private Integer idTriviaGrupo;
 	private String estadoTriviaGrupoParticipante;
 	private Integer cantidadRespuestasCorrectas;
 	private Integer cantidadPreguntas;
 	private List<AlternativaRespuesta> listAlternativaRespuesta;
 	private String usuarioModificacion;


}
