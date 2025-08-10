package com.promotick.membership.model.web;

import com.promotick.membership.model.util.BeanBase;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Catalogo extends BeanBase {

    private static final long serialVersionUID = -853261712420831303L;

    private Integer idCatalogo;
    private String nombreCatalogo;
    private Integer estadoCatalogo;
    private Integer idCatalogoNetsuite;
    private Integer diasEnvio;
    private String pdfPrimax;
    private String videoHome;
    private String previewHome;
    private String logoSegmento;
    private String imagenBienvenida;
    private String imagenPuntos;
    private String imagenCumpleanos;
    private String popupInicio;
    private Integer popup;
    private String avisoWeb;
    private String imagenAcelerador;
    private String imagenBoxUno;
    private String imagenBoxDos;
    private String pdfBox;
    private String avisoMeta;
    private String bannerFlotante;
    private String urlBannerFlotante;
    private Integer estadoBannerFlotante;
    private Boolean viewCapacitaciones;
    private Boolean viewMiMeta;
    private Boolean viewFacturas;
    private String codigoCatalogo;
    private Boolean viewTrivia;
    private Boolean tieneMundial;
    private Boolean tieneCampania;
    private Boolean tieneReferidos;
    private Boolean tieneTopBroker;
    private String imagenEncuesta1;
    private String imagenEncuesta2;
    private String imagenEncuesta3;
    private String moduloEncuesta;
    private Boolean viewInicio;
    private Boolean viewCatalogoProductos;
    private Boolean viewPuntos;
    private Boolean viewCotizacion;

    public String getImagenAcelerador() {
        return StringUtils.isEmpty(imagenAcelerador) ? null : imagenAcelerador;
    }
}
