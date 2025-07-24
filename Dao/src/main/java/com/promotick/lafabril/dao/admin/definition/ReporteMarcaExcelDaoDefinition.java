package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteMarca;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class ReporteMarcaExcelDaoDefinition extends DaoDefinition<ReporteMarca> {
    public ReporteMarcaExcelDaoDefinition() {
        super(ReporteMarca.class);
    }
}
