package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaParticipantes;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaRecomendadosEstado;
import com.promotick.lafabril.admin.controller.excel.validator.CargaRecomendadosEstadosValidator;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.descarga.FormCargaParticipanteEstado;
import com.promotick.lafabril.model.util.descarga.FormCargaRecomendadoEstado;
import com.promotick.lafabril.model.web.Recomendado;
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
@RequestMapping("recomendados/importar")
public class CargaRecomendadosController extends BaseController {

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
    public CargaRecomendadosController(ConfiguracionAdminService configuracionAdminService, CatalogoAdminService catalogoAdminService, CargaExcelAdminService cargaExcelAdminService, ApiS3Service apiS3Service, ApiProperties apiProperties, ConcesionarioAdminService concesionarioAdminService, TipoParticipanteAdminService tipoParticipanteAdminService, SubtipoParticipanteAdminService subtipoParticipanteAdminService, EmailAdminService emailAdminService, ParticipanteAdminService participanteAdminService) {
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

//    @GetMapping
//    public String init(Model model) {
//        model.addAttribute("tiposParticipantes", tipoParticipanteAdminService.listarTipoParticipantes());
//        model.addAttribute("categoriaParticipante", participanteAdminService.listarCategoriaParticipante());
//        model.addAttribute("catalogos", catalogoAdminService.listarCatalogos());
//        model.addAttribute("razones", configuracionAdminService.listarRazonSocial());
//        return ConstantesAdminView.VIEW_PARTICIPANTES_CARGA;
//    }

    @GetMapping(value = "carga-estado")
    public String initCargaEstado(Model model) {
        return ConstantesAdminView.VIEW_RECOMENDADOS_ESTADOS_CARGA;
    }

    @PostMapping("viewResumenCargaParticipante")
    public String viewResumenCargaParticipante(@RequestBody ResultCargaParticipantes resultCargaParticipante, Model model) {
        model.addAttribute("resultCargaParticipante", resultCargaParticipante);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_PARTICIPANTES;
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

            CargaRecomendadosEstadosValidator cargaRecomendadosEstadosValidator = new CargaRecomendadosEstadosValidator(
                    cargaExcelAdminService, participanteAdminService, emailAdminService
            );
            FileValidatorResult<FormCargaRecomendadoEstado, Recomendado> result = cargaRecomendadosEstadosValidator.build(excelfile);

            ResultCargaRecomendadosEstado resultCargaRecomendadosEstado = new ResultCargaRecomendadosEstado();
            resultCargaRecomendadosEstado.setRegistrosCorrectos(result.getSuccessfulCountRows());
            resultCargaRecomendadosEstado.setRegistrosError(result.getErrorCountRows());
            resultCargaRecomendadosEstado.setRegistrosTotal(result.getTotalCountRows());

            promotickResult.setData(resultCargaRecomendadosEstado);

            if (result.getErrorCountRows() > 0) {
                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_RECOMENDADOS_ERROR, result.getErrorRows());

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaRecomendadoEstado.class)
                        .setList(result.getErrorRows())
                        .setPath(apiProperties.getClient().getDirTemporal());

//                fileError = excelBuilder.buildFile();
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
                ExcelBuilder.getInstance(FormCargaRecomendadoEstado.class)
                        .setList((List<FormCargaRecomendadoEstado>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_RECOMENDADOS_ERROR))
                        .buildView()
        );
    }

    @PostMapping("viewResumenCargaRecomendadoEstado")
    public String viewResumenCargaRecomendadoEstado(@RequestBody ResultCargaRecomendadosEstado resultCargaRecomendadosEstado, Model model) {
        model.addAttribute("resultCargaRecomendadosEstado", resultCargaRecomendadosEstado);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_RECOMENDADOS_ESTADO;
    }
}
