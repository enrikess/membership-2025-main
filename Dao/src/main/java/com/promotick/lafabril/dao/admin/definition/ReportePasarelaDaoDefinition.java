package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReportePasarela;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class ReportePasarelaDaoDefinition extends DaoDefinition<ReportePasarela> {
    public ReportePasarelaDaoDefinition() {
        super(ReportePasarela.class);
    }
}
