package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ExcelClass(filename = "Reporte-Capacitacion", sheetName = "Reporte de Capacitacion")
public class ReporteCapacitacion implements Serializable {

    private static final long serialVersionUID = -5058305058166720922L;

    @ExcelColumn("NRO DOCUMENTO")
    private String nroDocumento;

    @ExcelColumn("NOMBRES")
    private String nombresParticipante;

    @ExcelColumn("APELLIDO PATERNO")
    private String appaternoParticipante;

    @ExcelColumn("APELLIDO MATERNO")
    private String apmaternoParticipante;

    @ExcelColumn("SEGMENTO")
    private String nombreCatalogo;

    @ExcelColumn("ID CAPACITACION")
    private String idCapacitacion;

    @ExcelColumn("CAPACITACION")
    private String nombreCapacitacion;

    @ExcelColumn("CALIFICACION CAPACITACION")
    private String calificacionCuestionario;

    @ExcelColumn("PREGUNTA")
    private String pregunta;

    @ExcelColumn("RESPUESTAS")
    private String respuestaParticipante;

    @ExcelColumn(value = "RESULTADO PREGUNTA", methodEvaluate = "evalResultadoPregunta")
    private String resultadoPreguntaString;

    @ExcelColumn("FECHA CREACION")
    private String fechaCreacionString;

    //TEMP
    private Boolean resultadoPregunta;

    public String evalResultadoPregunta() {
        return resultadoPregunta ? "CORRECTO" : "INCORRECTO";
    }
}
