package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.PetshopDao;
import com.promotick.lafabril.model.web.Petshop;
import com.promotick.lafabril.web.service.PetshopWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetshopWebServiceImpl implements PetshopWebService {

    private final PetshopDao petshopDao;

    @Override
    public List<Petshop> petshopListar() {
        return petshopDao.petshopListar();
    }
}
