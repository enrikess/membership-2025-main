package com.promotick.membership.web.controller;

import com.promotick.membership.model.MisionDto;
import com.promotick.membership.model.Promocion;
import com.promotick.membership.web.service.LoginService;
import com.promotick.membership.web.service.MisionService;
import com.promotick.membership.web.service.PromocionService;
import com.promotick.membership.web.util.BaseController;
import com.promotick.membership.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
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
            
            // Obtener la cédula desde la variable global del LoginService
            String cedula = loginService.obtenerUsuario();
            log.info("🔍 DEBUG: Valor obtenido de loginService.obtenerUsuario(): " + cedula);
            
            if (cedula != null && !cedula.isEmpty()) {
                model.addAttribute("cedula", cedula);
            } else {
                log.info("ℹ️ No hay usuario logueado en la variable global");
                log.info("ℹ️ DEBUG: No se agregará cédula al modelo");
            }
        } catch (Exception e) {
            log.error("❌ Error en index: " + e.getMessage());
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
