package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.CapacitacionRecurso;
import org.springframework.stereotype.Component;

@Component
public class CapacitacionRecursoDaoDefinition extends DaoDefinition<CapacitacionRecurso> {
    public CapacitacionRecursoDaoDefinition() {
        super(CapacitacionRecurso.class);
    }
}
