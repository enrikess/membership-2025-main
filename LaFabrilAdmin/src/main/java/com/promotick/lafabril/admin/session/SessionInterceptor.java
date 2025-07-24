package com.promotick.lafabril.admin.session;

import com.promotick.lafabril.admin.security.SpringUtil;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.configuration.models.PromotickInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SessionInterceptor extends PromotickInterceptor {
    @Override
    public String[] addPathPatterns() {
        return new String[0];
    }

    @Override
    public String[] excludePathPatterns() {
        return new String[]{
                SpringUtil.LOGIN_PROCESSING_URL,
                SpringUtil.LOGIN_PAGE,
                SpringUtil.ERROR,
                SpringUtil.CRONS,
                SpringUtil.API_ECUADOR_ADMIN
        };
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            ResponseBody responseBody = ((HandlerMethod) handler).getMethodAnnotation(ResponseBody.class);
            if (responseBody == null) {

                Usuario usuarioSesion = (Usuario) request.getSession().getAttribute(ConstantesSesion.SESSION_USUARIO);

                if (usuarioSesion == null) {
                    if (!request.getRequestURI().endsWith(SpringUtil.LOGIN_PAGE)) {
                        log.info("Sesion Expirada");
                        response.sendRedirect(request.getContextPath() + SpringUtil.LOGIN_PAGE);
                        return false;
                    }
                } else {
                    if (request.getRequestURI().endsWith(SpringUtil.LOGIN_PAGE)) {
                        log.info("Ya existe una sesion");
                        response.sendRedirect(request.getContextPath());
                        return false;
                    }
                }

            } else {
                Usuario usuarioSesion = (Usuario) request.getSession().getAttribute(ConstantesSesion.SESSION_USUARIO);
                if (usuarioSesion == null) {
                    this.makeResponseError(response);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return true;
        }

    }

    private void makeResponseError(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        PromotickResult promotickResult = new PromotickResult();
        promotickResult.setStatus(Boolean.FALSE);
        promotickResult.setMessage("Sesion ha caducado, por favor refresque la pagina");
        Util.printJson(response, promotickResult);
    }
}
