package com.promotick.lafabril.web.session;

import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Pedido;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class SessionArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@NonNull MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Participante.class) ||
                methodParameter.getParameterType().equals(Auditoria.class) ||
                methodParameter.getParameterType().equals(Pedido.class) ||
                methodParameter.getParameterType().equals(PromotickResult.class) ||
                methodParameter.getParameterType().equals(ConfiguracionWeb.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, @NonNull NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        if (methodParameter.getParameterType().equals(Participante.class)) {
            return this.resolverParticipante(servletRequest);
        } else if (methodParameter.getParameterType().equals(Auditoria.class)) {
            return this.resolverAuditoria(servletRequest);
        } else if (methodParameter.getParameterType().equals(Pedido.class)) {
            return this.resolverPedido(servletRequest);
        } else if (methodParameter.getParameterType().equals(PromotickResult.class)) {
            return new PromotickResult();
        } else if (methodParameter.getParameterType().equals(ConfiguracionWeb.class)) {
            return this.resolverConfiguracionWeb(servletRequest);
        }
        return null;
    }

    private Object resolverParticipante(HttpServletRequest servletRequest) {
        return servletRequest.getSession().getAttribute(ConstantesSesion.SESSION_PARTICIPANTE);
    }

    private Object resolverAuditoria(HttpServletRequest servletRequest) {
        Participante participante = (Participante) servletRequest.getSession().getAttribute(ConstantesSesion.SESSION_PARTICIPANTE);
        if (participante == null) {
            return new Auditoria();
        }
        return new Auditoria()
                .setUsuarioCreacion(participante.getUsuarioParticipante())
                .setUsuarioActualizacion(participante.getUsuarioParticipante());
    }

    private Object resolverPedido(HttpServletRequest servletRequest) {
        Pedido pedido = (Pedido) servletRequest.getSession().getAttribute(ConstantesSesion.SESSION_PEDIDO);
        if (pedido == null) {
            pedido = new Pedido();
            servletRequest.getSession().setAttribute(ConstantesSesion.SESSION_PEDIDO, pedido);
        }
        return pedido;
    }

    private Object resolverConfiguracionWeb(HttpServletRequest servletRequest) {
        return servletRequest.getSession().getAttribute(ConstantesSesion.SESSION_CONFIGURACION_WEB);
    }
}
