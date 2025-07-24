package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-mundial-polla", sheetName = "Reporte Mundial Polla")
public class ReporteMundialPolla implements Serializable{

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

	@ExcelColumn("Grupo A1")
	private String a1;

	@ExcelColumn("Grupo A2")
	private String a2;

	@ExcelColumn("Grupo B1")
	private String b1;

	@ExcelColumn("Grupo B2")
	private String b2;

	@ExcelColumn("Grupo C1")
	private String c1;

	@ExcelColumn("Grupo C2")
	private String c2;

	@ExcelColumn("Grupo D1")
	private String d1;

	@ExcelColumn("Grupo D2")
	private String d2;

	@ExcelColumn("Grupo E1")
	private String e1;

	@ExcelColumn("Grupo E2")
	private String e2;
	@ExcelColumn("Grupo F1")
	private String f1;
	@ExcelColumn("Grupo F2")
	private String f2;
	@ExcelColumn("Grupo G1")
	private String g1;
	@ExcelColumn("Grupo G2")
	private String g2;
	@ExcelColumn("Grupo H1")
	private String h1;
	@ExcelColumn("Grupo H2")
	private String h2;
	@ExcelColumn("1A2B")
	private String a1b2;
	@ExcelColumn("1B2A")
	private String b1a2;
	@ExcelColumn("1C2D")
	private String c1d2;
	@ExcelColumn("1D2C")
	private String d1c2;
	@ExcelColumn("1E2F")
	private String e1f2;
	@ExcelColumn("1F2E")
	private String f1e2;
	@ExcelColumn("1G2H")
	private String g1h2;
	@ExcelColumn("1H2G")
	private String h1g2;
	@ExcelColumn("1A2B / 1C2D")
	private String a1b2c1d2;
	@ExcelColumn("1B2A / 1D2C")
	private String b1a2d1c2;
	@ExcelColumn("1E2F / 1G2H")
	private String e1f2g1h2;
	@ExcelColumn("1F2E / 1H2G")
	private String f1e2h1g2;
	@ExcelColumn("1A2B / 1C2D - 1E2F / 1G2H")
	private String a1b2c1d2e1f2g1h2;
	@ExcelColumn("1B2A / 1D2C - 1F2E / 1H2G")
	private String b1a2d1c2f1e2h1g2;
	@ExcelColumn("FINAL")
	private String finalPartido;
}
