package com.promotick.membership.web.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class ApiUtil {
    public static HttpEntity<Void> crearRequestConHeaders(String token, String usuario, String host) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Accept", "application/json");
        headers.set("identificacion", usuario);
        headers.set("Host", host);
        return new HttpEntity<>(headers);
    }
}
