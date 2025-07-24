package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.reporte.ReporteMundialPronostico;
import com.promotick.lafabril.model.reporte.ReporteMundialRanking;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;


@Component
public class ReporteMundialRankingDaoDefinition extends DaoDefinition<ReporteMundialRanking> {

    public ReporteMundialRankingDaoDefinition() {
        super(ReporteMundialRanking.class);
    }

}