package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Direcciones-Error", sheetName = "Carga Direcciones Error", error = true)
public class FormCargaDirecciones implements Serializable {
    private static final long serialVersionUID = -1223343890153072124L;

    @ExcelColumn("Nro de documento")
    private String nroDocumento;

    @ExcelColumn("Direccion")
    private String direccion;

    @ExcelColumn("Referencia")
    private String referencia;

    @ExcelColumn("ID Zona")
    private String idZona;

    @ExcelColumn("ID Tipo Vivienda")
    private String idTipoVivienda;

    @ExcelColumn("Codigo Ubigeo")
    private String codigoUbigeo;

    @ExcelColumn("Error")
    private String error;

}
