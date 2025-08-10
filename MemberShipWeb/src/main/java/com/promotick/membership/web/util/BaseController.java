package com.promotick.membership.web.util;

import com.promotick.membership.model.util.PromotickResult;
import com.promotick.membership.model.util.Util;
import com.promotick.membership.model.util.UtilEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;

import java.util.Properties;

public class BaseController {

    @Autowired
    @Qualifier("propertiesPromotickConfig")
    public Properties properties;

    @Autowired
    public MessageSource messageSource;

    protected void evaluarResultado(Integer resultado, PromotickResult promotickResult, String mensaje) throws Exception {
        if (resultado == null) {
            throw new Exception(Util.getMessage(messageSource, UtilEnum.obtenerError(-2).getKey()));
        } else {
            if (resultado <= UtilEnum.ESTADO_OPERACION.EXITO.getCodigo()) {
                throw new Exception(Util.getMessage(messageSource, UtilEnum.obtenerError(resultado).getKey()));
            }
        }

        promotickResult.setMessage(mensaje);
    }

}
