package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.web.ConstantesWebMessage;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Producto;
import com.promotick.lafabril.model.web.ProductoCatalogo;
import com.promotick.lafabril.model.web.Wishlist;
import com.promotick.lafabril.web.service.ProductoWebService;
import com.promotick.lafabril.web.service.WishlistWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("wishlist")
public class WishlistController extends BaseController {

    private WishlistWebService wishlistWebService;
    private ProductoWebService productoWebService;

    @Autowired
    public WishlistController(WishlistWebService wishlistWebService, ProductoWebService productoWebService) {
        this.wishlistWebService = wishlistWebService;
        this.productoWebService = productoWebService;
    }

    @GetMapping
    public String wishlist() {
        return ConstantesWebView.VIEW_WISHLIST;
    }

    @GetMapping("viewWishlists")
    public String viewWishlists(Participante participante, Model model) {
        model.addAttribute("wishlists", wishlistWebService.listarWishlist(participante.getIdParticipante()));
        return ConstantesWebView.VIEW_FRAGMENTS_WISHLIST;
    }

    @PostMapping("viewWishlistAgregado")
    public String viewWishlistAgregado(@RequestBody PromotickResult promotickResult, Model model) {
        model.addAttribute("result", promotickResult);
        return ConstantesWebView.VIEW_FRAGMENTS_WISHLIST_AGREGADO_POPUP;
    }

    @ResponseBody
    @PostMapping(value = "agregarWishlist")
    public PromotickResult agregarWishlist(@RequestBody Wishlist wishlist, Participante participante, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            if (participante == null) {
                throw new Exception("Sesion perdida, por favor refresque la pagina");
            }

            ProductoCatalogo productoCatalogo = productoWebService.obtenerProductoID(participante.getCatalogo().getIdCatalogo(), wishlist.getProducto().getIdProducto(), participante.getIdParticipante());

            if (productoCatalogo == null) {
                throw new Exception("El producto no esta disponible");
            }

            productoCatalogo.evaluar();

            wishlist.setParticipante(participante);
            wishlist.setProducto(productoCatalogo.getProducto());
            wishlist.setAccion(UtilEnum.MANTENIMIENTO.REGISTRAR.getCodigo());
            wishlist.setAuditoria(auditoria);
            if (wishlist.getCantidadProducto() == null) {
                wishlist.setCantidadProducto(1);
            }

            Integer resultado = wishlistWebService.updateWishlist(wishlist);
            if (resultado < 0) {
                throw new Exception("Ocurrio un problema al agregar el producto");
            } else if (resultado == 0) {
                promotickResult.setMessage(Util.getMessage(messageSource, ConstantesWebMessage.MSG_PEDIDO_AGREGAR_ARTICULO_WISHLIST_EXISTENTE));
            } else {
                promotickResult.setMessage(Util.getMessage(messageSource, ConstantesWebMessage.MSG_PEDIDO_AGREGAR_ARTICULO_WISHLIST_EXITO));
            }
            promotickResult.setData(productoCatalogo.getProducto());
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping("eliminarWishlist/{idProducto}")
    public PromotickResult eliminarWishlist(@PathVariable Integer idProducto, Participante participante, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            Wishlist wishlist = new Wishlist(auditoria)
                    .setParticipante(participante)
                    .setProducto(new Producto().setIdProducto(idProducto));

            wishlist.setAccion(UtilEnum.MANTENIMIENTO.ELIMINAR.getCodigo());

            Integer result = wishlistWebService.updateWishlist(wishlist);
            if (result == null || result <= 0) {
                throw new Exception("No se pudo eliminar el producto favorito");
            }
            promotickResult.setMessage("Se elimino el producto favorito exitosamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

}
