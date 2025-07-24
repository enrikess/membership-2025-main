package com.promotick.lafabril.dao.admin.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.reporte.ReporteCategoria;
import org.springframework.stereotype.Component;

@Component
public class ReporteCategoriaExcelDaoDefinition extends DaoDefinition<ReporteCategoria> {
    public ReporteCategoriaExcelDaoDefinition() {
        super(ReporteCategoria.class);
    }
}
