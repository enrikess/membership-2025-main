package com.promotick.lafabril.model.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class ProductoCatalogo extends BeanBase {
    private static final long serialVersionUID = 7657331333903922600L;

    private Integer idProductoCatalogo;
    private Catalogo catalogo;
    private Producto producto;
    private Integer estadoProductoCatalogo;
    private Integer stockProductoCatalogo;
    private Integer frecuencia;
    private String tiempo;
    private Integer consumidoParticipante;
    private Boolean conFreciencia;
    private Integer cantidadFrecuencia;

    private boolean tieneStock;

    @JsonIgnore
    public boolean isTieneStockCatalogo() {
        tieneStock = stockProductoCatalogo == -1 || stockProductoCatalogo > 0;
        return tieneStock;
    }

    @JsonIgnore
    public void evaluar() throws Exception {

        if (!this.isTieneStockCatalogo()) {
            throw new Exception("Producto no tiene stock suficiente para su catalogo");
        }

        if (!this.getProducto().isTieneStockProducto()) {
            throw new Exception("Producto no tiene stock");
        }
    }
}
