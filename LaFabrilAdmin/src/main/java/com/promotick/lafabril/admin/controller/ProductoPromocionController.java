package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.admin.service.ProductoDestacadoAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.admin.service.ProductoPromocionAdminService;
import com.promotick.lafabril.model.web.ProductoPromocion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("catalogos/producto-promocion")
public class ProductoPromocionController extends BaseController {

    private ProductoPromocionAdminService productoPromocionAdminService;
    private ProductoDestacadoAdminService productoDestacadoAdminService;
    private CatalogoAdminService catalogoAdminService;

    @Autowired
    private ProductoPromocionController(ProductoDestacadoAdminService productoDestacadoAdminService,ProductoPromocionAdminService productoPromocionAdminService, CatalogoAdminService catalogoAdminService) {
        this.productoPromocionAdminService = productoPromocionAdminService;
        this.productoDestacadoAdminService = productoDestacadoAdminService;
        this.catalogoAdminService = catalogoAdminService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_PRODUCTOS_PROMOCION;
    }

    @ResponseBody
    @PostMapping(value = "listar-productos-promocion")
    public Datatable listarProductoPromocion(FiltroProductoPromocion filtroProductoPromocion) {
        log.info("#listarProductoPromocion");
        Integer conteo = productoPromocionAdminService.contarProductoPromocion(filtroProductoPromocion);
        Datatable datatable = new Datatable();
        datatable.setData(productoPromocionAdminService.listarProductoPromocion(filtroProductoPromocion));
        datatable.setRecordsFiltered(conteo);
        datatable.setRecordsTotal(conteo);
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "registrar")
    public PromotickResult registrar(@RequestBody ProductoPromocion productoPromocion, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            productoPromocion.setAuditoria(auditoria);
            Integer resultado = productoPromocionAdminService.registrarProductoPromocion(productoPromocion);
            this.evaluarResultado(resultado, promotickResult, "Producto promocion registrado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping(value = "eliminar/{idProductoPromocion}")
    public PromotickResult eliminarProductoDestacado(@PathVariable("idProductoPromocion") Integer idProductoPromocion, PromotickResult promotickResult) {
        try {
            Integer resultado = productoPromocionAdminService.eliminarProductoPromocion(idProductoPromocion);
            evaluarResultado(resultado, promotickResult, "Producto promocion fue eliminado con exito");
        } catch (Exception e) {
            promotickResult.setException(e);
        }

        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "buscar-producto-catalogo")
    public Datatable listarBucarProductoCatalogo(FiltroProductoDestacado filtroProductoDestacado) {
        if (filtroProductoDestacado.getIdCatalogo() == null) {
            filtroProductoDestacado.setIdCatalogo(0);
        }
        Integer conteo = productoDestacadoAdminService.contarProductoCategoria(filtroProductoDestacado);
        Datatable datatable = new Datatable();
        datatable.setData(productoDestacadoAdminService.listarProductoCategoria(filtroProductoDestacado));
        datatable.setRecordsFiltered(conteo);
        datatable.setRecordsTotal(conteo);
        return datatable;
    }

}
