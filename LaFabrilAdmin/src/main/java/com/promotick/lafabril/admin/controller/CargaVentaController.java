package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.service.ApiSmsService;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaVentas;
import com.promotick.lafabril.admin.controller.excel.validator.CargaVentasValidator;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaVentas;
import com.promotick.lafabril.model.util.form.CargaVentas;
import com.promotick.lafabril.model.web.CargaWeb;
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

@Slf4j
@Controller
@RequestMapping("ventas")
public class CargaVentaController extends BaseController {

    private CargaExcelAdminService cargaExcelAdminService;
	private ApiS3Service apiS3Service;
    private ApiProperties apiProperties;
    private EmailAdminService emailAdminService;
    private ParticipanteAdminService participanteAdminService;
    private MensajeAdminService mensajeAdminService;
    private ApiSmsService apiSmsService;
    private CargaAdminService cargaAdminService;

    @Autowired
    public CargaVentaController(CargaExcelAdminService cargaExcelAdminService, ApiS3Service apiS3Service, ApiProperties apiProperties, EmailAdminService emailAdminService, ParticipanteAdminService participanteAdminService, MensajeAdminService mensajeAdminService, ApiSmsService apiSmsService, CargaAdminService cargaAdminService) {
    	this.cargaExcelAdminService = cargaExcelAdminService;
        this.apiS3Service = apiS3Service;
        this.apiProperties = apiProperties;
        this.emailAdminService = emailAdminService;
        this.participanteAdminService = participanteAdminService;
        this.mensajeAdminService = mensajeAdminService;
        this.apiSmsService = apiSmsService;
        this.cargaAdminService = cargaAdminService;
    }

    @GetMapping
    public String inicio(){
        return ConstantesAdminView.VIEW_VENTAS_CARGA;
    }

    @PostMapping("viewResumenCargaVentas")
    public String viewResumenCargaVentas(@RequestBody ResultCargaVentas resultCargaVentas, Model model) {
        model.addAttribute("resultCargaVentas", resultCargaVentas);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_VENTAS;
    }

    @ResponseBody
    @PostMapping(value = "subir-excel")
    public PromotickResult importarExcel(@RequestParam("excelfile") MultipartFile excelfile, Auditoria auditoria, PromotickResult promotickResult) {
        File fileError = null;
        CargaWeb cargaWeb = null;
        try {

            String nombre = FilenameUtils.getBaseName(excelfile.getOriginalFilename());
            String extension = FilenameUtils.getExtension(excelfile.getOriginalFilename());
            String tag = UtilCommon.dateFormat("yyyyMMddhhmmss");
            String uploadKey = nombre + "-" + tag + "." + extension;

            AbstractResponse<FileMetadata> resultUploadS3 = Util.sentFileToS3(apiS3Service, uploadKey, UtilEnum.TIPO_CARGA.CARGA_VENTA, excelfile, null, true);

            cargaWeb = new CargaWeb()
                    .setIdTipoCarga(2) // venta/avance
                    .setUsuarioCarga(auditoria.getUsuarioCreacion())
                    .setArchivo(resultUploadS3.getData().getUrl());

            Integer registro = cargaAdminService.registroCargaWeb(cargaWeb);
            if (registro == null || registro <= 0) {
                throw new Exception("No se pudo registrar la carga");
            }
            cargaWeb.setIdCarga(registro);

            CargaVentasValidator cargaVentasValidator = new CargaVentasValidator(cargaExcelAdminService, participanteAdminService, mensajeAdminService, properties, cargaWeb.getIdCarga(), apiSmsService, emailAdminService, auditoria);
            FileValidatorResult<FormCargaVentas, CargaVentas> result = cargaVentasValidator.build(excelfile);

            ResultCargaVentas resultCargaVentas = new ResultCargaVentas();
            resultCargaVentas.setRegistrosCorrectos(result.getSuccessfulCountRows());
            resultCargaVentas.setRegistrosError(result.getErrorCountRows());
            resultCargaVentas.setRegistrosTotal(result.getTotalCountRows());
            promotickResult.setData(resultCargaVentas);

            cargaWeb.setCorrectos(resultCargaVentas.getRegistrosCorrectos());
            cargaWeb.setErrores(resultCargaVentas.getRegistrosError());
            cargaWeb.setTotal(resultCargaVentas.getRegistrosTotal());
            cargaWeb.setEstado(resultCargaVentas.getRegistrosError() == 0);
            cargaWeb.setComentario(resultCargaVentas.getRegistrosError() == 0 ? "CARGADO" : "CARGADO CON REGISTROS HA VALIDAR");

            if (result.getErrorCountRows() > 0) {

                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_VENTAS_ERROR, result.getErrorRows());

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaVentas.class)
                        .setList(result.getErrorRows())
                        .setPath(apiProperties.getClient().getDirTemporal());

                fileError = excelBuilder.buildFile();

                if (!fileError.exists()) {
                    throw new Exception("No se pudo guardar archivo temporal");
                }

                AbstractResponse<FileMetadata> error = Util.sentFileToS3(apiS3Service, excelBuilder.getFilename().replace(apiProperties.getClient().getDirTemporal(), ""), UtilEnum.TIPO_CARGA.CARGA_VENTA_ERROR, null, fileError, true);
                cargaWeb.setArchivoError(error.isStatus() ? error.getData().getUrl() : error.getMessage());
            }

        } catch (Exception e) {
            promotickResult.setException(e);
            if (cargaWeb != null && cargaWeb.getIdCarga() != null) {
                cargaWeb.setEstado(false);
                cargaWeb.setComentario(e.getMessage());
            }
        } finally {
            if (fileError != null) {
                FileUtils.deleteQuietly(fileError);
            }
            if (cargaWeb != null && cargaWeb.getIdCarga() != null) {
                Integer actualizar = cargaAdminService.actualizarCargaWeb(cargaWeb);
                log.info("Se actualiza la carga: {}", actualizar);
            }
        }

        return promotickResult;
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = "descargar-excel-error")
    public ModelAndView descargarExcelError() {
        return new ModelAndView(
            ExcelBuilder.getInstance(FormCargaVentas.class)
                    .setList((List<FormCargaVentas>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_VENTAS_ERROR))
                    .buildView()
        );
    }
}
