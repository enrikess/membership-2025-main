package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteEncuestaEmpleados;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class ReporteEncuestaEmpleadosDaoDefinition extends DaoDefinition<ReporteEncuestaEmpleados> {
    public ReporteEncuestaEmpleadosDaoDefinition() {
        super(ReporteEncuestaEmpleados.class);
    }
}
