package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.web.EncuestaDetalle;
import com.promotick.lafabril.common.UtilCommon;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ExcelClass(filename = "Reporte-encuestas", sheetName = "Reporte de Encuestas")
public class ReporteResultadoEncuesta implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("ID Participante")
    private Integer idParticipante;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Nombres")
    private String nombresParticipante;

    @ExcelColumn("Apellido Paterno")
    private String appaternoParticipante;

    @ExcelColumn("Email")
    private String emailParticipante;

    @ExcelColumn("Id Pedido")
    private String idPedido;

    @ExcelColumn("Id Encuesta")
    private String idEncuesta;

    @ExcelColumn("Tipo Encuesta")
    private String nombreTipoEncuesta;

    @ExcelColumn(value = "Pregunta", methodEvaluate = "preguntaEval")
    private String pregunta;

    @ExcelColumn(value ="Respuesta", methodEvaluate = "respuestaEval")
    private String respuesta;

    @ExcelColumn(value = "Fecha Respuesta", dateFormat = UtilCommon.FORMATO_FECHA_DIA_MES_ANIO_HORA)
    private Date fechaRespuesta;

    private String preguntaTags;

    private String respuestaTags;

    public static ReporteResultadoEncuesta getFromEntity(EncuestaDetalle encuestaDetalle) {
        if (encuestaDetalle == null) {
            return null;
        }

        return new ReporteResultadoEncuesta()
                .setIdParticipante(encuestaDetalle.getEncuesta().getParticipante().getIdParticipante())
                .setNroDocumento(encuestaDetalle.getEncuesta().getParticipante().getNroDocumento())
                .setNombresParticipante(encuestaDetalle.getEncuesta().getParticipante().getNombresParticipante())
                .setAppaternoParticipante(encuestaDetalle.getEncuesta().getParticipante().getAppaternoParticipante())
                .setEmailParticipante(encuestaDetalle.getEncuesta().getParticipante().getEmailParticipante())
                .setIdEncuesta(encuestaDetalle.getEncuesta().getIdEncuesta().toString())
                .setPreguntaTags(encuestaDetalle.getPregunta())
                .setNombreTipoEncuesta(encuestaDetalle.getEncuesta().getNombreTipoEncuesta())
                .setRespuestaTags(encuestaDetalle.getRespuesta())
                .setIdPedido(encuestaDetalle.getEncuesta().getPedido().getIdPedido() == null ? "" : encuestaDetalle.getEncuesta().getPedido().getIdPedido().toString())
                .setFechaRespuesta(encuestaDetalle.getAuditoria().getFechaCreacion());
    }

    public static List<ReporteResultadoEncuesta> getFromEntities(List<EncuestaDetalle> encuestaDetalles) {
        List<ReporteResultadoEncuesta> list = new ArrayList<>();

        if (encuestaDetalles == null || encuestaDetalles.isEmpty()) {
            return list;
        }

        for (EncuestaDetalle encuestaDetalle : encuestaDetalles) {
            list.add(getFromEntity(encuestaDetalle));
        }

        return list;
    }

    private String preguntaEval() {
        if(this.preguntaTags == null){
            return "";
        }else{
            return UtilCommon.removeHTMLTags(preguntaTags);
        }
    }

    private String respuestaEval() {
        if(this.respuestaTags == null){
            return "";
        }else{
            return UtilCommon.removeHTMLTags(respuestaTags);
        }
    }


}
