package com.promotick.lafabril.admin.security;

import com.promotick.lafabril.admin.service.ConfiguracionAdminService;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.admin.Menu;
import com.promotick.lafabril.model.util.LoginEntity;
import com.promotick.lafabril.model.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private ConfiguracionAdminService configuracionAdminService;

    @Autowired
    public LoginSuccessHandler(ConfiguracionAdminService configuracionAdminService) {
        this.configuracionAdminService = configuracionAdminService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SpringUser user = (SpringUser) authentication.getPrincipal();

        request.getSession().setAttribute(ConstantesSesion.SESSION_USUARIO, user.getUsuario());
        request.getSession().setAttribute(ConstantesSesion.SESSION_MENU_MAP, user.getUsuario().getRol().getMenus().stream().collect(Collectors.groupingBy(Menu::getModulo, LinkedHashMap::new, Collectors.toList())));
        request.getSession().setAttribute(ConstantesSesion.SESSION_CONFIGURACION_WEB, configuracionAdminService.obtenerConfiguracionWeb());

        if (request.getHeader(SpringUtil.HEADER_AJAX) != null) {
            Util.printJson(response, new LoginEntity().setStatus(true));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
