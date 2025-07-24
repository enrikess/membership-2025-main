package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.admin.service.ConfiguracionAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("administracion/banners-web")
public class BannerWebController extends BaseController {

    private ConfiguracionAdminService configuracionAdminService;
    private CatalogoAdminService catalogoAdminService;
    private ApiS3Service apiS3Service;

    @Autowired
    public BannerWebController(ConfiguracionAdminService configuracionAdminService, CatalogoAdminService catalogoAdminService, ApiS3Service apiS3Service) {
        this.configuracionAdminService = configuracionAdminService;
        this.catalogoAdminService = catalogoAdminService;
        this.apiS3Service = apiS3Service;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("ImgFolder", UtilEnum.TIPO_CARGA.CARGA_IMAGEN_BANNER);
        return ConstantesAdminView.VIEW_ADMINISTRACION_BANNER;
    }

    @ResponseBody
    @RequestMapping(value = "listar", method = RequestMethod.POST)
    public Datatable listarBanners(FiltroBanner filtroBanner) {
        List<ConfiguracionBanner> listConfiguracionbBanner = configuracionAdminService.bannerFiltroListar(filtroBanner);
        Datatable datatable = new Datatable();
        datatable.setData(listConfiguracionbBanner);
        datatable.setRecordsFiltered(listConfiguracionbBanner.size());
        datatable.setRecordsTotal(listConfiguracionbBanner.size());
        return datatable;
    }

    @ResponseBody
    @RequestMapping(value = "actualizar-estado", method = RequestMethod.POST)
    public PromotickResult actualizarEstado(@RequestBody ConfiguracionBanner configuracionBanner, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (configuracionBanner.getEstadoConfiguracionBanner().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                configuracionBanner.setEstadoConfiguracionBanner(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (configuracionBanner.getEstadoConfiguracionBanner().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                configuracionBanner.setEstadoConfiguracionBanner(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            configuracionBanner.setAccion(UtilEnum.MANTENIMIENTO.CAMBIAR_ESTADO.getCodigo());
            configuracionBanner.setAuditoria(auditoria);

            Integer resultado = configuracionAdminService.registroBanner(configuracionBanner);
            this.evaluarResultado(resultado, promotickResult, "Estado del banner se cambio con exito");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @GetMapping(value = "editar/{idBanner}")
    public String registrar(@PathVariable("idBanner")Integer idBanner, Model model){
        ConfiguracionBanner banner = configuracionAdminService.obtenerBanner(idBanner);
        if (banner.getIdConfiguracionBanner() == null) {
            return "redirect:/administracion/banner-web";
        }
        model.addAttribute("objBanner", banner);
        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());

        return ConstantesAdminView.VIEW_ADMINISTRACION_BANNER_REGISTRAR;
    }

    @GetMapping(value = "registrar")
    public String registrar(Model model) {
        model.addAttribute("objBanner", new ConfiguracionBanner());
        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_ADMINISTRACION_BANNER_REGISTRAR;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-banner")
    public PromotickResult guardarBanner(@RequestBody ConfiguracionBanner configuracionBanner,  PromotickResult promotickResult, Auditoria auditoria) {
        try {
            configuracionBanner.setAuditoria(auditoria);

            Integer resultado= configuracionAdminService.registroBanner(configuracionBanner);
            this.evaluarResultado(resultado, promotickResult, "El banner se actualizó correctamente");
            promotickResult.setData(resultado);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "cargar-imagenes")
    public PromotickResult cargarImagenes(
            @RequestParam(value = "idConfiguracionBanner") Integer idConfiguracionBanner,
            @RequestParam(value = "imagenBanner", required = false) MultipartFile imagenBanner,
            PromotickResult promotickResult, Auditoria auditoria, Usuario usuario) {
        try {
            ConfiguracionBanner configuracionBanner = new ConfiguracionBanner();
            configuracionBanner.setIdConfiguracionBanner(idConfiguracionBanner);
            configuracionBanner.setAuditoria(auditoria);

            if (imagenBanner != null) {
                String idImagenPrincipal = this.subirImagenS3(imagenBanner, usuario.getIdUsuario());
                if (idImagenPrincipal == null) {
                    throw new Exception("Error al subir imagen popup");
                }
                configuracionBanner.setImagenBanner(imagenBanner.getOriginalFilename());
            }

            Integer resultado= configuracionAdminService.registroBanner(configuracionBanner);
            this.evaluarResultado(resultado, promotickResult, "El Banner fue cargado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    private String subirImagenS3(MultipartFile file, Integer idUsuario) {
        try {
            S3UploadRequest s3UploadRequest = new S3UploadRequest();
            s3UploadRequest.setMultipartFile(file);
            s3UploadRequest.setFolderName("public/web/img/bg");
            s3UploadRequest.setKey(file.getOriginalFilename());
            s3UploadRequest.setPublic(true);
            s3UploadRequest.setType("public/web/img/bg");
            s3UploadRequest.setEntity(idUsuario);
            s3UploadRequest.setMediaType(Util.fromExtension(FilenameUtils.getExtension(file.getOriginalFilename())));
            AbstractResponse<FileMetadata> resultUpload = apiS3Service.uploadFileS3(s3UploadRequest);
            if (resultUpload.isStatus()) {
                return resultUpload.getData().getUrl();
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error S3" + e);
            return null;
        }
    }

}
