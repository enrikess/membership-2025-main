package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Referidos-Estado-Error", sheetName = "Carga Referidos Estado Error", error = true)
public class FormCargaRecomendadoEstado implements Serializable {
    private static final long serialVersionUID = 4277188805443742889L;

    @ExcelColumn("Nro Documento*")
    private String nroDocumento;

    @ExcelColumn("Estado*")
    private String estadoRecomendado;

    @ExcelColumn("Error")
    private String error;
}
