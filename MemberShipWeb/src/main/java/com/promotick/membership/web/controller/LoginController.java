package com.promotick.membership.web.controller;

import com.promotick.membership.model.Login;
import com.promotick.membership.model.LoginDto;
import com.promotick.membership.web.service.LoginService;
import com.promotick.membership.web.util.BaseController;
import com.promotick.membership.web.util.ConstantesWebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping()
public class LoginController extends BaseController {
    @Autowired
    LoginService loginService;

    /**
     * Endpoint para mostrar la vista HTML
     */
    @GetMapping("/login")
    public String login() {
        return ConstantesWebView.VIEW_LOGIN;
    }

    @GetMapping("/logout")
    public String logout() {
        loginService.logout();
        return "redirect:/";
    }

    /**
     * Endpoint para procesar login
     */
    @PostMapping(value = "/loguear", consumes = "application/json", produces = "application/json")
    public LoginDto loguear(@RequestParam String cedula) {
        log.info("📞 POST /loguear llamado");
        Login login = loginService.loguearCedula(cedula);
        return LoginDto.builder()
                .success(login.isSuccess())
                .data(login.getData())
                .error(login.getError())
                .build();
    }
}
