package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-facturas", sheetName = "Reporte de Facturas")
public class ReporteFacturasV2 implements Serializable {

    private static final long serialVersionUID = -379355750509227642L;

    @ExcelColumn("ID FACTURA")
    private String idFactura;

    @ExcelColumn("ARCHIVO")
    private String archivo;

    @ExcelColumn("FECHA CARGA")
    private String fechaRegistroString;

    @ExcelColumn("NRO DOCUMENTO")
    private String nroDocumento;

    @ExcelColumn("NOMBRES")
    private String nombresParticipante;

    @ExcelColumn("APELLIDO PATERNO")
    private String appaternoParticipante;

    @ExcelColumn("APELLIDO MATERNO")
    private String apmaternoParticipante;

    @ExcelColumn("EMAIL")
    private String emailParticipante;

    @ExcelColumn("DISTRIBUIDOR")
    private String canal;

    @ExcelColumn("PETSHOP")
    private String nombrePetshop;

}
