package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.model.util.UtilEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Transaccion extends BeanBase {

    private static final long serialVersionUID = 7645091070591501618L;

    private Integer idTransaccion;
    private Integer tipoOperacion;
    private Integer idEntidad;
    private Integer idCategoria;
    private Integer tipoDispositivo;
    private Integer idParticipante;
    private Integer idCatalogo;
    private UtilEnum.TIPO_OPERACION_VISITA tipoOperacionVisita;

    public Transaccion(Auditoria auditoria) {
        this.setAuditoria(auditoria);
    }
}
