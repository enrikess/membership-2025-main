package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-pasarela", sheetName = "Reporte de Transaciones Pasarela")
public class ReportePasarela implements Serializable {

    private static final long serialVersionUID = -525069374454133359L;

    @ExcelColumn("PARTICIPANTE")
    private String nombresParticipante;

    @ExcelColumn("NRO DOCUMENTO")
    private String nroDocumento;

    @ExcelColumn("ID TRANSACCION")
    private String idTransaccion;

    @ExcelColumn("MONTO")
    private String monto;

    @ExcelColumn("PUNTOS")
    private String puntos;

    @ExcelColumn("FECHA")
    private String fecha;

    @ExcelColumn("CODIGO AUTORIZACION")
    private String codigo;

    @ExcelColumn("ESTADO")
    private String estado;

    @ExcelColumn("MONEDA")
    private String moneda;

}
