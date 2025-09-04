package com.promotick.membership.web.service.impl;

import com.promotick.membership.common.ConstantesApi;
import com.promotick.membership.model.Promocion;
import com.promotick.membership.model.PromocionResponse;
import com.promotick.membership.web.service.LogService;
import com.promotick.membership.web.service.LoginService;
import com.promotick.membership.web.service.PromocionService;
import com.promotick.membership.web.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class PromocionServiceImpl implements PromocionService {
    @Autowired
    @Qualifier("propertiesPromotickConfig")
    private Properties properties;
    @Autowired
    private LoginService loginService;
    @Autowired
    private LogService logService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Promocion> obtenerPromociones(String token) {
        String baseUrl = properties.getProperty(ConstantesApi.RECOMPENSAS_URL);
        String url = baseUrl + ConstantesApi.RECOMPENSAS_API_PROMOCIONES;

        HttpEntity<Void> request = ApiUtil.crearRequestConHeaders(
                token,
                loginService.obtenerUsuario(),
                ConstantesApi.RECOMPENSAS_HOST
        );
        log.info("🔗 GET: " + url);
        try {
            ResponseEntity<PromocionResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    PromocionResponse.class
            );
            log.info("✅ GET exitoso - Status: " + response.getStatusCode());
            List<Promocion> promociones = response.getBody().getPromociones();
            return promociones;
        } catch (Exception e) {
            log.error("❌ Error obteniendo promociones: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), url, request.getHeaders(), "");
            return new ArrayList<>();
        }
    }

    @Override
    public List<Promocion> obtenerPromociones(List<String> palabras) {
        try {
            String token = loginService.obtenerToken();
            List<Promocion> promociones = this.obtenerPromociones(token);
            
            // Si la lista de palabras está vacía (size 0), retornar todas las promociones
            if (palabras.size() == 0) {
                return promociones;
            }
            
            return promociones.stream()
                    .filter(promocion ->
                            palabras.stream().allMatch(palabra ->
                                    (promocion.getNombreComercio() != null &&
                                            promocion.getNombreComercio().toLowerCase().contains(palabra.toLowerCase()))
                                            ||
                                            (promocion.getDescripcion() != null &&
                                                    promocion.getDescripcion().toLowerCase().contains(palabra.toLowerCase()))
                            )
                    )
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("❌ Error obteniendo promociones: " + e.getMessage());
            logService.generarLog("GET", e.getMessage(), "", null, "");
            return new ArrayList<>();
        }
    }
}
