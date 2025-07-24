package com.promotick.lafabril.dao.encuesta.definition;

import com.promotick.lafabril.model.encuesta.Pregunta;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component("PreguntaDaoDefinitionMod")
public class PreguntaDaoDefinition extends DaoDefinition<Pregunta> {
    public PreguntaDaoDefinition() {
        super(Pregunta.class);
    }
}
