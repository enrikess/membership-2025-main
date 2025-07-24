package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Ubigeo;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

@Repository
public class UbigeoDaoDefinition extends DaoDefinition<Ubigeo> {
    public UbigeoDaoDefinition() {
        super(Ubigeo.class);
    }
}
