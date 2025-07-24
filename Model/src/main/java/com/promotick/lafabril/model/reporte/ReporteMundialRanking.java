package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-mundial-ranking", sheetName = "Reporte Mundial Ranking")
public class ReporteMundialRanking implements Serializable{

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

	@ExcelColumn("Principiante")
	private String principiante;

	@ExcelColumn("Aficionado")
	private String aficionado;

	@ExcelColumn("Semi Profesional")
	private String semi;

	@ExcelColumn("Profesional")
	private String pro;

	@ExcelColumn("Clase Mundial")
	private String mundial;

	@ExcelColumn("Legendario")
	private String legendario;

	@ExcelColumn("Bono Total")
	private String puntosTotal;
}
