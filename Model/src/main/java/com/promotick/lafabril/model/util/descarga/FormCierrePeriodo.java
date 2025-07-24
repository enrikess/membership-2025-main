package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Cierre-Periodo-error", sheetName = "Cierre periodo Error", error = true)
public class FormCierrePeriodo implements Serializable {

    private static final long serialVersionUID = 3678119156826402499L;

    @ExcelColumn("ID Periodo Participante")
    private Integer idPeriodoParticipante;

    @ExcelColumn("Nombre periodo")
    private String nombrePeriodo;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Cli Ruc")
    private Integer estadoPeriodoParticipante;

    private static FormCierrePeriodo getEntity(PeriodoParticipante periodoParticipante) {
        return new FormCierrePeriodo()
                .setIdPeriodoParticipante(periodoParticipante.getIdPeriodoParticipante())
                .setNombrePeriodo(periodoParticipante.getPeriodo().getNombrePeriodo())
                .setNroDocumento(periodoParticipante.getParticipante().getNroDocumento())
                .setEstadoPeriodoParticipante(periodoParticipante.getEstadoPeriodoParticipante());
    }

    public static List<FormCierrePeriodo> getEntities(List<PeriodoParticipante> periodoParticipanteList) {
        List<FormCierrePeriodo> lista = new ArrayList<>();
        for (PeriodoParticipante periodoParticipante : periodoParticipanteList) {
            lista.add(getEntity(periodoParticipante));
        }
        return lista;
    }
}
