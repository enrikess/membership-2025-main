package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-mundial-pronostico", sheetName = "Reporte Mundial Pronostico")
public class ReporteMundialPronostico implements Serializable{

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

	@ExcelColumn("Completado")
	private String completado;

	@ExcelColumn("Etapa")
	private String etapaPartido;

	@ExcelColumn("Grupo")
	private String grupoPartido;

	@ExcelColumn("Pais 1")
	private String nombrePais1;

	@ExcelColumn("Pais 2")
	private String nombrePais2;

	@ExcelColumn("Marcador Pais 1")
	private String scorePais1;

	@ExcelColumn("Marcador Pais 2")
	private String scorePais2;



}
