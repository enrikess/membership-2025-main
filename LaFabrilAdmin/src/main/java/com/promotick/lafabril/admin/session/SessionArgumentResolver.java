package com.promotick.lafabril.admin.session;

import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
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
        return methodParameter.getParameterType().equals(Auditoria.class) ||
                methodParameter.getParameterType().equals(Usuario.class) ||
                methodParameter.getParameterType().equals(PromotickResult.class) ||
                methodParameter.getParameterType().equals(ConfiguracionWeb.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, @NonNull NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();

        if (methodParameter.getParameterType().equals(Auditoria.class)) {
            return this.resolveAuditoria(servletRequest);
        } else if (methodParameter.getParameterType().equals(Usuario.class)) {
            return this.resolveUsuario(servletRequest);
        } else if (methodParameter.getParameterType().equals(PromotickResult.class)) {
            return new PromotickResult();
        } else if (methodParameter.getParameterType().equals(ConfiguracionWeb.class)) {
            return this.resolverConfiguracionWeb(servletRequest);
        }

        return null;
    }

    private Object resolveAuditoria(HttpServletRequest servletRequest) {
        Usuario usuario = (Usuario) servletRequest.getSession().getAttribute(ConstantesSesion.SESSION_USUARIO);

        if (usuario == null) {
            return new Auditoria();
        }

        return new Auditoria()
                .setUsuarioCreacion(usuario.getUsuario())
                .setUsuarioActualizacion(usuario.getUsuario())
                .setIdUsuarioCreacion(usuario.getIdUsuario())
                .setIdUsuarioActualizacion(usuario.getIdUsuario());
    }

    private Object resolveUsuario(HttpServletRequest servletRequest) {
        return servletRequest.getSession().getAttribute(ConstantesSesion.SESSION_USUARIO);
    }

    private Object resolverConfiguracionWeb(HttpServletRequest servletRequest) {
        return servletRequest.getSession().getAttribute(ConstantesSesion.SESSION_CONFIGURACION_WEB);
    }
}
