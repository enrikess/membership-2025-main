package com.promotick.lafabril.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.promotick.lafabril.model.web.Log;
import com.promotick.lafabril.web.service.LogService;
import com.promotick.lafabril.web.service.RecompensasWebService;
import com.promotick.lafabril.web.util.ConstantesWebView;

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

    @GetMapping("/conexion")
    public String conexion() {
        System.out.println("📞 Endpoint /recompensas/conexion llamado");
        return recompensasService.obtenerToken();
    }

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
    @GetMapping("/login")
    public String login() {

        return ConstantesWebView.VIEW_RECOMPENSAS_LOGIN;
    }

    @GetMapping("/logout")
    public String logout() {
        recompensasService.limpiarIdentificadorCache();
        return "redirect:/recompensas/index";
    }


    /**
     * Endpoint para procesar login y usar cédula como identificador
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> loginPost(@RequestParam String cedula) {

        Log log = new Log();
        log.setUsuario("sistema");
        log.setAccion("GET " );
        log.setDetalle("No se pudo obtener token");
        log.setFecha(LocalDateTime.now());
        log.setHeaderJson("123");
        log.setBodyJson("123");
        log.setIp("123");
        log.setRuta("123");
        log.setRequest("123");
        log.setResponse("123");
        logService.guardarLog(log);

        System.out.println("📞 POST /recompensas/login llamado");
        System.out.println("🆔 Cédula recibida como identificador: " + cedula);

        Map<String, Object> response = new HashMap<>();

        try {
            Object respuestaJson = recompensasService.logearCedulaJSON(cedula);
            System.out.println("📋 Respuesta capturada del servicio:");
            System.out.println(respuestaJson);

            // Procesar la respuesta JSON
            if (respuestaJson instanceof JsonNode) {
                System.out.println("✅ Es instancia de JsonNode");
                JsonNode jsonNode = (JsonNode) respuestaJson;

                // DEBUG: Verificar estructura del JSON
                System.out.println("🔍 JSON completo: " + jsonNode.toString());
                System.out.println("🔍 Tiene campo 'code': " + jsonNode.has("code"));

                if (jsonNode.has("code")) {
                    int codeValue = jsonNode.get("code").asInt();
                    System.out.println("🔍 Valor del code: " + codeValue);
                    System.out.println("🔍 Es >= 400: " + (codeValue >= 400));
                    System.out.println("🔍 Es < 500: " + (codeValue < 500));
                    System.out.println("🔍 Condición completa: " + (codeValue >= 400 && codeValue < 500));
                } else {
                    System.out.println("❌ No tiene campo 'code'");
                }

                // Verificar si es error 400
                if (jsonNode.has("code") && jsonNode.get("code").asInt() >= 400 && jsonNode.get("code").asInt() < 500) {
                    // ES ERROR 4xx
                    int errorCode = jsonNode.get("code").asInt();
                    response.put("success", false);
                    response.put("statusCode", errorCode);
                    response.put("identificador", cedula);

                    // Extraer mensaje específico del error
                    String errorMessage = "Error de validación";
                    if (jsonNode.has("message")) {
                        errorMessage = jsonNode.get("message").asText();
                    }

                    // Si hay errores específicos, usar el primer mensaje de validación
                    if (jsonNode.has("errors")) {
                        JsonNode errorsArray = jsonNode.get("errors");
                        if (errorsArray.isArray() && errorsArray.size() > 0) {
                            JsonNode firstError = errorsArray.get(0);
                            if (firstError.has("validations")) {
                                JsonNode validations = firstError.get("validations");
                                if (validations.isArray() && validations.size() > 0) {
                                    errorMessage = validations.get(0).asText();
                                }
                            }
                        }
                    }

                    response.put("message", errorMessage);
                    response.put("data", jsonNode);

                    System.out.println("❌ Error 400 procesado: " + errorMessage);

                } else {
                    // RESPUESTA EXITOSA
                    response.put("success", true);
                    response.put("message", "Login procesado correctamente");
                    response.put("identificador", cedula);
                    response.put("data", jsonNode);

                    System.out.println("✅ Login exitoso procesado");
                }

            } else if (respuestaJson instanceof Map) {
                // Si es un Map (respuesta de error del servicio)
                @SuppressWarnings("unchecked")
                Map<String, Object> errorMap = (Map<String, Object>) respuestaJson;

                response.put("success", false);
                response.put("message", "Error en el servicio");
                response.put("error", errorMap.get("message"));
                response.put("statusCode", errorMap.get("statusCode"));

            } else {
                // Respuesta inesperada
                response.put("success", false);
                response.put("message", "Respuesta inesperada del servicio");
                response.put("error", respuestaJson != null ? respuestaJson.toString() : "null");
            }

        } catch (Exception e) {
            System.err.println("💥 Error procesando respuesta: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Error interno del servidor");
            response.put("error", e.getMessage());
        }

        System.out.println("📤 Respuesta final del controlador: " + response);
        return response;
    }

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

        return ConstantesWebView.VIEW_RECOMPENSAS_NO_LOGIN;
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
    public String detalle_mision(@PathVariable("id_mision") Number id_mision,Model model) {
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
    public String detalle_mision_logeado(@PathVariable("id_mision") Number id_mision,Model model) {
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



    @PostMapping("/api/promociones")
    @ResponseBody
    public Object buscarPromociones(@RequestBody Map<String, Object> payload) {
    List<String> palabras = (List<String>) payload.get("palabras");

        //List<String> palabras = (List<String>) payload.get("palabras");
        // Usa el nombre "palabras" para identificar el array
        Object promocionesFiltradas = recompensasService.obtenerPromociones(palabras);
        return promocionesFiltradas;
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
    public Object registrarMisionRecompensa(@PathVariable("id_mision") Number id_mision,@PathVariable("id_recompensa") Number id_recompensa) {
    
        String token = recompensasService.obtenerToken();
        String cedula = recompensasService.obtenerIdentificadorCache();

        //List<String> palabras = (List<String>) payload.get("palabras");
        // Usa el nombre "palabras" para identificar el array
        Object respuesta = recompensasService.registrarMisionRecompensa(id_mision,id_recompensa);

        return respuesta;
    } 
}