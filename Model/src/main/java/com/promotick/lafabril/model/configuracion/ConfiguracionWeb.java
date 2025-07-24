package com.promotick.lafabril.model.configuracion;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.Date;

@Data
public class
ConfiguracionWeb extends BeanBase {
    private static final long serialVersionUID = -4267736089267573284L;

    private Integer idConfiguracionWeb;
    private String correosCopiaOculta;
    private String correosContactos;
    private String destinatarioContactos;
    private String emailInfo;
    private String emailContacto;
    private String aliasContacto;
    private String aliasCorreo;
    private String asuntoCanje;
    private String correoSistemas;
    private String telefonoContacto;
    private String tycRegistro;
    private String claveDefecto;
    private String avisoWeb;
    private String horario;
    private String facebook;
    private String popupInicio;
    private String emailRebote;
    private String copiasRebote;
    private String twitter;
    private String youtube;
    private String emailCotizacion;
    private String telefonoWhatsapp;
    private String textoWhatsapp;
    private String bannerFlotante;
    private String urlAndroid;
    private String urlIos;
    private String paginaWeb;
    private String mapsKey;
    private String mapsUrlValidator;
    private String mapsUrlImage;
    private String mapsDefault;
    private Date fechaVencimiento;
    private Integer horaVencimiento;
    private Integer encuestaEstado;
    private Integer minPuntosCaducidad;
    private Double iva;
    private String correoRecomendado;
    private String anio;

    //no persiste
    private String fechaVencimientoString;
}
