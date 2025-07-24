package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ExcelClass(filename = "Reporte-puntos-descontados", sheetName = "Reporte de Puntos Descontados")
public class ReportePuntosDescontados implements Serializable {
    private static final long serialVersionUID = -4748166328895405363L;

    @ExcelColumn("Fecha operacion")
    private String fechaOperacion;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Nombres")
    private String nombres;

    @ExcelColumn("Apellido Paterno")
    private String apellidoPaterno;

    @ExcelColumn("Apellido Materno")
    private String apellidoMaterno;

    @ExcelColumn("Email")
    private String email;

    @ExcelColumn("Descripcion")
    private String descripcion;

    @ExcelColumn("Puntos Restados")
    private Integer puntosRestados;

    public static ReportePuntosDescontados parseFromEntity(ReporteVisita reporteVisita) {
        return new ReportePuntosDescontados()
                .setFechaOperacion(reporteVisita.getFechaOperacion())
                .setNroDocumento(reporteVisita.getNroDocumento())
                .setNombres(reporteVisita.getNombresParticipante())
                .setApellidoPaterno(reporteVisita.getAppaternoParticipante())
                .setApellidoMaterno(reporteVisita.getApmaternoParticipante())
                .setEmail(reporteVisita.getEmailParticipante())
                .setPuntosRestados(reporteVisita.getPuntosRestados())
                .setDescripcion(reporteVisita.getDescripcion());
    }

    public static List<ReportePuntosDescontados> parseFromEntities(List<ReporteVisita> reporteVisitas) {
        return reporteVisitas.stream().map(ReportePuntosDescontados::parseFromEntity).collect(Collectors.toList());
    }
}
