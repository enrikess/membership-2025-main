package com.promotick.lafabril.web.session;

import com.promotick.lafabril.web.security.SpringUtil;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.configuration.models.PromotickInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SessionInterceptor extends PromotickInterceptor {

    private static final String PATH_ACTUALIZAR_DATOS = "/actualizar";
    private static final String PATH_ENCUESTA = "/encuesta";

    @Override
    public String[] addPathPatterns() {
        return new String[0];
    }

    @Override
    public String[] excludePathPatterns() {
        return new String[]{
                SpringUtil.LOGIN_PROCESSING_URL,
                SpringUtil.LOGIN_PAGE,
                SpringUtil.RECOVERY_PAGE,
                SpringUtil.REGISTER_PAGE,
                SpringUtil.ERROR,
                SpringUtil.RECOMPENSAS,

        };
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            ResponseBody responseBody = ((HandlerMethod) handler).getMethodAnnotation(ResponseBody.class);
            if (responseBody == null) {

                Participante participante = (Participante) request.getSession().getAttribute(ConstantesSesion.SESSION_PARTICIPANTE);

                //Validacion de sesion
                if (participante == null) { //No existe sesion
                    if (!request.getRequestURI().endsWith(SpringUtil.LOGIN_PAGE) && !request.getRequestURI().contains(SpringUtil.RECOVERY_PAGE)) {
                        log.info("Sesion Expirada");
                        response.sendRedirect(request.getContextPath() + SpringUtil.LOGIN_PAGE);
                        return false;
                    }
                } else { // Existe session

                    if (!request.getRequestURI().contains("/view")) {
                        //participante = participanteWebService.loginParticipante(participante.getTipoDocumento().getIdTipoDocumento(), participante.getNroDocumento());
                        //request.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, participante);
                        //request.getSession().setAttribute(ConstantesSesion.SESSION_CONFIGURACION_WEB, configuracionWebService.obtenerConfiguracionWeb());
                    }

                    Boolean actualizacionDatos = (Boolean) request.getSession().getAttribute(ConstantesSesion.SESSION_ACTUALIZACION_DATOS);
                    Integer encuestaPendiente = (Integer) request.getSession().getAttribute(ConstantesSesion.SESSION_ENCUESTA_PENDIENTE);
                    ConfiguracionWeb configuracionWeb = (ConfiguracionWeb) Util.getSession().getAttribute(ConstantesSesion.SESSION_CONFIGURACION_WEB);
                    Integer encuesta = configuracionWeb.getEncuestaEstado();

                    //Validacion actualizacion de datos
                    if (actualizacionDatos && !request.getRequestURI().endsWith(PATH_ACTUALIZAR_DATOS) && !request.getRequestURI().endsWith(PATH_ENCUESTA)) {
                        log.info("Se redirige actualizacion de datos");
                        response.sendRedirect(request.getContextPath() + PATH_ACTUALIZAR_DATOS);
                        return false;
                    } else if (encuestaPendiente != null && !request.getRequestURI().endsWith(PATH_ENCUESTA) && !request.getRequestURI().endsWith(PATH_ACTUALIZAR_DATOS) && encuesta != null && encuesta > 0) {
                        log.info("Se redirige a responder encuesta");
                        response.sendRedirect(request.getContextPath() + PATH_ENCUESTA);
                        return false;
                    } else if (request.getRequestURI().endsWith(SpringUtil.LOGIN_PAGE)) {
                        log.info("Ya existe una sesion");
                        response.sendRedirect(request.getContextPath());
                        return false;
                    }

                }
            } else {
                Participante participante = (Participante) request.getSession().getAttribute(ConstantesSesion.SESSION_PARTICIPANTE);
                if (participante == null) {
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
