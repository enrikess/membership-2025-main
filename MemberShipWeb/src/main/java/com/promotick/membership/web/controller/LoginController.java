package com.promotick.membership.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.promotick.membership.web.service.LoginService;
import com.promotick.membership.web.util.BaseController;
import com.promotick.membership.web.util.ConstantesWebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
     * Endpoint para procesar login y usar cédula como identificador
     */
    @PostMapping("/loguear")
    @ResponseBody
    public Map<String, Object> loguear(@RequestParam String cedula) {
        log.info("📞 POST /loguear llamado");
        log.info("🆔 Cédula recibida como identificador: " + cedula);
        Map<String, Object> response = new HashMap<>();

        try {
            Object respuestaJson = loginService.loguearCedula(cedula);
            log.info("📋 Respuesta capturada del servicio:");
            log.info(respuestaJson.toString());

            // Procesar la respuesta JSON
            if (respuestaJson instanceof JsonNode) {
                log.info("✅ Es instancia de JsonNode");
                JsonNode jsonNode = (JsonNode) respuestaJson;

                // DEBUG: Verificar estructura del JSON
                log.info("🔍 JSON completo: " + jsonNode.toString());
                log.info("🔍 Tiene campo 'code': " + jsonNode.has("code"));

                if (jsonNode.has("code")) {
                    int codeValue = jsonNode.get("code").asInt();
                    log.info("🔍 Valor del code: " + codeValue);
                    log.info("🔍 Es >= 400: " + (codeValue >= 400));
                    log.info("🔍 Es < 500: " + (codeValue < 500));
                    log.info("🔍 Condición completa: " + (codeValue >= 400 && codeValue < 500));
                } else {
                    log.info("❌ No tiene campo 'code'");
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

                    log.info("❌ Error 400 procesado: " + errorMessage);

                } else {
                    // RESPUESTA EXITOSA
                    response.put("success", true);
                    response.put("message", "Login procesado correctamente");
                    response.put("identificador", cedula);
                    response.put("data", jsonNode);

                    log.info("✅ Login exitoso procesado");
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
            log.error("💥 Error procesando respuesta: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Error interno del servidor");
            response.put("error", e.getMessage());
        }

        log.info("📤 Respuesta final del controlador: " + response);
        return response;
    }
}
