package com.promotick.lafabril.model.web;

import com.promotick.apiclient.model.request.NetsuiteItemRequest;
import com.promotick.apiclient.model.request.NetsuiteRequest;
import com.promotick.apiclient.model.response.NetsuiteResponse;
import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.common.UtilCommon;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class Pedido extends BeanBase {
    private static final long serialVersionUID = -3223526575095331647L;

    private Integer idPedido;
    private Participante participante;
    private String nombrePedido;
    private String apellidoPaternoPedido;
    private String apellidoMaternoPedido;
    private String emailPedido;
    private String telefonoPedido;
    private String movilPedido;
    private Integer idTipoDocumentoPedido;
    private String nroDocumentoPedido;
    private Direccion direccion;
    private Date fechaPedido;
    private Integer puntosTotal = 0;
    private Date fechaEntrega;
    private Integer estadoPedido;
    private String registroNs;
    private TipoDocumento tipoDocumento;
    private Boolean procesadoNetsuite;
    private String erroresNetsuite;
    private Boolean pedidoManual;
    private Descuento descuento;

    //Temp
    private List<PedidoDetalle> pedidoDetalles = new LinkedList<>();
    private int totalItems;
    private Encuesta encuestaPedido; //se utiliza para el precanje
    private String fechaEntregaString;
    private String fechaPedidoString;
    private NetsuiteResponse netsuiteResponse;

    public String getFechaPedidoString() {
        if (fechaPedido != null) {
            fechaPedidoString = UtilCommon.dateFormat(fechaPedido, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaPedidoString;
    }

    public String getFechaEntregaString() {
        if (fechaEntrega != null) {
            fechaEntregaString = UtilCommon.dateFormat(fechaEntrega, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaEntregaString;
    }

    public int getTotalItems() {
        if (pedidoDetalles != null) {
            totalItems = pedidoDetalles.size();
        }
        return totalItems;
    }

    public NetsuiteRequest parseNetsuiteRequest() {
        return new NetsuiteRequest()
                .setAmericanExpress(null)
                .setCredibank(null)
                .setCodigoAutorizacion(null)
                .setFechaVencimientoAmericanExpress(null)
                .setFechaVencimientoCredibank(this.evaluarColores(this.pedidoDetalles))
                .setTelefonoOficina(null)
                .setIdConfiguracion(this.participante.getCatalogo().getIdCatalogoNetsuite())
                .setNombre(this.nombrePedido)
                .setApellidoPaterno(this.apellidoPaternoPedido)
                .setApellidoMaterno(this.apellidoMaternoPedido)
                .setCorreo(this.emailPedido)
                .setTelefonoCelular(this.movilPedido)
                .setTelefonoFijo(this.telefonoPedido)
                .setNroDocumento(this.nroDocumentoPedido)
                .setDepartamento(this.getDireccion().getUbigeo().getDepartamento())
                .setProvincia(this.getDireccion().getUbigeo().getProvincia())
                .setDistrito(this.getDireccion().getUbigeo().getDistrito())
                .setDireccion(this.direccion.getDireccionCalle())
                .setReferencia(this.evaluarRecarga(pedidoDetalles, this))
                .setFechaEntregaObject(this.fechaEntrega)
                .setFechaPedidoObject(this.fechaPedido)
                .setIdPedidoWeb(String.valueOf(this.idPedido))
                .setNombreCatalogo(this.getParticipante().getCatalogo().getNombreCatalogo())
                .setPuntos(this.puntosTotal)
                .setListaItems(this.parseItemRequest(this.pedidoDetalles));
    }

    private List<NetsuiteItemRequest> parseItemRequest(List<PedidoDetalle> pedidoDetalles) {
        List<NetsuiteItemRequest> netsuiteItemRequests = new LinkedList<>();
        int nroItem = 0;

        for (PedidoDetalle pedidoDetalle : pedidoDetalles) {
            nroItem++;
            netsuiteItemRequests.add(
                    new NetsuiteItemRequest()
                            .setCantidad(pedidoDetalle.getCantidad())
                            .setCategoria(pedidoDetalle.getProducto().getCategoria().getNombreCategoria())
                            .setCodigoProducto(pedidoDetalle.getProducto().getCodigoWeb())
                            .setDescripcion(pedidoDetalle.getProducto().getNombreProducto())
                            .setInternalIdProducto(pedidoDetalle.getProducto().getIdNetsuite())
                            .setNroItem(nroItem)
                            .setPuntos(pedidoDetalle.getPuntosTotal())
            );
        }

        return netsuiteItemRequests;
    }

    private String evaluarColores(List<PedidoDetalle> pedidoDetalles) {
        List<String> colores = new LinkedList<>();
        for (PedidoDetalle pd : pedidoDetalles) {
            if (pd.getProducto().getTipoProducto().isColores()) {
                colores.add(pd.getProducto().getCodigoWeb() + ": " + pd.getColor1() + (!StringUtils.isEmpty(pd.getColor2()) ? " - " + pd.getColor2() : ""));
            }
        }

        if (!colores.isEmpty()) {
            return String.format("COLORES(%s)", StringUtils.join(colores, ", "));
        }

        return null;
    }

    private String evaluarRecarga(List<PedidoDetalle> pedidoDetalles, Pedido pedido) {

        List<String> recargaCelular = new LinkedList<>();
        List<String> recargaTV = new LinkedList<>();
        List<String> correo = new LinkedList<>();
        boolean hasRecargaCelular = false;
        boolean hasCorreo = false;

        for (PedidoDetalle pd : pedidoDetalles) {
            if (pd.getProducto().getTipoProducto().isRecargaCelular()) {
                recargaCelular.add(pd.getProducto().getCodigoWeb() + ": " + pd.getNroCelular());
            } else if (pd.getProducto().getTipoProducto().isRecargaTv()) {
                recargaTV.add(pd.getProducto().getCodigoWeb() + ": " + pd.getNroDecodificador());
            }else if (pd.getProducto().getTipoProducto().isCorreo()) {
                correo.add(pd.getProducto().getCodigoWeb() + ": " + pd.getCorreo());
            }
        }

        String result = pedido.getDireccion().getReferencia();

        if (!recargaCelular.isEmpty() || !recargaTV.isEmpty() || !correo.isEmpty()) {
            result += " | ";
        }

        if (!recargaCelular.isEmpty()) {
            hasRecargaCelular = true;
            result += String.format("Nro(cel) a recargar:(%s)", StringUtils.join(recargaCelular, ", "));
        }

        if (!correo.isEmpty()) {
            if (hasRecargaCelular) {
                result += " | ";
            }
            hasCorreo = true;
            result += String.format(" Correo:(%s)", StringUtils.join(correo, ", "));
        }

        if (!recargaTV.isEmpty()) {
            if (hasRecargaCelular || hasCorreo) {
                result += " | ";
            }
            result += String.format(" Nro(TV) a recargar:(%s)", StringUtils.join(recargaTV, ", "));
        }

        return result;
    }
}
