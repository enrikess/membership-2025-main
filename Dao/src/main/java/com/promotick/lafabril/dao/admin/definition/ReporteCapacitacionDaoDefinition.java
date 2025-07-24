package com.promotick.lafabril.dao.admin.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.reporte.ReporteCapacitacion;
import org.springframework.stereotype.Component;

@Component
public class ReporteCapacitacionDaoDefinition extends DaoDefinition<ReporteCapacitacion> {
    public ReporteCapacitacionDaoDefinition() {
        super(ReporteCapacitacion.class);
    }
}
