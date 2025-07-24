package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.admin.Dashboard;
import com.promotick.lafabril.model.admin.GraficoVisitas;

public interface DashboardAdminService {
    Dashboard obtenerDashboard();

    GraficoVisitas graficoVisitas(String fecha);
}
