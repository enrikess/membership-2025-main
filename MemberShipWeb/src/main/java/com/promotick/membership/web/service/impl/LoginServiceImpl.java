package com.promotick.membership.web.service.impl;

import com.promotick.membership.common.ConstantesApi;
import com.promotick.membership.model.Login;
import com.promotick.membership.model.Token;
import com.promotick.membership.web.service.LogService;
import com.promotick.membership.web.service.LoginService;
import com.promotick.membership.web.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Properties;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    @Qualifier("propertiesPromotickConfig")
    private Properties properties;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LogService logService;

    private final RestTemplate restTemplate = new RestTemplate();
    private String tokenCache;
    private LocalDateTime tokenExpiracion;
    private String tokenType;
    private String tokenScope;
    private String usuario;

    @Override
    public String obtenerToken() {
        try {
            log.info("🔍 Verificando token en cache...");
            log.info("📋 Token cache actual: " + (tokenCache != null ? "EXISTE" : "NULL"));
            log.info("📋 Token expiración: " + tokenExpiracion);

            if (tokenEsValido()) {
                log.info("✅ Usando token desde cache");
                log.info("⏰ Expira en: " + ChronoUnit.MINUTES.between(LocalDateTime.now(), tokenExpiracion)
                        + " minutos");
                return tokenCache;
            }

            log.info("🔄 Token no válido - Generando nuevo token...");
            // Si no hay token válido, obtener uno nuevo
            String nuevoToken = generarToken();
            log.info("🎯 Nuevo token generado: " + (nuevoToken != null ? "ÉXITO" : "FALLÓ"));
            return nuevoToken;
        } catch (Exception e) {
            log.error("❌ Error obteniendo token: " + e.getMessage());
            return null;
        }
    }

    /**
     * Limpiar cache del token
     */
    public void limpiarCache() {
        log.info("🗑️ LLAMADA A limpiarCache() - Stack trace:");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 2; i < Math.min(6, stackTrace.length); i++) {
            log.info("   " + stackTrace[i].toString());
        }

        this.tokenCache = null;
        this.tokenExpiracion = null;
        this.tokenType = null;
        this.tokenScope = null;
        log.info("🗑️ Cache del token limpiado");
    }

    private String procesarRespuestaToken(ResponseEntity<Token> response) {
        log.info("🔍 Procesando respuesta del token...");
        log.info("📋 Status Code: " + response.getStatusCode());
        log.info("📋 Tiene Body: " + (response.getBody() != null));

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Token token = response.getBody();
            log.info("📋 Access Token existe: " + (token.getAccessToken() != null));

            if (token.getAccessToken() != null) {
                log.info("✅ Token válido encontrado - Guardando en cache");
                // Guardar token en cache
                this.tokenCache = token.getAccessToken();
                this.tokenType = token.getTokenType();
                this.tokenScope = token.getScope();

                log.info("📋 Token guardado en cache: " + (this.tokenCache != null ? "SÍ" : "NO"));
                log.info("📋 Token type: " + this.tokenType);
                log.info("📋 Token scope: " + this.tokenScope);

                // Calcular expiración basada en expires_in
                Integer expiresIn = token.getExpiresIn();
                log.info("📋 Expires in (segundos): " + expiresIn);

                if (expiresIn != null) {
                    this.tokenExpiracion = LocalDateTime.now().plusSeconds(expiresIn);
                } else {
                    // Valor por defecto: 1 hora
                    this.tokenExpiracion = LocalDateTime.now().plusHours(1);
                }

                log.info("📋 Token expirará: " + this.tokenExpiracion);
                return this.tokenCache;
            } else {
                log.error("❌ No se encontró access_token en la respuesta");
            }
        } else {
            log.error("❌ Respuesta inválida: " + response.getStatusCode());
        }
        return null;
    }

    /**
     * Obtener identificador del cache si está válido
     */
    public String obtenerUsuario() {
        if (usuario != null) {
            return usuario;
        }
        return null;
    }

    /**
     * Guardar identificador en cache
     */
    public void guardarUsuario(String identificador) {
        this.usuario = identificador;
        log.info("✅ Usuario guardado en cache: " + identificador);
    }

    /**
     * Limpiar cache del identificador
     */
    @Override
    public void logout() {
        this.usuario = null;
        this.limpiarCache();
    }

    @Override
    public Login loguearCedula(String cedula) {
        log.info("🔐 Procesando login con cédula: " + cedula);
        String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
        String url = baseUrl + ConstantesApi.RECOMPENSAS_API_LOGIN;
        String token = this.obtenerToken();
        HttpEntity<Void> request = ApiUtil.crearRequestConHeaders(
                token,
                cedula,
                ConstantesApi.RECOMPENSAS_HOST
        );
        try {
            ResponseEntity<Login> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Login.class
            );
            Login login = response.getBody();
            if (login.isSuccess()) {
                this.guardarUsuario(cedula);
            }
            return login;
        } catch (Exception e) {
            log.error("❌ Error realizando el login: " + e.getMessage());
            logService.generarLog("POST", e.getMessage(), url, request.getHeaders(), "");
            return Login.builder()
                    .success(false)
                    .error(e.getMessage())
                    .build();
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

    private String generarToken() {
        String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL_BASE_TOKEN);
        String tokenUrl = baseUrl + ConstantesApi.RECOMPENSAS_API_TOKEN;
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Accept", "application/json");
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", properties.getProperty(ConstantesApi.RECOMPENSAS_GRANT_TYPE));
            formData.add("client_id", properties.getProperty(ConstantesApi.RECOMPENSAS_CLIENT_ID));
            formData.add("client_secret", properties.getProperty(ConstantesApi.RECOMPENSAS_CLIENT_SECRET));

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

            ResponseEntity<Token> response = restTemplate.exchange(
                    tokenUrl, HttpMethod.POST, request,
                    new org.springframework.core.ParameterizedTypeReference<Token>() {
                    });
            String resultado = procesarRespuestaToken(response);
            log.info("🎯 Resultado final del procesamiento: " + (resultado != null ? "ÉXITO" : "FALLÓ"));
            return resultado;

        } catch (Exception e) {
            logService.generarLog("POST", e.getMessage(), tokenUrl, headers, "");
            log.error("❌ Error obteniendo token dinámico: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
