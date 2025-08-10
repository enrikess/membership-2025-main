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

//    @GetMapping("/conexion")
//    public String conexion() {
//        System.out.println("📞 Endpoint /recompensas/conexion llamado");
//        return recompensasService.obtenerToken();
//    }

    /**
     * Endpoint para obtener misiones disponibles
     */
    @GetMapping("/misiones")
    public Object obtenerMisiones() {
        System.out.println("📞 Endpoint /recompensas/misiones llamado");
        return recompensasService.obtenerPromociones();
    }

    /**
     * Endpoint genérico para hacer consultas personalizadas
     */
    // @PostMapping("/consulta")
    // public String hacerConsulta(@RequestParam String endpoint,
    // @RequestBody(required = false) Object payload) {
    // System.out.println("📞 Endpoint /recompensas/consulta llamado - " +
    // endpoint);
    // return recompensasService.hacerConsultaConToken(endpoint, payload);
    // }VIEW_RECOMPENSAS_LOGIN


    /**
     * Endpoint para mostrar la vista HTML
     */
    @GetMapping("/index")
    public String index(Model model) {
        try {
            String token = recompensasService.obtenerToken();
            String cedula = recompensasService.obtenerIdentificadorCache();
            if (token != null && !token.isEmpty()) {
                Object promociones = recompensasService.obtenerPromociones();
                Object misiones = recompensasService.obtenerMisiones();

                model.addAttribute("promociones", promociones);
                model.addAttribute("misiones", misiones);
                model.addAttribute("cedula", cedula);

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

        return ConstantesWebView.VIEW_RECOMPENSAS_INDEX;
    }


    @GetMapping("/hiring")
    public String hiring(Model model) {
        try {
            String token = recompensasService.obtenerToken();
            String cedula = recompensasService.obtenerIdentificadorCache();
            if (token != null && !token.isEmpty()) {
                Object promociones = recompensasService.obtenerPromociones();
                Object misiones = recompensasService.obtenerMisiones();

                model.addAttribute("promociones", promociones);
                model.addAttribute("misiones", misiones);
                model.addAttribute("cedula", cedula);

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

        return ConstantesWebView.VIEW_RECOMPENSAS_HIRING;
    }


    /**
     * Endpoint para mostrar la vista HTML
     */
    @GetMapping("/detalle_mision/{id_mision}")
    public String detalle_mision(@PathVariable("id_mision") Number id_mision, Model model) {
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



    @GetMapping("/api/tag-palabra")
    public String tagPalabra(@RequestParam String palabra, Model model) {
        model.addAttribute("palabra", palabra);
        return ConstantesWebView.VIEW_RECOMPENSAS_COMPONENTES_TAG_FILTRAR_PROMOCIONES;

    }

    @GetMapping("/api/detalle_mision/{id_mision}")
    @ResponseBody
    public Object buscarMisionesId(@PathVariable("id_mision") Number id_mision) {

        String token = recompensasService.obtenerToken();
        String cedula = recompensasService.obtenerIdentificadorCache();

        //List<String> palabras = (List<String>) payload.get("palabras");
        // Usa el nombre "palabras" para identificar el array
        Object mision = recompensasService.obtenerMisionesPorId(id_mision);

        return mision;
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