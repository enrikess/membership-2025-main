package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.CapacitacionAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.FiltroCapacitacion;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Capacitacion;
import com.promotick.lafabril.model.web.CapacitacionRecurso;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("capacitaciones/recursos")
@RequiredArgsConstructor
public class CapacitacionRecursoAdminController {

    private final CapacitacionAdminService capacitacionAdminService;
    private final ApiS3Service apiS3Service;

    @GetMapping
    public String init(@RequestParam(value = "idCapacitacion", required = false) Integer idCapacitacion, Model model) {
        if (idCapacitacion != null) {
            Capacitacion capacitacion = capacitacionAdminService.capacitacionAdminObtener(idCapacitacion);
            if (capacitacion == null) {
                return "redirect:/capacitaciones/recursos";
            }
            model.addAttribute("capacitacion", capacitacion);
        }
        model.addAttribute("capacitaciones", capacitacionAdminService.capacitacionesFiltroListar(FiltroCapacitacion.empty()));
        return ConstantesAdminView.VIEW_CAPACITACIONES_RECURSOS;
    }

    @GetMapping("viewCapacitacionRecursos/{idCapacitacion}")
    public String viewCapacitacionRecursos(@PathVariable Integer idCapacitacion, Model model) {
        Capacitacion capacitacion = new Capacitacion();
        capacitacion.setIdCapacitacion(idCapacitacion);
        capacitacion.setRecursos(capacitacionAdminService.capacitacionRecursosAdminListarTodos(idCapacitacion));
        model.addAttribute("capacitacion", capacitacion);
        return ConstantesAdminView.VIEW_FRAGMENTS_CAPACITACIONES_RECURSOS;
    }

    @GetMapping("viewCapacitacionRecurso/{idCapacitacionRecurso}")
    public String viewCapacitacionRecursoObtener(@PathVariable Integer idCapacitacionRecurso, Model model) {
        CapacitacionRecurso capacitacionRecurso = capacitacionAdminService.capacitacionRecursosAdminObtener(idCapacitacionRecurso);
        boolean esNuevo = idCapacitacionRecurso == 0;
        if (capacitacionRecurso == null) {
            capacitacionRecurso = new CapacitacionRecurso();
            esNuevo = true;
        }
        model.addAttribute("recurso", capacitacionRecurso);
        model.addAttribute("esNuevo", esNuevo);
        return ConstantesAdminView.VIEW_FRAGMENTS_CAPACITACIONES_RECURSOS_FORM;
    }

    @ResponseBody
    @PostMapping("guardarOrden")
    public PromotickResult guardarOrden(@RequestBody Capacitacion capacitacion, PromotickResult promotickResult) {
        try {
            String lista = capacitacion.getRecursos().stream()
                    .map(r -> r.getIdCapacitacionRecurso() + "," + r.getOrdenRecurso())
                    .collect(Collectors.joining(";"));
            capacitacionAdminService.capacitacionRecursosAdminOrdenar(lista);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping("eliminar/{idCapacitacionRecurso}")
    public PromotickResult eliminar(@PathVariable Integer idCapacitacionRecurso, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            Integer eliminacion = capacitacionAdminService.capacitacionRecursosAdminEliminar(idCapacitacionRecurso, auditoria.getIdUsuarioActualizacion());
            if (eliminacion == null || eliminacion <= 0) {
                throw new Exception("Ocurrio un error al eliminar este recurso");
            }
            promotickResult.setMessage("El recurso fue eliminado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping("activar/{idCapacitacionRecurso}")
    public PromotickResult activar(@PathVariable Integer idCapacitacionRecurso, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            Integer eliminacion = capacitacionAdminService.capacitacionRecursosAdminActivar(idCapacitacionRecurso, auditoria.getIdUsuarioActualizacion());
            if (eliminacion == null || eliminacion <= 0) {
                throw new Exception("Ocurrio un error al activar este recurso");
            }
            promotickResult.setMessage("El recurso fue activado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping("guardarCapacitacionRecurso")
    public PromotickResult guardarCapacitacionRecurso(
            CapacitacionRecurso capacitacionRecurso,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            PromotickResult promotickResult,
            Usuario usuario,
            Auditoria auditoria) {
        try {
            if (archivo != null) {
                String archivoUrl = this.subirImagenS3(archivo, usuario.getIdUsuario(), Util.generateName(archivo));
                if (archivoUrl == null) {
                    throw new Exception("Error al subir el archivo");
                }
                capacitacionRecurso.setContenido(archivoUrl);
            }

            capacitacionRecurso.setAuditoria(auditoria);
            Integer registro = capacitacionAdminService.capacitacionRecursoAdminMantenimiento(capacitacionRecurso);
            if (registro == null || registro <= 0) {
                throw new Exception("Ocurrio un error a guardar la informacion");
            }
            promotickResult.setData(registro);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    private String subirImagenS3(MultipartFile file, Integer idUsuario, String nombre) {
        try {
            S3UploadRequest s3UploadRequest = new S3UploadRequest();
            s3UploadRequest.setMultipartFile(file);
            s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_CAPACITACION_RECURSOS.getFolder());
            s3UploadRequest.setKey(nombre);
            s3UploadRequest.setPublic(true);
            s3UploadRequest.setType(UtilEnum.TIPO_CARGA.CARGA_CAPACITACION_RECURSOS.name());
            s3UploadRequest.setEntity(idUsuario);
            s3UploadRequest.setMediaType(Util.fromExtension(FilenameUtils.getExtension(file.getOriginalFilename())));
            AbstractResponse<FileMetadata> resultUpload = apiS3Service.uploadFileS3(s3UploadRequest);
            if (resultUpload.isStatus()) {
                return resultUpload.getData().getUrl();
            }
            return null;
        } catch (Exception e) {
            log.error("Error S3", e);
            return null;
        }
    }
}
