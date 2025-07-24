package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.facturacion.Factura;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-mis-compras", sheetName = "Reporte de mis Compras")
public class ReporteFacturas implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("Fecha de carga")
    private String fechaCarga;

    @ExcelColumn("Evento")
    private String evento;

    @ExcelColumn("Nro Factura")
    private String nroFactura;

    @ExcelColumn("Fecha Facturacion")
    private String fechaFacturacion;

    @ExcelColumn("Monto")
    private String monto;

    @ExcelColumn("Puntos posibles")
    private String puntosPosibles;


    public static ReporteFacturas getFromEntity(Factura factura) {
        if (factura == null) {
            return null;
        }

        return new ReporteFacturas()
                .setFechaCarga(factura.getFechaGeneradoString())
                .setEvento(factura.getTipoFactura().getNombreTipoFactura())
                .setNroFactura(factura.getNroFactura())
                .setFechaFacturacion(factura.getFechaGeneradoString())
                .setMonto("$ " + factura.getMontoFactura())
                .setPuntosPosibles(String.valueOf(factura.getPuntosPosibles()));
    }

    public static List<ReporteFacturas> getFromEntities(List<Factura> facturas) {
        List<ReporteFacturas> list = new ArrayList<>();

        if (facturas == null || facturas.isEmpty()) {
            return list;
        }

        for (Factura factura : facturas) {
            list.add(getFromEntity(factura));
        }

        return list;
    }
}
