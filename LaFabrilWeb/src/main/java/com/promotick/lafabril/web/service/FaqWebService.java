package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Faq;

import java.util.List;

public interface FaqWebService {
    List<Faq> listarFaq(Integer idFaq, Integer idCatalogo, Integer estadoFaq);
}
