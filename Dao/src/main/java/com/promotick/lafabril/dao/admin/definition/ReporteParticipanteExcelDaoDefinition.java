package com.promotick.lafabril.dao.admin.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.reporte.ReporteParticipante;
import org.springframework.stereotype.Component;

@Component
public class ReporteParticipanteExcelDaoDefinition extends DaoDefinition<ReporteParticipante> {
    public ReporteParticipanteExcelDaoDefinition() {
        super(ReporteParticipante.class);
    }
}
