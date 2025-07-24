package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-productos-visitados", sheetName = "Reporte de Productos Visitados")
public class ReporteVisitaProducto implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("ID producto")
    private Integer idProducto;

    @ExcelColumn("Catalogo")
    private String nombreCatalogo;

    @ExcelColumn("Codigo Web")
    private String codigoWeb;

//    @ExcelColumn("Categoria")
//    private String nombreCategoria;
//
//    @ExcelColumn("Subcategoria")
//    private String nombreSubCategoria;

    @ExcelColumn("Nombre producto")
    private String nombreProducto;

    @ExcelColumn("Puntos")
    private Integer puntosProducto;

    @ExcelColumn("Marca")
    private String nombreMarca;

    @ExcelColumn("Visitas")
    private Integer conteo;


    public static ReporteVisitaProducto getFromEntity(ReporteVisita reporteVisita) {
        if (reporteVisita == null) {
            return null;
        }

        return new ReporteVisitaProducto()
                .setIdProducto(reporteVisita.getIdProducto())
                .setCodigoWeb(reporteVisita.getCodigoWeb())
//                .setNombreCategoria(reporteVisita.getNombreCategoria())
//                .setNombreSubCategoria(reporteVisita.getNombreSubCategoria())
                .setNombreProducto(reporteVisita.getNombreProducto())
                .setNombreMarca(reporteVisita.getNombreMarca())
                .setPuntosProducto(reporteVisita.getPuntosProducto())
                .setConteo(reporteVisita.getConteo())
                .setNombreCatalogo(reporteVisita.getNombreCatalogo());
    }

    public static List<ReporteVisitaProducto> getFromEntities(List<ReporteVisita> reporteVisitas) {
        List<ReporteVisitaProducto> list = new ArrayList<>();

        if (reporteVisitas == null || reporteVisitas.isEmpty()) {
            return list;
        }

        for (ReporteVisita reporteVisita : reporteVisitas) {
            list.add(getFromEntity(reporteVisita));
        }

        return list;
    }
}
