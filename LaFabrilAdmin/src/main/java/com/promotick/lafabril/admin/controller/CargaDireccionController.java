package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.controller.excel.descarga.UbigeoDescarga;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaDirecciones;
import com.promotick.lafabril.admin.controller.excel.validator.CargaDireccionesValidator;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.DireccionAdminService;
import com.promotick.lafabril.admin.service.UbigeoAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaDirecciones;
import com.promotick.lafabril.model.web.Direccion;
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

@Controller
@RequestMapping("direcciones/importar")
public class CargaDireccionController extends BaseController {

    private ApiS3Service apiS3Service;
    private UbigeoAdminService ubigeoAdminService;
    private DireccionAdminService direccionAdminService;
    private ApiProperties apiProperties;

    @Autowired
    public CargaDireccionController(ApiS3Service apiS3Service, UbigeoAdminService ubigeoAdminService, DireccionAdminService direccionAdminService, ApiProperties apiProperties) {
        this.apiS3Service = apiS3Service;
        this.ubigeoAdminService = ubigeoAdminService;
        this.direccionAdminService = direccionAdminService;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("tipoViviendas", ubigeoAdminService.listarTipoVivienda());
        model.addAttribute("zonas", ubigeoAdminService.listarZonas());
        return ConstantesAdminView.VIEW_DIRECCIONES_CARGA;
    }

    @PostMapping("viewResumenCargaDireccion")
    public String viewResumenCargaParticipante(@RequestBody ResultCargaDirecciones resultCargaDirecciones, Model model) {
        model.addAttribute("resultCargaDirecciones", resultCargaDirecciones);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_DIRECCIONES;
    }

    @GetMapping(value = "ubigeo")
    public ModelAndView descargarUbigeos() {
        return new ModelAndView(
                ExcelBuilder.getInstance(UbigeoDescarga.class)
                        .setList(UbigeoDescarga.parseEntities(ubigeoAdminService.listarUbigeos(ConstantesCommon.COD_PAIS)))
                        .buildView()
        );
    }

    @ResponseBody
    @PostMapping(value = "subir-excel")
    public PromotickResult importarExcel(@RequestParam("excelfile") MultipartFile excelfile, PromotickResult promotickResult, Auditoria auditoria) {

        File fileError = null;
        try {
            String nombre = FilenameUtils.getBaseName(excelfile.getOriginalFilename());
            String extension = FilenameUtils.getExtension(excelfile.getOriginalFilename());
            String tag = UtilCommon.dateFormat("yyyyMMddhhmmss");
            String uploadKey = nombre + "-" + tag + "." + extension;

            Util.sentFileToS3(apiS3Service, uploadKey, UtilEnum.TIPO_CARGA.CARGA_DIRECCIONES, excelfile);

            CargaDireccionesValidator cargaDireccionesValidator = new CargaDireccionesValidator(
                    direccionAdminService,
                    direccionAdminService.listarZonas(),
                    direccionAdminService.listarTipoViviendas(),
                    auditoria);
            FileValidatorResult<FormCargaDirecciones, Direccion> result = cargaDireccionesValidator.build(excelfile);

            ResultCargaDirecciones resultCargaDirecciones = new ResultCargaDirecciones();
            resultCargaDirecciones.setRegistrosCorrectos(result.getSuccessfulCountRows());
            resultCargaDirecciones.setRegistrosError(result.getErrorCountRows());
            resultCargaDirecciones.setRegistrosTotal(result.getTotalCountRows());

            promotickResult.setData(resultCargaDirecciones);

            if (result.getErrorCountRows() > 0) {
                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_DIRECCIONES_ERROR, result.getErrorRows());

                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCargaDirecciones.class)
                        .setList(result.getErrorRows())
                        .setPath(apiProperties.getClient().getDirTemporal());

                fileError = excelBuilder.buildFile();

                if (!fileError.exists()) {
                    throw new Exception("No se pudo guardar archivo temporal");
                }

                Util.sentFileToS3(apiS3Service, excelBuilder.getFilename(), UtilEnum.TIPO_CARGA.CARGA_DIRECCIONES_ERROR, fileError);

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
                ExcelBuilder.getInstance(FormCargaDirecciones.class)
                        .setList((List<FormCargaDirecciones>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_DIRECCIONES_ERROR))
                        .buildView()
        );
    }
}
