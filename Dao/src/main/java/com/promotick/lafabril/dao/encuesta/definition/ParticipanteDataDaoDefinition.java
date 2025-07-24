package com.promotick.lafabril.dao.encuesta.definition;

import com.promotick.lafabril.model.encuesta.ParticipanteData;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component("ParticipanteDataDaoDefinitionMod")
public class ParticipanteDataDaoDefinition extends DaoDefinition<ParticipanteData> {
    public ParticipanteDataDaoDefinition() {
        super(ParticipanteData.class);
    }
}
