package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.admin.UsuarioPruebaDao;
import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.lafabril.web.service.UsuarioPruebaWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsuarioPruebaWebServiceImpl implements UsuarioPruebaWebService {

    private UsuarioPruebaDao usuarioPruebaDao;

    @Autowired
    public UsuarioPruebaWebServiceImpl(UsuarioPruebaDao usuarioPruebaDao) {
        this.usuarioPruebaDao = usuarioPruebaDao;
    }

    @Override
    public List<UsuarioPrueba> listarUsuariosPrueba(String nroDocumento, Integer orden) {
        return usuarioPruebaDao.listarUsuariosPrueba(nroDocumento, orden);
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
