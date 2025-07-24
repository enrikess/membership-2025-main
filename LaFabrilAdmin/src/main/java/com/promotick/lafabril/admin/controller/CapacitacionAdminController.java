package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.controller.excel.descarga.CapacitacionDescarga;
import com.promotick.lafabril.admin.service.CapacitacionAdminService;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.web.Capacitacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("capacitaciones")
@RequiredArgsConstructor
public class CapacitacionAdminController extends BaseController {

    private final CapacitacionAdminService capacitacionAdminService;
    private final ApiS3Service apiS3Service;
    private final CatalogoAdminService catalogoAdminService;

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_CAPACITACIONES;
    }

    @GetMapping("nuevo")
    public String nuevaCapacitacion(Model model) {
        model.addAttribute("capacitacion", new Capacitacion());
        model.addAttribute("esNuevo", true);
        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_CAPACITACIONES_DETALLE;
    }

    @GetMapping("{idCapacitacion}")
    public String editarCapacitacion(@PathVariable Integer idCapacitacion, Model model) {
        Capacitacion capacitacion = capacitacionAdminService.capacitacionAdminObtener(idCapacitacion);
        if (capacitacion == null) {
            return "redirect:/capacitaciones";
        }
        model.addAttribute("capacitacion", capacitacion);
        model.addAttribute("esNuevo", false);
        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_CAPACITACIONES_DETALLE;
    }

    @ResponseBody
    @PostMapping("listar")
    public Datatable listarCapacitaciones(FiltroCapacitacion filtroCapacitacion) {
        Datatable datatable = new Datatable();
        int conteo = capacitacionAdminService.capacitacionesFiltroContar(filtroCapacitacion);
        datatable.setRecordsFiltered(conteo);
        datatable.setRecordsTotal(conteo);
        datatable.setData(capacitacionAdminService.capacitacionesFiltroListar(filtroCapacitacion));
        com.promotick.lafabril.admin.util.Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_CAPACITACION, filtroCapacitacion);
        return datatable;
    }

    @GetMapping(value = "descargar-capacitaciones")
    public ModelAndView descargarCapacitaciones() {
        FiltroCapacitacion filtroCapacitacion = (FiltroCapacitacion) com.promotick.lafabril.admin.util.Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_CAPACITACION);
        if (filtroCapacitacion == null) {
            filtroCapacitacion = new FiltroCapacitacion();
        }
        filtroCapacitacion.setStart(0);
        filtroCapacitacion.setLength(Integer.MAX_VALUE);

        return new ModelAndView(
                ExcelBuilder.getInstance(CapacitacionDescarga.class)
                        .setList(CapacitacionDescarga.parseEntities(capacitacionAdminService.capacitacionesFiltroListar(filtroCapacitacion)))
                        .buildView()
        );
    }

    @ResponseBody
    @PostMapping("guardarCapacitacion")
    public PromotickResult guardarCapacitacion(
            Capacitacion capacitacion,
            @RequestParam(value = "imagenPrincipal", required = false) MultipartFile imagenPrincipal,
            @RequestParam(value = "imagenSecundaria", required = false) MultipartFile imagenSecundaria,
            PromotickResult promotickResult,
            Usuario usuario,
            Auditoria auditoria) {
        try {
            if (imagenPrincipal != null) {
                String imagenPrincipalUrl = this.subirImagenS3(imagenPrincipal, usuario.getIdUsuario(), com.promotick.lafabril.admin.util.Util.generateName(imagenPrincipal));
                if (imagenPrincipalUrl == null) {
                    throw new Exception("Error al subir imagen principal");
                }
                capacitacion.setImagenUno(imagenPrincipalUrl);
            }

            if (imagenSecundaria != null) {
                String imagenDetalleUrl = this.subirImagenS3(imagenSecundaria, usuario.getIdUsuario(), com.promotick.lafabril.admin.util.Util.generateName(imagenSecundaria));
                if (imagenDetalleUrl == null) {
                    throw new Exception("Error al subir imagen detalle");
                }
                capacitacion.setImagenDetalle(imagenDetalleUrl);
            }
            capacitacion.setAuditoria(auditoria);
            Integer registro = capacitacionAdminService.capacitacionAdminMantenimiento(capacitacion);
            if (registro == null || registro <= 0) {
                throw new Exception("Ocurrio un error a guardar la informacion");
            }
            promotickResult.setData(registro);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody Capacitacion capacitacion, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            capacitacion.setEstadoCapacitacion(!capacitacion.getEstadoCapacitacion());
            capacitacion.setAuditoria(auditoria);
            Integer resultado = capacitacionAdminService.capacitacionAdminEstadoCambiar(capacitacion);
            if (resultado == null || resultado <= 0) {
                throw new Exception("Ocurrio un error al cambiar el estado de la capacitacion");
            }
            promotickResult.setMessage("Se cambio de estado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    private String subirImagenS3(MultipartFile file, Integer idUsuario, String nombre) {
        try {
            S3UploadRequest s3UploadRequest = new S3UploadRequest();
            s3UploadRequest.setMultipartFile(file);
            s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_CAPACITACION.getFolder());
            s3UploadRequest.setKey(nombre);
            s3UploadRequest.setPublic(true);
            s3UploadRequest.setType(UtilEnum.TIPO_CARGA.CARGA_CAPACITACION.name());
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
