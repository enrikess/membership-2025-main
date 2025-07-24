package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.common.UtilCommon;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-visitas-detallado", sheetName = "Reporte de Visitas Detallado")
public class ReporteVisitaParticipanteDetallado implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("ID Participante")
    private Integer idParticipante;

    @ExcelColumn("Catalogo")
    private String nombreCatalogo;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Nombres")
    private String nombresParticipante;

    @ExcelColumn("Apellido Paterno")
    private String appaternoParticipante;

    @ExcelColumn("Apellido Materno")
    private String apmaternoParticipante;

    @ExcelColumn("Email")
    private String emailParticipante;

    @ExcelColumn(value = "Estado", methodEvaluate = "estadoParticipanteEval")
    private String estado;

    @ExcelColumn(value = "Fecha Visita", dateFormat = UtilCommon.FORMATO_FECHA_DIA_MES_ANIO)
    private Date fechaVisita;

    @ExcelColumn(value = "Hora Visita", dateFormat = UtilCommon.FORMATO_FECHA_HORA)
    private Date horaVisita;

    @ExcelColumn("Origen")
    private String origen;

    private Integer estadoParticipante;

    public static ReporteVisitaParticipanteDetallado getFromEntity(ReporteVisita reporteVisita) {
        if (reporteVisita == null) {
            return null;
        }

        return new ReporteVisitaParticipanteDetallado()
                .setIdParticipante(reporteVisita.getIdParticipante())
                .setNombreCatalogo(reporteVisita.getNombreCatalogo())
                .setNroDocumento(reporteVisita.getNroDocumento())
                .setNombresParticipante(reporteVisita.getNombresParticipante())
                .setAppaternoParticipante(reporteVisita.getAppaternoParticipante())
                .setApmaternoParticipante(reporteVisita.getApmaternoParticipante())
                .setEmailParticipante(reporteVisita.getEmailParticipante())
                .setEstadoParticipante(reporteVisita.getEstadoParticipante())
                .setFechaVisita(reporteVisita.getFechaVisita())
                .setHoraVisita(reporteVisita.getHoraVisita())
                .setOrigen(reporteVisita.getTipoDispositivo()==1?"WEB":"APP");
    }

    public static List<ReporteVisitaParticipanteDetallado> getFromEntities(List<ReporteVisita> reporteVisitas) {
        List<ReporteVisitaParticipanteDetallado> list = new ArrayList<>();

        if (reporteVisitas == null || reporteVisitas.isEmpty()) {
            return list;
        }

        for (ReporteVisita reporteVisita : reporteVisitas) {
            list.add(getFromEntity(reporteVisita));
        }

        return list;
    }

    private String estadoParticipanteEval() {
        if (this.estadoParticipante == 1) {
            return "ACTIVO";
        }
        return "INACTIVO";
    }
}
