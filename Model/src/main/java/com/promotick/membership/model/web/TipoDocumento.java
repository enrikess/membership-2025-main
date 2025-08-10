package com.promotick.membership.model.web;

import com.promotick.membership.model.util.BeanBase;
import lombok.Data;

@Data
public class TipoDocumento extends BeanBase {
    private static final long serialVersionUID = -7415371435095320907L;

    private Integer idTipoDocumento;
    private String nombreTipoDocumento;
    private Integer estadoTipoDocumento;
    private Integer tamanioMinimo;
    private Integer tamanioMaximo;
    private Integer ordenTipoDocumento;
    private Integer alfanumerico;

}
