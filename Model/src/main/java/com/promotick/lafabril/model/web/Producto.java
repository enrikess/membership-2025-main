package com.promotick.lafabril.model.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.common.UtilCommon;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class Producto extends BeanBase {

    private static final long serialVersionUID = 1924747554986806049L;
    private Integer idProducto;
    private Categoria categoria;
    private Marca marca;
    private String nombreProducto;
    private String descripcionProducto;
    private String especificacionesProducto;
    private Integer estadoProducto;
    private String terminosProducto;
    private Integer puntosProducto;
    private Double precioProducto;
    private String codigoWeb;
    private String idNetsuite;
    private String imagenUno;
    private String imagenDos;
    private String imagenDetalle;
    private String nombreMarca;
    private String nombreCatalogo;
    private TipoProducto tipoProducto;
    private TagProducto tagProducto;
    private Integer stockProducto;
    private String url;
    private Integer idCatalogo;
    private String tags;
    private Integer puntosRegular;
    private String nombreTag;
    private String imagenMovil;
    private String imagenMovilDestacada;
    private String imagenMovilDetalle;
    private String imagenMovilDetalleDos;
    private String imagenMovilDetalleTres;
    private String imagenDestacada;
    private Integer imagenEnvioExpress;
    private String pdfAden;

    private String codigoProducto;
    private String codigoMarca;
    private String codigoCategoria;
    private String codigoCatalogo;

    private boolean showLabel;
    private String label;

    private boolean tieneStock;

    @JsonIgnore
    public boolean isTieneStockProducto() {
        tieneStock = stockProducto == -1 || stockProducto > 0;
        return tieneStock;
    }

    @JsonIgnore
    public String getUrl() {
        url = (categoria.getUrl() != null ? (categoria.getUrl() + "/") : "") + "producto/" + UtilCommon.parseUrl(idProducto, nombreProducto);
        return url;
    }

    @JsonIgnore
    public Boolean isShowLabel() {
        if (tagProducto == null) {
            return false;
        } else if (tagProducto.getIdTagProducto() == null) {
            return false;
        } else if (StringUtils.isEmpty(tagProducto.getClaseTagProducto())) {
            return false;
        } else if (tagProducto.getClaseTagProducto().equals("otro") && StringUtils.isEmpty(nombreTag)) {
            return false;
        } else {
            return true;
        }
    }

    @JsonIgnore
    public String getLabel() {
        if (tagProducto == null) {
            return "";
        } else if (tagProducto.getIdTagProducto() == null) {
            return "";
        } else if (StringUtils.isEmpty(tagProducto.getClaseTagProducto())) {
            return "";
        } else if (tagProducto.getClaseTagProducto().equals("otro") && StringUtils.isNotEmpty(nombreTag)) {
            return nombreTag;
        } else {
            return tagProducto.getDescripcionTagProducto();
        }
    }

}
