package com.promotick.membership.web.service;


import com.promotick.membership.model.Login;

public interface LoginService {

    void logout();

    String obtenerToken();

    String obtenerUsuario();

    Login loguearCedula(String cedula);
}
