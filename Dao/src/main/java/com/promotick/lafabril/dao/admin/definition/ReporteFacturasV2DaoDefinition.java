package com.promotick.lafabril.dao.admin.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.reporte.ReporteFacturasV2;
import org.springframework.stereotype.Component;

@Component
public class ReporteFacturasV2DaoDefinition extends DaoDefinition<ReporteFacturasV2> {
    public ReporteFacturasV2DaoDefinition() {
        super(ReporteFacturasV2.class);
    }
}
