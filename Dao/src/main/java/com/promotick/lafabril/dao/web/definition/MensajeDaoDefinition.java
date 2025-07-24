package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Mensaje;
import org.springframework.stereotype.Component;

@Component
public class MensajeDaoDefinition extends DaoDefinition<Mensaje> {
    public MensajeDaoDefinition() {
        super(Mensaje.class);
    }
}
