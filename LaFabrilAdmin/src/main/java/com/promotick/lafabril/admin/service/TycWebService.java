package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.web.Tyc;

import java.util.List;

public interface TycWebService {
    List<Tyc> listarTyc(Integer idTyc, Integer idCatalogo, Integer estadoTyc);
}
