package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaProductos;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.controller.excel.validator.CargaProductosValidator;
import com.promotick.lafabril.admin.service.ProductoAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.descarga.FormCargaProductos;
import com.promotick.lafabril.model.web.Producto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("catalogos/importar-producto")
public class CargaProductoController extends BaseController {

    private ProductoAdminService productoAdminService;

    @Autowired
    public CargaProductoController(ProductoAdminService productoAdminService) {
        this.productoAdminService = productoAdminService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("tagsList", productoAdminService.listarTags());
        return ConstantesAdminView.VIEW_PRODUCTOS_CARGA;
    }

    @PostMapping("viewResumenCargaProductos")
    public String viewResumenCargaProductos(@RequestBody ResultCargaProductos resultCargaProductos, Model model) {
        model.addAttribute("resultCargaProductos", resultCargaProductos);
        return ConstantesAdminView.VIEW_FRAGMENTS_CARGA_RESUMEN_PRODUCTOS;
    }

    @ResponseBody
    @PostMapping(value = "subir-excel")
    public PromotickResult importarExcel(@RequestParam("excelfile") MultipartFile excelfile, PromotickResult promotickResult) {
        try {
            CargaProductosValidator cargaProductosValidator = new CargaProductosValidator(productoAdminService);
            FileValidatorResult<FormCargaProductos, Producto> result = cargaProductosValidator.build(excelfile);

            ResultCargaProductos resultCargaProductos = new ResultCargaProductos();
            resultCargaProductos.setRegistrosCorrectos(result.getSuccessfulCountRows());
            resultCargaProductos.setRegistrosError(result.getErrorCountRows());
            resultCargaProductos.setTotalRegistros(result.getTotalCountRows());
            promotickResult.setData(resultCargaProductos);
            if (result.getErrorCountRows() > 0) {
                Util.getSession().setAttribute(ConstantesSesion.SESSION_CARGA_PRODUCTO_ERROR, result.getErrorRows());
            }

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = "descargar-excel-error")
    public ModelAndView descargarExcelError() {
        return new ModelAndView(
                ExcelBuilder.getInstance(FormCargaProductos.class)
                        .setList((List<FormCargaProductos>) Util.getSession().getAttribute(ConstantesSesion.SESSION_CARGA_PRODUCTO_ERROR))
                        .buildView()
        );
    }

}
