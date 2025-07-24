package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Faq;

import java.util.List;

public interface FaqDao {
    List<Faq> listarFaq(Integer idFaq, Integer idCatalogo, Integer estadoFaq);
}


