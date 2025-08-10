package com.promotick.membership.model.web;


import com.promotick.membership.model.util.BeanBase;
import com.promotick.membership.common.UtilCommon;
import lombok.Data;

import java.util.Date;

@Data
public class Participante extends BeanBase {

    private static final long serialVersionUID = 8741047251120089858L;

    private Integer idParticipante;
    private TipoDocumento tipoDocumento;
    private Catalogo catalogo;
    private RazonSocial tipoRazonSocial;
    private Region region;
    private Regional regional;
    private Direccion direccion;
    private Direccion direccionCasa;
    private Direccion direccionOficina;
    private Direccion direccionCasaCampo;
    private Direccion direccionCasaPlaya;
    private Direccion direccionOtro;
    private String usuarioParticipante;
    private String claveParticipante;
    private String nroDocumento;
    private String nombresParticipante;
    private String appaternoParticipante;
    private String apmaternoParticipante;
    private String emailParticipante;
    private String telefonoParticipante;
    private String movilParticipante;
    private String ciudad;
    private Integer estadoParticipante;
    private Boolean actualizarDatos;
    private String genero;
    private Date fechaNacimiento;
    private Boolean aceptaTyc;
    private Boolean aceptaUsoDatos;
    private Boolean aceptaComunicacion;
    private Date fechaAceptacion;
    private String cedula;
    private Boolean estadoCanjes;
    private String razonSocial;
    private String codCliente;
    private Boolean emailEnviado;
    private String emailObservacion;
    private String madrePadre;
    private Integer nroHijos;
    private String estadoCivil;
    private Concesionario concesionario;
    private String hobby;
    private Integer idTipoParticipante;
    private Integer metaAnual;
    private String nombreVendedor;
    private SubtipoParticipante subtipoParticipante;
    private String promocion;
    private String rucComercial;
    private String clasificacion;
    private String cliente;
    private String representante;
    private Integer idCategoriaParticipante;
    private Petshop petshop;
    private String foto;
    private String canal;
    private String provincia;
    private Date fechaAniversarioLocal;

    //Temp
    private Integer puntosDisponibles;
    private String fechaNacimientoString;
    private String fechaRegistroString;
    private String claveAnterior;
//    private TransaccionToken transaccionToken;
    private Integer puntosPosibles;
    private Double periodoMeta;
    private Integer productosNuevos;
    private String posicion;
    private Boolean aprobar;
    private String emailNuevo;
    private Integer validaPasarela;
    private String fechaAniversarioLocalString;


    public String getFechaRegistroString() {
        if (getAuditoria().getFechaCreacion() != null) {
            fechaRegistroString = UtilCommon.dateFormat(getAuditoria().getFechaCreacion(), UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaRegistroString;
    }
}
