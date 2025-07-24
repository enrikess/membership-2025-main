package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.CapacitacionParticipante;
import org.springframework.stereotype.Component;

@Component
public class CapacitacionParticipanteDaoDefinition extends DaoDefinition<CapacitacionParticipante> {
    public CapacitacionParticipanteDaoDefinition() {
        super(CapacitacionParticipante.class);
    }
}
