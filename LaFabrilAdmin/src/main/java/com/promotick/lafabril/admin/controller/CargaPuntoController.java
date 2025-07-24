package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.service.ApiSmsService;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaPuntos;
import com.promotick.lafabril.admin.controller.excel.validator.CargaPuntosValidator;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.EmailAdminService;
import com.promotick.lafabril.admin.service.MensajeAdminService;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.descarga.FormCargaPuntos;
import com.promotick.lafabril.model.util.form.CargaPuntos;
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
@RequestMapping("puntos")
public class CargaPuntoController extends BaseController {

    private CargaExcelAdminService cargaExcelAdminService;
	private ApiS3Service apiS3Service;
    private ApiProperties apiProperties;
    private EmailAdminService emailAdminService;
    private ParticipanteAdminService participanteAdminService;
    private MensajeAdminService mensajeAdminService;
    private ApiSmsService apiSmsService;

    @Autowired
    public CargaPuntoController(CargaExcelAdminService cargaExcelAdminService, ApiS3Service apiS3Service, ApiProperties apiProperties, EmailAdminService emailAdminService, ParticipanteAdminService participanteAdminService, MensajeAdminService mensajeAdminService, ApiSmsService apiSmsService) {
    	this.cargaExcelAdminService = cargaExcelAdminService;
        this.apiS3Service = apiS3Service;
        this.apiProperties = apiProperties;
        this.emailAdminService = emailAdminService;
        this.participanteAdminService = participanteAdminService;
        this.mensajeAdminService = mensajeAdminService;
        this.apiSmsService = apiSmsService;
    }

    @GetMapping
    public String inicio(){
        return ConstantesAdminView.VIEW_PUNTOS_CARGA;
    }

    @PostMapping("viewResumenCargaPuntos")
    public String viewResumenCargaPuntos(@RequestBody ResultCargaPuntos resultCargaPuntos, Model model) {
        model.addAttribute("resultCargaPuntos", resultCargaPuntos);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_PUNTOS;
    }

    @ResponseBody
    @PostMapping(value = "subir-excel")
    public PromotickResult importarExcel(@RequestParam("excelfile") MultipartFile excelfile, PromotickResult promotickResult) {
        File fileError = null;
        try {

            String nombre = FilenameUtils.getBaseName(excelfile.getOriginalFilename());
            String extension = FilenameUtils.getExtension(excelfile.getOriginalFilename());
            String tag = UtilCommon.dateFormat("yyyyMMddhhmmss");
            String uploadKey = nombre + "-" + tag + "." + extension;

//            AbstractResponse<FileMetadata> resultUploadS3 = Util.sentFileToS3(apiS3Service, uploadKey, UtilEnum.TIPO_CARGA.CARGA_PUNTO_MANUAL, excelfile);

            CargaPuntosValidator cargaPuntosValidator = new CargaPuntosValidator(cargaExcelAdminService, participanteAdminService, mensajeAdminService, properties, 999, apiSmsService, emailAdminService);
//            CargaPuntosValidator cargaPuntosValidator = new CargaPuntosValidator(cargaExcelAdminService, participanteAdminService, mensajeAdminService, properties, resultUploadS3.getData().getIdResource(), apiSmsService, emailAdminService);
            FileValidatorResult<FormCargaPuntos, CargaPuntos> result = cargaPuntosValidator.build(excelfile);

            ResultCargaPuntos resultCargaPuntos = new ResultCargaPuntos();
            resultCargaPuntos.setRegistrosCorrectos(result.getSuccessfulCountRows());
            resultCargaPuntos.setRegistrosError(result.getErrorCountRows());
            resultCargaPuntos.setRegistrosTotal(result.getTotalCountRows());
            promotickResult.setData(resultCargaPuntos);

            if (result.getErrorCountRows() > 0) {

                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_PUNTOS_ERROR, result.getErrorRows());

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaPuntos.class)
                        .setList(result.getErrorRows())
                        .setPath(apiProperties.getClient().getDirTemporal());

                fileError = excelBuilder.buildFile();

                if (!fileError.exists()) {
                    throw new Exception("No se pudo guardar archivo temporal");
                }

//                Util.sentFileToS3(apiS3Service, excelBuilder.getFilename(), UtilEnum.TIPO_CARGA.CARGA_PUNTO_MANUAL_ERROR, fileError);
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
        return new ModelAndView(
            ExcelBuilder.getInstance(FormCargaPuntos.class)
                    .setList((List<FormCargaPuntos>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_PUNTOS_ERROR))
                    .buildView()
        );
    }
}
