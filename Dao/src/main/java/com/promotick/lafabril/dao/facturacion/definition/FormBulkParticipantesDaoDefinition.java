package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class FormBulkParticipantesDaoDefinition extends DaoDefinition<FormBulkParticipantes> {
    public FormBulkParticipantesDaoDefinition() {
        super(FormBulkParticipantes.class);
    }
}
