package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Tyc;

import java.util.List;

public interface TycDao {
    List<Tyc> listarTyc(Integer idTyc, Integer idCatalogo, Integer estadoTyc);
}
