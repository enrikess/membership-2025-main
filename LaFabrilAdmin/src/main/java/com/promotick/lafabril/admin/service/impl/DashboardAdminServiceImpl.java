package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.DashboardAdminService;
import com.promotick.lafabril.dao.admin.DashboardDao;
import com.promotick.lafabril.model.admin.Dashboard;
import com.promotick.lafabril.model.admin.GraficoVisitas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardAdminServiceImpl implements DashboardAdminService {

    private DashboardDao dashboardDao;

    @Autowired
    public DashboardAdminServiceImpl(DashboardDao dashboardDao) {
        this.dashboardDao = dashboardDao;
    }

    @Override
    public Dashboard obtenerDashboard() {
        return dashboardDao.obtenerDashboard();
    }

    @Override
    public GraficoVisitas graficoVisitas(String fecha) {
        return dashboardDao.graficoVisitas(fecha);
    }
}
