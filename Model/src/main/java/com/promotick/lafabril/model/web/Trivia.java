package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Trivia extends BeanBase {

	private static final long serialVersionUID = 7833663451720299166L;

	private Integer idTriviaMundial;
	private String preguntaTrivia;
	private String jsonAlternativasTrivia;
	private Integer idAlternativaCorrecta;
	private Integer idTriviaGrupoMundial;
	private String estadoTriviaGrupoParticipante;


}
