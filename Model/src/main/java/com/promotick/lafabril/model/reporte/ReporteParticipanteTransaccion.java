package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.web.ParticipanteTransaccion;
import com.promotick.lafabril.common.UtilCommon;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-puntos", sheetName = "Reporte de puntos")
public class ReporteParticipanteTransaccion implements Serializable {
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

    //@ExcelColumn("Apellido Materno")
    //private String apmaternoParticipante;

    @ExcelColumn(value = "Genero", methodEvaluate = "generoEval")
    private String generoParticipante;


    @ExcelColumn("Email")
    private String emailParticipante;

    @ExcelColumn(value = "Fecha Operacion", dateFormat = UtilCommon.FORMATO_FECHA_DIA_MES_ANIO_2)
    private Date fechaOperacion;

    @ExcelColumn("Descripcion")
    private String descripcion;

    @ExcelColumn("Puntos")
    private String puntos;

    private Integer estadoParticipante;

    private String genero;

    public static ReporteParticipanteTransaccion getFromEntity(ParticipanteTransaccion participanteTransaccion) {
        if (participanteTransaccion == null) {
            return null;
        }

        return new ReporteParticipanteTransaccion()
                .setIdParticipante(participanteTransaccion.getParticipante().getIdParticipante())
                .setNroDocumento(participanteTransaccion.getParticipante().getNroDocumento())
                .setNombresParticipante(participanteTransaccion.getParticipante().getNombresParticipante())
                .setAppaternoParticipante(participanteTransaccion.getParticipante().getAppaternoParticipante())
                //.setApmaternoParticipante(participanteTransaccion.getParticipante().getApmaternoParticipante())
                .setEmailParticipante(participanteTransaccion.getParticipante().getEmailParticipante())
                .setGenero(participanteTransaccion.getParticipante().getGenero())
                .setNombreCatalogo(participanteTransaccion.getParticipante().getCatalogo().getNombreCatalogo())
                .setFechaOperacion(participanteTransaccion.getFechaOperacion())
                .setDescripcion(participanteTransaccion.getDescripcion())
                .setPuntos(participanteTransaccion.getValorPuntos().toString());
    }

    public static List<ReporteParticipanteTransaccion> getFromEntities(List<ParticipanteTransaccion> participanteTransaccions) {
        List<ReporteParticipanteTransaccion> list = new ArrayList<>();

        if (participanteTransaccions == null || participanteTransaccions.isEmpty()) {
            return list;
        }

        for (ParticipanteTransaccion participanteTransaccion : participanteTransaccions) {
            list.add(getFromEntity(participanteTransaccion));
        }

        return list;
    }

    private String estadoParticipanteEval() {
        if (this.estadoParticipante == 1) {
            return "ACTIVO";
        }
        return "INACTIVO";
    }

    private String generoEval() {
        if(this.genero == null){
            return "";
        }else if (this.genero.equals("M")) {
            return "Masculino";
        }else if(this.genero.equals("F")) {
            return "Femenino";
        }else{
            return "";
        }
    }
}
