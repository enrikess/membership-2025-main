package com.promotick.lafabril.model.web;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {

    private static final long serialVersionUID = 1L;
    private Long idLogAccion;
    private String usuario;
    private String accion;
    private String detalle;
    private LocalDateTime fecha;
    private String headerJson;
    private String bodyJson;
    private String ip;         // IP del cliente
    private String ruta;       // Ruta o endpoint accedido
    private String request;    // Request completo como JSON o texto
    private String response;

    
    public Long getIdLogAccion() {
        return idLogAccion;
    }

    public void setIdLogAccion(Long idLogAccion) {
        this.idLogAccion = idLogAccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getHeaderJson() {
        return headerJson;
    }

    public void setHeaderJson(String headerJson) {
        this.headerJson = headerJson;
    }

    public String getBodyJson() {
        return bodyJson;
    }

    public void setBodyJson(String bodyJson) {
        this.bodyJson = bodyJson;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }



}