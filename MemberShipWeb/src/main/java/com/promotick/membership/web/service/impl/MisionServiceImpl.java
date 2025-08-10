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
            ResponseEntity<MisionListResponse> responseMisiones = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    MisionListResponse.class
            );
            log.info("✅ GET exitoso - Status: " + responseMisiones.getStatusCode());
            List<Mision> misiones = responseMisiones.getBody().getMisiones();
            this.obtenerMisionesRegistradas(request);
//            ResponseEntity<MisionListResponse> responseRegistradas = restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    request,
//                    MisionListResponse.class
//            );
//            log.info("✅ GET exitoso - Status: " + responseRegistradas.getStatusCode());
              List<MisionRegistrada> misionesRegistradas = new ArrayList<>();

              List<MisionDto> misionDtos = misiones.stream()
                      .map(mision -> {
                          MisionRegistrada registrada = misionesRegistradas.stream()
                                  .filter(misionRegistrada -> misionRegistrada.getIdMision() == mision.getIdMision())
                                  .findFirst().orElse(null);
                          return MisionDto.builder()
                                  .idMision(mision.getIdMision())
                                  .descripcion(mision.getDescripcion())
                                  .progreso(registrada != null ? registrada.getProgreso() : 0)
                                  .build();
                      })
                      .collect(Collectors.toList());
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
        try {
            //            ResponseEntity<MisionListResponse> responseRegistradas = restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    request,
//                    MisionListResponse.class
//            );
//            log.info("✅ GET exitoso - Status: " + responseRegistradas.getStatusCode());
            List<MisionRegistrada> misionesRegistradas = new ArrayList<>();
            return misionesRegistradas;
        } catch (Exception e) {
            log.error("❌ Error obteniendo misiones: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
            return new ArrayList<>();
        }
    }

    @Override
    public Object registrarMisionRecompensa(long idMision,long idRecompensa){
            return null;
//        Object resultado = hacerConsultaPOST("/recompensas/v1/misiones/registrar?misionId="+idMision+"&recompensaId="+idRecompensa, new HashMap<>(), identificadorCache);
//        return resultado;
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
