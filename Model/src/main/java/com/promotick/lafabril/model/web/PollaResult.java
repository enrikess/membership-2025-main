package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class PollaResult extends BeanBase {

	private static final long serialVersionUID = 7833663451720299166L;

	private String estadoPollaParticipante;
	private Object result;

}
