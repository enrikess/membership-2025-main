package com.promotick.membership.web.service;



public interface LoginService {

    void logout();

    String obtenerToken();

    String obtenerUsuario();

    Object loguearCedula(String cedula);
}
