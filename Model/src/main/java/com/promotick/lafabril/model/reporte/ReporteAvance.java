package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.facturacion.ParticipanteAvance;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-ventas", sheetName = "Reporte de Ventas")
public class ReporteAvance implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("Número de documento")
    private String documento;

    @ExcelColumn("Fecha de operación")
    private String fecha;

    @ExcelColumn("Valor de venta")
    private Double valor;

    @ExcelColumn("Descripción")
    private String descripcion;

    public static ReporteAvance getFromEntity(ParticipanteAvance participanteAvance) {
        if (participanteAvance == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new ReporteAvance()
                .setDocumento(participanteAvance.getNroDocumento())
                .setDescripcion(participanteAvance.getDescripcion())
                .setFecha(formatter.format(participanteAvance.getFechaOperacion()))
                .setValor(participanteAvance.getValorVenta());

    }

    public static List<ReporteAvance> getFromEntities(List<ParticipanteAvance> participanteAvances) {
        List<ReporteAvance> list = new ArrayList<>();

        if (participanteAvances == null || participanteAvances.isEmpty()) {
            return list;
        }

        for (ParticipanteAvance participanteAvance : participanteAvances) {
            list.add(getFromEntity(participanteAvance));
        }

        return list;
    }
}
