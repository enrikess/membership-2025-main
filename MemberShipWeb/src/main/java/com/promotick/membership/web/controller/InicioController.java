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

import java.util.List;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class InicioController extends BaseController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private PromocionService promocionService;
    @Autowired
    private MisionService misionService;

    /**
     * Endpoint para mostrar la vista HTML
     */
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        try {
            String cedula = loginService.obtenerUsuario();
            String token = loginService.obtenerToken();
            //List<Promocion> promociones = promocionService.obtenerPromociones(token);
            List<MisionDto> misiones = misionService.obtenerMisiones(token);
            model.addAttribute("misiones", misiones);
            model.addAttribute("cedula", cedula);
            model.addAttribute("tokenStatus", "success");
        } catch (Exception e) {
            model.addAttribute("tokenStatus", "error");
            model.addAttribute("cedula", "");
            model.addAttribute("misiones", "[]");
        }
        return ConstantesWebView.VIEW_RECOMPENSAS_INDEX;
    }

//    /**
//     * Endpoint para mostrar la vista HTML
//     */
//    @GetMapping({"/", "/index"})
//    public String index(Model model) {
//        try {
//            String cedula = loginService.obtenerUsuario();
//            String token = loginService.obtenerToken();
//            List<Promocion> promociones = promocionService.obtenerPromociones(token);
//            List<MisionDto> misiones = misionService.obtenerMisiones(token);
//            model.addAttribute("promociones", promociones);
//            model.addAttribute("misiones", misiones);
//            model.addAttribute("cedula", cedula);
//            model.addAttribute("tokenStatus", "success");
//        } catch (Exception e) {
//            model.addAttribute("tokenStatus", "error");
//            model.addAttribute("promociones", "[]");
//            model.addAttribute("misiones", "[]");
//        }
//        return ConstantesWebView.VIEW_RECOMPENSAS_INDEX;
//    }
}
