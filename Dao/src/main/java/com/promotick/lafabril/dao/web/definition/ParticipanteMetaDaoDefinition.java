package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.ParticipanteMeta;
import org.springframework.stereotype.Component;

@Component
public class ParticipanteMetaDaoDefinition extends DaoDefinition<ParticipanteMeta> {
    public ParticipanteMetaDaoDefinition() {
        super(ParticipanteMeta.class);
    }
}
