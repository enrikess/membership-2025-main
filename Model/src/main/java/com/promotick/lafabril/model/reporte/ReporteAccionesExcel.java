package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-acciones-sellout", sheetName = "Reporte de Acciones de Sellout")
public class ReporteAccionesExcel implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("ID")
    private Integer id;

    @ExcelColumn("Fecha")
    private String fecha;

    @ExcelColumn("Descripcion")
    private String descripcion;

    @ExcelColumn("Accion")
    private String accion;

    @ExcelColumn("Cantidad")
    private Integer cantidad;

    public static ReporteAccionesExcel getFromEntity(ReporteAcciones reporteAcciones) {
        if (reporteAcciones == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new ReporteAccionesExcel()
                .setId(reporteAcciones.getIdLafabrilProducto())
                .setFecha(formatter.format(reporteAcciones.getFechaProducto()))
                .setDescripcion(reporteAcciones.getDescripcion())
                .setAccion(reporteAcciones.getAccionProducto())
                .setCantidad(reporteAcciones.getCantidadProducto());
    }

    public static List<ReporteAccionesExcel> getFromEntities(List<ReporteAcciones> reporteAcciones) {
        List<ReporteAccionesExcel> list = new ArrayList<>();

        if (reporteAcciones == null || reporteAcciones.isEmpty()) {
            return list;
        }

        for (ReporteAcciones reporteAccion : reporteAcciones) {
            list.add(getFromEntity(reporteAccion));
        }

        return list;
    }

}
