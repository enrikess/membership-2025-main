package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.UsuarioPruebaAdminService;
import com.promotick.lafabril.dao.admin.UsuarioPruebaDao;
import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.lafabril.model.web.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsuarioPruebaAdminServiceImpl implements UsuarioPruebaAdminService {
    private UsuarioPruebaDao usuarioPruebaDao;

    @Autowired
    public UsuarioPruebaAdminServiceImpl(UsuarioPruebaDao usuarioPruebaDao) {
        this.usuarioPruebaDao = usuarioPruebaDao;
    }

    @Override
    public List<UsuarioPrueba> listarUsuariosPrueba(String nroDocumento, Integer orden) {
        return usuarioPruebaDao.listarUsuariosPrueba(nroDocumento, orden);
    }

    @Override
    @Transactional
    public Integer registrarUsuarioPrueba(UsuarioPrueba usuarioPrueba) {
        return usuarioPruebaDao.registrarUsuarioPrueba(usuarioPrueba);
    }

    @Override
    @Transactional
    public Integer actualizarEstadoUsuarioPrueba(UsuarioPrueba usuarioPrueba) {
        return usuarioPruebaDao.actualizarEstadoUsuarioPrueba(usuarioPrueba);
    }

    @Override
    @Transactional
    public Integer registrarParticipantePrueba(Participante participante) {
        return usuarioPruebaDao.registrarParticipantePrueba(participante);
    }

    @Override
    public List<String> usuariosPrueba() {
        String usuariosPrueba = usuarioPruebaDao.obtenerUsuariosPrueba();
        List<String> result = new ArrayList<>();
        if (!StringUtils.isEmpty(usuariosPrueba)) {
            result.addAll(Arrays.asList(usuariosPrueba.split(",")));
        }
        return result;
    }
}
