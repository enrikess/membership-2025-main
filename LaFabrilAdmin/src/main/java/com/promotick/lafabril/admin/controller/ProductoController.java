package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.web.*;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.reporte.ReporteProducto;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("catalogos/productos")
public class ProductoController extends BaseController {

	private ProductoAdminService productoAdminService;
	private MarcaAdminService marcaAdminService;
	private CategoriaAdminService categoriaAdminService;
	private ApiS3Service apiS3Service;
	private CatalogoAdminService catalogoAdminService;
	private EmailAdminService emailAdminService;

	@Autowired
	private ProductoController(ProductoAdminService productoAdminService, MarcaAdminService marcaAdminService, CategoriaAdminService categoriaAdminService, ApiS3Service apiS3Service, CatalogoAdminService catalogoAdminService, EmailAdminService emailAdminService){
		this.productoAdminService = productoAdminService;
		this.marcaAdminService=marcaAdminService;
		this.categoriaAdminService=categoriaAdminService;
		this.apiS3Service = apiS3Service;
		this.catalogoAdminService = catalogoAdminService;
		this.emailAdminService = emailAdminService;
	}
	
	@GetMapping
	public String init() {
		return ConstantesAdminView.VIEW_PRODUCTOS;
	}
	
	@ResponseBody
	@PostMapping(value="listar-productos")
	public Datatable listarProductos(FiltroProducto filtroProducto){
		Datatable datatable = new Datatable();
		Integer total = productoAdminService.contarProductos(filtroProducto);
		datatable.setData(productoAdminService.listarProductos(filtroProducto));
		datatable.setRecordsFiltered(total);
		datatable.setRecordsTotal(total);
		return datatable;
	}
	
	@ResponseBody
	@PostMapping(value="actualizar-estado")
	public PromotickResult actualizarEstado(@RequestBody Producto producto, Auditoria auditoria, PromotickResult promotickResult){
		try {
			if(producto.getEstadoProducto().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())){
				producto.setEstadoProducto(UtilEnum.ESTADO.INACTIVO.getCodigo());
			}else if(producto.getEstadoProducto().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())){
				producto.setEstadoProducto(UtilEnum.ESTADO.ACTIVO.getCodigo());
			}
			producto.setAccion(UtilEnum.MANTENIMIENTO.CAMBIAR_ESTADO.getCodigo());
			producto.setAuditoria(auditoria);
			Integer resultado= productoAdminService.actualizarEstadoProducto(producto);

			this.evaluarResultado(resultado, promotickResult, "Estado de producto cambiado con exito");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}
	
	@GetMapping(value="registrar")
	public String registrar(Model model){
		model.addAttribute("categoriaslist", categoriaAdminService.obtenerCategoriasGeneral(ConstantesCommon.ZERO_VALUE));
		model.addAttribute("marcasList", marcaAdminService.listarMarcas(new FiltroMarca()));
		Producto producto = new Producto();
		TipoProducto tipoProducto = new TipoProducto();
		producto.setTipoProducto(tipoProducto);
		model.addAttribute("objProducto", producto);
		model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());
		model.addAttribute("tagsList", productoAdminService.listarTags());
		return ConstantesAdminView.VIEW_PRODUCTOS_DETALLE;
	}


	@GetMapping(value = "{idProducto}/{idCatalogo}")
	public String registrar(@PathVariable("idProducto")Integer idProducto, @PathVariable Integer idCatalogo, Model model){
		FiltroProducto filtroProducto = new FiltroProducto();
        filtroProducto.setIdProducto(idProducto);
		filtroProducto.setIdCatalogo(idCatalogo);
		List<Producto> producto = productoAdminService.listarProductos(filtroProducto);
		if(producto.isEmpty()){
			return "redirect:/catalogos/productos";
		}
		List<ProductoCatalogo> lista = productoAdminService.listarProductoCatalogoXProducto(filtroProducto.getIdProducto());
		List<Catalogo> catalogos = catalogoAdminService.listarCatalogos();
		catalogos.removeIf(e -> lista.stream().map(ProductoCatalogo::getCatalogo).collect(Collectors.toList()).contains(e));

		model.addAttribute("objProducto", producto.get(0));
		model.addAttribute("categoriaslist", categoriaAdminService.obtenerCategoriasGeneral(ConstantesCommon.ZERO_VALUE));
		model.addAttribute("marcasList", marcaAdminService.listarMarcas(new FiltroMarca()));
		model.addAttribute("tagsList", productoAdminService.listarTags());
		model.addAttribute("catalogosList", catalogos);
		return ConstantesAdminView.VIEW_PRODUCTOS_DETALLE;
	} 

	@ResponseBody
	@PostMapping(value="listar-marcas")
	public Datatable listarMarcas(FiltroMarca filtroMarca){
		Datatable datatable = new Datatable();
		Integer total = marcaAdminService.contarMarcasFiltro(filtroMarca);
		datatable.setData(marcaAdminService.listarMarcasFiltro(filtroMarca));
		datatable.setRecordsFiltered(total);
		datatable.setRecordsTotal(total);
		return datatable;
	}

	@ResponseBody
	@PostMapping(value="listar-catalogos-producto")
	public Datatable listarCatalogosProducto(FiltroProducto filtroProducto){
		Datatable datatable = new Datatable();
		List<ProductoCatalogo> lista = productoAdminService.listarProductoCatalogoXProducto(filtroProducto.getIdProducto());
		datatable.setData(lista);
		datatable.setRecordsFiltered(lista.size());
		datatable.setRecordsTotal(lista.size());
		return datatable;
	}


	@ResponseBody
	@PostMapping(value = "actualizar-producto")
	public PromotickResult guardarProducto(@RequestBody Producto producto, PromotickResult promotickResult, Auditoria auditoria) {
		try {
			producto.setAccion(UtilEnum.MANTENIMIENTO.ACTUALIZAR.getCodigo());
			producto.setAuditoria(auditoria);

			Integer resultado= productoAdminService.registrarProducto(producto);
			this.evaluarResultado(resultado, promotickResult, "El producto se actualizó correctamente");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}

	@ResponseBody
	@PostMapping(value = "actualizar-stock")
	public PromotickResult actualizarStockProductoCatalogo(@RequestBody ProductoCatalogo productoCatalogo, PromotickResult promotickResult, Auditoria auditoria) {
		try {
			productoCatalogo.setAuditoria(auditoria);
			Integer resultado= productoAdminService.actualizarStockProductoCatalogo(productoCatalogo);
			this.evaluarResultado(resultado, promotickResult, "Se actualizo el stock del producto por catalogo");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}

	@ResponseBody
	@PostMapping(value = "registrarProductoCatalogo")
	public PromotickResult registrarProductoCatalogo(@RequestBody ProductoCatalogo productoCatalogo, PromotickResult promotickResult, Auditoria auditoria) {
		try {
			productoCatalogo.setAuditoria(auditoria);
			Integer resultado= productoAdminService.registrarProductoCatalogo(productoCatalogo);
			this.evaluarResultado(resultado, promotickResult, "Se registro el catalogo exitosamente");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}

	@ResponseBody
	@PostMapping(value = "actualizar-estado-pc")
	public PromotickResult actualizarEstadoProductoCatalogo(@RequestBody ProductoCatalogo productoCatalogo, PromotickResult promotickResult, Auditoria auditoria) {
		try {
			if(productoCatalogo.getEstadoProductoCatalogo().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())){
				productoCatalogo.setEstadoProductoCatalogo(UtilEnum.ESTADO.INACTIVO.getCodigo());
			}else if(productoCatalogo.getEstadoProductoCatalogo().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())){
				productoCatalogo.setEstadoProductoCatalogo(UtilEnum.ESTADO.ACTIVO.getCodigo());
			}
			productoCatalogo.setAuditoria(auditoria);
			Integer resultado= productoAdminService.actualizarEstadoProductoCatalogo(productoCatalogo);
			this.evaluarResultado(resultado, promotickResult, "Se actualizo el estado del producto por catalogo");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}

	@ResponseBody
	@PostMapping(value = "cargar-imagenes")
	public PromotickResult cargarImagenes(
			@RequestParam(value = "idProducto") Integer idProducto,
			@RequestParam(value = "imagenPrincipal", required = false) MultipartFile imagenPrincipal,
			@RequestParam(value = "imagenDetalle", required = false) MultipartFile imagenDetalle,
			@RequestParam(value = "imagenPrincipalApp", required = false) MultipartFile imagenPrincipalApp,
			@RequestParam(value = "imagenDetalleApp", required = false) MultipartFile imagenDetalleApp,
			PromotickResult promotickResult, Auditoria auditoria, Usuario usuario) {
		try {
			Producto producto = new Producto();
			producto.setIdProducto(idProducto);
			producto.setAuditoria(auditoria);
			producto.setAccion(UtilEnum.MANTENIMIENTO.IMAGENES.getCodigo());

			if (imagenPrincipal != null) {
				Integer idImagenPrincipal = this.subirImagenS3(imagenPrincipal, usuario.getIdUsuario());
				if (idImagenPrincipal == null) {
					throw new Exception("Error al subir imagen principal");
				}
				producto.setImagenUno(imagenPrincipal.getOriginalFilename());
			}

			if (imagenDetalle != null) {
				Integer idImagenDetalle = this.subirImagenS3(imagenDetalle, usuario.getIdUsuario());
				if (idImagenDetalle == null) {
					throw new Exception("Error al subir imagen detalle");
				}
				producto.setImagenDetalle(imagenDetalle.getOriginalFilename());
			}

			if (imagenPrincipalApp != null) {
				Integer idImagenPrincipalApp = this.subirImagenS3(imagenPrincipalApp, usuario.getIdUsuario());
				if (idImagenPrincipalApp == null) {
					throw new Exception("Error al subir imagen principal");
				}
				producto.setImagenMovil(imagenPrincipalApp.getOriginalFilename());
			}

			if (imagenDetalleApp != null) {
				Integer idImagenDetalleApp = this.subirImagenS3(imagenDetalleApp, usuario.getIdUsuario());
				if (idImagenDetalleApp == null) {
					throw new Exception("Error al subir imagen detalle");
				}
				producto.setImagenMovilDetalle(imagenDetalleApp.getOriginalFilename());
			}

			Integer resultado= productoAdminService.registrarProducto(producto);
			this.evaluarResultado(resultado, promotickResult, "Las imagenes fueron cargadas correctamente");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}

	@ResponseBody
	@PostMapping(value = "cargar-pdf")
	public PromotickResult cargarPdf(
			@RequestParam(value = "idProducto") Integer idProducto,
			@RequestParam(value = "pdfAden", required = false) MultipartFile pdfAden,
			PromotickResult promotickResult, Auditoria auditoria, Usuario usuario) {
		try {
			Producto producto = new Producto();
			producto.setIdProducto(idProducto);
			producto.setAuditoria(auditoria);
			producto.setAccion(UtilEnum.MANTENIMIENTO.CARGA_PDF.getCodigo());

			if (pdfAden != null) {
				Integer idPdfAden = this.subirPdfS3(pdfAden, usuario.getIdUsuario());
				if (idPdfAden == null) {
					throw new Exception("Error al subir pdf");
				}
				producto.setPdfAden(pdfAden.getOriginalFilename());
			}

			Integer resultado= productoAdminService.registrarProducto(producto);
			this.evaluarResultado(resultado, promotickResult, "El PDF fue cargado correctamente");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}


	@ResponseBody
	@PostMapping(value = "registrarProducto")
	public PromotickResult guardarProducto(
			@RequestParam("codigoWeb") String codigoWeb,
			@RequestParam("nombreProducto") String nombreProducto,
			@RequestParam("descripcionProducto") String descripcionProducto,
			@RequestParam("nombreTag") String nombreTag,
			@RequestParam("idMarca") Integer idMarca,
			@RequestParam("idCategoria") Integer idSubcategoria,
			@RequestParam("puntosProducto") Integer puntosProducto,
			@RequestParam(value = "puntosRegular", required = false) Integer puntosRegular,
			@RequestParam("precioProducto") Double precioProducto,
			@RequestParam("especificacionesProducto") String especificacionesProducto,
			@RequestParam("terminosProducto") String terminosProducto,
			@RequestParam("stockProducto") Integer stockProducto,
			@RequestParam("idTagProducto") Integer idTagProducto,
			@RequestParam("idTipoProducto") Integer idTipoProducto,
			@RequestParam("catalogos") String catalogos,
			@RequestParam("idNetsuite") String idNetsuite,
			@RequestParam(value = "tags", required = false) String tags,
			@RequestParam(value = "imagenPrincipal", required = false) MultipartFile imagenPrincipal,
			@RequestParam(value = "imagenDetalle", required = false) MultipartFile imagenDetalle,
			@RequestParam(value = "imagenPrincipalApp", required = false) MultipartFile imagenPrincipalApp,
			@RequestParam(value = "imagenDetalleApp", required = false) MultipartFile imagenDetalleApp,
			@RequestParam(value = "pdfAden", required = false) MultipartFile pdfAden,
			@RequestParam("imagenEnvioExpress") Integer imagenEnvioExpress,
			Usuario usuario, Auditoria auditoria, PromotickResult promotickResult){
		try {
			Producto producto = new Producto();
			if (imagenPrincipal != null) {
				Integer idImagenPrincipal = this.subirImagenS3(imagenPrincipal, usuario.getIdUsuario());
				if (idImagenPrincipal == null) {
					throw new Exception("Error al subir imagen principal");
				}
				producto.setImagenUno(imagenPrincipal.getOriginalFilename());
			}

			if (imagenDetalle != null) {
				Integer idImagenDetalle = this.subirImagenS3(imagenDetalle, usuario.getIdUsuario());
				if (idImagenDetalle == null) {
					throw new Exception("Error al subir imagen detalle");
				}
				producto.setImagenDetalle(imagenDetalle.getOriginalFilename());
			}


			if (imagenPrincipalApp != null) {
				Integer idImagenPrincipalApp = this.subirImagenS3(imagenPrincipalApp, usuario.getIdUsuario());
				if (idImagenPrincipalApp == null) {
					throw new Exception("Error al subir imagen principal");
				}
				producto.setImagenMovil(imagenPrincipalApp.getOriginalFilename());
			}

			if (imagenDetalleApp != null) {
				Integer idImagenDetalleApp = this.subirImagenS3(imagenDetalleApp, usuario.getIdUsuario());
				if (idImagenDetalleApp == null) {
					throw new Exception("Error al subir imagen detalle");
				}
				producto.setImagenMovilDetalle(imagenDetalleApp.getOriginalFilename());
			}


			if (pdfAden != null) {
				Integer idPdfAden = this.subirPdfS3(pdfAden, usuario.getIdUsuario());
				if (idPdfAden == null) {
					throw new Exception("Error al subir PDF");
				}
				producto.setPdfAden(pdfAden.getOriginalFilename());
				log.info(": pdfAden.getOriginalFilename: " + pdfAden.getOriginalFilename()) ;
			}

			producto.setCodigoWeb(codigoWeb);
			producto.setNombreTag(nombreTag);
			producto.setDescripcionProducto(descripcionProducto);
			producto.setNombreProducto(nombreProducto);
			producto.setMarca(new Marca().setIdMarca(idMarca));
			producto.setCategoria(new Categoria().setIdCategoria(idSubcategoria));
			producto.setPuntosRegular(puntosRegular);
			producto.setPuntosProducto(puntosProducto);
			producto.setPrecioProducto(precioProducto);
			producto.setEspecificacionesProducto(especificacionesProducto);
			producto.setTerminosProducto(terminosProducto);
			producto.setStockProducto(stockProducto);
			producto.setTipoProducto(new TipoProducto().setIdTipoProducto(idTipoProducto));
			producto.setTagProducto(new TagProducto().setIdTagProducto(idTagProducto));
			producto.setNombreCatalogo(catalogos);
			producto.setTags(tags);
			producto.setAccion(UtilEnum.MANTENIMIENTO.REGISTRAR.getCodigo());
			producto.setAuditoria(auditoria);
			producto.setImagenEnvioExpress(imagenEnvioExpress);
			producto.setIdNetsuite(idNetsuite);

			Integer resultado= productoAdminService.procesoRegistrarProducto(producto);

			evaluarResultado(resultado, promotickResult, "Producto registrado correctamente");
		} catch (Exception e) {
			promotickResult.setException(e);
		}

		return promotickResult;
	}

	@ResponseBody
	@GetMapping("enviarNotificacion")
	public PromotickResult enviarNotificacion(PromotickResult promotickResult) {
		try {
			emailAdminService.envioEmailProductosNuevos(); //test
			promotickResult.setMessage("Se inicio el proceso de notificacion para productos nuevos");
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}

	private Integer subirImagenS3(MultipartFile file, Integer idUsuario) {
		try {
			S3UploadRequest s3UploadRequest = new S3UploadRequest();
			s3UploadRequest.setMultipartFile(file);
			s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_IMAGEN.getFolder());
			s3UploadRequest.setKey(file.getOriginalFilename());
			s3UploadRequest.setPublic(true);
			s3UploadRequest.setType(UtilEnum.TIPO_CARGA.CARGA_IMAGEN.name());
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

	private Integer subirPdfS3(MultipartFile file, Integer idUsuario) {
		try {
			S3UploadRequest s3UploadRequest = new S3UploadRequest();
			s3UploadRequest.setMultipartFile(file);
			s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_PDF.getFolder());
			s3UploadRequest.setKey(file.getOriginalFilename());
			s3UploadRequest.setPublic(true);
			s3UploadRequest.setType(UtilEnum.TIPO_CARGA.CARGA_PDF.name());
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

	@RequestMapping(value = "descargar-excel", method = RequestMethod.GET)
	public ModelAndView descargarExcel(Model model) {
		return new ModelAndView(
				ExcelBuilder.getInstance(ReporteProducto.class)
						.setList(productoAdminService.reporteProductos(new FiltroProducto()))
						.buildView()
		);
	}
}
