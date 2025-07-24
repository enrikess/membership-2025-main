package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-Categorias", sheetName = "Reporte de Categorias")
public class ReporteCategoria implements Serializable {
    private static final long serialVersionUID = 4666677514370838146L;

    @ExcelColumn("Codigo")
    private Integer idCategoria;

    @ExcelColumn("Nombre")
    private String nombreCategoria;

    @ExcelColumn("Tipo categoria")
    private String tipoCategoria;

    @ExcelColumn("categoria Principal")
    private String categoriaParent;

    @ExcelColumn("Orden")
    private String ordenCategoria;

    @ExcelColumn("Estado categoria")
    private String estadoCategoria;
}
