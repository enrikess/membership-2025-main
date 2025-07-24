package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Capacitacion;
import org.springframework.stereotype.Component;

@Component
public class CapacitacionDaoDefinition extends DaoDefinition<Capacitacion> {
    public CapacitacionDaoDefinition() {
        super(Capacitacion.class);
    }
}
