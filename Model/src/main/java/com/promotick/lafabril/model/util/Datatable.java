package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class Datatable implements Serializable {

    private static final long serialVersionUID = -4575376534358650314L;

    private Integer recordsTotal;
    private Integer recordsFiltered;
    private Object data;

}
