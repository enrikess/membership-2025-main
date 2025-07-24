package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Participantes-Estado-Error", sheetName = "Carga Participantes Estado Error", error = true)
public class FormCargaParticipanteEstado implements Serializable {
    private static final long serialVersionUID = 4277188805443742889L;

    @ExcelColumn("Nro Documento*")
    private String nroDocumento;

    @ExcelColumn("Estado*")
    private String estadoParticipante;

    @ExcelColumn("Estado Canje")
    private String estadoCanje;

    @ExcelColumn("Error")
    private String error;
}
