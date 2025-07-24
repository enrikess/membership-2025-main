package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaImagenes;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaPdf;
import com.promotick.lafabril.admin.service.CargaImagenesService;
import com.promotick.lafabril.admin.service.CargaPdfService;
import com.promotick.lafabril.admin.service.impl.CargaImagenesServiceImpl;
import com.promotick.lafabril.admin.service.impl.CargaPdfServiceImpl;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.admin.ConstantesAdminMessage;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaImagen;
import com.promotick.lafabril.model.util.descarga.FormCargaPdf;
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
@RequestMapping("catalogos/importar-pdf")
public class CargaPdfController extends BaseController {

    private ApiProperties apiProperties;
    private ApiS3Service apiS3Service;
    private CargaPdfService cargaPdfService;

    @Autowired
    public CargaPdfController(ApiProperties apiProperties, ApiS3Service apiS3Service, CargaPdfService cargaPdfService) {
        this.apiProperties = apiProperties;
        this.apiS3Service = apiS3Service;
        this.cargaPdfService = cargaPdfService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_PDF_CARGA;
    }

    @PostMapping("viewResumenCargaPdf")
    public String viewResumenCargaPdf(@RequestBody ResultCargaPdf resultCargaPdf, Model model) {
        model.addAttribute("resultCargaPdf", resultCargaPdf);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_PDF;
    }

    @ResponseBody
    @PostMapping(value = "cargar-archivo-rar")
    public PromotickResult cargarArchivo(@RequestParam("pdfFile") MultipartFile rarfile, PromotickResult promotickResult) {
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

            ResultCargaPdf resultCarga = cargaPdfService.procesarCargaPdf(rar, fullPath, name);
            if (resultCarga.getException() != null) {
                throw new Exception(resultCarga.getException());
            }


            promotickResult.setData(resultCarga);
            if (resultCarga.getRegistrosError() > 0) {

                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_PDF_ERROR, resultCarga.getListaErrores());
                String mensaje = com.promotick.lafabril.admin.util.Util.getMessage(messageSource, ConstantesAdminMessage.MSG_CARGA_ARCHIVO_ERROR_DESCARGAR, null);
                promotickResult.setMessage(mensaje);

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaPdf.class)
                        .setList(resultCarga.getListaErrores())
                        .setPath(apiProperties.getClient().getDirTemporal());

                fileError = excelBuilder.buildFile();

                if (!fileError.exists()) {
                    throw new Exception("No se pudo guardar archivo temporal");
                }

                Util.sentFileToS3(apiS3Service, excelBuilder.getFilename(), UtilEnum.TIPO_CARGA.CARGA_PDF_ERROR, fileError);
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
    @PostMapping(value = "cargar-archivo-pdf")
    public PromotickResult cargarArchivo(@RequestParam("pdfFile") MultipartFile[] pdfFiles, PromotickResult promotickResult) {
        File fileError = null;
        ResultCargaPdf resultCargaPdf = new ResultCargaPdf();
        try {
            for (MultipartFile pdfFile : pdfFiles) {

                try {
                    resultCargaPdf.setRegistrosTotal(resultCargaPdf.getRegistrosTotal() + 1);

                    String extension = FilenameUtils.getExtension(pdfFile.getOriginalFilename());

                    if (extension == null) {
                        throw new Exception("No se pudo encontrar la extension del archivo");
                    }

                    if (!CargaPdfServiceImpl.FORMATOS_ACEPTADOS.contains(extension.toUpperCase())) {
                        throw new Exception("El formato: " + extension + " no es un pdf");
                    }

                    S3UploadRequest s3UploadRequest = new S3UploadRequest();
                    s3UploadRequest.setMultipartFile(pdfFile);

                    s3UploadRequest.setKey(pdfFile.getOriginalFilename());
                    s3UploadRequest.setPublic(true);
                    s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_PDF.getFolder());
                    s3UploadRequest.setEntity(1);
                    s3UploadRequest.setMediaType(com.promotick.lafabril.admin.util.Util.fromExtension(extension));
                    AbstractResponse<FileMetadata> resultUpload = apiS3Service.uploadFileS3(s3UploadRequest);

                    if (resultUpload.isStatus()) {
                        resultCargaPdf.setRegistrosCorrectos(resultCargaPdf.getRegistrosCorrectos() + 1);
                    } else {
                        resultCargaPdf.setRegistrosError(resultCargaPdf.getRegistrosError() + 1);
                        resultCargaPdf.getListaErrores().add(new FormCargaPdf().setNombreArchivo(pdfFile.getOriginalFilename()).setError(resultUpload.getMessage()));
                        throw new Exception(resultUpload.getMessage());
                    }
                } catch (Exception e) {
                    log.error("cargarArchivo", e);
                }

            }
            promotickResult.setData(resultCargaPdf);

            if (resultCargaPdf.getException() != null) {
                throw new Exception(resultCargaPdf.getException());
            }

            if (resultCargaPdf.getRegistrosError() > 0) {

                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_PDF_ERROR, resultCargaPdf.getListaErrores());
                String mensaje = Util.getMessage(messageSource, ConstantesAdminMessage.MSG_CARGA_ARCHIVO_ERROR_DESCARGAR, null);
                promotickResult.setMessage(mensaje);

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaPdf.class)
                        .setList(resultCargaPdf.getListaErrores())
                        .setPath(apiProperties.getClient().getDirTemporal());

                fileError = excelBuilder.buildFile();

                if (!fileError.exists()) {
                    throw new Exception("No se pudo guardar archivo temporal");
                }


                Util.sentFileToS3(apiS3Service, excelBuilder.getFilename(), UtilEnum.TIPO_CARGA.CARGA_PDF_ERROR, fileError);

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
                ExcelBuilder.getInstance(FormCargaPdf.class)
                        .setList((List<FormCargaPdf>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_PDF_ERROR))
                        .buildView()
        );
    }

}
