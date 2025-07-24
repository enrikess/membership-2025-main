package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.web.service.*;
import com.promotick.lafabril.web.service.*;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Transaccion;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("productos")
public class ProductoController extends BaseController {

    private ProductoDestacadoWebService productoDestacadoWebService;
    private ProductoPromocionWebService productoPromocionWebService;
    private ProductoNovedosoWebService productoNovedosoWebService;
    private ProductoVisitadoWebService productoVisitadoWebService;
    private ProductoWebService productoWebService;
    private ConfiguracionWebService configuracionWebService;
    private TransaccionWebService transaccionWebService;
    private CategoriaWebService categoriaWebService;


    @Autowired
    public ProductoController(ProductoPromocionWebService productoPromocionWebService,ProductoDestacadoWebService productoDestacadoWebService, TransaccionWebService transaccionWebService, ProductoNovedosoWebService productoNovedosoWebService, ProductoVisitadoWebService productoVisitadoWebService, ProductoWebService productoWebService, ConfiguracionWebService configuracionWebService, CategoriaWebService categoriaWebService) {
        this.productoDestacadoWebService = productoDestacadoWebService;
        this.productoNovedosoWebService = productoNovedosoWebService;
        this.productoVisitadoWebService = productoVisitadoWebService;
        this.productoPromocionWebService = productoPromocionWebService;
        this.productoWebService = productoWebService;
        this.configuracionWebService = configuracionWebService;
        this.categoriaWebService = categoriaWebService;
        this.transaccionWebService = transaccionWebService;
    }

    @GetMapping
    public String productosXCategoria(@RequestParam(value = "buscar", required = false) String buscar, Model model, Participante participante) {

        if (StringUtils.isEmpty(buscar)) {
            return "redirect:/catalogo";
        }

        List<ConfiguracionBanner> bannerProductos = configuracionWebService.listarBanner(
                ConstantesCommon.ZERO_VALUE,
                participante.getCatalogo().getIdCatalogo(),
                UtilEnum.ESTADO.ACTIVO.getCodigo(),
                UtilEnum.TIPO_BANNER.BANNER_PRODUCTOS.getValue()
        );

        model.addAttribute("bannerProductos", bannerProductos);
        model.addAttribute("idCategoria", 0);
        model.addAttribute("avisoWeb", participante.getCatalogo().getAvisoWeb());
        model.addAttribute("rangoPuntos", productoWebService.obtenerRangoPuntos(participante.getCatalogo().getIdCatalogo(), 0, buscar));
        model.addAttribute("buscar", buscar);
        model.addAttribute("categorias", categoriaWebService.obtenerCategoriasGeneral(participante.getCatalogo().getIdCatalogo()));
        return ConstantesWebView.VIEW_PRODUCTOS;
    }

    @GetMapping(value = "viewProductosDestacados")
    public String viewProductosDestacados(Participante participante, Model model) {
        model.addAttribute("productos", productoDestacadoWebService.listarProductoDestacadoWeb(
                new FiltroProductoDestacado()
                        .setIdCatalogo(participante.getCatalogo().getIdCatalogo())
                )
        );
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO;
    }

    @GetMapping(value = "viewProductosNovedosos")
    public String viewProductosNovedosos(Participante participante, Model model) {
        model.addAttribute("productos", productoNovedosoWebService.listarProductoNovedosoWeb(
                new FiltroProductoNovedoso()
                        .setIdCatalogo(participante.getCatalogo().getIdCatalogo())
                )
        );
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO;
    }

    @GetMapping(value = "viewProductosVisitados")
    public String viewProductosVisitados(Participante participante, Model model) {
        model.addAttribute("productos", productoVisitadoWebService.listarProductoVisitadoWeb(
                new FiltroProductoVisitado()
                        .setIdCatalogo(participante.getCatalogo().getIdCatalogo())
                )
        );
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO;
    }

    @GetMapping(value = "viewProductosPromociones")
    public String viewProductosPromociones(Participante participante, Model model) {
        model.addAttribute("productos", productoPromocionWebService.listarProductoPromocionWeb(
                new FiltroProductoPromocion()
                        .setIdCatalogo(participante.getCatalogo().getIdCatalogo())
                )
        );
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO_PROMOCION;
    }

    @GetMapping(value = "viewAutoCompletar/{buscar}")
    public String viewProductosPromociones(@PathVariable String buscar,Participante participante, Model model) {
        model.addAttribute("productos", productoWebService.listarProductoXCatalogo(
                new FiltroCatalogo()
                        .setIdCatalogo(participante.getCatalogo().getIdCatalogo())
                        .setBuscar(buscar)
                )
        );
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO_AUTOCOMPLETE;
    }

    @GetMapping(value = "viewProducto/{idProducto}")
    public String viewProducto(@PathVariable("idProducto") Integer idProducto, Participante participante, Model model) {
        model.addAttribute("productoCatalogo", productoWebService.obtenerProductoID(participante.getCatalogo().getIdCatalogo(), idProducto, participante.getIdParticipante()));
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO_POPUP;
    }

    @PostMapping(value = "viewListarProductos")
    public String viewListarProductos(@RequestBody FiltroCatalogo filtroCatalogo, Participante participante, Model model) {
        filtroCatalogo.setIdCatalogo(participante.getCatalogo().getIdCatalogo());
        model.addAttribute("productosCatalogo", productoWebService.listarProductoXCatalogo(filtroCatalogo));
        guardarTransaccion(filtroCatalogo,participante);
        Util.getSession().setAttribute(ConstantesSesion.SESSION_PRODUCTO_CATEGORIA, null);
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO_CATALOGO;
    }

    public void guardarTransaccion(FiltroCatalogo filtroCatalogo,Participante participante){
        if(filtroCatalogo.getCategoria() != null) {
            Transaccion transaccion = new Transaccion(new Auditoria().setUsuarioCreacion("WEB"))
                    .setTipoDispositivo(UtilEnum.TIPO_DISPOSITVO_VISITA.WEB.getCodigo())
                    .setIdParticipante(participante.getIdParticipante())
                    .setTipoOperacionVisita(UtilEnum.TIPO_OPERACION_VISITA.CATEGORIA_PRODUCTO)
                    .setIdCategoria(filtroCatalogo.getCategoria())
                    .setIdCatalogo(participante.getCatalogo().getIdCatalogo());
            transaccionWebService.guardarTransaccionWeb(transaccion);
        }
    }
    @ResponseBody
    @PostMapping(value = "contarProductos")
    public PromotickResult contarProductos(@RequestBody FiltroCatalogo filtroCatalogo, Participante participante, PromotickResult promotickResult) {
        try {
            filtroCatalogo.setIdCatalogo(participante.getCatalogo().getIdCatalogo());
            promotickResult.setData(productoWebService.contarProductoXCatalogo(filtroCatalogo));
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @GetMapping(value = "viewProductosInteres/{idMarca}")
    public String viewProductosInteres(@PathVariable Integer idMarca, Participante participante, Model model) {
        model.addAttribute("productosCatalogo", productoWebService.listarProductosInteres(idMarca, participante.getCatalogo().getIdCatalogo()));
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO_INTERES;
    }
}
