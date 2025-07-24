package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Ventas-Error", sheetName = "Carga Ventas Error", error = true)
public class FormCargaVentas implements Serializable {
    private static final long serialVersionUID = -2427232274280995678L;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Valor venta")
    private String valorVenta;

    @ExcelColumn("Fecha Operacion")
    private String fechaOperacion;

    @ExcelColumn("Descripcion")
    private String descripcion;

    @ExcelColumn("Error")
    private String error;
}
