package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.FaqDao;
import com.promotick.lafabril.model.web.Faq;
import com.promotick.lafabril.web.service.FaqWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqWebServiceImpl implements FaqWebService {

    private FaqDao faqDao;

    @Autowired
    public FaqWebServiceImpl(FaqDao faqDao) {
        this.faqDao = faqDao;
    }

    @Override
    public List<Faq> listarFaq(Integer idFaq, Integer idCatalogo, Integer estadoFaq) {
        return faqDao.listarFaq(idFaq, idCatalogo, estadoFaq);
    }
}
