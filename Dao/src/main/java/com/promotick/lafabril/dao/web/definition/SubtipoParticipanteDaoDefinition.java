package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.SubtipoParticipante;
import org.springframework.stereotype.Component;

@Component
public class SubtipoParticipanteDaoDefinition extends DaoDefinition<SubtipoParticipante> {
    public SubtipoParticipanteDaoDefinition() {
        super(SubtipoParticipante.class);
    }
}
