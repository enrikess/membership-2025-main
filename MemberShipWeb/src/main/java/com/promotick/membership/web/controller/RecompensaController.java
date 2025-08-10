package com.promotick.membership.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.promotick.membership.web.service.LogService;
import com.promotick.membership.web.service.RecompensasWebService;
import com.promotick.membership.web.util.ConstantesWebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/recompensas")
public class RecompensaController {

    @Autowired
    private LogService logService;


    @Autowired
    private RecompensasWebService recompensasService;

    @PostConstruct
    public void init() {
        System.out.println("🚀 TestController inicializado correctamente!");
    }

    /**
     * Endpoint para mostrar la vista HTML
     */
    @GetMapping("/detalle_mision_logeado/{id_mision}")
    public String detalle_mision_logeado(@PathVariable("id_mision") Number id_mision, Model model) {
        try {
            String token = recompensasService.obtenerToken();
            String cedula = recompensasService.obtenerIdentificadorCache();
            if (token != null && !token.isEmpty()) {
                model.addAttribute("cedula", cedula);
                model.addAttribute("id_mision", id_mision);
                model.addAttribute("tokenStatus", "success");
            } else {
                model.addAttribute("tokenStatus", "error");
                model.addAttribute("promociones", "[]");
                model.addAttribute("misiones", "[]");
            }

        } catch (Exception e) {
            model.addAttribute("tokenStatus", "error");
            model.addAttribute("promociones", "[]");
            model.addAttribute("misiones", "[]");
        }

        return ConstantesWebView.VIEW_RECOMPENSAS_DETALLE_MISION;
    }


    @GetMapping("/api/registrar_mision_recompensa/{id_mision}/{id_recompensa}")
    @ResponseBody
    public Object registrarMisionRecompensa(@PathVariable("id_mision") Number id_mision, @PathVariable("id_recompensa") Number id_recompensa) {

        String token = recompensasService.obtenerToken();
        String cedula = recompensasService.obtenerIdentificadorCache();

        //List<String> palabras = (List<String>) payload.get("palabras");
        // Usa el nombre "palabras" para identificar el array
        Object respuesta = recompensasService.registrarMisionRecompensa(id_mision, id_recompensa);

        return respuesta;
    }
}