package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.admin.service.CatalogoService;
import com.promotick.lafabril.admin.service.ConfiguracionAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.web.Acelerador;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Tyc;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("administracion/acelerador")
public class AceleradorWebController extends BaseController {

    private ConfiguracionAdminService configuracionAdminService;
    private CatalogoAdminService catalogoAdminService;
    private ApiS3Service apiS3Service;
    private CatalogoService catalogoService;

    @Autowired
    public AceleradorWebController(CatalogoService catalogoService, ConfiguracionAdminService configuracionAdminService, CatalogoAdminService catalogoAdminService, ApiS3Service apiS3Service) {
        this.configuracionAdminService = configuracionAdminService;
        this.catalogoAdminService = catalogoAdminService;
        this.apiS3Service = apiS3Service;
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("ImgFolder", UtilEnum.TIPO_CARGA.CARGA_IMAGEN_BANNER);
        model.addAttribute("catalogos", catalogoService.listarCatalogos());
        return ConstantesAdminView.VIEW_ACELERADOR_BANNER;
    }

    @ResponseBody
    @GetMapping(value = "catalogos")
    public List<Catalogo> obtenerCatalogosDisponibles(Model model) {
        List<Catalogo> listCatalogo = catalogoService.listarCatalogos();
        List<Acelerador> listTyc = configuracionAdminService.aceleradorListar(new FiltroAcelerador());

        listCatalogo.removeIf(item -> listTyc.stream().anyMatch(i -> Objects.equals(i.getCatalogo().getIdCatalogo(), item.getIdCatalogo())));
        return listCatalogo;
    }


    @ResponseBody
    @RequestMapping(value = "listar", method = RequestMethod.POST)
    public Datatable listar() {
        List<Acelerador> aceledores = configuracionAdminService.aceleradorListar(new FiltroAcelerador());
        Datatable datatable = new Datatable();
        datatable.setData(aceledores);
        datatable.setRecordsFiltered(aceledores.size());
        datatable.setRecordsTotal(aceledores.size());
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
    public String registrar(@PathVariable("idAcelerador")Integer idAcelerador, Model model){
        ConfiguracionBanner banner = configuracionAdminService.obtenerBanner(idAcelerador);
        if (banner.getIdConfiguracionBanner() == null) {
            return "redirect:/administracion/banner-web";
        }
        model.addAttribute("objBanner", banner);
        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());

        return ConstantesAdminView.VIEW_ADMINISTRACION_ACELERADOR_REGISTRAR;
    }

    @ResponseBody
    @PostMapping(value = "editar")
    public PromotickResult editar(@RequestBody FiltroAcelerador filtroAcelerador, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            filtroAcelerador.setOperacion("CREAR");
            filtroAcelerador.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = configuracionAdminService.registroAcelerador(filtroAcelerador);
            this.evaluarResultado(resultado,  promotickResult, "Editado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "borrar")
    public PromotickResult borrar(@RequestBody FiltroAcelerador filtroAcelerador, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            filtroAcelerador.setOperacion("ELIMINAR");
            filtroAcelerador.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = configuracionAdminService.registroAcelerador(filtroAcelerador);
            this.evaluarResultado(resultado,  promotickResult, "Eliminado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

//    @GetMapping(value = "registrar")
//    public String registrar(Model model) {
//        model.addAttribute("objBanner", new ConfiguracionBanner());
//        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());
//        return ConstantesAdminView.VIEW_ADMINISTRACION_ACELERADOR_REGISTRAR;
//    }

    @ResponseBody
    @PostMapping(value = "crear")
    public PromotickResult guardar(@RequestBody FiltroAcelerador filtroAcelerador,  PromotickResult promotickResult, Auditoria auditoria) {
        try {
            filtroAcelerador.setOperacion("CREAR");
            filtroAcelerador.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = configuracionAdminService.registroAcelerador(filtroAcelerador);
            this.evaluarResultado(resultado, promotickResult, "El acelerador se actualizó correctamente");
            promotickResult.setData(resultado);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "cargar-imagenes")
    public PromotickResult cargarImagenes(
            @RequestParam(value = "idAcelerador") Integer idAcelerador,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenAcelerador,
            PromotickResult promotickResult, Auditoria auditoria, Usuario usuario) {
        try {
            FiltroAcelerador filtroAcelerador = new FiltroAcelerador();
            filtroAcelerador.setIdAcelerador(idAcelerador);
            filtroAcelerador.setOperacion("EDITAR");
            filtroAcelerador.setUsuarioCreacion(auditoria.getUsuarioCreacion());

            if (imagenAcelerador != null) {
                String idImagenPrincipal = this.subirImagenS3(imagenAcelerador, usuario.getIdUsuario());
                if (idImagenPrincipal == null) {
                    throw new Exception("Error al subir imagen popup");
                }
                filtroAcelerador.setImagen(idImagenPrincipal);
            }

            Integer resultado= configuracionAdminService.registroAcelerador(filtroAcelerador);
            this.evaluarResultado(resultado, promotickResult, "El acelerador fue cargado correctamente");
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
            s3UploadRequest.setKey(URLEncoder.encode(file.getOriginalFilename()));
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
