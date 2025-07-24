package com.promotick.lafabril.web.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.ParticipanteDireccion;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.web.service.DireccionWebService;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.service.SubtipoParticipanteWebService;
import com.promotick.lafabril.web.service.UbigeoWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("perfil")
public class PerfilController extends BaseController {

    private BCryptPasswordEncoder passwordEncoder;
    private ParticipanteWebService participanteWebService;
    private DireccionWebService direccionWebService;
    private UbigeoWebService ubigeoWebService;
    private SubtipoParticipanteWebService subtipoParticipanteWebService;
    private ApiS3Service apiS3Service;

    @Autowired
    public PerfilController(BCryptPasswordEncoder passwordEncoder, ParticipanteWebService participanteWebService, DireccionWebService direccionWebService, UbigeoWebService ubigeoWebService, SubtipoParticipanteWebService subtipoParticipanteWebService, ApiS3Service apiS3Service) {
        this.passwordEncoder = passwordEncoder;
        this.participanteWebService = participanteWebService;
        this.direccionWebService = direccionWebService;
        this.ubigeoWebService = ubigeoWebService;
        this.subtipoParticipanteWebService = subtipoParticipanteWebService;
        this.apiS3Service = apiS3Service;
    }

    @GetMapping
    public String init(@RequestParam(value = "tab", required = false, defaultValue = "1") String tabSelected, Model model, Participante participante) {
        model.addAttribute("subtiposParticipante", subtipoParticipanteWebService.listarSubtiposParticipante());
        model.addAttribute("formulariodireccion",false);
        model.addAttribute("tabSelected",tabSelected);
        model.addAttribute("address", direccionWebService.listarDireccionesParticipante(participante.getIdParticipante()));

        return ConstantesWebView.VIEW_PERFIL;
    }

    @GetMapping("viewFormularioDireccion/{idDireccion}")
    public String viewInformacionMeta(@PathVariable Integer idDireccion, Model model, Participante participante) {

        ParticipanteDireccion participanteDireccion = null;
        if (idDireccion != 0) {
            participanteDireccion = direccionWebService.listarDireccionesParticipante(participante.getIdParticipante()).stream().filter(pd -> pd.getIdParticipanteDireccion().intValue() == idDireccion).findAny().orElse(null);
            if (participanteDireccion != null) {
                model.addAttribute("ciudadesPerfil", ubigeoWebService.listarDistritos(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP, participanteDireccion.getDireccion().getUbigeo().getCodprov()));
            }
        }

        model.addAttribute("provinciasPerfil", ubigeoWebService.listarProvincias(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP));
        model.addAttribute("zonasPerfil", direccionWebService.listarZonas());
        model.addAttribute("tipoViviendasPerfil", direccionWebService.listarTipoViviendas());
        model.addAttribute("direccion", participanteDireccion);
        return ConstantesWebView.VIEW_FRAGMENTS_DIRECCION_FORMULARIO;
    }

    @ResponseBody
    @PostMapping(value = "guardar")
    public PromotickResult guardarPerfil(@RequestBody Participante participante, Participante participanteSession, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            participante.setIdParticipante(participanteSession.getIdParticipante());
            participante.setAuditoria(auditoria);

            Integer resultado = participanteWebService.actualizarParticipantePerfil(participante);

            this.evaluarResultado(resultado, promotickResult, "Informacion actualizada correctamente");

            Util.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, participanteWebService.loginParticipante(participanteSession.getTipoDocumento().getIdTipoDocumento(), participanteSession.getNroDocumento()));

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "anularDireccion")
    public PromotickResult anularDireccion(@RequestBody Participante participante, Participante participanteSession, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            participante.setCatalogo(participanteSession.getCatalogo());
            participante.setNroDocumento(participanteSession.getNroDocumento());
            participante.setIdParticipante(participanteSession.getIdParticipante());
            participante.setAuditoria(auditoria);
            participante.setAccion(UtilEnum.MANTENIMIENTO.ELIMINAR_DIRECCION.getCodigo());

            Integer resultado = participanteWebService.actualizarParticipante(participante);

            this.evaluarResultado(resultado, promotickResult, "Informacion actualizada correctamente");

            Util.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, participanteWebService.loginParticipante(participanteSession.getTipoDocumento().getIdTipoDocumento(), participanteSession.getNroDocumento()));

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "actualizarClavePerfil")
    public PromotickResult actualizarClavePerfil(@RequestBody Participante participante, Participante participanteSession, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (!passwordEncoder.matches(participante.getClaveAnterior(), participanteSession.getClaveParticipante())) {
                throw new Exception("La clave actual es incorrecta");
            }

            participante.setAccion(3);
            participante.setCatalogo(participanteSession.getCatalogo());
            participante.setIdParticipante(participanteSession.getIdParticipante());
            participante.setClaveParticipante(passwordEncoder.encode(participante.getClaveParticipante()));
            participante.setAuditoria(auditoria);

            Integer resultado = participanteWebService.actualizarParticipante(participante);

            this.evaluarResultado(resultado, promotickResult, "Clave actualizada con exito");

            Util.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, participanteWebService.loginParticipante(participanteSession.getTipoDocumento().getIdTipoDocumento(), participanteSession.getNroDocumento()));

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @PostMapping("subirFoto")
    public String subirFoto(@RequestPart MultipartFile file, Participante participante) {
        try {
            if (file.isEmpty()) {
                return "redirect:/perfil";
            }
            String nombre = UUID.randomUUID().toString().replace("-", "") + "-" + file.getOriginalFilename();
            AbstractResponse<FileMetadata> response = Util.sentFileToS3(apiS3Service, nombre, UtilEnum.TIPO_CARGA.CARGA_FOTO, file, null, true);
            log.info("Se carga imagen {}", response.getData().geteTag());

            Integer registro = participanteWebService.participanteActualizarFoto(participante.getIdParticipante(), nombre);
            if (registro == null || registro <= 0) {
                throw new Exception("Ocurrio un error al guardar la foto");
            }
            Util.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, participanteWebService.loginParticipante(participante.getTipoDocumento().getIdTipoDocumento(), participante.getNroDocumento()));

            return "redirect:/perfil?subirfoto=true";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/perfil?subirfoto=false";
        }
    }
}
