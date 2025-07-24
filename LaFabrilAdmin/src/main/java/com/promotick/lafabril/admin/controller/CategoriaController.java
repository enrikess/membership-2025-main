package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.admin.service.CategoriaAdminService;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.reporte.ReporteCategoria;
import com.promotick.lafabril.model.web.Categoria;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("catalogos/categorias")
public class CategoriaController extends BaseController {

    private CategoriaAdminService categoriaAdminService;
    private ApiS3Service apiS3Service;

    @Autowired
    public CategoriaController(CategoriaAdminService categoriaAdminService, ApiS3Service apiS3Service) {
        this.categoriaAdminService = categoriaAdminService;
        this.apiS3Service = apiS3Service;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_CATEGORIAS;
    }

    @ResponseBody
    @PostMapping(value = "listar-categorias")
    public Datatable listaCategorias(FiltroCategoria filtroCategoria) {
        List<Categoria> lista = categoriaAdminService.obtenerCategoriasAdmin(filtroCategoria);
        filtroCategoria.setLength(null);
        filtroCategoria.setStart(0);
        List<Categoria> conteo = categoriaAdminService.obtenerCategoriasAdmin(filtroCategoria);
        Datatable datatable = new Datatable();
        datatable.setData(lista);
        datatable.setRecordsFiltered(conteo.size());
        datatable.setRecordsTotal(conteo.size());
        return datatable;
    }

    @ResponseBody
    @RequestMapping(value = "actualizar-estado", method = RequestMethod.POST)
    public PromotickResult actualizarEstado(@RequestBody Categoria categoria, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (categoria.getEstadoCategoria().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                categoria.setEstadoCategoria(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (categoria.getEstadoCategoria().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                categoria.setEstadoCategoria(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            categoria.setAccion(UtilEnum.MANTENIMIENTO.CAMBIAR_ESTADO.getCodigo());
            categoria.setAuditoria(auditoria);

            Integer resultado = categoriaAdminService.registroCategoria(categoria);
            this.evaluarResultado(resultado, promotickResult, "Estado de la categoria se cambio con exito");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @GetMapping(value = "registrar")
    public String registrar(Model model) {
        model.addAttribute("objCategoria", new Categoria());
        model.addAttribute("categoriaslist", categoriaAdminService.obtenerCategoriasGeneral(ConstantesCommon.ZERO_VALUE));
        return ConstantesAdminView.VIEW_CATEGORIAS_DETALLE;
    }

    @GetMapping(value = "{idCategoria}")
    public String registrar(@PathVariable("idCategoria") Integer idCategoria, Model model) {
        List<Categoria> categorias = categoriaAdminService.obtenerCategoriasAdmin(new FiltroCategoria().setIdCategoria(idCategoria));
        if (categorias.isEmpty()) {
            return "redirect:/catalogos/categorias";
        }
        model.addAttribute("objCategoria", categorias.get(0));
        model.addAttribute("categoriaslist", categoriaAdminService.obtenerCategoriasGeneral(ConstantesCommon.ZERO_VALUE));

        return ConstantesAdminView.VIEW_CATEGORIAS_DETALLE;
    }

    @ResponseBody
    @PostMapping(value = "guardar-categoria")
    public PromotickResult guardarCategoria(@RequestBody Categoria categoria, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (categoria.getIdCategoria() == null || categoria.getIdCategoria().equals(0)) {
                categoria.setAccion(UtilEnum.MANTENIMIENTO.REGISTRAR.getCodigo());
            } else {
                categoria.setAccion(UtilEnum.MANTENIMIENTO.ACTUALIZAR.getCodigo());
            }
            categoria.setAuditoria(auditoria);
            Integer resultado = categoriaAdminService.registroCategoria(categoria);
            this.evaluarResultado(resultado, promotickResult, "Categoria registrada con exito");
            promotickResult.setData(resultado);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }


    @ResponseBody
    @PostMapping(value = "cargar-imagenes")
    public PromotickResult cargarImagenes(
            @RequestParam(value = "idCategoria") Integer idCategoria,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            PromotickResult promotickResult, Auditoria auditoria, Usuario usuario) {
        try {
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(idCategoria);
            categoria.setAuditoria(auditoria);

            if (imagen != null) {
                String idImagenPrincipal = this.subirImagenS3(imagen, usuario.getIdUsuario());
                if (idImagenPrincipal == null) {
                    throw new Exception("Error al subir imagen popup");
                }
                categoria.setImagenCategoria(idImagenPrincipal);
            }
            categoria.setAccion(UtilEnum.MANTENIMIENTO.IMAGENES.getCodigo());
            Integer resultado= categoriaAdminService.registroCategoria(categoria);
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

    @RequestMapping(value = "descargar-excel", method = RequestMethod.GET)
    public ModelAndView descargarExcelMarcas() {
        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteCategoria.class)
                        .setList(categoriaAdminService.reporteCategorias(new FiltroCategoria()))
                        .buildView()
        );
    }

}
