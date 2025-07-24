package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReportePedido;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroPedidos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("reportes/pedidos")
public class ReportePedidoController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReportePedidoController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_PEDIDOS;
    }

    @ResponseBody
    @PostMapping(value = "listar-pedidos")
    public Datatable listatPedidos(FiltroPedidos filtroPedidos) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_PEDIDOS, filtroPedidos);
        Integer total = reporteAdminService.contarPedidos(filtroPedidos);
        datatable.setData(reporteAdminService.listarPedidos(filtroPedidos));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping("viewPedidoDetalleList/{idPedido}")
    public String viewPedidoDetalleList(@PathVariable("idPedido") Integer idPedido, Model model) {
        model.addAttribute("pedidoDetalleList", reporteAdminService.listarDetallePedido(idPedido));
        return ConstantesAdminView.VIEW_FRAGMENTS_REPORTES_PEDIDO_DETALLE;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {

        FiltroPedidos filtroPedidos = (FiltroPedidos) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_PEDIDOS);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReportePedido.class)
                        .setList(reporteAdminService.listarPedidoDetalle(filtroPedidos))
                        .buildView()
        );
    }


}
