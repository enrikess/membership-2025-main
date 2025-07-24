package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Producto-Error", sheetName = "Carga Producto Error", error = true)
public class FormCargaProductos implements Serializable {

    private static final long serialVersionUID = 1802430627624355404L;

    @ExcelColumn("ID Producto")
    private String idProducto;

    @ExcelColumn("ID Catalogo")
    private String idCatalogo;

    @ExcelColumn("Codigo web")
    private String codigoWeb;

    @ExcelColumn("ID Marca")
    private String idMarca;

    @ExcelColumn("Id Categoria")
    private String idCategoria;

    @ExcelColumn("Tipo Producto")
    private String idTipoProducto;

    @ExcelColumn("Nombre producto")
    private String nombreProducto;

    @ExcelColumn("Imagen Principal")
    private String imagenPrincipal;

    @ExcelColumn("Imagen Detalle")
    private String imagenDetalle;

    @ExcelColumn("Estado Producto")
    private String estadoProducto;

    @ExcelColumn("Descripcion producto")
    private String descripcionProducto;

    @ExcelColumn("Terminos producto")
    private String terminosProducto;

    @ExcelColumn("Especificaciones producto")
    private String especificacionesProducto;

    @ExcelColumn("Puntos producto")
    private String puntosProducto;

    @ExcelColumn("Precio Producto")
    private String PrecioProducto;

    @ExcelColumn("ID Netsuite")
    private String idNetsuite;

    @ExcelColumn("Tags")
    private String tags;

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

    @ExcelColumn("Imagen Envio Express")
    private String imagenEnvioExpress;

    @ExcelColumn("PDF Aden")
    private String pdfAden;

    @ExcelColumn("Error")
    private String error;

}
