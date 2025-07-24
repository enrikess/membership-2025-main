package com.promotick.lafabril.admin.util;

import com.promotick.lafabril.admin.util.values.TipoCargaEnum;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;
import lombok.Data;

import java.util.List;

@Data
public class CronException extends Exception {
    private static final long serialVersionUID = 6514398867498176609L;

    private TipoCargaEnum tipoCargaEnum;
    private List<FormBulkParticipantes> formBulkParticipantesErrores;
    private List<FormBulkFacturas> formBulkFacturasErrores;

    public CronException(String message) {
        super(message);
    }

}
