package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteCanjeProducto;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroVisitas;
import com.promotick.lafabril.model.util.UtilEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("reportes/productos-canjeados")
public class ReporteProductosCanjeadosController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReporteProductosCanjeadosController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_PRODUCTOS_CANJEADOS;
    }

    @ResponseBody
    @PostMapping(value = "listar-prod-canjeados")
    public Datatable listaVisitas(FiltroVisitas filtroVisitas) {
        Datatable datatable = new Datatable();
        filtroVisitas.setTipoOperacion(UtilEnum.TIPO_OPERACION_VISITA.PRODUCTOS_CANJEADOS.getCodigo());
        filtroVisitas.setTipoDispositivo(UtilEnum.TIPO_DISPOSITVO_VISITA.WEB.getCodigo());

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_PRODUCTO_CANJEADOS, filtroVisitas);

        Integer total = reporteAdminService.contarVisitas(filtroVisitas);
        datatable.setData(reporteAdminService.listarVisitas(filtroVisitas));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @RequestMapping(value = "descargar-excel", method = RequestMethod.GET)
    public ModelAndView descargarExcelPedidos() {
        FiltroVisitas filtroVisitas = (FiltroVisitas) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_PRODUCTO_CANJEADOS);
        filtroVisitas.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteCanjeProducto.class)
                        .setList(ReporteCanjeProducto.getFromEntities(reporteAdminService.listarVisitas(filtroVisitas)))
                        .buildView()
        );
    }

}
