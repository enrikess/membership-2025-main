package com.promotick.membership.web.controller;

import com.promotick.membership.model.MisionDto;
import com.promotick.membership.model.Promocion;
import com.promotick.membership.web.service.LoginService;
import com.promotick.membership.web.service.MisionService;
import com.promotick.membership.web.service.PromocionService;
import com.promotick.membership.web.util.BaseController;
import com.promotick.membership.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class InicioController extends BaseController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MisionService misionService;

    /**
     * Endpoint para mostrar la vista HTML
     */
    @GetMapping("/")
    public String index(Model model) {
        try {
            List<MisionDto> misiones = misionService.obtenerMisiones();
            model.addAttribute("misiones", misiones);
        } catch (Exception e) {
            model.addAttribute("misiones", "[]");
        }
        return ConstantesWebView.VIEW_RECOMPENSAS_INDEX;
    }

    @GetMapping("/hiring")
    public String hiring() {
        return ConstantesWebView.VIEW_RECOMPENSAS_HIRING;
    }

    @GetMapping("/tag-palabra")
    public String tagPalabra(@RequestParam String palabra, Model model) {
        model.addAttribute("palabra", palabra);
        return ConstantesWebView.VIEW_RECOMPENSAS_COMPONENTES_TAG_FILTRAR_PROMOCIONES;
    }
}
