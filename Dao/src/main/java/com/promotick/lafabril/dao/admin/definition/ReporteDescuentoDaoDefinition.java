package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteDescuento;
import com.promotick.lafabril.model.reporte.ReportePasarela;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class ReporteDescuentoDaoDefinition extends DaoDefinition<ReporteDescuento> {
    public ReporteDescuentoDaoDefinition() {
        super(ReporteDescuento.class);
    }
}
