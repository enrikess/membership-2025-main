package com.promotick.lafabril.web.security;

import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.LoginEntity;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.Transaccion;
import com.promotick.lafabril.web.service.EncuestaWebService;
import com.promotick.lafabril.web.service.TransaccionWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TransaccionWebService transaccionWebService;
    private final ConfiguracionWebService configuracionWebService;
    private final EncuestaWebService encuestaWebService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        SpringUser user = (SpringUser) authentication.getPrincipal();

        //Put Session
        request.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, user.getParticipante());
        request.getSession().setAttribute(ConstantesSesion.SESSION_ACTUALIZACION_DATOS, user.getParticipante().getActualizarDatos());
        request.getSession().setAttribute(ConstantesSesion.SESSION_ENCUESTA_PENDIENTE, encuestaWebService.encuestaPedidoObtener(user.getParticipante().getIdParticipante()));
        request.getSession().setAttribute(ConstantesSesion.SESSION_CONFIGURACION_WEB, configuracionWebService.obtenerConfiguracionWeb());
        request.getSession().setAttribute(ConstantesSesion.SESSION_PEDIDO, new Pedido());
        request.getSession().setAttribute(ConstantesSesion.SESSION_POPUP_INICIO, true);

        //Guardado de visita
        Transaccion transaccion = new Transaccion(new Auditoria().setUsuarioCreacion("WEB"))
                .setTipoDispositivo(UtilEnum.TIPO_DISPOSITVO_VISITA.WEB.getCodigo())
                .setIdParticipante(user.getParticipante().getIdParticipante())
                .setTipoOperacionVisita(UtilEnum.TIPO_OPERACION_VISITA.VISITA_WEB)
                .setIdCatalogo(user.getParticipante().getCatalogo().getIdCatalogo());

        transaccionWebService.guardarTransaccionWeb(transaccion);

        if (request.getHeader(SpringUtil.HEADER_AJAX) != null) {
            Util.printJson(response, new LoginEntity().setStatus(true));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }

}
