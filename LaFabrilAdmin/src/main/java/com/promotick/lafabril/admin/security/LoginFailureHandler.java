package com.promotick.lafabril.admin.security;

import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.admin.ConstantesAdminMessage;
import com.promotick.lafabril.model.util.LoginEntity;
import com.promotick.lafabril.model.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private MessageSource messageSource;

    @Autowired
    public LoginFailureHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String urlToRedirect = request.getContextPath() + SpringUtil.AUTHENTICATION_FAILURE_URL;

        String message = Util.getMessage(messageSource, getMessage(exception));

        if (request.getHeader(SpringUtil.HEADER_AJAX) != null) {
            Util.printJson(response, new LoginEntity().setMessage(message));
        } else {
            request.getSession().setAttribute(ConstantesSesion.SESSION_KEY_MSG_SECUIRY_ERROR, message);
            response.sendRedirect(urlToRedirect);
        }
    }

    private String getMessage(AuthenticationException exception) {
        String keyMsgError = StringUtils.EMPTY;

        if (exception instanceof BadCredentialsException) {
            keyMsgError = ConstantesAdminMessage.MSG_EXCEPTION_SECURITY_ERROR_CREDENCIALES_INCORRECTAS;
        } else if (exception instanceof DisabledException) {
            keyMsgError = ConstantesAdminMessage.MSG_EXCEPTION_SECURITY_ERROR_USUARIO_DESABILITADO;
        } else if (exception instanceof AccountExpiredException) {
            keyMsgError = ConstantesAdminMessage.MSG_EXCEPTION_SECURITY_ERROR_USUARIO_EXPIRADO;
        } else if (exception instanceof CredentialsExpiredException) {
            keyMsgError = ConstantesAdminMessage.MSG_EXCEPTION_SECURITY_ERROR_CREDENCIALES_EXPIRADAS;
        } else if (exception instanceof LockedException) {
            keyMsgError = ConstantesAdminMessage.MSG_EXCEPTION_SECURITY_ERROR_USUARIO_BLOQUEADO;
        }

        return keyMsgError;
    }

}
