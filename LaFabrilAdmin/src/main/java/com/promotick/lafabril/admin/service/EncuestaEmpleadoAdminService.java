package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.reporte.ReporteEncuestaEmpleados;
import com.promotick.lafabril.model.util.FiltroReporteEncuestaEmpleados;

import java.util.List;

public interface EncuestaEmpleadoAdminService {
    List<ReporteEncuestaEmpleados> reporteEncuestaEmpleadosListar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados);

    Integer reporteEncuestaEmpleadosContar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados);
}
