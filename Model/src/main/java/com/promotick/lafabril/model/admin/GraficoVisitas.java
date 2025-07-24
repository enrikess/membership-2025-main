package com.promotick.lafabril.model.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class GraficoVisitas implements Serializable {

    private static final long serialVersionUID = -3184141433359199576L;

    private List<String> columnas = new LinkedList<>();
    private List<Integer> valores = new LinkedList<>();
}
