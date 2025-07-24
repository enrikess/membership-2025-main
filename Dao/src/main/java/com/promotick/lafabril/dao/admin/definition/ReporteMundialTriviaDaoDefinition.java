package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteMundialPronostico;
import com.promotick.lafabril.model.reporte.ReporteMundialTrivia;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;


@Component
public class ReporteMundialTriviaDaoDefinition extends DaoDefinition<ReporteMundialTrivia> {

    public ReporteMundialTriviaDaoDefinition() {
        super(ReporteMundialTrivia.class);
    }

}