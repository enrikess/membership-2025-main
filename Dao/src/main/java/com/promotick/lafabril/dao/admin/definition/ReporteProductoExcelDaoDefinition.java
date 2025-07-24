package com.promotick.lafabril.dao.admin.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.reporte.ReporteProducto;
import org.springframework.stereotype.Component;

@Component
public class ReporteProductoExcelDaoDefinition extends DaoDefinition<ReporteProducto> {
    public ReporteProductoExcelDaoDefinition() {
        super(ReporteProducto.class);
    }
}
