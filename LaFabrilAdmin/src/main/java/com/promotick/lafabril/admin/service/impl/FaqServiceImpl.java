package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CatalogoService;
import com.promotick.lafabril.admin.service.FaqService;
import com.promotick.lafabril.dao.web.CatalogoDao;
import com.promotick.lafabril.dao.web.FaqDao;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Faq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {

    private FaqDao faqDao;

    @Autowired
    public FaqServiceImpl(FaqDao faqDao) {
        this.faqDao = faqDao;
    }

    @Override
    public List<Faq> listarFaq(Integer idFaq, Integer idCatalogo, Integer estadoFaq) {
        return faqDao.listarFaq(idFaq, idCatalogo, estadoFaq);
    }
}
