package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteMundialPolla;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;


@Component
public class ReporteMundialPollaDaoDefinition extends DaoDefinition<ReporteMundialPolla> {

    public ReporteMundialPollaDaoDefinition() {
        super(ReporteMundialPolla.class);
    }

}