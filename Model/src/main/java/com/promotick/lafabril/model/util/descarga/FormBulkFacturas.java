package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-facturas-error", sheetName = "Carga Facturas Error", error = true)
public class FormBulkFacturas implements Serializable {

    private static final long serialVersionUID = 3744059538934625309L;
    private CargaProceso cargaProceso;

    @ExcelColumn("Fecha Info")
    private String fechaInfo;

    @ExcelColumn("Tipo de Factura")
    private String facturaTipo;

    @ExcelColumn("Cli Ruc")
    private String cliRuc;

    @ExcelColumn("Fecha de emision factura")
    private String fechaEmision;

    @ExcelColumn("Nro factura")
    private String nroFactura;

    @ExcelColumn("Ratio puntos")
    private String ratioPuntos;

    @ExcelColumn("Monto facturado")
    private String montoFactura;

    @ExcelColumn("Motivo rechazo")
    private String motivoRechazo;

}
