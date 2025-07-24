package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-participantes-error", sheetName = "Carga Participantes Error", error = true)
public class FormBulkParticipantes implements Serializable {
    private static final long serialVersionUID = -2427232274280995678L;

    private CargaProceso cargaProceso;

    @ExcelColumn("Fecha Info")
    private String fechaInfo;

    @ExcelColumn("Tipo de Documento")
    private String tipoDocumento;

    @ExcelColumn("Cli Ruc")
    private String cliRuc;

    @ExcelColumn("Nombres")
    private String nombres;

    @ExcelColumn("Apellido paterno")
    private String apPaterno;

    @ExcelColumn("Apellido materno")
    private String apMaterno;

    @ExcelColumn("Telefono")
    private String telefono;

    @ExcelColumn("movil")
    private String movil;

    @ExcelColumn("email")
    private String email;

    @ExcelColumn("Meta Trimestral")
    private String metaTrimestral;

    @ExcelColumn("Meta Anual")
    private String metaAnual;

    @ExcelColumn("Razon social")
    private String razonSocial;

    @ExcelColumn("Cli codigo")
    private String clicodigo;

    @ExcelColumn("Cliente vencido")
    private String clienteVencido;

    @ExcelColumn("Motivo rechazo")
    private String motivoRechazo;

}
