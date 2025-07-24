package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-mundial-consolidado", sheetName = "Reporte Mundial")
public class ReporteMundialConsolidado implements Serializable{

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

	@ExcelColumn("Polla Mundialista")
	private String polla;

	@ExcelColumn("Puntos por Polla")
	private Integer puntosPolla;

	@ExcelColumn("Nro Trivias Jugadas")
	private Integer triviasJugadas;

	@ExcelColumn("Nro Trivias Acertadas")
	private Integer triviasCorrectas;

	@ExcelColumn("Puntos por trivias")
	private Integer puntosTrivia;

	@ExcelColumn("Nro partidos diarios jugados")
	private Integer pronosticosJugados;

	@ExcelColumn("Grupos")
	private Integer pronosticosGruposJugados;

	@ExcelColumn("Etapa")
	private Integer pronosticosEtapasJugados;

	@ExcelColumn("Puntos Partidos Diarios")
	private Integer puntosPronosticos;

	@ExcelColumn("Categoria Alcanzada")
	private String categoriaParticipante;

	@ExcelColumn("Bono por Categoria")
	private Integer puntosBono;

	@ExcelColumn("Puntos por Mundial")
	private Integer puntosTotal;


}
