package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.ContactoDao;
import com.promotick.lafabril.model.web.Contacto;
import com.promotick.lafabril.web.service.ContactoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactoWebServiceImpl implements ContactoWebService {

    private ContactoDao contactoDao;

    @Autowired
    public ContactoWebServiceImpl(ContactoDao contactoDao) {
        this.contactoDao = contactoDao;
    }

    @Override
    @Transactional
    public Integer guardarContacto(Contacto contacto) {
        return contactoDao.guardarContacto(contacto);
    }
}
