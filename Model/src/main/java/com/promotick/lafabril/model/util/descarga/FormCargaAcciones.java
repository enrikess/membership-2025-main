package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Productos-Error", sheetName = "Carga Productos Error", error = true)
public class FormCargaAcciones implements Serializable {
    private static final long serialVersionUID = -2427232274280995678L;

    @ExcelColumn("Accion")
    private String accion;

    @ExcelColumn("Descripcion")
    private String descripcion;

    @ExcelColumn("Cantidad")
    private String cantidad;

    @ExcelColumn("Fecha")
    private String fecha;

    @ExcelColumn("Error")
    private String error;
}
