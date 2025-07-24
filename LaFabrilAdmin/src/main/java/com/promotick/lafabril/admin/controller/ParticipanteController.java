package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.model.reporte.ReporteParticipante;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.web.Participante;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("participantes")
public class ParticipanteController extends BaseController {

    private ParticipanteAdminService participanteAdminService;
    private CatalogoAdminService catalogoAdminService;
    private ConfiguracionAdminService configuracionAdminService;
    private ConcesionarioAdminService concesionarioAdminService;
    private TipoParticipanteAdminService tipoParticipanteAdminService;
    private SubtipoParticipanteAdminService subtipoParticipanteAdminService;
    private EmailAdminService emailAdminService;
    private UbigeoAdminService ubigeoAdminService;

    @Autowired
    public ParticipanteController(ParticipanteAdminService participanteAdminService, CatalogoAdminService catalogoAdminService, ConfiguracionAdminService configuracionAdminService, ConcesionarioAdminService concesionarioAdminService, TipoParticipanteAdminService tipoParticipanteAdminService, SubtipoParticipanteAdminService subtipoParticipanteAdminService, EmailAdminService emailAdminService, UbigeoAdminService ubigeoAdminService) {
        this.participanteAdminService = participanteAdminService;
        this.catalogoAdminService = catalogoAdminService;
        this.configuracionAdminService = configuracionAdminService;
        this.concesionarioAdminService = concesionarioAdminService;
        this.tipoParticipanteAdminService = tipoParticipanteAdminService;
        this.subtipoParticipanteAdminService = subtipoParticipanteAdminService;
        this.emailAdminService = emailAdminService;
        this.ubigeoAdminService = ubigeoAdminService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_PARTICIPANTES;
    }

    @GetMapping(value="registrar")
    public String registrar(Model model){
        log.info("pagina registrar participante");
        model.addAttribute("tipoDocumentos", configuracionAdminService.listarTipoDocumentos());
        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
        model.addAttribute("objParticipante", new Participante());
        model.addAttribute("concesionarios", concesionarioAdminService.listarConcesionarios());
        model.addAttribute("tiposParticipantes", tipoParticipanteAdminService.listarTipoParticipantes());
        model.addAttribute("subtiposParticipantes", subtipoParticipanteAdminService.listarSubtiposParticipante());
        return ConstantesAdminView.VIEW_PARTICIPANTES_DETALLE;
    }

    @GetMapping(value = "{idParticipante}")
    public String detalleParticipante(@PathVariable("idParticipante")Integer idParticipante, Model model){
        log.info("pagina actualizar participante");

        Participante participante = participanteAdminService.obtenerByID(idParticipante);
        if (participante == null) {
            return "redirect:/participantes";
        }

        model.addAttribute("objParticipante", participante);
        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
        model.addAttribute("brokers", participanteAdminService.listarBroker());
        model.addAttribute("concesionarios", concesionarioAdminService.listarConcesionarios());
        model.addAttribute("tiposParticipantes", tipoParticipanteAdminService.listarTipoParticipantes());
        model.addAttribute("categoriaParticipante", participanteAdminService.listarCategoriaParticipante());
        model.addAttribute("regiones", configuracionAdminService.obtenerRegiones());
        model.addAttribute("provincias", ubigeoAdminService.listarProvincias(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP));
        model.addAttribute("regionales", participanteAdminService.listarRegional());
//        model.addAttribute("subtiposParticipantes", subtipoParticipanteAdminService.listarSubtiposParticipante());
        return ConstantesAdminView.VIEW_PARTICIPANTES_DETALLE;
    }

    @ResponseBody
    @RequestMapping(value = "listar-participantes", method = RequestMethod.POST)
    public Datatable listarProductos(FiltroParticipante filtroParticipante) {
        Datatable datatable = new Datatable();
        Integer total = participanteAdminService.participantesContar(filtroParticipante);
        datatable.setData(participanteAdminService.participantesListar(filtroParticipante));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody Participante participante, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (participante.getEstadoParticipante().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                participante.setEstadoParticipante(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (participante.getEstadoParticipante().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                participante.setEstadoParticipante(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            participante.setAuditoria(auditoria);
            Integer resultado = participanteAdminService.actualizarEstadoParticipante(participante);

            this.evaluarResultado(resultado, promotickResult, "Se cambio el estado del participante con exito.");

            promotickResult.setData(participante);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

	@ResponseBody
	@PostMapping(value = "guardar-participante")
	public PromotickResult guardarParticipante(@RequestBody Participante participante, Auditoria auditoria, PromotickResult promotickResult){
		log.info("guardar-participante");
		try {
		    int accion = participante.getIdParticipante() == null ? 1 : 2;
            participante.setAuditoria(auditoria);
            participante.setAccion(accion);
            participante.setFechaNacimiento(UtilCommon.stringToDate(participante.getFechaNacimientoString()));

            Integer resultado = participanteAdminService.actualizarParticipante(participante);
            this.evaluarResultado(resultado, promotickResult, accion == 1 ? "Se registro el participante con exito." : "Se actulizo el participante con exito");

		} catch (Exception e) {
            promotickResult.setException(e);
		}
		return promotickResult;
	}

    @RequestMapping(value = "descargar-excel", method = RequestMethod.GET)
    public ModelAndView descargarExcel(Model model) {
        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteParticipante.class)
                        .setList(participanteAdminService.reporteParticipantes(new FiltroParticipante()))
                        .buildView()
        );
    }

    @GetMapping(value="activar")
    public String actualizarEstado (Model model) {
        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_PARTICIPANTES_ESTADOS;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado-canje")
    public PromotickResult actualizarEstadoCanje(@RequestBody Participante participante, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            Boolean enviarMail = false;
            if (participante.getEstadoCanjes()) {
                participante.setEstadoCanjes(false);
                enviarMail = true;
            } else if (participante.getEstadoCanjes()) {
                participante.setEstadoCanjes(true);
            }
            Participante participanteDB = participanteAdminService.obtenerByID(participante.getIdParticipante());
            participante.setAuditoria(auditoria);
            Integer resultado = participanteAdminService.actualizarEstadoParticipanteCanje(participante);

            if(enviarMail){
                emailAdminService.envioEmailEstadoCanje(participanteDB);
            }

            this.evaluarResultado(resultado, promotickResult, "Se cambio el estado del participante con exito.");

            promotickResult.setData(participante);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "aprobar-participante")
    public PromotickResult aprobarParticipantee(@RequestBody Participante participante, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            participante.setAuditoria(auditoria);
            Integer resultado = participanteAdminService.participanteAprobar(participante);

            this.evaluarResultado(resultado, promotickResult, "Se aprobo el participante con exito.");

            promotickResult.setData(participante);

            if (participante.getAprobar()) {
                Participante participanteDB = participanteAdminService.participanteAprobarMailBienvenidaObtener(participante.getIdParticipante());
                if (participanteDB != null) {
                    ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
                    emailAdminService.envioEmailBienvenidaIndividual(participanteDB, configuracionWeb, false);
                } else {
                    log.error("El participante {} no se encuentra en la DB o esta deshabilitado", participante.getIdParticipante());
                }
            }
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

}
