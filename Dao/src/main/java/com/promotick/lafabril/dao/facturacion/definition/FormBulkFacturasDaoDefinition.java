package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class FormBulkFacturasDaoDefinition extends DaoDefinition<FormBulkFacturas> {
    public FormBulkFacturasDaoDefinition() {
        super(FormBulkFacturas.class);
    }
}
