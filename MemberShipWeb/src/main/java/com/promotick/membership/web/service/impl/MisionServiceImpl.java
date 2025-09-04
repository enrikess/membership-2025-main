package com.promotick.membership.web.service.impl;

import com.promotick.membership.common.ConstantesApi;
import com.promotick.membership.common.UtilCommon;
import com.promotick.membership.model.*;
import com.promotick.membership.web.service.LogService;
import com.promotick.membership.web.service.LoginService;
import com.promotick.membership.web.service.MisionService;
import com.promotick.membership.web.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MisionServiceImpl implements MisionService {
    @Autowired
    @Qualifier("propertiesPromotickConfig")
    private Properties properties;
    @Autowired
    private LoginService loginService;
    @Autowired
    private LogService logService;
    private final RestTemplate restTemplate = new RestTemplate();

public List<MisionDto> obtenerMisiones() {
        String token = loginService.obtenerToken();
        String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
        String url = baseUrl + ConstantesApi.RECOMPENSAS_API_MISIONES;
        HttpEntity<Void> request = ApiUtil.crearRequestConHeaders(
                token,
                loginService.obtenerUsuario(),
                ConstantesApi.RECOMPENSAS_HOST
        );
        log.info("🔗 GET: " + url);
        try {
            // Obtener misiones normales
            ResponseEntity<MisionListResponse> responseMisiones = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    MisionListResponse.class
            );
            log.info("✅ GET exitoso - Status: " + responseMisiones.getStatusCode());
            List<Mision> misiones = responseMisiones.getBody().getMisiones();
            log.info("📋 Misiones obtenidas: " + misiones.size());
            
            // Verificar si hay usuario logueado
            String cedula = loginService.obtenerUsuario();
            
            if (cedula == null || cedula.isEmpty()) {
                // Sin usuario logueado - retornar misiones simples
                log.info("ℹ️ No hay usuario logueado - Retornando misiones sin progreso");
                List<MisionDto> misionDtos = misiones.stream()
                        .map(mision -> MisionDto.builder()
                                .idMision(mision.getIdMision())
                                .descripcion(mision.getDescripcion())
                                .progreso(0.0)
                                .build())
                        .collect(Collectors.toList());
                return misionDtos;
            }
            
            // Con usuario logueado - hacer merge con misiones registradas
            List<MisionRegistrada> misionesRegistradas = this.obtenerMisionesRegistradas(request);
            log.info("📋 Usuario logueado - Misiones registradas obtenidas: " + misionesRegistradas.size());

            List<MisionDto> misionDtos = misiones.stream()
                    .map(mision -> {
                        MisionRegistrada registrada = misionesRegistradas.stream()
                                .filter(misionRegistrada -> misionRegistrada.getIdMision() == mision.getIdMision())
                                .findFirst().orElse(null);
                        
                        double progreso = registrada != null ? registrada.getProgreso() : 0.0;
                        log.debug("🔍 Misión ID: " + mision.getIdMision() + 
                                 " - Progreso: " + progreso + 
                                 " - Registrada: " + (registrada != null));
                        
                        return MisionDto.builder()
                                .idMision(mision.getIdMision())
                                .descripcion(mision.getDescripcion())
                                .progreso(progreso)
                                .build();
                    })
                    .collect(Collectors.toList());
            
            log.info("✅ Merge completado - Total DTOs: " + misionDtos.size());
            return misionDtos;

        } catch (Exception e) {
            log.error("❌ Error obteniendo misiones: " + e.getMessage());
            
            // Logging con manejo de errores para evitar problemas de conexión en primera carga
            try {
                logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
            } catch (Exception logEx) {
                log.warn("⚠️ No se pudo guardar log en BD (posible problema de inicialización): " + logEx.getMessage());
            }
            
            return new ArrayList<>();
        }
    }

    @Override
    public DetalleMisionDto obtenerMisionesPorId(long idMision) {
        String token = loginService.obtenerToken();
        String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
        String url = baseUrl + ConstantesApi.RECOMPENSAS_API_MISIONES + "?misionId=" + idMision;
        HttpEntity<Void> request = ApiUtil.crearRequestConHeaders(
                token,
                loginService.obtenerUsuario(),
                ConstantesApi.RECOMPENSAS_HOST
        );
        log.info("🔗 GET: " + url);
        try {
            ResponseEntity<MisionResponse> responseMisiones = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    MisionResponse.class
            );
            log.info("✅ GET exitoso - Status: " + responseMisiones.getStatusCode());
            Mision mision = responseMisiones.getBody().getMision();
            
            if (mision == null) {
                log.error("❌ La misión obtenida es null");
                return DetalleMisionDto.builder().build();
            }
            
            log.info("📋 Misión obtenida - ID: " + mision.getIdMision() + " - Descripción: " + mision.getDescripcion());
            
            // Verificar si hay usuario logueado
            String cedula = loginService.obtenerUsuario();
            
            if (cedula == null || cedula.isEmpty()) {
                // Sin usuario logueado - retornar misión sin progreso
                log.info("ℹ️ No hay usuario logueado - Retornando misión sin progreso");
                return this.buildDetalleMisionDto(mision, 0.0, false);
            }
            
            // Con usuario logueado - consultar progreso
            List<MisionRegistrada> misionesRegistradas = obtenerMisionesRegistradas(request);
            log.info("📋 Usuario logueado - Consultando progreso de misión ID: " + idMision);
            
            DetalleMisionDto detalleMisionDto = misionesRegistradas.stream()
                    .filter(misionRegistrada -> misionRegistrada.getIdMision() == mision.getIdMision())
                    .map(misionRegistrada -> this.buildDetalleMisionDto(mision, misionRegistrada.getProgreso(), true))
                    .findFirst().orElse(this.buildDetalleMisionDto(mision, 0.0, false));
        return detalleMisionDto;
        } catch (Exception e) {
            log.error("❌ Error obteniendo misión por ID " + idMision + ": " + e.getMessage());
            
            // Logging con manejo de errores para evitar problemas de conexión en primera carga
            try {
                logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
            } catch (Exception logEx) {
                log.warn("⚠️ No se pudo guardar log en BD (posible problema de inicialización): " + logEx.getMessage());
            }
            // Retornar un objeto vacío pero válido en lugar de null
            return DetalleMisionDto.builder()
                    .descripcion("Error al cargar la misión")
                    .progreso(0.0)
                    .registrada(false)
                    .build();
        }
    }

    private List<MisionRegistrada> obtenerMisionesRegistradas(HttpEntity<Void> request) {
        String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
        String url = baseUrl + ConstantesApi.RECOMPENSAS_API_MISIONES_REGISTRADAS;
        log.info("🔗 GET Misiones Registradas: " + url);
        
        try {
            ResponseEntity<MisionRegistradaListResponse> responseRegistradas = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    MisionRegistradaListResponse.class
            );
            log.info("✅ GET Misiones Registradas exitoso - Status: " + responseRegistradas.getStatusCode());
            
            if (responseRegistradas.getBody() != null && responseRegistradas.getBody().getMisionesRegistradas() != null) {
                List<MisionRegistrada> misionesRegistradas = responseRegistradas.getBody().getMisionesRegistradas();
                log.info("📋 Misiones registradas encontradas: " + misionesRegistradas.size());
                return misionesRegistradas;
            } else {
                log.warn("⚠️ Respuesta de misiones registradas es null o vacía");
                return new ArrayList<>();
            }
            
        } catch (Exception e) {
            log.error("❌ Error obteniendo misiones registradas: " + e.getMessage());
            
            // Logging con manejo de errores para evitar problemas de conexión en primera carga
            try {
                logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
            } catch (Exception logEx) {
                log.warn("⚠️ No se pudo guardar log en BD (posible problema de inicialización): " + logEx.getMessage());
            }
            
            return new ArrayList<>();
        }
    }

    @Override
    public String registrarMisionRecompensa(long idMision,long idRecompensa){
        String token = loginService.obtenerToken();
        String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
        String url = baseUrl + ConstantesApi.RECOMPENSAS_API_MISIONES_REGISTRAR+ "?misionId="+idMision+"&recompensaId="+idRecompensa;
        log.info("🔗 POST Misiones Registradas: " + url);
        HttpEntity<Void> request = ApiUtil.crearRequestConHeaders(
                token,
                loginService.obtenerUsuario(),
                ConstantesApi.RECOMPENSAS_HOST
        );
        try {
            ResponseEntity<MisionRegistradaResponse> responseRegistradas = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    MisionRegistradaResponse.class
            );
            log.info("✅ GET Misiones Registradas exitoso - Status: " + responseRegistradas.getStatusCode());

            if (responseRegistradas.getBody() != null && responseRegistradas.getBody().getCode() == 200) {
                return "{\"code\": 200, \"message\": \"Misión registrada exitosamente\"}";
            } else {
                log.warn("⚠️ Respuesta de misiones registradas es null o vacía");
                return responseRegistradas.getBody().getErrors().get(0).getMessage();
            }

        } catch (HttpClientErrorException e) {
            log.error("❌ Error HTTP al registrar misión - Status: " + e.getStatusCode() + " - Body: " + e.getResponseBodyAsString());
            
            // Logging con manejo de errores para evitar problemas de conexión en primera carga
            try {
                logService.generarLog("POST", e.getMessage(), url, request.getHeaders(), "");
            } catch (Exception logEx) {
                log.warn("⚠️ No se pudo guardar log en BD (posible problema de inicialización): " + logEx.getMessage());
            }
            
            // Retornar directamente el JSON de error que viene del API
            return e.getResponseBodyAsString();
            
        } catch (Exception e) {
            log.error("❌ Error general al registrar misión: " + e.getMessage());
            
            // Logging con manejo de errores para evitar problemas de conexión en primera carga
            try {
                logService.generarLog("POST", e.getMessage(), url, request.getHeaders(), "");
            } catch (Exception logEx) {
                log.warn("⚠️ No se pudo guardar log en BD (posible problema de inicialización): " + logEx.getMessage());
            }
            
            // Error genérico - crear JSON de error
            return "{\"code\": 500, \"message\": \"Error interno del servidor\"}";
        }
    }

    private DetalleMisionDto buildDetalleMisionDto(Mision mision, double progreso, boolean registrada) {
        return DetalleMisionDto.builder()
                .descripcion(mision.getDescripcion())
                .fechaFin(UtilCommon.dateFormat(mision.getFechaRegistroFin()))
                .progreso(progreso)
                .recompensas(mision.getRecompensas())
                .registrada(registrada)
                .build();
    }
}
