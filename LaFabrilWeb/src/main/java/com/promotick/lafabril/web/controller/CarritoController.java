package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.model.web.*;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.web.service.ProductoWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("carrito")
public class CarritoController extends BaseController {

    private ProductoWebService productoWebService;

    @Autowired
    public CarritoController(ProductoWebService productoWebService) {
        this.productoWebService = productoWebService;
    }

    @GetMapping
    public String inicio() {
        return ConstantesWebView.VIEW_CARRITO;
    }

    @GetMapping("viewPedido")
    public String viewPedido(Model model, Pedido pedido, Participante participante) {
        model.addAttribute("pedido", pedido);
        model.addAttribute("mensajeStock", productoWebService.validarStock(pedido, participante.getCatalogo(), participante.getIdParticipante()));
        return ConstantesWebView.VIEW_FRAGMENTS_PEDIDO_CARRITO;
    }

    @GetMapping(value = "viewPopupCarrito")
    public String viewPopupCarrito(Pedido pedido, Model model) {
        model.addAttribute("pedido", pedido);

        return ConstantesWebView.VIEW_FRAGMENTS_PEDIDO_CARRITO_POPUP;
    }

    @ResponseBody
    @GetMapping(value = "cantidadItemsCarrito")
    public PromotickResult cantidadDeItemsCarrito(Pedido pedido, PromotickResult promotickResult) {
        return promotickResult.setData(pedido.getPedidoDetalles().size());
    }

    @ResponseBody
    @RequestMapping(value = "obtenerCalculo", method = RequestMethod.GET)
    public Map obtenerCalculo(Pedido pedido) {
        int puntos = 0;
        if (pedido.getPedidoDetalles() != null) {
            for (PedidoDetalle pd : pedido.getPedidoDetalles()) {
                puntos += pd.getCantidad() * pd.getProducto().getPuntosProducto();
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", puntos);
        return map;
    }

    @ResponseBody
    @PostMapping(value = "agregarItemCarrito/{idProducto}/{cantidad}")
    public PromotickResult agregarCarrito(@RequestBody PedidoDetalle pedidoDetalle, @PathVariable Integer idProducto, @PathVariable Integer cantidad, Participante participante, Pedido pedido, PromotickResult promotickResult) {
        try {
            ProductoCatalogo productoCatalogo = productoWebService.obtenerProductoID(participante.getCatalogo().getIdCatalogo(), idProducto, participante.getIdParticipante());

            if (productoCatalogo == null) {
                throw new Exception("Producto no esta disponible en este momento");
            }

            productoCatalogo.evaluar();

            Pedido pedidoSession = this.agregarItemCarrito(productoCatalogo.getProducto(), cantidad, pedido, pedidoDetalle);
            promotickResult.setMessage("Se agrego con exito al carrito de compras");
            promotickResult.setData(productoCatalogo.getProducto());
            promotickResult.setExtra1(pedidoSession.getTotalItems());
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping(value = "eliminarItemCarrito/{idProducto}")
    public PromotickResult eliminarItemCarrito(@PathVariable("idProducto") Integer idProducto, Pedido pedido, PromotickResult promotickResult) {
        try {
            if (pedido.getPedidoDetalles() != null) {
                pedido.getPedidoDetalles().removeIf(e -> e.getProducto().getIdProducto().intValue() == idProducto);
                this.recalcularTotal(pedido);
                Util.getSession().setAttribute(ConstantesSesion.SESSION_PEDIDO, pedido);
            }
            promotickResult.setMessage("Se elimino el producto correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping(value = "actualizarProducto/{idProducto}/{cantidad}")
    public PromotickResult actualizarProducto(@PathVariable("idProducto") Integer idProducto, @PathVariable("cantidad") Integer cantidad, Pedido pedido, PromotickResult promotickResult) {
        try {

            if (pedido.getPedidoDetalles() != null) {

                pedido.getPedidoDetalles().forEach(pedidoDetalle -> {
                    if (pedidoDetalle.getProducto().getIdProducto().equals(idProducto)) {
                        pedidoDetalle.setCantidad(cantidad);
                        pedidoDetalle.setPuntosTotal(pedidoDetalle.getPuntosUnitario() * cantidad);
                    }
                });

                this.recalcularTotal(pedido);

                Util.getSession().setAttribute(ConstantesSesion.SESSION_PEDIDO, pedido);
            }
            promotickResult.setMessage("Se actualizo el producto correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }


    private Pedido agregarItemCarrito(Producto producto, Integer cantidad, Pedido pedidoSesion, PedidoDetalle pedidoDetalleRequest) {
        if (pedidoSesion.getPedidoDetalles() == null) {
            pedidoSesion.setPedidoDetalles(new ArrayList<>());
        }

        PedidoDetalle pedidoDetalle = pedidoSesion.getPedidoDetalles().stream()
                .filter(pd -> pd.getProducto().getIdProducto().intValue() == producto.getIdProducto())
                .peek(pd -> {
                    pd.setCantidad(pd.getCantidad() + 1)
                            .setPuntosTotal(pd.getPuntosUnitario() * pd.getCantidad())
                            .setNroCelular(pedidoDetalleRequest.getNroCelular())
                            .setNroDecodificador(pedidoDetalleRequest.getNroDecodificador())
                            .setColor1(pedidoDetalleRequest.getColor1())
                            .setColor2(pedidoDetalleRequest.getColor2())
                            .setCorreo(pedidoDetalleRequest.getCorreo());
                })
                .findAny()
                .orElse(null);

        if (pedidoDetalle == null) {
            PedidoDetalle pd = new PedidoDetalle()
                    .setProducto(producto)
                    .setCantidad(cantidad)
                    .setPuntosUnitario(producto.getPuntosProducto())
                    .setPuntosTotal(producto.getPuntosProducto() * cantidad)
                    .setNroCelular(pedidoDetalleRequest.getNroCelular())
                    .setNroDecodificador(pedidoDetalleRequest.getNroDecodificador())
                    .setColor1(pedidoDetalleRequest.getColor1())
                    .setColor2(pedidoDetalleRequest.getColor2())
                    .setCorreo(pedidoDetalleRequest.getCorreo());
            pedidoSesion.getPedidoDetalles().add(pd);
        }

        this.recalcularTotal(pedidoSesion);

        Util.getSession().setAttribute(ConstantesSesion.SESSION_PEDIDO, pedidoSesion);
        return pedidoSesion;
    }

    private void recalcularTotal(Pedido pedidoSession) {
        Integer total = pedidoSession.getPedidoDetalles().stream()
                .map(PedidoDetalle::getPuntosTotal)
                .reduce(0, Integer::sum);

        pedidoSession.setPuntosTotal(total);
    }

}
