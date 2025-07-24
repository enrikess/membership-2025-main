package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.utils.file.excel.ExcelFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.model.web.*;
import com.promotick.lafabril.admin.service.ProductoAdminService;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaProductos;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class CargaProductosValidator extends ExcelFileValidator<FormCargaProductos, Producto> {

    private static final List<Integer> estadosPermitidos;
    private static final List<Integer> tipoProductosPermitidos;
    private static final List<Integer> tipoEtiquetaPermitidos;
    private static final List<Integer> imagenExpressPermitidos;
    static {
        estadosPermitidos = new ArrayList<>();
        estadosPermitidos.add(1);
        estadosPermitidos.add(-1);

        tipoProductosPermitidos = new ArrayList<>();
        tipoProductosPermitidos.add(1);
        tipoProductosPermitidos.add(2);
        tipoProductosPermitidos.add(3);
        tipoProductosPermitidos.add(4);
        tipoProductosPermitidos.add(5);
        tipoProductosPermitidos.add(7);
        tipoProductosPermitidos.add(8);

        tipoEtiquetaPermitidos = new ArrayList<>();
        tipoEtiquetaPermitidos.add(1);
        tipoEtiquetaPermitidos.add(2);
        tipoEtiquetaPermitidos.add(3);

        imagenExpressPermitidos = new ArrayList<>();
        imagenExpressPermitidos.add(1);
        imagenExpressPermitidos.add(-1);
    }

    private ProductoAdminService productoAdminService;

    public CargaProductosValidator(ProductoAdminService productoAdminService) {
        this.productoAdminService = productoAdminService;
    }


    @Override
    public FormCargaProductos iterator(int rowNumber, Row row) {
        return new FormCargaProductos()
                .setIdProducto(UtilCommon.removeDecimals(this.parseString(row, 0)))
                .setIdCatalogo(this.parseString(row, 1))
                .setCodigoWeb(this.parseString(row, 2))
                .setIdMarca(UtilCommon.removeDecimals(this.parseString(row, 3)))
                .setIdCategoria(UtilCommon.removeDecimals(this.parseString(row, 4)))
                .setIdTipoProducto(UtilCommon.removeDecimals(this.parseString(row, 5)))
                .setNombreProducto(this.parseString(row, 6))
                .setImagenPrincipal(this.parseString(row, 7))
                .setImagenDetalle(this.parseString(row, 8))
                .setEstadoProducto(UtilCommon.removeDecimals(this.parseString(row, 9)))
                .setDescripcionProducto(this.parseString(row, 10))
                .setTerminosProducto(this.parseString(row, 11))
                .setEspecificacionesProducto(this.parseString(row, 12))
                .setPuntosProducto(UtilCommon.removeDecimals(this.parseString(row, 13)))
                .setPrecioProducto(this.parseString(row, 14))
                .setIdNetsuite(this.parseString(row, 15))
                .setTags(this.parseString(row, 16))
                .setPuntosRegular(UtilCommon.removeDecimals(this.parseString(row, 17)))
                .setIdTagProducto(this.parseString(row, 18))
                .setNombreTag(this.parseString(row, 19))
                .setImagenMovil(this.parseString(row, 20))
                .setImagenMovilDestacada(this.parseString(row, 21))
                .setImagenMovilDetalle(this.parseString(row, 22))
                .setImagenMovilDetalleDos(this.parseString(row, 23))
                .setImagenMovilDetalleTres(this.parseString(row, 24))
                .setImagenEnvioExpress(UtilCommon.removeDecimals(this.parseString(row, 25)))
                .setPdfAden(this.parseString(row, 26))
                ;
    }

    @Override
    public Producto validate(FormCargaProductos formCargaProductos, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaProductos.getIdProducto(), "Id de producto no especificado")
                    .numeric(formCargaProductos.getIdProducto(), "Id de prducto tiene que ser numerico entero")
                    .and()
                    .notEmpty(formCargaProductos.getIdCatalogo(), "Id de catalogo no especificado")
                    .numeric(formCargaProductos.getIdCatalogo(), "Id de catalogo tiene que ser numerico entero")
                    .positive(formCargaProductos.getIdCatalogo(), "Id de catalogo tiene que ser numerico positivo")
                    .and()
                    .notEmpty(formCargaProductos.getCodigoWeb(), "Codigo web es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getIdMarca(), "Id de marca no especificado")
                    .numeric(formCargaProductos.getIdMarca(), "Id de marca tiene que ser numerico entero")
                    .positive(formCargaProductos.getIdMarca(), "Id de marca tiene que ser numerico positivo")
                    .and()
                    .notEmpty(formCargaProductos.getIdCategoria(), "Id de categoria no especificado")
                    .numeric(formCargaProductos.getIdCategoria(), "Id de categoria tiene que ser numerico entero")
                    .positive(formCargaProductos.getIdCategoria(), "Id de categoria tiene que ser numerico positivo")
                    .and()
                    .notEmpty(formCargaProductos.getIdTipoProducto(), "Tipo de producto no especificado")
                    .numeric(formCargaProductos.getIdTipoProducto(), "Tipo de producto tiene que ser numerico entero")
                    .numericRange(formCargaProductos.getIdTipoProducto(), tipoProductosPermitidos, "Tipo de producto incorrecto, los permitidos son: " + StringUtils.join(tipoProductosPermitidos, "|"))
                    .and()
                    .notEmpty(formCargaProductos.getNombreProducto(), "El nombre de producto es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getImagenPrincipal(), "La imagen principal es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getImagenDetalle(), "La imagen detalle es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getDescripcionProducto(), "La descripcion de producto es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getEstadoProducto(), "Estado de producto no especificado")
                    .numeric(formCargaProductos.getEstadoProducto(), "Estado de producto tiene que ser numerico entero")
                    .numericRange(formCargaProductos.getEstadoProducto(), estadosPermitidos, "Estado de producto incorrecto, los permitidos son: " + StringUtils.join(estadosPermitidos, "|"))
                    .and()
                    .notEmpty(formCargaProductos.getTerminosProducto(), "Los terminos del producto es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getEspecificacionesProducto(), "Las especificaciones del producto es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getPuntosProducto(), "Puntos de producto no especificado")
                    .numeric(formCargaProductos.getPuntosProducto(), "Puntos de producto tiene que ser numerico entero")
                    .positive(formCargaProductos.getPuntosProducto(), "Puntos de producto tiene que ser numerico positivo")
                    .and()
                    .notEmpty(formCargaProductos.getPrecioProducto(), "ingrese un precio")
                    .decimal(formCargaProductos.getPrecioProducto(), "Precio de producto inválido")
                    .and()
                    .notEmpty(formCargaProductos.getIdNetsuite(), "ID Netsuite es obligatorio")
                    .and()
                    .numeric(formCargaProductos.getPuntosRegular(), "Puntos regulares tiene que ser numerico entero")
                    .and()
                    .notEmpty(formCargaProductos.getIdTagProducto(), "Tipo de etiqueta no especificado")
                    .numeric(formCargaProductos.getIdTagProducto(), "Tipo de etiqueta tiene que ser numerico entero")
                    .numericRange(formCargaProductos.getIdTagProducto(), tipoEtiquetaPermitidos, "Tipo de etiqueta incorrecto, los permitidos son: " + StringUtils.join(tipoProductosPermitidos, "|"))
                    .and()
                    .notEmpty(formCargaProductos.getImagenMovil(), "La imagen movil es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getImagenMovilDetalle(), "La imagen movil detalle es obligatorio")
                    .and()
                    .notEmpty(formCargaProductos.getImagenEnvioExpress(), "Imagen Express no especificada")
                    .numeric(formCargaProductos.getImagenEnvioExpress(), "Imagen Express tiene que ser numerico entero")
                    .numericRange(formCargaProductos.getImagenEnvioExpress(), imagenExpressPermitidos, "Imagen Express incorrecto, los permitidos son: " + StringUtils.join(imagenExpressPermitidos, "|"))
//                    .notEmpty(formCargaProductos.getTags(), "")
                    .validate();

            if (buildValidator.hasError()) {
                formCargaProductos.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                String[] listaTags = null;
                if (formCargaProductos.getTags() != null) {
                    listaTags = formCargaProductos.getTags().split("\\|");
                }

                Producto producto = new Producto()
                        .setIdProducto(Integer.parseInt(formCargaProductos.getIdProducto()))
                        .setIdCatalogo(Integer.parseInt(formCargaProductos.getIdCatalogo()))
                        .setCodigoWeb(formCargaProductos.getCodigoWeb())
                        .setMarca(new Marca().setIdMarca(Integer.parseInt(formCargaProductos.getIdMarca())))
                        .setNombreProducto(formCargaProductos.getNombreProducto())
                        .setImagenUno(formCargaProductos.getImagenPrincipal())
                        .setImagenDetalle(formCargaProductos.getImagenDetalle())
                        .setEstadoProducto(Integer.parseInt(formCargaProductos.getEstadoProducto()))
                        .setDescripcionProducto(formCargaProductos.getDescripcionProducto())
                        .setTerminosProducto(formCargaProductos.getTerminosProducto())
                        .setEspecificacionesProducto(formCargaProductos.getEspecificacionesProducto())
                        .setCategoria(new Categoria().setIdCategoria(Integer.parseInt(formCargaProductos.getIdCategoria())))
                        .setPuntosProducto(Integer.parseInt(formCargaProductos.getPuntosProducto()))
                        .setPrecioProducto(Double.parseDouble(formCargaProductos.getPrecioProducto()))
                        .setIdNetsuite(formCargaProductos.getIdNetsuite())
                        .setTipoProducto(new TipoProducto().setIdTipoProducto(Integer.parseInt(formCargaProductos.getIdTipoProducto())))
                        .setTags(formCargaProductos.getTags())
                        .setPuntosRegular(StringUtils.isNotEmpty(formCargaProductos.getPuntosRegular())?  Integer.parseInt(formCargaProductos.getPuntosRegular()) : null)
                        .setTagProducto(new TagProducto().setIdTagProducto(Integer.parseInt(formCargaProductos.getIdTagProducto())))
                        .setNombreTag(formCargaProductos.getNombreTag())
                        .setImagenMovil(formCargaProductos.getImagenMovil())
                        .setImagenMovilDestacada(formCargaProductos.getImagenMovilDestacada())
                        .setImagenMovilDetalle(formCargaProductos.getImagenMovilDetalle())
                        .setImagenMovilDetalleDos(formCargaProductos.getImagenMovilDetalleDos())
                        .setImagenMovilDetalleTres(formCargaProductos.getImagenMovilDetalleTres())
                        .setImagenEnvioExpress(Integer.parseInt(formCargaProductos.getImagenEnvioExpress()))
                        .setPdfAden(formCargaProductos.getPdfAden())
                        ;


                producto.setAccion(UtilEnum.MANTENIMIENTO.CARGA_EXCEL.getCodigo());
                producto.setAuditoria(new Auditoria().setUsuarioActualizacion("Carga Excel"));

                Integer result = productoAdminService.registrarProducto(producto);
                UtilEnum.CARGA_PRODUCTOS_RESULTS resultEnum = UtilEnum.CARGA_PRODUCTOS_RESULTS.getMensageFromCode(result);
                if (!resultEnum.equals(UtilEnum.CARGA_PRODUCTOS_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }
                return producto;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            formCargaProductos.setError(e.getMessage());
            return null;
        }
    }
}
