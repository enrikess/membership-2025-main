package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Reporte-productos", sheetName = "Reporte de Productos")
public class ReporteProducto implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("ID catalogo")
    private Integer idCatalogo;

    @ExcelColumn("ID producto")
    private Integer idProducto;

    @ExcelColumn("ID producto catalogo")
    private Integer idProductoCatalogo;

    @ExcelColumn("Codigo Web")
    private String codigoWeb;

    @ExcelColumn("ID Marca")
    private String idMarca;

    @ExcelColumn("ID Categoria")
    private String idCategoria;

    @ExcelColumn("Tipo producto")
    private String idTipoProducto;

    @ExcelColumn("Nombre Producto")
    private String nombreProducto;

    @ExcelColumn("Imagen Principal")
    private String imagenUno;

    @ExcelColumn("Imagen Detalle")
    private String imagenDetalle;

    @ExcelColumn("Estado Producto")
    private String estadoProducto;

    @ExcelColumn("Estado Producto Catalogo")
    private String estadoProductoCatalogo;

    @ExcelColumn("Descripcion Producto")
    private String descripcionProducto;

    @ExcelColumn("Terminos y condiciones")
    private String terminosProducto;

    @ExcelColumn("Especificaciones")
    private String especificacionesProducto;

    @ExcelColumn("Puntos")
    private String puntosProducto;

    @ExcelColumn("Precio")
    private String precioProducto;

    @ExcelColumn("ID Netsuite")
    private String idNetsuite;

    @ExcelColumn("Puntos Regular")
    private String puntosRegular;

    @ExcelColumn("Tipo Etiqueta")
    private String idTagProducto;

    @ExcelColumn("Nombre Etiqueta")
    private String nombreTag;

    @ExcelColumn("Imagen Movil")
    private String imagenMovil;

    @ExcelColumn("Imagen Movil Destacada")
    private String imagenMovilDestacada;

    @ExcelColumn("Imagen Movil Detalle")
    private String imagenMovilDetalle;

    @ExcelColumn("Imagen Movil Detalle Dos")
    private String imagenMovilDetalleDos;

    @ExcelColumn("Imagen Movil Detalle Tres")
    private String imagenMovilDetalleTres;

    @ExcelColumn("Imagen Destacada")
    private String imagenDestacada;

    @ExcelColumn("Imagen Express")
    private String imagenEnvioExpress;

}
