package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.ConfiguracionAdminService;
import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.admin.service.UsuarioPruebaAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("administracion/usuario-prueba")
public class UsuarioPruebaController extends BaseController {

    private UsuarioPruebaAdminService usuarioPruebaAdminService;
    private ParticipanteAdminService participanteAdminService;
    private CatalogoAdminService catalogoAdminService;
    private ConfiguracionAdminService configuracionAdminService;

    @Autowired
    public UsuarioPruebaController(UsuarioPruebaAdminService usuarioPruebaAdminService, ParticipanteAdminService participanteAdminService, CatalogoAdminService catalogoAdminService, ConfiguracionAdminService configuracionAdminService) {
        this.usuarioPruebaAdminService = usuarioPruebaAdminService;
        this.participanteAdminService = participanteAdminService;
        this.catalogoAdminService = catalogoAdminService;
        this.configuracionAdminService = configuracionAdminService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());
        model.addAttribute("tipoDocumentoList", configuracionAdminService.listarTipoDocumentos());
        return ConstantesAdminView.VIEW_ADMINISTRACION_USUARIOS_PRUEBA;
    }

    @ResponseBody
    @GetMapping(value = "listar-usuarios/{orden}")
    public Datatable listarUsuarios(@PathVariable("orden") Integer orden) {
        List<UsuarioPrueba> lista = usuarioPruebaAdminService.listarUsuariosPrueba(null, orden);
        Datatable datatable = new Datatable();
        datatable.setData(lista);
        datatable.setRecordsFiltered(lista.size());
        datatable.setRecordsTotal(lista.size());
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "nuevoUsuarioPrueba")
    public PromotickResult buscarParticipante(@RequestParam("nroDocumento") String nroDocumento, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            List<UsuarioPrueba> lista = usuarioPruebaAdminService.listarUsuariosPrueba(nroDocumento, -1);
            if (!lista.isEmpty()) {
                throw new Exception("El usuario con Nro de documennto [" + nroDocumento + "] ya se encuentra registrado");
            }

            Participante participante = participanteAdminService.obtenerParticipantePorNroDocumento(nroDocumento);
            if (participante == null || participante.getIdParticipante() == null) {
                throw new Exception("El participante no se encuentra registrado");
            }

            UsuarioPrueba usuarioPrueba = new UsuarioPrueba();
            usuarioPrueba.setEstadoUsuarioPrueba(1);
            usuarioPrueba.setParticipante(participante);
            usuarioPrueba.setAuditoria(auditoria);
            Integer resultado = usuarioPruebaAdminService.registrarUsuarioPrueba(usuarioPrueba);
            evaluarResultado(resultado, promotickResult, "Se registro correctamente el usuario de prueba");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody UsuarioPrueba usuario, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (usuario.getEstadoUsuarioPrueba().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                usuario.setEstadoUsuarioPrueba(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (usuario.getEstadoUsuarioPrueba().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                usuario.setEstadoUsuarioPrueba(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            usuario.setAccion(UtilEnum.MANTENIMIENTO.CAMBIAR_ESTADO.getCodigo());
            usuario.setAuditoria(auditoria);
            Integer resultado = usuarioPruebaAdminService.actualizarEstadoUsuarioPrueba(usuario);
            this.evaluarResultado(resultado, promotickResult, "Estado del usuario de prueba se cambio correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "nuevoParticipante")
    public PromotickResult registrarParticipante(@RequestBody Participante participante, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            participante.setAuditoria(auditoria);
            Integer resultado = usuarioPruebaAdminService.registrarParticipantePrueba(participante);
            if (resultado > 0) {
                promotickResult.setMessage("Se registro el usuario de prueba correctamente");
            } else {
                switch (resultado) {
                    case -1:
                        throw new Exception("El usuario ya se encuentra registrado por otro participante");
                    case -2:
                        throw new Exception("El DNI ya se encuentra registrado por otro participante");
                    default:
                        throw new Exception("Ocurrio un inconveniente al procesr su peticion");
                }
            }
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
