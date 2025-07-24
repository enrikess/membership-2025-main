package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.FestividadesAdminService;
import com.promotick.lafabril.dao.web.FestividadesDao;
import com.promotick.lafabril.model.web.Festividades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FestividadesAdminServiceImpl implements FestividadesAdminService {

    private FestividadesDao festividadesDao;

    @Autowired
    public FestividadesAdminServiceImpl(FestividadesDao festividadesDao) {
        this.festividadesDao = festividadesDao;
    }

    @Override
    public List<Festividades> obtenerFestividades() {
        return festividadesDao.obtenerFestividades();
    }
}
