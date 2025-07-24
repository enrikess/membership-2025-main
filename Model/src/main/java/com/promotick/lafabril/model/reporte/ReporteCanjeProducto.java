package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-productos-canjeados", sheetName = "Reporte de Productos Canjeados")
public class ReporteCanjeProducto implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("ID producto")
    private Integer idProducto;

    @ExcelColumn("Catalogo")
    private String nombreCatalogo;

    @ExcelColumn("Codigo Web")
    private String codigoWeb;

    @ExcelColumn("Nombre producto")
    private String nombreProducto;

    @ExcelColumn("Categoria")
    private String nombreCategoria;

    @ExcelColumn("Subcategoria")
    private String nombreSubCategoria;

    @ExcelColumn("Puntos")
    private Integer puntosProducto;

    @ExcelColumn("Canjes")
    private Integer conteo;


    public static ReporteCanjeProducto getFromEntity(ReporteVisita reporteVisita) {
        if (reporteVisita == null) {
            return null;
        }

        return new ReporteCanjeProducto()
                .setIdProducto(reporteVisita.getIdProducto())
                .setCodigoWeb(reporteVisita.getCodigoWeb())
                .setNombreProducto(reporteVisita.getNombreProducto())
                .setNombreCategoria(reporteVisita.getNombreCategoria())
                .setNombreSubCategoria(reporteVisita.getNombreSubCategoria())
                .setPuntosProducto(reporteVisita.getPuntosProducto())
                .setConteo(reporteVisita.getConteo())
                .setNombreCatalogo(reporteVisita.getNombreCatalogo());
    }

    public static List<ReporteCanjeProducto> getFromEntities(List<ReporteVisita> reporteVisitas) {
        List<ReporteCanjeProducto> list = new ArrayList<>();

        if (reporteVisitas == null || reporteVisitas.isEmpty()) {
            return list;
        }

        for (ReporteVisita reporteVisita : reporteVisitas) {
            list.add(getFromEntity(reporteVisita));
        }

        return list;
    }
}
