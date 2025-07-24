package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Metas-Error", sheetName = "Carga Metas Error", error = true)
public class FormCargaMetas implements Serializable {
    private static final long serialVersionUID = -2427232274280995678L;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Anio")
    private String anio;

    @ExcelColumn("Trimestre")
    private String mes;

    @ExcelColumn("Meta")
    private String meta;

    @ExcelColumn("Descripcion")
    private String descripcion;

    @ExcelColumn("Porcentaje de Rebate")
    private String porcentajeRebate;

    @ExcelColumn("Valor de pago")
    private String valorPago;

    @ExcelColumn("Acciones de Sellout")
    private String accionesDeSellout;

    @ExcelColumn("Premio en puntos")
    private String premio;

    @ExcelColumn("Error")
    private String error;
}
