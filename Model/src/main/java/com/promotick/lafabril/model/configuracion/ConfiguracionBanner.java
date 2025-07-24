package com.promotick.lafabril.model.configuracion;

import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.model.web.Catalogo;
import lombok.Data;

@Data
public class ConfiguracionBanner extends BeanBase {
    private static final long serialVersionUID = 6999566770807987009L;

    private Integer idConfiguracionBanner;
    private Catalogo catalogo;
    private String imagenBanner;
    private String imagenTexto;
    private String textoBoton;
    private String urlBoton;
    private Integer urlLocation;
    private String iconoBanner;
    private Integer estadoConfiguracionBanner;
    private Integer idCatalogo;
    private Integer orden;
    private Integer tipoBanner;
    private String textoUno;
    private String textoDos;
    private String textoTres;
    private String textoCuatro;
    private String textoCinco;

}
