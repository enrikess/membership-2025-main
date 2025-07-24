package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-mundial-trivia", sheetName = "Reporte Mundial Trivia")
public class ReporteMundialTrivia implements Serializable{

	private static final long serialVersionUID = 2068885050024304718L;

	private Integer idParticipante;

	@ExcelColumn("Catalogo")
	private String nombreCatalogo;

	@ExcelColumn("Usuario")
	private String usuarioParticipante;

	@ExcelColumn("Nombre")
	private String nombreParticipante;

	@ExcelColumn("Apellido Paterno")
	private String appaterno;

	@ExcelColumn("Apellido Materno")
	private String apmaterno;

	@ExcelColumn("Broker")
	private String broker;

	@ExcelColumn("Ciudad")
	private String ciudad;

	@ExcelColumn("Regional")
	private String regional;

	@ExcelColumn("Estado")
	private String statusParticipante;

	@ExcelColumn("Fecha Respuesta")
	private String fechaRespuesta;

	@ExcelColumn("Hora Respuesta")
	private String horaRespuesta;

	@ExcelColumn("Trivia")
	private String nombreTrivia;

	@ExcelColumn("Completado")
	private String completado;

	@ExcelColumn("Pregunta 1")
	private String respuesta1;

	@ExcelColumn("Pregunta 2")
	private String respuesta2;

	@ExcelColumn("Pregunta 3")
	private String respuesta3;

	@ExcelColumn("Pregunta 4")
	private String respuesta4;

	@ExcelColumn("Pregunta 5")
	private String respuesta5;

	@ExcelColumn("Ganador")
	private String ganador;
}
