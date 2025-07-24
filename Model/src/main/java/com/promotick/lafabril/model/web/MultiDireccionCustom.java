package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.model.util.TipoDireccion;
import lombok.Data;

import java.util.List;

@Data
public class MultiDireccionCustom extends BeanBase {

    private static final long serialVersionUID = 8948645213008366186L;

    private List<Direccion> direcciones;
    private Direccion principal;
    private List<TipoDireccion> disponibles;

}
