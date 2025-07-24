package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Puntos-Error", sheetName = "Carga Puntos Error", error = true)
public class FormCargaPuntos implements Serializable {
    private static final long serialVersionUID = -2427232274280995678L;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Tipo Operacion")
    private String tipoOperacion;

    @ExcelColumn("Monto")
    private String monto;

    @ExcelColumn("Descripcion")
    private String descripcion;

    @ExcelColumn("Fecha Operacion")
    private String fechaOperacion;

    @ExcelColumn("Factura")
    private String nroFactura;

    @ExcelColumn("Error")
    private String error;

    private Integer idCargaPuntos;
}
