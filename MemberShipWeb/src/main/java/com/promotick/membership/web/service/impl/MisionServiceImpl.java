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
            
            // Obtener misiones registradas
            List<MisionRegistrada> misionesRegistradas = this.obtenerMisionesRegistradas(request);
            log.info("📋 Misiones obtenidas: " + misiones.size());
            log.info("📋 Misiones registradas obtenidas: " + misionesRegistradas.size());

            // Hacer merge entre misiones y misiones registradas
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
            logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
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
            List<MisionRegistrada> misionesRegistradas = obtenerMisionesRegistradas(request);
            DetalleMisionDto detalleMisionDto = misionesRegistradas.stream()
                    .filter(misionRegistrada -> misionRegistrada.getIdMision() == mision.getIdMision())
                    .map(misionRegistrada -> this.buildDetalleMisionDto(mision, misionRegistrada.getProgreso(), true))
                    .findFirst().orElse(this.buildDetalleMisionDto(mision, 0.0, false));
        return detalleMisionDto;
        } catch (Exception e) {
            log.error("❌ Error obteniendo misiones: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
            return DetalleMisionDto.builder().build();
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
            logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
            return new ArrayList<>();
        }
    }

    @Override
    public String registrarMisionRecompensa(long idMision,long idRecompensa){
        String token = loginService.obtenerToken();
        String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
        String url = baseUrl + ConstantesApi.RECOMPENSAS_API_MISIONES_REGISTRAR+ "?misionId="+idMision+"&recompensaId="+idRecompensa;
        log.info("🔗 GET Misiones Registradas: " + url);
        HttpEntity<Void> request = ApiUtil.crearRequestConHeaders(
                token,
                loginService.obtenerUsuario(),
                ConstantesApi.RECOMPENSAS_HOST
        );
        try {
            ResponseEntity<MisionRegistradaResponse> responseRegistradas = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    MisionRegistradaResponse.class
            );
            log.info("✅ GET Misiones Registradas exitoso - Status: " + responseRegistradas.getStatusCode());

            if (responseRegistradas.getBody() != null && responseRegistradas.getBody().getCode() == 200) {
                return "OK";
            } else {
                log.warn("⚠️ Respuesta de misiones registradas es null o vacía");
                return responseRegistradas.getBody().getErrors().get(0).getMessage();
            }

        } catch (Exception e) {
            log.error("❌ Error al registrar mision: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
            return e.getMessage();
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
