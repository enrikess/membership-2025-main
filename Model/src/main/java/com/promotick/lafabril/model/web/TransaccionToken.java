package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransaccionToken extends BeanBase {

    private static final long serialVersionUID = -3246147180391122178L;
    private Integer idEntidad;
    private Integer tipoTransaccionToken;
    private String token;
    private Integer estadoTransaccionToken;

    public TransaccionToken(Auditoria auditoria) {
        this.setAuditoria(auditoria);
    }
}
