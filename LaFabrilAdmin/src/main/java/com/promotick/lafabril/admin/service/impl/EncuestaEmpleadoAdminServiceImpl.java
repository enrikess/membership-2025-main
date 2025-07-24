package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.EncuestaEmpleadoAdminService;
import com.promotick.lafabril.dao.encuesta.EncuestaEmpleadosDao;
import com.promotick.lafabril.model.reporte.ReporteEncuestaEmpleados;
import com.promotick.lafabril.model.util.FiltroReporteEncuestaEmpleados;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EncuestaEmpleadoAdminServiceImpl implements EncuestaEmpleadoAdminService {

    private final EncuestaEmpleadosDao encuestaEmpleadosDao;

    @Override
    public List<ReporteEncuestaEmpleados> reporteEncuestaEmpleadosListar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados) {
        return encuestaEmpleadosDao.reporteEncuestaEmpleadosListar(filtroReporteEncuestaEmpleados);
    }

    @Override
    public Integer reporteEncuestaEmpleadosContar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados) {
        return encuestaEmpleadosDao.reporteEncuestaEmpleadosContar(filtroReporteEncuestaEmpleados);
    }
}
