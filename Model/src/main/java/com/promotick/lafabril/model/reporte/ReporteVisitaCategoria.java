package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-categorias-visitadas", sheetName = "Reporte de Categorias Visitadas")
public class ReporteVisitaCategoria implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

//    @ExcelColumn("Codigo Categoria")
//    private Integer idCategoria;

    @ExcelColumn("Catalogo")
    private String nombreCatalogo;

//    @ExcelColumn(value = "Tipo", methodEvaluate = "evaluarTipo")
//    private String tipoCategoria;

    @ExcelColumn("Nombre Categoria")
    private String nombreCategoria;

    @ExcelColumn("Nombre Sub-Categoria")
    private String nombreSubCategoria;

    @ExcelColumn("Visitas")
    private Integer conteo;

    private Integer idTipoCategoria;

    private String evaluarTipo() {
        if (this.idTipoCategoria == null) {
            return "CATEGORIA";
        }
        return "SUB-CATEGORIA";
    }

    public static ReporteVisitaCategoria getFromEntity(ReporteVisita reporteVisita) {
        if (reporteVisita == null) {
            return null;
        }

        return new ReporteVisitaCategoria()
//                .setIdCategoria(reporteVisita.getIdCategoria())
                .setNombreCatalogo(reporteVisita.getNombreCatalogo())
                .setNombreCategoria(reporteVisita.getNombreCategoria())
                .setNombreSubCategoria(reporteVisita.getNombreSubCategoria())
                .setConteo(reporteVisita.getConteo())
//                .setIdTipoCategoria(reporteVisita.getIdCategoriaParent())
                ;
    }

    public static List<ReporteVisitaCategoria> getFromEntities(List<ReporteVisita> reporteVisitas) {
        List<ReporteVisitaCategoria> list = new ArrayList<>();

        if (reporteVisitas == null || reporteVisitas.isEmpty()) {
            return list;
        }

        for (ReporteVisita reporteVisita : reporteVisitas) {
            list.add(getFromEntity(reporteVisita));
        }

        return list;
    }
}
