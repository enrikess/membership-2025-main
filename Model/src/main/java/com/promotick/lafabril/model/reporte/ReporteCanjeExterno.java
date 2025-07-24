package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ExcelClass(filename = "Reporte-canjes-externo", sheetName = "Reporte de Canjes Externos")
public class ReporteCanjeExterno  implements Serializable {
    private static final long serialVersionUID = -4748166328895405363L;

    @ExcelColumn("Fecha canje")
    private String fechaCanje;

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

    @ExcelColumn("Producto")
    private String producto;

    @ExcelColumn("Puntos")
    private Integer puntos;

    public static ReporteCanjeExterno parseFromEntity(ReporteVisita reporteVisita) {
        return new ReporteCanjeExterno()
                .setFechaCanje(reporteVisita.getFechaCanje())
                .setNroDocumento(reporteVisita.getNroDocumento())
                .setNombres(reporteVisita.getNombresParticipante())
                .setApellidoPaterno(reporteVisita.getAppaternoParticipante())
                .setApellidoMaterno(reporteVisita.getApmaternoParticipante())
                .setEmail(reporteVisita.getEmailParticipante())
                .setProducto(reporteVisita.getNombreProducto())
                .setPuntos(reporteVisita.getPuntosProducto());
    }

    public static List<ReporteCanjeExterno> parseFromEntities(List<ReporteVisita> reporteVisitas) {
        return reporteVisitas.stream().map(ReporteCanjeExterno::parseFromEntity).collect(Collectors.toList());
    }
}
