package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.CapacitacionRespuesta;
import org.springframework.stereotype.Component;

@Component
public class CapacitacionRespuestaDaoDefinition extends DaoDefinition<CapacitacionRespuesta> {
    public CapacitacionRespuestaDaoDefinition() {
        super(CapacitacionRespuesta.class);
    }
}
