package com.promotick.membership.web.service;

import org.springframework.http.HttpHeaders;

public interface LogService {

    void generarLog(String accion, String mensaje, String url, HttpHeaders headers, String body);
}
