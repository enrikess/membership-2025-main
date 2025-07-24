package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Zona;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class ZonaDaoDefinition extends DaoDefinition<Zona> {
    public ZonaDaoDefinition() {
        super(Zona.class);
    }
}
