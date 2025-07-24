package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteMundialPolla;
import com.promotick.lafabril.model.reporte.ReporteMundialPronostico;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;


@Component
public class ReporteMundialPronosticoDaoDefinition extends DaoDefinition<ReporteMundialPronostico> {

    public ReporteMundialPronosticoDaoDefinition() {
        super(ReporteMundialPronostico.class);
    }

}