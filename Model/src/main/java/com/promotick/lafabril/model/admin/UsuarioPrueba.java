package com.promotick.lafabril.model.admin;

import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.model.web.Participante;
import lombok.Data;

@Data
public class UsuarioPrueba extends BeanBase {

    private static final long serialVersionUID = -2614194461880920226L;
    private Integer idUsuarioPrueba;
    private Participante participante;
    private Integer estadoUsuarioPrueba;

}
