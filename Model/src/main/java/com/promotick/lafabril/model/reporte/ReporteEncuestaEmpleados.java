package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-encuesta-empleados", sheetName = "Reporte encuesta empleados")
public class ReporteEncuestaEmpleados implements Serializable {

    private static final long serialVersionUID = -6796628612561021823L;

    @ExcelColumn("ID PARTICIPANTE")
    private Integer idParticipante;

    @ExcelColumn("NRO DOCUMENTO")
    private String nroDocumento;

    @ExcelColumn("NOMBRES")
    private String nombresParticipante;

    @ExcelColumn("APELLIDO PATERNO")
    private String appaternoParticipante;

    @ExcelColumn("APELLIDO MATERNO")
    private String apmaternoParticipante;

    @ExcelColumn("EMAIL")
    private String emailParticipante;

    @ExcelColumn("ENCUESTA")
    private String nombreEncuesta;

    @ExcelColumn("CAMPANIA")
    private String nombreCampania;

    @ExcelColumn("PREGUNTA")
    private String textoPregunta;

    @ExcelColumn("RESPUESTA")
    private String textoRespuesta;

    @ExcelColumn("FECHA RESPUESTA")
    private String fechaCreacionString;

}
