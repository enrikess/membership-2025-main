package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-descuento", sheetName = "Reporte de Descuentos")
public class ReporteDescuento implements Serializable {

    private static final long serialVersionUID = -6644207994861840330L;

    @ExcelColumn("Fecha Pedido")
    private String fechaPedido;

    @ExcelColumn("iD Pedido")
    private String idPedido;

    @ExcelColumn("Nro Documento")
    private String nrodocumento;

    @ExcelColumn("Nombre")
    private String nombreParticipante;

    @ExcelColumn("Apellido")
    private String apellidoParticipante;

    @ExcelColumn("Puntos SubTotal")
    private String puntosSubTotal;

    @ExcelColumn("Codigo")
    private String codigoDescuento;

    @ExcelColumn("Puntos Descuento")
    private String puntosDescuento;

    @ExcelColumn("Puntos Total")
    private String puntosTotal;

}
