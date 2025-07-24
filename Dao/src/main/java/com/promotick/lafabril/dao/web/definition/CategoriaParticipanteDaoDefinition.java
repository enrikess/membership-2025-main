package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.CategoriaParticipante;
import com.promotick.lafabril.model.web.TipoParticipante;
import org.springframework.stereotype.Component;

@Component
public class CategoriaParticipanteDaoDefinition extends DaoDefinition<CategoriaParticipante> {
    public CategoriaParticipanteDaoDefinition() {
        super(CategoriaParticipante.class);
    }
}
