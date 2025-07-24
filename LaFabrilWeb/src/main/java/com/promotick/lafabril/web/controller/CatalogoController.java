package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Categoria;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.ProductoCatalogo;
import com.promotick.lafabril.model.web.Transaccion;
import com.promotick.lafabril.web.service.CategoriaWebService;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.web.service.ProductoWebService;
import com.promotick.lafabril.web.service.TransaccionWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("catalogo")
public class CatalogoController extends BaseController {

    private ConfiguracionWebService configuracionWebService;
    private CategoriaWebService categoriaWebService;
    private TransaccionWebService transaccionWebService;
    private ProductoWebService productoWebService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogoController.class);

    @Autowired
    public CatalogoController(ConfiguracionWebService configuracionWebService, CategoriaWebService categoriaWebService, TransaccionWebService transaccionWebService, ProductoWebService productoWebService) {
        this.configuracionWebService = configuracionWebService;
        this.categoriaWebService = categoriaWebService;
        this.transaccionWebService = transaccionWebService;
        this.productoWebService = productoWebService;
    }

    @GetMapping
    public String init(@RequestParam(value = "buscar", required = false) String buscar, Model model, Participante participante) {
        List<Categoria> categorias = categoriaWebService.obtenerCategoriasGeneral(participante.getCatalogo().getIdCatalogo());
        List<ConfiguracionBanner> bannerCategorias = configuracionWebService.listarBanner(
                ConstantesCommon.ZERO_VALUE,
                participante.getCatalogo().getIdCatalogo(),
                UtilEnum.ESTADO.ACTIVO.getCodigo(),
                UtilEnum.TIPO_BANNER.BANNER_CATEGORIAS.getValue()
        );
        LOGGER.info("Busqueda Productos: " + buscar);

        model.addAttribute("bannerCategorias", bannerCategorias);
        model.addAttribute("categorias", categorias);
        model.addAttribute("avisoWeb", participante.getCatalogo().getAvisoWeb());
        model.addAttribute("buscarResultado", buscar);
        model.addAttribute("buscar", buscar);
        model.addAttribute("sessionCategoria", Util.getSession().getAttribute(ConstantesSesion.SESSION_PRODUCTO_CATEGORIA));

        return ConstantesWebView.VIEW_CATEGORIAS;
    }

    @GetMapping(value = "{idCategoria}/*")
    public String productosXCategoria(@PathVariable Integer idCategoria, Model model, Participante participante) {
        List<ConfiguracionBanner> bannerProductos = configuracionWebService.listarBanner(
                ConstantesCommon.ZERO_VALUE,
                participante.getCatalogo().getIdCatalogo(),
                UtilEnum.ESTADO.ACTIVO.getCodigo(),
                UtilEnum.TIPO_BANNER.BANNER_PRODUCTOS.getValue()
        );

        Categoria categoria = categoriaWebService.obtenerCategoria(participante.getCatalogo().getIdCatalogo(), idCategoria);
        if (categoria == null) {
            return "redirect:/catalogo";
        }

        Transaccion transaccion = new Transaccion(new Auditoria().setUsuarioCreacion("WEB"))
                .setTipoDispositivo(UtilEnum.TIPO_DISPOSITVO_VISITA.WEB.getCodigo())
                .setIdParticipante(participante.getIdParticipante())
                .setTipoOperacionVisita(UtilEnum.TIPO_OPERACION_VISITA.CATEGORIA_PRODUCTO)
                .setIdCategoria(idCategoria)
                .setIdCatalogo(participante.getCatalogo().getIdCatalogo());

        transaccionWebService.guardarTransaccionWeb(transaccion);

        model.addAttribute("bannerProductos", bannerProductos);
        model.addAttribute("idCategoria", idCategoria);
        model.addAttribute("avisoWeb", participante.getCatalogo().getAvisoWeb());
        model.addAttribute("rangoPuntos", productoWebService.obtenerRangoPuntos(participante.getCatalogo().getIdCatalogo(), idCategoria, null));
        model.addAttribute("categorias", categoriaWebService.obtenerCategoriasGeneral(participante.getCatalogo().getIdCatalogo()));
//        return ConstantesWebView.VIEW_PRODUCTOS;
        return "redirect:/catalogo";
    }

    @GetMapping(value = "{idCategoria}/*/producto/{idProducto}/*")
    public String productoDetalle(@PathVariable Integer idCategoria, @PathVariable Integer idProducto, Model model, Participante participante) {

        try {
            ProductoCatalogo productoCatalogo = productoWebService.obtenerProductoCategoriaID(participante.getCatalogo().getIdCatalogo(), idCategoria, idProducto);
            if (productoCatalogo == null) {
                throw new Exception("producto no encontrado");
            }

            String[] terminos = productoCatalogo.getProducto().getTerminosProducto().split("\\|");
            String[] especificaciones = productoCatalogo.getProducto().getEspecificacionesProducto().split("\\|");

            model.addAttribute("producto", productoCatalogo.getProducto());
            model.addAttribute("terminos", terminos);
            model.addAttribute("especificaciones", especificaciones);
            model.addAttribute("footer", false);


            Transaccion transaccion = new Transaccion(new Auditoria().setUsuarioCreacion("WEB"))
                    .setTipoDispositivo(UtilEnum.TIPO_DISPOSITVO_VISITA.WEB.getCodigo())
                    .setIdParticipante(participante.getIdParticipante())
                    .setTipoOperacionVisita(UtilEnum.TIPO_OPERACION_VISITA.CONSULTA_PRODUCTO)
                    .setIdCategoria(productoCatalogo.getProducto().getCategoria().getIdCategoria())
                    .setIdEntidad(productoCatalogo.getProducto().getIdProducto())
                    .setIdCatalogo(participante.getCatalogo().getIdCatalogo());

            transaccionWebService.guardarTransaccionWeb(transaccion);

            Util.getSession().setAttribute(ConstantesSesion.SESSION_PRODUCTO_CATEGORIA, idCategoria);

            return ConstantesWebView.VIEW_PRODUCTO_DETALLE;
        } catch (Exception e) {
            return "redirect:/catalogo";
        }

    }

    @PostMapping("viewProductoAgregado")
    public String viewProductoAgregado(@RequestBody PromotickResult promotickResult, Model model) {
        model.addAttribute("result", promotickResult);
        return ConstantesWebView.VIEW_FRAGMENTS_PRODUCTO_AGREGADO_POPUP;
    }
}
