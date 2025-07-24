package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.ProductoDestacadoAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroProductoDestacado;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.web.ProductoDestacado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("catalogos/producto-destacado")
public class ProductoDestacadoController extends BaseController {

    private ProductoDestacadoAdminService productoDestacadoAdminService;
    private CatalogoAdminService catalogoAdminService;

    @Autowired
    private ProductoDestacadoController(ProductoDestacadoAdminService productoDestacadoAdminService, CatalogoAdminService catalogoAdminService) {
        this.productoDestacadoAdminService = productoDestacadoAdminService;
        this.catalogoAdminService = catalogoAdminService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_PRODUCTOS_DESTACADOS;
    }

    @ResponseBody
    @PostMapping(value = "listar-productos-destacados")
    public Datatable listarProductoDestacado(FiltroProductoDestacado filtroProductoDestacado) {
        log.info("#listarProductosDestacado");
        Integer conteo = productoDestacadoAdminService.contarProductoDestacado(filtroProductoDestacado);
        Datatable datatable = new Datatable();
        datatable.setData(productoDestacadoAdminService.listarProductoDestacado(filtroProductoDestacado));
        datatable.setRecordsFiltered(conteo);
        datatable.setRecordsTotal(conteo);
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "registrar")
    public PromotickResult registrar(@RequestBody ProductoDestacado productoDestacado, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            productoDestacado.setAuditoria(auditoria);
            Integer resultado = productoDestacadoAdminService.registrarProductoDestacado(productoDestacado);
            this.evaluarResultado(resultado, promotickResult, "Producto destacado registrado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping(value = "eliminar/{idProductoDestacado}")
    public PromotickResult eliminarProductoDestacado(@PathVariable("idProductoDestacado") Integer idProductoDestacado, PromotickResult promotickResult) {
        try {
            Integer resultado = productoDestacadoAdminService.eliminarProductoDestacado(idProductoDestacado);
            evaluarResultado(resultado, promotickResult, "Producto destacado fue eliminado con exito");
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
