package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class TriviaResult extends BeanBase {

	private static final long serialVersionUID = 7833663451720299166L;

	private String estadoTriviaParticipante;
	private Object result;

}
