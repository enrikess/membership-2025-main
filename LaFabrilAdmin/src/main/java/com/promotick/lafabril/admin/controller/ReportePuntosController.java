package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroParticipanteTransaccion;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteParticipanteTransaccion;
import com.promotick.lafabril.model.web.Participante;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("puntos/reporte")
public class ReportePuntosController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReportePuntosController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_PUNTOS;
    }


    @ResponseBody
    @PostMapping(value = "listar-transacciones")
    public Datatable listaVisitas(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        Datatable datatable = new Datatable();
        filtroParticipanteTransaccion.setTipo(UtilEnum.TIPO_MIS_PUNTOS.REPORTE_ADMIN.getValue());
        filtroParticipanteTransaccion.setParticipante(new Participante());
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_PUNTOS, filtroParticipanteTransaccion);

        Integer total = reporteAdminService.contarPuntos(filtroParticipanteTransaccion);
        datatable.setData(reporteAdminService.listarPuntos(filtroParticipanteTransaccion));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {
        FiltroParticipanteTransaccion filtroParticipanteTransaccion = (FiltroParticipanteTransaccion) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_PUNTOS);
        filtroParticipanteTransaccion.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteParticipanteTransaccion.class)
                        .setList(ReporteParticipanteTransaccion.getFromEntities(reporteAdminService.listarPuntos(filtroParticipanteTransaccion)))
                        .buildView()
        );
    }


}
