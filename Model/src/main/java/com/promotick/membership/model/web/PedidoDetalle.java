package com.promotick.membership.model.web;

import com.promotick.membership.model.util.BeanBase;
import lombok.Data;

@Data
public class PedidoDetalle extends BeanBase {
    private static final long serialVersionUID = -3490790328048468918L;

    private Integer idPedidoDetalle;
    private Integer idPedido;
    private Producto producto;
    private Integer cantidad;
    private Integer puntosUnitario;
    private Integer puntosTotal;
    private Integer estadoPedidoDetalle;
    private String nroCelular;
    private String nroDecodificador;
    private String color1;
    private String color2;
    private String nombreTemporal;
    private String correo;
}
