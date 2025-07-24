package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.CapacitacionPregunta;
import org.springframework.stereotype.Component;

@Component
public class CapacitacionPreguntaDaoDefinition extends DaoDefinition<CapacitacionPregunta> {
    public CapacitacionPreguntaDaoDefinition() {
        super(CapacitacionPregunta.class);
    }
}
