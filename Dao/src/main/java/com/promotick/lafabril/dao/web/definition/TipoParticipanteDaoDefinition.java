package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.TipoParticipante;
import org.springframework.stereotype.Component;

@Component
public class TipoParticipanteDaoDefinition extends DaoDefinition<TipoParticipante> {
    public TipoParticipanteDaoDefinition() {
        super(TipoParticipante.class);
    }
}
