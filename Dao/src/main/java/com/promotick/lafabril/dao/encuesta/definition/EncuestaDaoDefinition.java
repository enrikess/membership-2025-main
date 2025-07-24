package com.promotick.lafabril.dao.encuesta.definition;

import com.promotick.lafabril.model.encuesta.Encuesta;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component("EncuestaDaoDefinitionMod")
public class EncuestaDaoDefinition extends DaoDefinition<Encuesta> {
    public EncuestaDaoDefinition() {
        super(Encuesta.class);
    }
}
