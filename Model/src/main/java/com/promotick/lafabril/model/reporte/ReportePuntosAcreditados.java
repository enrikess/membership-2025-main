package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.facturacion.Factura;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-puntos-acreditados", sheetName = "Reporte de Puntos acreditados")
public class ReportePuntosAcreditados implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("Nombres")
    private String nombres;

    @ExcelColumn("Apellido paterno")
    private String apPaterno;

    @ExcelColumn("Apellido materno")
    private String apMaterno;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Tipo")
    private String tipo;

    @ExcelColumn("Nro factura")
    private String nroFactura;

    @ExcelColumn("Fecha factura")
    private String fechaFactura;

    @ExcelColumn("Puntos acreditados")
    private Integer puntosAcreditados;


    public static ReportePuntosAcreditados getFromEntity(Factura factura) {
        if (factura == null) {
            return null;
        }

        return new ReportePuntosAcreditados()
                .setNombres(factura.getParticipante().getNombresParticipante())
                .setApPaterno(factura.getParticipante().getAppaternoParticipante())
                .setApMaterno(factura.getParticipante().getApmaternoParticipante())
                .setNroDocumento(factura.getParticipante().getNroDocumento())
                .setTipo(factura.getTipoFactura().getNombreTipoFactura())
                .setNroFactura(factura.getNroFactura())
                .setFechaFactura(factura.getFechaEmisionString())
                .setPuntosAcreditados(factura.getPuntosAcreditados());

    }

    public static List<ReportePuntosAcreditados> getFromEntities(List<Factura> facturas) {
        List<ReportePuntosAcreditados> list = new ArrayList<>();

        if (facturas == null || facturas.isEmpty()) {
            return list;
        }

        for (Factura factura : facturas) {
            list.add(getFromEntity(factura));
        }

        return list;
    }
}
