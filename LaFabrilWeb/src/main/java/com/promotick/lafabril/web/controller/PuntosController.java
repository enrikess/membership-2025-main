package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroParticipanteTransaccion;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.web.controller.excel.EstadoCuentaExcelView;
import com.promotick.lafabril.web.service.FacturaWebService;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.service.PedidoWebService;
import com.promotick.lafabril.web.service.UbigeoWebService;
import com.promotick.lafabril.web.util.ConstantesWebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.TimeZone;

@Controller
@RequestMapping("mis-puntos")
public class PuntosController {

    private ParticipanteWebService participanteWebService;
    private EstadoCuentaExcelView estadoCuentaExcelView;
    private FacturaWebService facturaWebService;
    private PedidoWebService pedidoWebService;
    private UbigeoWebService ubigeoWebService;

    @Autowired
    public PuntosController(ParticipanteWebService participanteWebService, EstadoCuentaExcelView estadoCuentaExcelView, FacturaWebService facturaWebService, PedidoWebService pedidoWebService, UbigeoWebService ubigeoWebService) {
        this.participanteWebService = participanteWebService;
        this.estadoCuentaExcelView = estadoCuentaExcelView;
        this.facturaWebService = facturaWebService;
        this.pedidoWebService = pedidoWebService;
        this.ubigeoWebService = ubigeoWebService;
    }

    @GetMapping
    public String init(Model model, Participante participante) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        FiltroParticipanteTransaccion filtroParticipanteTransaccion = new FiltroParticipanteTransaccion();
        filtroParticipanteTransaccion.setParticipante(participante);
        model.addAttribute("fechaActualizacion", participanteWebService.ultimaActualizacionParticipante(participante.getIdParticipante()));
        model.addAttribute("fechaHasta", UtilCommon.dateFormat(UtilCommon.FORMATO_FECHA_ANIO_MES_DIA));
        model.addAttribute("fechaDesde", UtilCommon.dateFormat(calendar.getTime(), UtilCommon.FORMATO_FECHA_ANIO_MES_DIA));
//        model.addAttribute("puntosCanjeados", participanteWebService.puntosCanjeadosParticipante(filtroParticipanteTransaccion));
//        model.addAttribute("puntosAcumulados", participanteWebService.puntosAcumuladosParticipante(filtroParticipanteTransaccion));
//        model.addAttribute("puntosDescargados", participanteWebService.puntosDescargadosParticipante(filtroParticipanteTransaccion));
        return ConstantesWebView.VIEW_MIS_PUNTOS;
    }

    @GetMapping("viewHistorialPuntos/{anio}")
    public String viewHistorialPuntos(@PathVariable Integer anio, Model model, Participante participante) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        model.addAttribute("facturasAgrupadas", facturaWebService.listarFacturacionAgrupada(participante.getIdParticipante(), calendar.get(Calendar.YEAR)));
        model.addAttribute("anio", calendar.get(Calendar.YEAR));
        model.addAttribute("mes", UtilCommon.obtenerMes(calendar.get(Calendar.MONTH) + 1));
        model.addAttribute("porcentaje", participanteWebService.obtenerParticipante(participante.getIdParticipante()));
//        return ConstantesWebView.VIEW_FRAGMENTS_MIS_PUNTOS_FACTURAS_AGRUPADAS;
        return ConstantesWebView.VIEW_FRAGMENTS_MIS_PUNTOS_RESUMEN_META;
    }

    @GetMapping("viewResumenMeta/{anio}")
    public String viewResumenMeta(@PathVariable Integer anio, Model model, Participante participante) {
        model.addAttribute("metaFacturacion", facturaWebService.obtenerMetaFacturacionParticipante(participante.getIdParticipante(), anio, 0));
        return ConstantesWebView.VIEW_FRAGMENTS_MIS_PUNTOS_RESUMEN_META;
    }

    @GetMapping("viewDetallePuntos/{tipo}")
    public String viewDetallePuntos(@PathVariable Integer tipo, Model model, Participante participante) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        model.addAttribute("fechaActualizacion", participanteWebService.ultimaActualizacionParticipante(participante.getIdParticipante()));
        model.addAttribute("fechaHasta", UtilCommon.dateFormat(UtilCommon.FORMATO_FECHA_ANIO_MES_DIA));
        model.addAttribute("fechaDesde", UtilCommon.dateFormat(calendar.getTime(), UtilCommon.FORMATO_FECHA_ANIO_MES_DIA));

        FiltroParticipanteTransaccion filtroParticipanteTransaccion = new FiltroParticipanteTransaccion();
        filtroParticipanteTransaccion.setParticipante(participante);

        switch (tipo) {
            case 1:
                model.addAttribute("puntosAcumulados", participanteWebService.puntosAcumuladosParticipante(filtroParticipanteTransaccion));
                return ConstantesWebView.VIEW_FRAGMENTS_MIS_PUNTOS_DETALLE_ACUMULADOS;
            case 2:
                model.addAttribute("puntosCanjeados", participanteWebService.puntosCanjeadosParticipante(filtroParticipanteTransaccion));
                return ConstantesWebView.VIEW_FRAGMENTS_MIS_PUNTOS_DETALLE_CANJEADOS;
            case 3:
                model.addAttribute("puntosDescargados", participanteWebService.puntosDescargadosParticipante(filtroParticipanteTransaccion));
                return ConstantesWebView.VIEW_FRAGMENTS_MIS_PUNTOS_DETALLE_DESCARGADOS;

        }
        return null;
    }

    @GetMapping(value = "viewDetallePedido/{idPedido}")
    public String viewDetallePedido(@PathVariable("idPedido") Integer idPedido, Model model) {
        Pedido pedido = pedidoWebService.listarPedidoById(idPedido);
        pedido.setPedidoDetalles(pedidoWebService.listarDetallePedido(idPedido));
        pedido.getDireccion().setUbigeo(ubigeoWebService.obtenerUbigeoID(pedido.getDireccion().getUbigeo().getIdUbigeo()));
        model.addAttribute("pedido", pedido);
        return ConstantesWebView.VIEW_FRAGMENTS_MIS_PUNTOS_DETALLE_PEDIDO;
    }

    @ResponseBody
    @PostMapping(value = "listarPosibles")
    public Datatable listarPosibles(FiltroParticipanteTransaccion filtroParticipanteTransaccion, Participante participante) {
        Datatable datatable = new Datatable();
        filtroParticipanteTransaccion.setParticipante(participante);
        filtroParticipanteTransaccion.setTipo(UtilEnum.TIPO_MIS_PUNTOS.POSIBLES.getValue());
        Integer conteo = participanteWebService.contarMisPuntos(filtroParticipanteTransaccion);
        datatable.setRecordsTotal(conteo);
        datatable.setRecordsFiltered(conteo);
        datatable.setData(participanteWebService.obtenerMisPuntos(filtroParticipanteTransaccion));
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "listarAcumulados")
    public Datatable obtenerAcumulados(FiltroParticipanteTransaccion filtroParticipanteTransaccion, Participante participante) {
        Datatable datatable = new Datatable();
        filtroParticipanteTransaccion.setParticipante(participante);
        filtroParticipanteTransaccion.setTipo(UtilEnum.TIPO_MIS_PUNTOS.ACUMULADOS.getValue());
        Integer conteo = participanteWebService.contarMisPuntos(filtroParticipanteTransaccion);
        datatable.setRecordsTotal(conteo);
        datatable.setRecordsFiltered(conteo);
        datatable.setData(participanteWebService.obtenerMisPuntos(filtroParticipanteTransaccion));
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "listarCanjeados")
    public Datatable obtenerCanjeados(FiltroParticipanteTransaccion filtroParticipanteTransaccion, Participante participante) {
        Datatable datatable = new Datatable();
        filtroParticipanteTransaccion.setParticipante(participante);
        filtroParticipanteTransaccion.setTipo(UtilEnum.TIPO_MIS_PUNTOS.CANJEADOS.getValue());
        Integer conteo = participanteWebService.contarMisPuntos(filtroParticipanteTransaccion);
        datatable.setRecordsTotal(conteo);
        datatable.setRecordsFiltered(conteo);
        datatable.setData(participanteWebService.obtenerMisPuntos(filtroParticipanteTransaccion));
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "listarVencidos")
    public Datatable obtenerVencidos(FiltroParticipanteTransaccion filtroParticipanteTransaccion, Participante participante) {
        Datatable datatable = new Datatable();
        filtroParticipanteTransaccion.setParticipante(participante);
        filtroParticipanteTransaccion.setTipo(UtilEnum.TIPO_MIS_PUNTOS.VENCIDOS.getValue());
        Integer conteo = participanteWebService.contarMisPuntos(filtroParticipanteTransaccion);
        datatable.setRecordsTotal(conteo);
        datatable.setRecordsFiltered(conteo);
        datatable.setData(participanteWebService.obtenerMisPuntos(filtroParticipanteTransaccion));
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "listarDescargados")
    public Datatable listarDescargados(FiltroParticipanteTransaccion filtroParticipanteTransaccion, Participante participante) {
        Datatable datatable = new Datatable();
        filtroParticipanteTransaccion.setParticipante(participante);
        filtroParticipanteTransaccion.setTipo(UtilEnum.TIPO_MIS_PUNTOS.DESCARGADOS.getValue());
        Integer conteo = participanteWebService.contarMisPuntos(filtroParticipanteTransaccion);
        datatable.setRecordsTotal(conteo);
        datatable.setRecordsFiltered(conteo);
        datatable.setData(participanteWebService.obtenerMisPuntos(filtroParticipanteTransaccion));
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "listarTodoPuntos")
    public Datatable obtenerTodosPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion, Participante participante) {
        Datatable datatable = new Datatable();
        filtroParticipanteTransaccion.setParticipante(participante);
        filtroParticipanteTransaccion.setTipo(UtilEnum.TIPO_MIS_PUNTOS.ESTADO_CUENTA.getValue());
        Integer conteo = participanteWebService.contarMisPuntos(filtroParticipanteTransaccion);
        datatable.setRecordsTotal(conteo);
        datatable.setRecordsFiltered(conteo);
        datatable.setData(participanteWebService.obtenerMisPuntos(filtroParticipanteTransaccion));
        return datatable;
    }

    @RequestMapping(value = "descargar-estado_cuenta", method = RequestMethod.GET)
    public ModelAndView descargarExcelError(Model model, Participante participante) {
        String nombredocumento = "estado-cuenta";
        StringBuilder sb = new StringBuilder()
                .append(nombredocumento)
                .append("-")
                .append(UtilCommon.dateFormat("ddMMyyyyhhmmss"))
                .append(".xlsx");

        FiltroParticipanteTransaccion filtroParticipanteTransaccion = new FiltroParticipanteTransaccion();
        filtroParticipanteTransaccion.setParticipante(participante);
        filtroParticipanteTransaccion.setTipo(UtilEnum.TIPO_MIS_PUNTOS.ESTADO_CUENTA.getValue());
        filtroParticipanteTransaccion.setLength(-1);

        model.addAttribute("filename", sb.toString());
        model.addAttribute("listParticipanteTransaccion", participanteWebService.obtenerMisPuntos(filtroParticipanteTransaccion));

        return new ModelAndView(estadoCuentaExcelView);
    }

}
