package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.lafabril.model.web.Participante;

import java.util.List;

public interface UsuarioPruebaAdminService {
    List<UsuarioPrueba> listarUsuariosPrueba(String nroDocumento, Integer orden);

    Integer registrarUsuarioPrueba(UsuarioPrueba usuarioPrueba);

    Integer actualizarEstadoUsuarioPrueba(UsuarioPrueba usuarioPrueba);

    Integer registrarParticipantePrueba(Participante participante);

    List<String> usuariosPrueba();
}
