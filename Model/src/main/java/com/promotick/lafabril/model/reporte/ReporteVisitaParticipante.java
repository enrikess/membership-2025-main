package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-visitas", sheetName = "Reporte de Visitas")
public class ReporteVisitaParticipante implements Serializable {
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

//    @ExcelColumn("Email")
//    private String emailParticipante;

    @ExcelColumn(value = "Estado", methodEvaluate = "estadoParticipanteEval")
    private String estado;

    @ExcelColumn("Visitas")
    private Integer conteo;

    private Integer estadoParticipante;

    public static ReporteVisitaParticipante getFromEntity(ReporteVisita reporteVisita) {
        if (reporteVisita == null) {
            return null;
        }

        return new ReporteVisitaParticipante()
                .setIdParticipante(reporteVisita.getIdParticipante())
                .setNombreCatalogo(reporteVisita.getNombreCatalogo())
                .setNroDocumento(reporteVisita.getNroDocumento())
                .setNombresParticipante(reporteVisita.getNombresParticipante())
                .setAppaternoParticipante(reporteVisita.getAppaternoParticipante())
                .setApmaternoParticipante(reporteVisita.getApmaternoParticipante())
//                .setEmailParticipante(reporteVisita.getEmailParticipante())
                .setEstadoParticipante(reporteVisita.getEstadoParticipante())
                .setConteo(reporteVisita.getConteo());
    }

    public static List<ReporteVisitaParticipante> getFromEntities(List<ReporteVisita> reporteVisitas) {
        List<ReporteVisitaParticipante> list = new ArrayList<>();

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
