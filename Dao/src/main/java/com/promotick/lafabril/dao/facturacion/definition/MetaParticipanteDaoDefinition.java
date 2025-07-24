package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class MetaParticipanteDaoDefinition extends DaoDefinition<MetaParticipante> {
    public MetaParticipanteDaoDefinition() {
        super(MetaParticipante.class);
    }
}
