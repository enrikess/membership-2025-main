package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.util.RangoPuntos;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class RangoPuntosDaoDefinition extends DaoDefinition<RangoPuntos> {
    public RangoPuntosDaoDefinition() {
        super(RangoPuntos.class);
    }
}
