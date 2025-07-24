package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-Marcas", sheetName = "Reporte de Marcas")
public class ReporteMarca implements Serializable {
    private static final long serialVersionUID = 4666677514370838146L;

    @ExcelColumn("Codigo")
    private Integer idMarca;

    @ExcelColumn("Nombre")
    private String nombreMarca;

    @ExcelColumn("Descripcion larga")
    private String descripcionLarga;

    @ExcelColumn("Estado Marca")
    private String estadoMarca;

}
