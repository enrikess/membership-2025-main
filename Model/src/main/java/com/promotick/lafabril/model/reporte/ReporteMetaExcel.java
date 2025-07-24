package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-metas", sheetName = "Reporte de Metas")
public class ReporteMetaExcel implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Año")
    private Integer anio;

    @ExcelColumn("Trimestre")
    private Integer mes;

    @ExcelColumn("Meta")
    private Double meta;

    @ExcelColumn("Descripción")
    private String descripcion;

    @ExcelColumn("Porcentaje de rebate")
    private Double porcentajeRebate;

    @ExcelColumn("Valor de pago")
    private Double valorPago;

    @ExcelColumn("Fecha de Registro")
    private String fechaRegistro;

    @ExcelColumn("Acciones de Sellout")
    private String accionesDeSellout;

    public static ReporteMetaExcel getFromEntity(ReporteMetas reporteMetas) {
        if (reporteMetas == null) {
            return null;
        }

        String accionesFormat = reporteMetas.getAccionProducto();
        if (accionesFormat != null && accionesFormat.length() > 2) {
            accionesFormat = accionesFormat.substring(1, accionesFormat.length() - 1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new ReporteMetaExcel()
                .setMeta(reporteMetas.getValorMeta())
                .setAnio(reporteMetas.getAnio())
                .setMes(reporteMetas.getMes())
                .setNroDocumento(reporteMetas.getNroDocumento())
                .setDescripcion(reporteMetas.getDescripcion())
                .setAccionesDeSellout(accionesFormat)
                .setPorcentajeRebate(reporteMetas.getPorcentajeRebate())
                .setFechaRegistro(formatter.format(reporteMetas.getFechaRegistro()))
                .setValorPago(reporteMetas.getValorPago());
    }

    public static List<ReporteMetaExcel> getFromEntities(List<ReporteMetas> reporteMetas) {
        List<ReporteMetaExcel> list = new ArrayList<>();

        if (reporteMetas == null || reporteMetas.isEmpty()) {
            return list;
        }

        for (ReporteMetas reporteMeta : reporteMetas) {
            list.add(getFromEntity(reporteMeta));
        }

        return list;
    }

}
