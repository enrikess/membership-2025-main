package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.TipoProducto;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class TipoProductoDaoDefinition extends DaoDefinition<TipoProducto> {
    public TipoProductoDaoDefinition() {
        super(TipoProducto.class);
    }
}
