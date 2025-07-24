package com.promotick.lafabril.web.util;

import com.promotick.lafabril.model.web.Pedido;
import lombok.Data;

@Data
public class ResultadoProcesoPedido {

    private Integer idPedido;
    private Integer puntosDisponibles;
    private Pedido pedido;

}
