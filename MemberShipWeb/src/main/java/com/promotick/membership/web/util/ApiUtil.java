package com.promotick.membership.web.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ApiUtil {
    public static HttpEntity<Void> crearRequestConHeaders(String token, String usuario, String host) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("Accept", "application/json");
        headers.set("Host", host);
        headers.set("identificacion", usuario);
        return new HttpEntity<>(headers);
    }
    
}

