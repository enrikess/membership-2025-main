package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("login")
public class LoginController extends BaseController {

    private ConfiguracionWebService configuracionWebService;

    @Autowired
    public LoginController(ConfiguracionWebService configuracionWebService) {
        this.configuracionWebService = configuracionWebService;
    }

    @GetMapping
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "clave", required = false) String clave,
                            Model model) {
        log.info("#LoginController.login");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        if (error != null) {
            String mggError = (String) Util.getSession().getAttribute(ConstantesSesion.SESSION_KEY_MSG_SECUIRY_ERROR);
            if (mggError != null) {
                model.addAttribute("error", mggError);
            }
        } else if (clave != null) {
            model.addAttribute("msg", "Se cambio su clave exitosamente, por favor vuelva a iniciar sesion");
        } else if (logout != null) {
            model.addAttribute("msg", "Has salido con exito");
        }

        model.addAttribute("tipodocumento", configuracionWebService.obtenerTipoDocumentos());
        model.addAttribute("configuracionWeb", configuracionWebService.obtenerConfiguracionWeb());

        return ConstantesWebView.VIEW_LOGIN;
    }
}
