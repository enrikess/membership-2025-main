package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Imagenes-Error", sheetName = "Carga Imagenes Error", error = true)
public class FormCargaImagen implements Serializable {
    private static final long serialVersionUID = -2427232274280995678L;

    @ExcelColumn("Nombre de Archivo")
    private String nombreArchivo;

    @ExcelColumn("Error")
    private String error;
}
