package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaImagenes;
import com.promotick.lafabril.admin.service.impl.CargaImagenesServiceImpl;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.CargaImagenesService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.admin.ConstantesAdminMessage;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaImagen;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("catalogos/importar-imagen")
public class CargaImagenController extends BaseController {

    private ApiProperties apiProperties;
    private ApiS3Service apiS3Service;
    private CargaImagenesService cargaImagenesService;

    @Autowired
    public CargaImagenController(ApiProperties apiProperties, ApiS3Service apiS3Service, CargaImagenesService cargaImagenesService) {
        this.apiProperties = apiProperties;
        this.apiS3Service = apiS3Service;
        this.cargaImagenesService = cargaImagenesService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_IMAGENES_CARGA;
    }

    @PostMapping("viewResumenCargaImagenes")
    public String viewResumenCargaImagenes(@RequestBody ResultCargaImagenes resultCargaImagenes, Model model) {
        model.addAttribute("resultCargaImagenes", resultCargaImagenes);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_IMAGENES;
    }

    @ResponseBody
    @PostMapping(value = "cargar-archivo-rar/{tipoImagen}")
    public PromotickResult cargarArchivo(@RequestParam("imagenFile") MultipartFile rarfile, @PathVariable Integer tipoImagen, PromotickResult promotickResult) {
        File fileError = null;
        try {
            String extension = FilenameUtils.getExtension(rarfile.getOriginalFilename());
            String name = FilenameUtils.getBaseName(rarfile.getOriginalFilename());

            if (extension == null) {
                throw new Exception("No se pudo encontrar la extension del archivo");
            }

            if (!extension.equalsIgnoreCase("rar")) {
                throw new Exception("El archivo no es un comprimido RAR");
            }

            String folderName = UUID.randomUUID().toString().replace("-", "");
            String path = apiProperties.getClient().getDirTemporal();
            String fullPath = path + folderName;

            File rarFolder = new File(fullPath);
            if (!rarFolder.mkdir()) {
                throw new Exception("Error al descomprimir el archivo");
            }

            String rarName = name + "-" + folderName + "." + extension;
            String rarFullPath = path + "/" + rarName;
            File rar = new File(rarFullPath);
            rarfile.transferTo(rar);

            ResultCargaImagenes resultCarga = cargaImagenesService.procesarCargaImagenes(rar, fullPath, name, tipoImagen);
            if (resultCarga.getException() != null) {
                throw new Exception(resultCarga.getException());
            }


            promotickResult.setData(resultCarga);
            if (resultCarga.getRegistrosError() > 0) {

                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_IMAGEN_ERROR, resultCarga.getListaErrores());
                String mensaje = com.promotick.lafabril.admin.util.Util.getMessage(messageSource, ConstantesAdminMessage.MSG_CARGA_ARCHIVO_ERROR_DESCARGAR, null);
                promotickResult.setMessage(mensaje);

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaImagen.class)
                        .setList(resultCarga.getListaErrores())
                        .setPath(apiProperties.getClient().getDirTemporal());

                fileError = excelBuilder.buildFile();

                if (!fileError.exists()) {
                    throw new Exception("No se pudo guardar archivo temporal");
                }

                Util.sentFileToS3(apiS3Service, excelBuilder.getFilename(), UtilEnum.TIPO_CARGA.CARGA_IMAGEN_ERROR, fileError);
            } else {
                String mensaje = com.promotick.lafabril.admin.util.Util.getMessage(messageSource, ConstantesAdminMessage.MSG_CARGA_ARCHIVO_EXITO, null);
                promotickResult.setMessage(mensaje);
            }

        } catch (Exception e) {
            promotickResult.setException(e);
        } finally {
            if (fileError != null) {
                FileUtils.deleteQuietly(fileError);
            }
        }

        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "cargar-archivo-imagen/{tipoImagen}")
    public PromotickResult cargarArchivo(@RequestParam("imagenFile") MultipartFile[] imagenFiles, @PathVariable Integer tipoImagen, PromotickResult promotickResult) {
        File fileError = null;
        ResultCargaImagenes resultCargaImagenes = new ResultCargaImagenes();
        try {
            for (MultipartFile imagenFile : imagenFiles) {

                try {
                    resultCargaImagenes.setRegistrosTotal(resultCargaImagenes.getRegistrosTotal() + 1);

                    String extension = FilenameUtils.getExtension(imagenFile.getOriginalFilename());

                    if (extension == null) {
                        throw new Exception("No se pudo encontrar la extension del archivo");
                    }

                    if (!CargaImagenesServiceImpl.FORMATOS_ACEPTADOS.contains(extension.toUpperCase())) {
                        throw new Exception("El formato: " + extension + " no es una imagen");
                    }

                    S3UploadRequest s3UploadRequest = new S3UploadRequest();
                    s3UploadRequest.setMultipartFile(imagenFile);
                    if(tipoImagen == 1){
                        s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_IMAGEN.getFolder());
                    }else{
                        s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_IMAGEN_APP.getFolder());
                    }
                    s3UploadRequest.setKey(imagenFile.getOriginalFilename());
                    s3UploadRequest.setPublic(true);
                    s3UploadRequest.setType(UtilEnum.TIPO_CARGA.CARGA_IMAGEN.name());
                    s3UploadRequest.setEntity(1);
                    s3UploadRequest.setMediaType(com.promotick.lafabril.admin.util.Util.fromExtension(extension));
                    AbstractResponse<FileMetadata> resultUpload = apiS3Service.uploadFileS3(s3UploadRequest);

                    if (resultUpload.isStatus()) {
                        resultCargaImagenes.setRegistrosCorrectos(resultCargaImagenes.getRegistrosCorrectos() + 1);
                    } else {
                        resultCargaImagenes.setRegistrosError(resultCargaImagenes.getRegistrosError() + 1);
                        resultCargaImagenes.getListaErrores().add(new FormCargaImagen().setNombreArchivo(imagenFile.getOriginalFilename()).setError(resultUpload.getMessage()));
                        throw new Exception(resultUpload.getMessage());
                    }
                } catch (Exception e) {
                    log.error("cargarArchivo", e);
                }

            }
            promotickResult.setData(resultCargaImagenes);

            if (resultCargaImagenes.getException() != null) {
                throw new Exception(resultCargaImagenes.getException());
            }

            if (resultCargaImagenes.getRegistrosError() > 0) {

                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_IMAGEN_ERROR, resultCargaImagenes.getListaErrores());
                String mensaje = Util.getMessage(messageSource, ConstantesAdminMessage.MSG_CARGA_ARCHIVO_ERROR_DESCARGAR, null);
                promotickResult.setMessage(mensaje);

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaImagen.class)
                        .setList(resultCargaImagenes.getListaErrores())
                        .setPath(apiProperties.getClient().getDirTemporal());

                fileError = excelBuilder.buildFile();

                if (!fileError.exists()) {
                    throw new Exception("No se pudo guardar archivo temporal");
                }


                Util.sentFileToS3(apiS3Service, excelBuilder.getFilename(), UtilEnum.TIPO_CARGA.CARGA_IMAGEN_ERROR, fileError);

            } else {
                String mensaje = com.promotick.lafabril.admin.util.Util.getMessage(messageSource, ConstantesAdminMessage.MSG_CARGA_ARCHIVO_EXITO, null);
                promotickResult.setMessage(mensaje);
            }

        } catch (Exception e) {
            promotickResult.setException(e);
        } finally {
            if (fileError != null) {
                FileUtils.deleteQuietly(fileError);
            }
        }

        return promotickResult;
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = "descargar-excel-error")
    public ModelAndView descargarExcelError() {
        log.info("descargarExcelError");
        return new ModelAndView(
                ExcelBuilder.getInstance(FormCargaImagen.class)
                        .setList((List<FormCargaImagen>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_IMAGEN_ERROR))
                        .buildView()
        );
    }

}
