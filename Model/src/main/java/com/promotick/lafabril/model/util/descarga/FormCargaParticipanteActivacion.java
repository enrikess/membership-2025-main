package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Participantes-Activacion-Error", sheetName = "Carga Participantes Activacion Error", error = true)
public class FormCargaParticipanteActivacion implements Serializable {


    private static final long serialVersionUID = 252534341342447460L;

    @ExcelColumn("Nro Documento*")
    private String nroDocumento;

    @ExcelColumn("Activacion*")
    private String activacion;

    @ExcelColumn("Error")
    private String error;
}
