package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Catalogo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("administracion/popups-web")
public class PopupWebController extends BaseController {

	private ApiS3Service apiS3Service;
	private CatalogoAdminService catalogoAdminService;

	@Autowired
	private PopupWebController(ApiS3Service apiS3Service, CatalogoAdminService catalogoAdminService){
		this.apiS3Service = apiS3Service;
		this.catalogoAdminService = catalogoAdminService;
	}
	
	@GetMapping
	public String init() {
		return ConstantesAdminView.VIEW_ADMINISTRACION_POPUP;
	}

	@ResponseBody
	@GetMapping(value = "listar/{orden}")
	public Datatable listarSegmentos(@PathVariable("orden") Integer orden) {
		List<Catalogo> listaPopup = catalogoAdminService.listarCatalogos(ConstantesCommon.ZERO_VALUE, orden);
		Datatable datatable = new Datatable();
		datatable.setData(listaPopup);
		datatable.setRecordsFiltered(listaPopup.size());
		datatable.setRecordsTotal(listaPopup.size());
		return datatable;
	}

	@ResponseBody
	@PostMapping(value = "actualizar-estado")
	public PromotickResult actualizarEstado(@RequestBody Catalogo catalogo, PromotickResult promotickResult) {
		try {
			Integer tipoActualizar = UtilEnum.TIPO_ACTUALIZAR.POPUP_WEB.getCodigo();
			if (catalogo.getPopup().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
				catalogo.setPopup(UtilEnum.ESTADO.INACTIVO.getCodigo());
			} else if (catalogo.getPopup().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
				catalogo.setPopup(UtilEnum.ESTADO.ACTIVO.getCodigo());
			}
			Integer resultado = catalogoAdminService.actualizarEstadoCatalogo(catalogo.getIdCatalogo(), catalogo.getPopup(), tipoActualizar);
			evaluarResultado(resultado, promotickResult, "Popup Web actualizado correctamente");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}

	@GetMapping(value = "editar/{idSegmento}")
	public String registrar(@PathVariable("idSegmento")Integer idSegmento, Model model){
		List<Catalogo> catalogos = catalogoAdminService.listarCatalogos(idSegmento, 1);
		if (catalogos.isEmpty()) {
			return "redirect:/administracion/popups-web";
		}
		model.addAttribute("objSegmento", catalogos.get(0));

		return ConstantesAdminView.VIEW_ADMINISTRACION_POPUP_EDITAR;
	}


	@ResponseBody
	@PostMapping(value = "cargar-imagenes")
	public PromotickResult cargarImagenes(
            @RequestParam(value = "idCatalogo") Integer idCatalogo,
            @RequestParam(value = "popupImagen", required = false) MultipartFile popupImagen,
            PromotickResult promotickResult, Auditoria auditoria, Usuario usuario) {
		try {
			Catalogo catalogo = new Catalogo();
			catalogo.setIdCatalogo(idCatalogo);
			catalogo.setAuditoria(auditoria);

			if (popupImagen != null) {
				Integer idImagenPrincipal = this.subirImagenS3(popupImagen, usuario.getIdUsuario());
				if (idImagenPrincipal == null) {
					throw new Exception("Error al subir imagen popup");
				}
				catalogo.setPopupInicio(popupImagen.getOriginalFilename());
			}

			Integer resultado= catalogoAdminService.actualizarPopupCatalogo(catalogo);
			this.evaluarResultado(resultado, promotickResult, "El popup fue cargado correctamente");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}


	private Integer subirImagenS3(MultipartFile file, Integer idUsuario) {
		try {
			S3UploadRequest s3UploadRequest = new S3UploadRequest();
			s3UploadRequest.setMultipartFile(file);
			s3UploadRequest.setFolderName("public/web/img");
			s3UploadRequest.setKey(file.getOriginalFilename());
			s3UploadRequest.setPublic(true);
			s3UploadRequest.setType("public/web/img");
			s3UploadRequest.setEntity(idUsuario);
			s3UploadRequest.setMediaType(Util.fromExtension(FilenameUtils.getExtension(file.getOriginalFilename())));
			AbstractResponse<FileMetadata> resultUpload = apiS3Service.uploadFileS3(s3UploadRequest);
			if (resultUpload.isStatus()) {
				return resultUpload.getData().getIdResource();
			}
			return null;
		} catch (Exception e) {
			log.error("Error S3", e);
			return null;
		}
	}
}
