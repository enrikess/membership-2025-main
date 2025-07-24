package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.controller.excel.ReporteEstadoCuentaExcelView;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.EstadoCuentaColumnas;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.dao.web.ParticipanteDao;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroEstadoCuenta;
import com.promotick.lafabril.model.util.PromotickResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("reportes/estado-cuenta")
public class ReporteEstadoCuentaController extends BaseController {

    private ReporteAdminService reporteAdminService;
    private ReporteEstadoCuentaExcelView reporteEstadoCuentaExcelView;
    private ParticipanteDao participanteDao;

    @Autowired
    public ReporteEstadoCuentaController(ReporteAdminService reporteAdminService, ReporteEstadoCuentaExcelView reporteEstadoCuentaExcelView, ParticipanteDao participanteDao) {
        this.reporteAdminService = reporteAdminService;
        this.reporteEstadoCuentaExcelView = reporteEstadoCuentaExcelView;
        this.participanteDao = participanteDao;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_ESTADO_CUENTA;
    }

    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listarEstadoCuenta(FiltroEstadoCuenta filtroEstadoCuenta) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_ESTADO_CUENTA, filtroEstadoCuenta);
        datatable.setData(reporteAdminService.obtenerEstadoCuenta(filtroEstadoCuenta));
        Integer contidad = reporteAdminService.obtenerEstadoCuentaTotal();
        datatable.setRecordsFiltered(contidad);
        datatable.setRecordsTotal(contidad);
        return datatable;
    }

    @RequestMapping(value = "descargar-excel", method = RequestMethod.GET)
    public ModelAndView descargarExcel(Model model) {
        String nombredocumento = "Reporte-estado-cuenta";
        StringBuilder sb = new StringBuilder()
                .append(nombredocumento)
                .append("-")
                .append(UtilCommon.dateFormat("yyyyMMddHHmmss"))
                .append(".xlsx");

        model.addAttribute("fileName", sb.toString());

        FiltroEstadoCuenta filtroEstadoCuenta = (FiltroEstadoCuenta) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_ESTADO_CUENTA);
        filtroEstadoCuenta.setLength(-1);

        model.addAttribute("listaEstadoCuenta", reporteAdminService.obtenerEstadoCuenta(filtroEstadoCuenta));
        return new ModelAndView(reporteEstadoCuentaExcelView);
    }


    @ResponseBody
    @PostMapping(value = "obtenerColumnas")
    public PromotickResult obtenerHeader(@RequestBody FiltroEstadoCuenta filtroEstadoCuenta, PromotickResult promotickResult) {
        try {
            promotickResult.setData(EstadoCuentaColumnas.getInstance(filtroEstadoCuenta));
        } catch (Exception e) {
            promotickResult.setException(e);
        }

        return promotickResult;
    }

}
