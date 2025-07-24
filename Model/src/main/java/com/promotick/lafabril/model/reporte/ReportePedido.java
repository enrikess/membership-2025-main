package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ExcelClass(filename = "Reporte-Pedidos", sheetName = "Reporte de Pedidos")
public class ReportePedido implements Serializable {

    private static final long serialVersionUID = 3281642000999858049L;

    @ExcelColumn("Catalogo")
    private String nombreCatalogo;

    @ExcelColumn("Nro Pedido")
    private Integer idPedido;

    @ExcelColumn(value = "Fecha Canje", dateFormat = "dd-MM-yyyy")
    private Date fechaPedido;

    @ExcelColumn(value = "Fecha Entrega", dateFormat = "dd-MM-yyyy")
    private Date fechaEntrega;

    @ExcelColumn("Nro Documento")
    private String nroDocumento;

    @ExcelColumn("Nombre")
    private String nombresParticipante;

    @ExcelColumn(value = "Apellidos", methodEvaluate = "apellidosParticipante")
    private String apellidos;

    @ExcelColumn("Telefono Pedido")
    private String telefonoPedido;

    @ExcelColumn("Movil Pedido")
    private String movilPedido;

    @ExcelColumn("Ciudad")
    private String ciudad;

    @ExcelColumn("Codigo Producto")
    private String codigoWeb;

    @ExcelColumn("Producto")
    private String nombreProducto;

    @ExcelColumn("Categoria")
    private String nombreCategoria;

    @ExcelColumn("Subcategoria")
    private String nombreSubCategoria;

    @ExcelColumn("Marca")
    private String nombreMarca;

    @ExcelColumn("Cantidad")
    private Integer cantidad;

    @ExcelColumn("Puntos unitarios")
    private Integer puntosUnitario;

    @ExcelColumn("Puntos Subtotal")
    private Integer puntosSubtotal;

//    @ExcelColumn("Descripcion")
//    private String descripcionProducto;

    @ExcelColumn("Puntos Total")
    private Integer puntosTotal;

//    @ExcelColumn("Estacion de Servicio")
//    private String primax;

    @ExcelColumn("Recarga celular")
    private String nroCelular;

    @ExcelColumn("Recarga TV")
    private String nroDecodificador;

//    @ExcelColumn("Color 1")
//    private String color1;

//    @ExcelColumn("Color 2")
//    private String color2;

    @ExcelColumn("Correo")
    private String correo;

    private String appaternoParticipante;
    private String apmaternoParticipante;

    private String apellidosParticipante() {
        if (this.apmaternoParticipante.isEmpty()) {
            return appaternoParticipante;
        }else {
            return appaternoParticipante+" "+apmaternoParticipante;
        }
    }
}
