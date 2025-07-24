package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.lafabril.model.web.Participante;

import java.util.List;

public interface UsuarioPruebaDao {
    List<UsuarioPrueba> listarUsuariosPrueba(String nroDocumento, Integer orden);

    Integer registrarUsuarioPrueba(UsuarioPrueba usuarioPrueba);

    Integer actualizarEstadoUsuarioPrueba(UsuarioPrueba usuarioPrueba);

    Integer registrarParticipantePrueba(Participante participante);

    String obtenerUsuariosPrueba();
}
