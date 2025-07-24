package com.promotick.lafabril.web.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.model.reporte.ReporteFacturas;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroFacturaParticipante;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.FacturaWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mis-compras")
public class ComprasController extends BaseController {

    private FacturaWebService facturaWebService;

    @Autowired
    public ComprasController(FacturaWebService facturaWebService) {
        this.facturaWebService = facturaWebService;
    }

    @GetMapping
    public String init() {
        return ConstantesWebView.VIEW_MIS_COMPRAS;
    }

    @ResponseBody
    @PostMapping(value = "listarFacturas")
    public Datatable listarPosibles(FiltroFacturaParticipante filtroFacturaParticipante, Participante participante) {
        Datatable datatable = new Datatable();
        filtroFacturaParticipante.setParticipante(participante);

        Integer conteo = facturaWebService.contarFacturasParticipante(filtroFacturaParticipante);
        datatable.setRecordsTotal(conteo);
        datatable.setRecordsFiltered(conteo);
        datatable.setData(facturaWebService.listarFacturasParticipante(filtroFacturaParticipante));
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos(Participante participante) {
        FiltroFacturaParticipante filtroFacturaParticipante = new FiltroFacturaParticipante();
        filtroFacturaParticipante.setParticipante(participante);
        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteFacturas.class)
                        .setList(ReporteFacturas.getFromEntities(facturaWebService.listarFacturasParticipante(filtroFacturaParticipante)))
                        .buildView()
        );
    }
}
