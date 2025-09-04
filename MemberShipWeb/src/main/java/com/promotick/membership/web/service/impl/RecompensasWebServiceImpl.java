package com.promotick.membership.web.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.promotick.membership.common.ConstantesApi;
import com.promotick.membership.web.service.LogService;
import com.promotick.membership.web.service.RecompensasWebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.promotick.membership.model.web.Log;

@Service
public class RecompensasWebServiceImpl implements RecompensasWebService {

    @Autowired
    @Qualifier("propertiesPromotickConfig")
    private Properties properties;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private LogService logService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    // Cache del token JWT
    private String tokenCache;
    private LocalDateTime tokenExpiracion;
    private String tokenType;
    private String tokenScope;
    // Cache del identificador de usuario
    private String identificadorCache;

    @Override
    public Boolean probarConexion() {
        try {
            String token = obtenerToken();
            return token != null && !token.isEmpty();
        } catch (Exception e) {
            System.err.println("❌ Error en probarConexion: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), "", null, "");
            return false;
        }
    }

    @Override
    public String obtenerToken() {
        try {
            // Verificar si el token en cache es válido
            if (tokenEsValido()) {
                System.out.println("✅ Usando token desde cache");
                System.out.println("⏰ Expira en: " + ChronoUnit.MINUTES.between(LocalDateTime.now(), tokenExpiracion)
                        + " minutos");
                return tokenCache;
            }

            // Si no hay token válido, obtener uno nuevo
            String nuevoToken = obtenerTokenDinamico();
            return nuevoToken;

        } catch (Exception e) {
            System.err.println("❌ Error obteniendo token: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), "", null, "");
            return null;
        }
    }

    /**
     * Verificar si el token en cache es válido
     */
    private boolean tokenEsValido() {
        return tokenCache != null
                && tokenExpiracion != null
                && LocalDateTime.now().isBefore(tokenExpiracion.minusMinutes(5)); // Renovar 5 min antes de expirar
    }

    /**
     * Limpiar cache del token
     */
    public void limpiarCache() {
        this.tokenCache = null;
        this.tokenExpiracion = null;
        this.tokenType = null;
        this.tokenScope = null;
        System.out.println("🗑️ Cache del token limpiado");
    }

    private String obtenerTokenDinamico() {
        try {
            // Usar URL base de configuración + endpoint de token
            String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL_BASE_TOKEN);

            // Limpiar y normalizar la URL
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }

            String tokenUrl = baseUrl + "/apiservice/protocol/openid-connect/token";

            System.out.println("🚀 Solicitando token dinámico...");
            System.out.println("🔗 Base URL: " + baseUrl);
            System.out.println("🔗 Token URL: " + tokenUrl);
            System.out.println("🔑 Client ID: " + properties.getProperty(ConstantesApi.RECOMPENSAS_CLIENT_ID));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Accept", "application/json");

            // Construir form data como key-value pairs (igual que en Postman)
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", properties.getProperty(ConstantesApi.RECOMPENSAS_GRANT_TYPE));
            formData.add("client_id", properties.getProperty(ConstantesApi.RECOMPENSAS_CLIENT_ID));
            formData.add("client_secret", properties.getProperty(ConstantesApi.RECOMPENSAS_CLIENT_SECRET));

            System.out.println("📤 Form Data (key-value pairs):");
            formData.forEach((key, value) -> System.out.println("   " + key + " = " + value.get(0)));

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    tokenUrl, HttpMethod.POST, request,
                    new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
            });

            return procesarRespuestaToken(response);

        } catch (HttpClientErrorException e) {
            System.err.println("❌ Error HTTP " + e.getStatusCode() + ": " + e.getResponseBodyAsString());
            logService.generarLog("POST", "Error HTTP " + e.getStatusCode() + ": " + e.getResponseBodyAsString(), "", null, "");
            return null;
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo token dinámico: " + e.getMessage());
            logService.generarLog("POST", e.getMessage(), "", null, "");
            return null;
        }
    }

    private String procesarRespuestaToken(ResponseEntity<Map<String, Object>> response) {
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();

            System.out.println("📋 Respuesta completa del servidor de tokens:");
            responseBody.forEach((key, value) -> System.out.println("   " + key + " = " + value));

            String accessToken = (String) responseBody.get("access_token");

            if (accessToken != null) {
                // Guardar token en cache
                this.tokenCache = accessToken;
                this.tokenType = (String) responseBody.get("token_type");
                this.tokenScope = (String) responseBody.get("scope");

                // Calcular expiración basada en expires_in
                Integer expiresIn = (Integer) responseBody.get("expires_in");
                if (expiresIn != null) {
                    this.tokenExpiracion = LocalDateTime.now().plusSeconds(expiresIn);
                    System.out.println(
                            "⏰ Token expira en: " + expiresIn + " segundos (" + (expiresIn / 60) + " minutos)");
                    System.out.println("📅 Fecha de expiración: " + tokenExpiracion);
                } else {
                    // Valor por defecto: 1 hora
                    this.tokenExpiracion = LocalDateTime.now().plusHours(1);
                }

                System.out.println("✅ Token dinámico obtenido y guardado en cache");
                System.out.println("🔖 Token Type: " + tokenType);
                System.out.println("🎯 Scope: " + tokenScope);
                System.out.println("🎫 Token (primeros 100 chars): "
                        + accessToken.substring(0, Math.min(100, accessToken.length())) + "...");

                return accessToken;
            } else {
                System.err.println("❌ No se encontró access_token en la respuesta");
            }
        } else {
            System.err.println("❌ Respuesta inválida: " + response.getStatusCode());
        }
        return null;
    }

    /**
     * Implementación específica para obtener misiones disponibles
     */
    @Override
    public Object obtenerPromociones() {
        try {
            // Ejemplo de payload para consultar misiones

            return hacerConsultaGET("/recompensas/v1/promociones");

        } catch (Exception e) {
            System.err.println("❌ Error obteniendo misiones: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), "/recompensas/v1/promociones", null, "");
            return "❌ Error: " + e.getMessage();
        }
    }

    /**
     * Implementación específica para obtener misiones disponibles
     */
    @Override
    public Object obtenerPromociones(List<String> palabras) {
        try {
            // Ejemplo de payload para consultar misiones
            JsonNode resultado = (JsonNode) hacerConsultaGET("/recompensas/v1/promociones");
            // Accede al array "data" dentro del JSON
            JsonNode dataNode = resultado.get("data");

            ObjectMapper mapper = new ObjectMapper();
            List<JsonNode> promocionesFiltradas = new java.util.ArrayList<>();

            if (dataNode != null && dataNode.isArray() && palabras != null && !palabras.isEmpty()) {
                for (JsonNode promo : dataNode) {
                    String pr_nombre_comercio = promo.has("pr_nombre_comercio") ? promo.get("pr_nombre_comercio").asText().toLowerCase() : "";
                    String pr_descripcion = promo.has("pr_descripcion") ? promo.get("pr_descripcion").asText().toLowerCase() : "";

                    // Todas las palabras deben coincidir en nombre o descripción
                    boolean todasCoinciden = palabras.stream().allMatch(palabra -> {
                        String palabraLower = palabra.toLowerCase();
                        return pr_nombre_comercio.contains(palabraLower) || pr_descripcion.contains(palabraLower);
                    });

                    if (todasCoinciden) {
                        promocionesFiltradas.add(promo);
                    }
                }
                // Retornar solo las promociones filtradas en el mismo formato
                return mapper.createObjectNode().set("data", mapper.valueToTree(promocionesFiltradas));
            }

            return resultado;
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo misiones: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), "/recompensas/v1/promociones", null, "");
            return "❌ Error: " + e.getMessage();
        }
    }

    /**
     * Método GET para endpoints que no requieren payload
     */
    public Object hacerConsultaGET(String endpoint) {
        HttpHeaders headers = null;
        String url = null;
        ResponseEntity<String> response = null;
        String ip = request.getRemoteAddr();
        try {
            String token = obtenerToken();
            if (token == null) {
//                crearLog(
//                    identificadorCache != null ? identificadorCache : "anonimo",
//                    "GET " + endpoint,
//                    "No se pudo obtener token",
//                    "", "", ip, endpoint, "", ""
//                );
                return crearRespuestaError("No se pudo obtener token de autenticación", 500);
            }

            String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }

            url = baseUrl + endpoint;

            headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.set("Accept", "application/json");
            headers.set("identificacion", identificadorCache);
            headers.set("Host", ConstantesApi.RECOMPENSAS_HOST);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            System.out.println("🔗 GET: " + url);
            System.out.println("🔑 Token: " + token.substring(0, Math.min(50, token.length())) + "...");

            response = restTemplate.exchange(
                    url, HttpMethod.GET, request, String.class);

            System.out.println("✅ GET exitoso - Status: " + response.getStatusCode());

        // crearLog(
        //     identificadorCache != null ? identificadorCache : "anonimo",
        //     "GET " + endpoint,
        //     "GET exitoso - Status: " + response.getStatusCode(),
        //     headers,
        //     "",
        //     url,
        //     "",
        //     response.getBody()
        // );
            // PARSEAR Y DEVOLVER JSON COMO OBJETO
            String jsonResponse = response.getBody();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                return jsonNode;
            } catch (Exception parseEx) {
                return crearRespuestaTexto(jsonResponse, response.getStatusCode().value());
            }

        } catch (HttpClientErrorException e) {
            String errorJsonResponse = e.getResponseBodyAsString();
            logService.generarLog("GET", "Error HTTP " + e.getStatusCode() + ": " + errorJsonResponse, url != null ? url : "", headers, "");
            try {
                JsonNode errorNode = objectMapper.readTree(errorJsonResponse);
                return errorNode;
            } catch (Exception parseEx) {
                return crearRespuestaError(errorJsonResponse, e.getStatusCode().value());
            }
        } catch (Exception e) {
            logService.generarLog("GET", "Error de conexión: " + e.getMessage(), url != null ? url : "", headers, "");
            return crearRespuestaError("Error de conexión: " + e.getMessage(), 500);
        }
    }

//    @Override
//    public Object logearCedulaJSON(String cedula) {
//        System.out.println("🔐 Procesando login con cédula en header (JSON): " + cedula);
//        Object resultado = hacerConsultaPOST("/recompensas/v1/login", new HashMap<>(), cedula);
//
//        // Si el login es exitoso, guardar el identificador en cache
//        if (resultado instanceof JsonNode) {
//            JsonNode jsonNode = (JsonNode) resultado;
//            // Verificar si NO es un error (no tiene code de error)
//            if (!jsonNode.has("code") || jsonNode.get("code").asInt() < 400) {
//                System.out.println("✅ Login exitoso - Guardando identificador en cache");
//                guardarIdentificador(cedula);
//            }
//        }
//
//        return resultado;
//
//    }

    /**
     * Método POST para endpoints que requieren cédula en header - RETORNA JSON
     */
    public Object hacerConsultaPOST(String endpoint, Object payload, String cedula) {
        String ip = request.getRemoteAddr();
        HttpHeaders headers = null;
        String url = null;
        ResponseEntity<String> response = null;
        try {
            String token = obtenerToken();
            if (token == null) {
//                crearLog(
//                    identificadorCache != null ? identificadorCache : "anonimo",
//                    "POST " + endpoint,
//                    "No se pudo obtener token",
//                    "", payload != null ? payload.toString() : "", ip, endpoint, payload != null ? payload.toString() : "", ""
//                );
                return crearRespuestaError("No se pudo obtener token de autenticación", 500);
            }

            String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
            url = baseUrl + endpoint;

            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);
            headers.set("Accept", "application/json");
            headers.set("identificacion", cedula);
            headers.set("Host", ConstantesApi.RECOMPENSAS_HOST);
            HttpEntity<Object> request = new HttpEntity<>(payload, headers);

            System.out.println("🔗 POST: " + url);
            System.out.println("🔑 Token: " + token.substring(0, Math.min(50, token.length())) + "...");
            System.out.println("🆔 Header identificacion: " + cedula);
            System.out.println("📦 Payload: " + payload);

            response = restTemplate.exchange(
                    url, HttpMethod.POST, request, String.class);

            System.out.println("✅ POST exitoso - Status: " + response.getStatusCode());
//            crearLog(
//                identificadorCache != null ? identificadorCache : "anonimo",
//                "POST " + endpoint,
//                "POST exitoso - Status: " + response.getStatusCode(),
//                headers.toString(),
//                payload != null ? payload.toString() : "",
//                ip,
//                endpoint,
//                payload != null ? payload.toString() : "",
//                response.getBody()
//            );
            // PARSEAR Y DEVOLVER JSON COMO OBJETO
            String jsonResponse = response.getBody();
            System.out.println("📋 JSON recibido del servidor:");
            System.out.println(jsonResponse);

            try {
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                System.out.println("✅ JSON parseado correctamente como objeto");
                return jsonNode;
            } catch (Exception parseEx) {
                System.err.println("⚠️ Error parseando JSON, devolviendo como texto: " + parseEx.getMessage());
                return crearRespuestaTexto(jsonResponse, response.getStatusCode().value());
            }

        } catch (HttpClientErrorException e) {
            System.err.println("❌ Error HTTP " + e.getStatusCode());

            // PARSEAR JSON DE ERROR Y DEVOLVERLO COMO OBJETO
            String errorJsonResponse = e.getResponseBodyAsString();
            logService.generarLog("POST", "Error HTTP " + e.getStatusCode() + ": " + errorJsonResponse, url != null ? url : "", headers, payload != null ? payload.toString() : "");
            System.err.println("📋 JSON de error del servidor:");
            System.err.println(errorJsonResponse);

            try {
                JsonNode errorNode = objectMapper.readTree(errorJsonResponse);
                System.out.println("✅ JSON de error parseado correctamente como objeto");
                return errorNode;
            } catch (Exception parseEx) {
                System.err.println("⚠️ Error parseando JSON de error: " + parseEx.getMessage());
                return crearRespuestaError(errorJsonResponse, e.getStatusCode().value());
            }

        } catch (Exception e) {
            logService.generarLog("POST", "Error de conexión: " + e.getMessage(), url != null ? url : "", headers, payload != null ? payload.toString() : "");
            System.err.println("❌ Error de conexión: " + e.getMessage());
            return crearRespuestaError("Error de conexión: " + e.getMessage(), 500);
        }
    }

    /**
     * Crear respuesta de error estructurada
     */
    private Map<String, Object> crearRespuestaError(String mensaje, int statusCode) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", true);
        error.put("message", mensaje);
        error.put("statusCode", statusCode);
        error.put("timestamp", LocalDateTime.now().toString());
        return error;
    }

    /**
     * Crear respuesta con texto cuando no se puede parsear JSON
     */
    private Map<String, Object> crearRespuestaTexto(String contenido, int statusCode) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("success", true);
        respuesta.put("data", contenido);
        respuesta.put("statusCode", statusCode);
        respuesta.put("timestamp", LocalDateTime.now().toString());
        return respuesta;
    }

    /**
     * Guardar identificador en cache después de login exitoso
     */
    public void guardarIdentificador(String cedula) {
        this.identificadorCache = cedula;
        System.out.println("💾 Identificador guardado en cache: " + cedula + " (válido hasta " + ")");
    }

    /**
     * Obtener identificador del cache si está válido
     */
    public String obtenerIdentificadorCache() {
        if (identificadorCache != null) {
            System.out.println("✅ Usando identificador del cache: " + identificadorCache);
            return identificadorCache;
        }
        System.out.println("❌ Cache del identificador expirado o no válido");
        limpiarIdentificadorCache();
        return null;
    }

    /**
     * Limpiar cache del identificador
     */
    @Override
    public void limpiarIdentificadorCache() {
        this.identificadorCache = null;
        System.out.println("🗑️ Cache del identificador limpiado");
    }

    /**
     * Verificar si hay un identificador válido en cache
     */
    public boolean tieneIdentificadorValido() {
        return obtenerIdentificadorCache() != null;
    }

    public Object obtenerMisiones() {
        try {
            // Ejemplo de payload para consultar misiones

            Object misionesObj = hacerConsultaGET("/recompensas/v1/misiones");
            Object registradasObj = hacerConsultaGET("/recompensas/v1/registradas");
            if (misionesObj instanceof JsonNode) {
                JsonNode misionesNode = (JsonNode) misionesObj;
                JsonNode dataMisionesNode = misionesNode.get("data");
                JsonNode registradasNode = (JsonNode) registradasObj;
                if (registradasNode.has("code") && registradasNode.get("code").asInt() >= 400) {
                    // Es un error, retorna o maneja según tu lógica
                    //System.err.println("❌ Error en registradas: " + registradasNode.get("message").asText());
                    // Puedes retornar la misión sin modificar o manejar el error como prefieras
                    return misionesObj;
                }
                JsonNode dataRegistradasNode = registradasNode.get("data");
                if (dataMisionesNode != null && dataMisionesNode.isArray()) {
                    for (JsonNode mision : dataMisionesNode) {
                        if (!(mision instanceof ObjectNode)) continue; // Solo si es modificable
                        ObjectNode misionObj = (ObjectNode) mision;
                        for (JsonNode registrada : dataRegistradasNode) {
                            if (mision.has("mi_id_mision") && 
                            registrada.has("mu_id_mision") &&
                            mision.get("mi_id_mision").asInt() == registrada.get("mu_id_mision").asInt()) 
                            {
                                misionObj.set("mu_progreso", registrada.get("mu_progreso"));
                            }
                            

                        }

                    }
                }
            }


            return misionesObj;

        } catch (Exception e) {
            System.err.println("❌ Error obteniendo misiones: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), "/recompensas/v1/misiones", null, "");
            return "❌ Error: " + e.getMessage();
        }
    }

    @Override
    public Object obtenerMisionesPorId(Number idMision) {
        try {
            // Ejemplo de payload para consultar misiones
            Object registradasObj = hacerConsultaGET("/recompensas/v1/registradas");
            Object misionObj = hacerConsultaGET("/recompensas/v1/misiones?misionId=" + idMision);
            JsonNode registradasNode = (JsonNode) registradasObj;
            JsonNode dataRegistradasNode = registradasNode.get("data");
            if (dataRegistradasNode instanceof JsonNode) {
                JsonNode misionesNode = (JsonNode) misionObj;
                JsonNode dataMisionesNode = misionesNode.get("data");
                if (dataMisionesNode != null && dataMisionesNode.isObject()) {
                    ObjectNode misionUnica = (ObjectNode) dataMisionesNode;
                    boolean encontrada = false;
                    if (dataRegistradasNode != null && dataRegistradasNode.isArray()) {
                        for (JsonNode registrada : dataRegistradasNode) {
                            if (
                                misionUnica.has("mi_id_mision") &&
                                registrada.has("mu_id_mision") &&
                                misionUnica.get("mi_id_mision").asInt() == registrada.get("mu_id_mision").asInt()
                            ) {
                                // Si coincide, agrega el campo que desees
                                misionUnica.put("mu_progreso", registrada.get("mu_progreso"));
                                misionUnica.put("registrada", true);

                                break;
                            }
                        }
                    }
                    return misionesNode;
                }
            }
            return misionObj;

        } catch (Exception e) {
            System.err.println("❌ Error obteniendo misiones: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), "/recompensas/v1/misiones?misionId=" + idMision, null, "");
            return "❌ Error: " + e.getMessage();
        }
    }


    @Override
    public Object registrarMisionRecompensa(Number idMision,Number idRecompensa){

        Object resultado = hacerConsultaPOST("/recompensas/v1/misiones/registrar?misionId="+idMision+"&recompensaId="+idRecompensa, new HashMap<>(), identificadorCache);
        return resultado;
    }

    private void crearLog(String usuario, String accion, String detalle, HttpHeaders headers, String body, String url, String request, String response) {
        logService.generarLog(accion, detalle, url, headers, body);
    }

}
