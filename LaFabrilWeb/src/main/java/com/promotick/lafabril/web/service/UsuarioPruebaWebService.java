package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.admin.UsuarioPrueba;

import java.util.List;

public interface UsuarioPruebaWebService {
    List<UsuarioPrueba> listarUsuariosPrueba(String nroDocumento, Integer orden);
    List<String> usuariosPrueba();
}
