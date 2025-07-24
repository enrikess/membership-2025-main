package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaParticipantes;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaParticipantesEstado;
import com.promotick.lafabril.admin.controller.excel.validator.CargaParticipantesActivacionValidator;
import com.promotick.lafabril.admin.controller.excel.validator.CargaParticipantesEstadosValidator;
import com.promotick.lafabril.admin.controller.excel.validator.CargaParticipantesValidator;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaParticipante;
import com.promotick.lafabril.model.util.descarga.FormCargaParticipanteActivacion;
import com.promotick.lafabril.model.util.descarga.FormCargaParticipanteEstado;
import com.promotick.lafabril.model.web.Participante;
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
@RequestMapping("participantes/importar")
public class CargaParticipanteController extends BaseController {

    private CargaExcelAdminService cargaExcelAdminService;
    private ApiS3Service apiS3Service;
    private ApiProperties apiProperties;
    private ConcesionarioAdminService concesionarioAdminService;
    private TipoParticipanteAdminService tipoParticipanteAdminService;
    private ConfiguracionAdminService configuracionAdminService;
    private CatalogoAdminService catalogoAdminService;
    private SubtipoParticipanteAdminService subtipoParticipanteAdminService;
    private EmailAdminService emailAdminService;
    private ParticipanteAdminService participanteAdminService;

    @Autowired
    public CargaParticipanteController(ConfiguracionAdminService configuracionAdminService, CatalogoAdminService catalogoAdminService, CargaExcelAdminService cargaExcelAdminService, ApiS3Service apiS3Service, ApiProperties apiProperties, ConcesionarioAdminService concesionarioAdminService, TipoParticipanteAdminService tipoParticipanteAdminService, SubtipoParticipanteAdminService subtipoParticipanteAdminService, EmailAdminService emailAdminService, ParticipanteAdminService participanteAdminService) {
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.apiS3Service = apiS3Service;
        this.apiProperties = apiProperties;
        this.concesionarioAdminService = concesionarioAdminService;
        this.tipoParticipanteAdminService = tipoParticipanteAdminService;
        this.subtipoParticipanteAdminService = subtipoParticipanteAdminService;
        this.emailAdminService = emailAdminService;
        this.configuracionAdminService = configuracionAdminService;
        this.catalogoAdminService = catalogoAdminService;
        this.participanteAdminService = participanteAdminService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("tiposParticipantes", tipoParticipanteAdminService.listarTipoParticipantes());
        model.addAttribute("categoriaParticipante", participanteAdminService.listarCategoriaParticipante());
//        model.addAttribute("tiposDocumentos", configuracionAdminService.listarTipoDocumentos());
        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
        model.addAttribute("razones", configuracionAdminService.listarRazonSocial());
        return ConstantesAdminView.VIEW_PARTICIPANTES_CARGA;
    }

    @GetMapping(value = "carga-estado")
    public String initCargaEstado(Model model) {
//        model.addAttribute("tiposParticipantes", tipoParticipanteAdminService.listarTipoParticipantes());
//        model.addAttribute("categoriaParticipante", participanteAdminService.listarCategoriaParticipante());
//        model.addAttribute("tiposDocumentos", configuracionAdminService.listarTipoDocumentos());
//        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
//        model.addAttribute("razones", configuracionAdminService.listarRazonSocial());
        return ConstantesAdminView.VIEW_PARTICIPANTES_ESTADOS_CARGA;
    }

    @GetMapping(value = "carga-activacion")
    public String initCargaActivacion() {
        return ConstantesAdminView.VIEW_PARTICIPANTES_ESTADOS_ACTIVACION;
    }

    @PostMapping("viewResumenCargaParticipante")
    public String viewResumenCargaParticipante(@RequestBody ResultCargaParticipantes resultCargaParticipante, Model model) {
        model.addAttribute("resultCargaParticipante", resultCargaParticipante);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_PARTICIPANTES;
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

            Util.sentFileToS3(apiS3Service, uploadKey, UtilEnum.TIPO_CARGA.CARGA_PARTICIPANTE, excelfile); //test

            CargaParticipantesValidator cargaParticipantesValidator = new CargaParticipantesValidator(
                    cargaExcelAdminService, emailAdminService, configuracionAdminService, participanteAdminService, properties
            );
            FileValidatorResult<FormCargaParticipante, Participante> result = cargaParticipantesValidator.build(excelfile);

            ResultCargaParticipantes resultCargaParticipantes = new ResultCargaParticipantes();
            resultCargaParticipantes.setRegistrosCorrectos(result.getSuccessfulCountRows());
            resultCargaParticipantes.setRegistrosError(result.getErrorCountRows());
            resultCargaParticipantes.setRegistrosTotal(result.getTotalCountRows());

            promotickResult.setData(resultCargaParticipantes);

            if (result.getErrorCountRows() > 0) {
                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_PARTICIPANTES_ERROR, result.getErrorRows());

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaParticipante.class)
                        .setList(result.getErrorRows())
                        .setPath(apiProperties.getClient().getDirTemporal());

                fileError = excelBuilder.buildFile();

                if (!fileError.exists()) {
                    throw new Exception("No se pudo guardar archivo temporal");
                }

                Util.sentFileToS3(apiS3Service, excelBuilder.getFilename(), UtilEnum.TIPO_CARGA.CARGA_PARTICIPANTE_ERROR, fileError); //test

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
                ExcelBuilder.getInstance(FormCargaParticipante.class)
                        .setList((List<FormCargaParticipante>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_PARTICIPANTES_ERROR))
                        .buildView()
        );
    }

    @ResponseBody
    @PostMapping(value = "subir-excel/estado")
    public PromotickResult importarExcelEstado(@RequestParam("excelfile") MultipartFile excelfile, PromotickResult promotickResult) {

        File fileError = null;
        try {
            String nombre = FilenameUtils.getBaseName(excelfile.getOriginalFilename());
            String extension = FilenameUtils.getExtension(excelfile.getOriginalFilename());
            String tag = UtilCommon.dateFormat("yyyyMMddhhmmss");
            String uploadKey = nombre + "-" + tag + "." + extension;

//            Util.sentFileToS3(apiS3Service, uploadKey, UtilEnum.TIPO_CARGA.CARGA_PARTICIPANTE_ESTADO, excelfile);

            CargaParticipantesEstadosValidator cargaParticipantesEstadosValidator = new CargaParticipantesEstadosValidator(
                    cargaExcelAdminService, participanteAdminService, emailAdminService
            );
            FileValidatorResult<FormCargaParticipanteEstado, Participante> result = cargaParticipantesEstadosValidator.build(excelfile);

            ResultCargaParticipantesEstado resultCargaParticipantesEstado = new ResultCargaParticipantesEstado();
            resultCargaParticipantesEstado.setRegistrosCorrectos(result.getSuccessfulCountRows());
            resultCargaParticipantesEstado.setRegistrosError(result.getErrorCountRows());
            resultCargaParticipantesEstado.setRegistrosTotal(result.getTotalCountRows());

            promotickResult.setData(resultCargaParticipantesEstado);

            if (result.getErrorCountRows() > 0) {
                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_PARTICIPANTES_ERROR, result.getErrorRows());

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaParticipanteEstado.class)
                        .setList(result.getErrorRows())
                        .setPath(apiProperties.getClient().getDirTemporal());

//                fileError = excelBuilder.buildFile();
//
//                if (!fileError.exists()) {
//                    throw new Exception("No se pudo guardar archivo temporal");
//                }

//                Util.sentFileToS3(apiS3Service, excelBuilder.getFilename(), UtilEnum.TIPO_CARGA.CARGA_PARTICIPANTE_ESTADO_ERROR, fileError);

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
    @GetMapping(value = "descargar-excel-estado-error")
    public ModelAndView descargarExcelEstadoError() {
        return new ModelAndView(
                ExcelBuilder.getInstance(FormCargaParticipanteEstado.class)
                        .setList((List<FormCargaParticipanteEstado>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_PARTICIPANTES_ERROR))
                        .buildView()
        );
    }

    @PostMapping("viewResumenCargaParticipanteEstado")
    public String viewResumenCargaParticipanteEstado(@RequestBody ResultCargaParticipantesEstado resultCargaParticipantesEstado, Model model) {
        model.addAttribute("resultCargaParticipante", resultCargaParticipantesEstado);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_PARTICIPANTES_ESTADO;
    }

    @ResponseBody
    @PostMapping(value = "subir-excel/activacion")
    public PromotickResult importarExcelActivacion(@RequestParam("excelfile") MultipartFile excelfile, PromotickResult promotickResult) {

        File fileError = null;
        try {

            CargaParticipantesActivacionValidator cargaParticipantesActivacionValidator = new CargaParticipantesActivacionValidator(
                    participanteAdminService
            );
            FileValidatorResult<FormCargaParticipanteActivacion, Participante> result = cargaParticipantesActivacionValidator.build(excelfile);

            ResultCargaParticipantesEstado resultCargaParticipantesEstado = new ResultCargaParticipantesEstado();
            resultCargaParticipantesEstado.setRegistrosCorrectos(result.getSuccessfulCountRows());
            resultCargaParticipantesEstado.setRegistrosError(result.getErrorCountRows());
            resultCargaParticipantesEstado.setRegistrosTotal(result.getTotalCountRows());

            promotickResult.setData(resultCargaParticipantesEstado);

            if (result.getErrorCountRows() > 0) {
                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_PARTICIPANTES_ERROR, result.getErrorRows());
            }

            emailAdminService.envioEmailBienvenida();
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
    @GetMapping(value = "descargar-excel-activacion-error")
    public ModelAndView descargarExcelActivacionError() {
        return new ModelAndView(
                ExcelBuilder.getInstance(FormCargaParticipanteActivacion.class)
                        .setList((List<FormCargaParticipanteActivacion>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_PARTICIPANTES_ERROR))
                        .buildView()
        );
    }

    @PostMapping("viewResumenCargaParticipanteActivacion")
    public String viewResumenCargaParticipanteActivacion(@RequestBody ResultCargaParticipantesEstado resultCargaParticipantesEstado, Model model) {
        model.addAttribute("resultCargaParticipante", resultCargaParticipantesEstado);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_PARTICIPANTES_ACTIVACION;
    }
}
