package com.promotick.membership.web.service.impl;

import com.promotick.membership.dao.web.LogDao;
import com.promotick.membership.model.web.Log;
import com.promotick.membership.web.service.LogService;
import com.promotick.membership.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LoginService loginService;
    @Autowired
    private LogDao logDao;

    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional
    public void generarLog(String accion, String mensaje, String url, HttpHeaders headers, String body) {
        Log log = Log.builder()
                .ip(request.getRemoteAddr())
                .fecha(LocalDateTime.now())
                .usuario(loginService.obtenerUsuario())
                .accion(accion)
                .detalle(mensaje)
                .ruta(url)
                .headerJson(headers.toString())
                .bodyJson(body)
                .build();
                logDao.guardarLog(log);
    }
}