package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.admin.Dashboard;
import com.promotick.lafabril.model.admin.GraficoVisitas;

public interface DashboardDao {

    Dashboard obtenerDashboard();

    GraficoVisitas graficoVisitas(String fecha);

}
