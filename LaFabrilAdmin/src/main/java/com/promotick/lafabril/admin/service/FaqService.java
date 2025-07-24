package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Faq;

import java.util.List;

public interface FaqService {
    List<Faq> listarFaq(Integer idFaq, Integer idCatalogo, Integer estadoFaq);
}
