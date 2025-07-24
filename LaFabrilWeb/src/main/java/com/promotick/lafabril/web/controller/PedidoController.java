package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.model.web.*;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.web.service.*;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import com.promotick.lafabril.web.util.ResultadoProcesoPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("pedido")
@RequiredArgsConstructor
public class PedidoController extends BaseController {

    private final ProductoWebService productoWebService;
    private final PedidoProcesoService pedidoProcesoService;
    private final PedidoWebService pedidoWebService;
    private final ParticipanteWebService participanteWebService;
    private final DireccionWebService direccionWebService;

    @GetMapping(value = "imprimir")
    public String imprimir(Model model) {
        String resultado = (String) Util.getSession().getAttribute(ConstantesSesion.SESSION_IMPRESION);
        model.addAttribute("html", resultado);
        return ConstantesWebView.VIEW_IMPRESION;
    }

    @ResponseBody
    @PostMapping(value = "registrarPedido")
    public PromotickResult registrarPedido(@RequestBody Pedido formPedido, Participante participanteSesion, Pedido pedido, Auditoria auditoria, PromotickResult promotickResult) {

        try {

            String mensajeStock = productoWebService.validarStock(pedido, participanteSesion.getCatalogo(), participanteSesion.getIdParticipante());
            if (mensajeStock != null) {
                throw new Exception(mensajeStock);
            }

            pedido.setParticipante(participanteSesion)
                    .setDireccion(formPedido.getDireccion())
                    .setNombrePedido(participanteSesion.getNombresParticipante())
                    .setApellidoPaternoPedido(participanteSesion.getAppaternoParticipante())
                    .setApellidoMaternoPedido(participanteSesion.getApmaternoParticipante())
                    .setEmailPedido(participanteSesion.getEmailParticipante())
                    .setTelefonoPedido(participanteSesion.getTelefonoParticipante())
                    .setMovilPedido(participanteSesion.getMovilParticipante())
                    .setNroDocumentoPedido(participanteSesion.getNroDocumento())
                    .setEncuestaPedido(formPedido.getEncuestaPedido())
                    .setIdTipoDocumentoPedido(participanteSesion.getTipoDocumento().getIdTipoDocumento());
            pedido.setAuditoria(auditoria);

            if (pedido.getPedidoDetalles().size()< 1){
                throw new Exception("Error al canjear el producto, por favor vuelva a intentarlo");
            }

            for (PedidoDetalle detalles : pedido.getPedidoDetalles()) {
                ProductoCatalogo productoCatalogo = productoWebService.obtenerProductoID(participanteSesion.getCatalogo().getIdCatalogo(), detalles.getProducto().getIdProducto(), participanteSesion.getIdParticipante());
                this.evaluarProducto(productoCatalogo);

                Integer puntosUnitario = productoCatalogo.getProducto().getPuntosProducto();
                Integer totaldet = puntosUnitario * detalles.getCantidad();
                detalles.setPuntosUnitario(puntosUnitario);
                detalles.setPuntosTotal(totaldet);
                detalles.setAuditoria(auditoria);
                detalles.setEstadoPedidoDetalle(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }

            this.recalcularTotal(pedido);

            ResultadoProcesoPedido resultadoProcesoPedido = pedidoProcesoService.procesarPedido(pedido);
            promotickResult.setMessage("Su Pedido fue creado correctamente");
            promotickResult.setExtra1(resultadoProcesoPedido.getPuntosDisponibles());
            promotickResult.setData(resultadoProcesoPedido.getPedido());

            Util.getSession().setAttribute(ConstantesSesion.SESSION_PEDIDO_GRACIAS, pedido);
            Util.getSession().setAttribute(ConstantesSesion.SESSION_PEDIDO, new Pedido());
            Util.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, participanteWebService.loginParticipante(participanteSesion.getTipoDocumento().getIdTipoDocumento(), participanteSesion.getNroDocumento()));
        } catch (Exception e) {
            promotickResult.setException(e);
        }

        return promotickResult;
    }

    private void evaluarProducto(ProductoCatalogo productoCatalogo) throws Exception {
        if (productoCatalogo == null) {
            throw new Exception("Uno de los productos no esta disponible actualmente");
        }

        productoCatalogo.evaluar();
    }

    private void recalcularTotal(Pedido pedidoSession) {
        Integer total = pedidoSession.getPedidoDetalles().stream()
                .map(PedidoDetalle::getPuntosTotal)
                .reduce(0, Integer::sum);

        Descuento descuento = (Descuento) Util.getSession().getAttribute(ConstantesSesion.SESSION_DESCUENTO);
        Integer montoDescuento = 0;
        if(descuento != null){
            if(descuento.getIdDescuento() != null && descuento.getIdDescuento() > 0){
                pedidoSession.setDescuento(new Descuento()
                        .setCodigoDescuento(descuento.getCodigoDescuento())
                        .setPuntosDescuento(pedidoWebService.obtenerMontoDescuento(descuento.getIdDescuento(), pedidoSession.getPuntosTotal())));
                montoDescuento = pedidoSession.getDescuento().getPuntosDescuento();
            }
        }

        pedidoSession.setPuntosTotal(total - montoDescuento);
    }

    @ResponseBody
    @PostMapping("registrarDireccion")
    public PromotickResult registrarDireccion(@RequestBody ParticipanteDireccion participanteDireccion, PromotickResult promotickResult, Participante participante, Auditoria auditoria) {
        try {
            participanteDireccion.setIdParticipante(participante.getIdParticipante());
            participanteDireccion.getDireccion().setAuditoria(auditoria);
            boolean esRegistro = participanteDireccion.getIdParticipanteDireccion() != null;

            Integer registro = direccionWebService.registrarDireccionParticipante(participanteDireccion);
            if (registro == null || registro <= 0) {
                throw new Exception(esRegistro ? "No se pudo registrar la direccion" : "No se pudo actualizar la direccion");
            }

            promotickResult.setMessage(esRegistro ? "Se actualizo la direccion exitosamente" : "Se registro la direccion exitosamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping("eliminarDireccion/{idParticipanteDireccion}")
    public PromotickResult registrarDireccion(@PathVariable Integer idParticipanteDireccion, PromotickResult promotickResult, Participante participante) {
        try {

            Integer registro = direccionWebService.eliminarDireccionParticipante(participante.getIdParticipante(), idParticipanteDireccion);
            if (registro == null || registro <= 0) {
                throw new Exception("No se pudo eliminar la direccion");
            }

            promotickResult.setMessage("Se elimino la direccion exitosamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value="validarDescuento")
    public PromotickResult validarDescuento(@RequestBody Descuento descuento, PromotickResult promotickResult, Participante participante, Pedido pedido){
        try{
            Integer idDescuento = pedidoWebService.validarDescuento(descuento.getCodigoDescuento(), participante.getIdParticipante());
            if(idDescuento != null && idDescuento > 0){
                Integer montoDescuento = pedidoWebService.obtenerMontoDescuento(idDescuento, pedido.getPuntosTotal());
                Util.getSession().setAttribute(ConstantesSesion.SESSION_DESCUENTO, new Descuento()
                        .setCodigoDescuento(descuento.getCodigoDescuento())
                        .setIdDescuento(idDescuento)
                        .setPuntosDescuento(montoDescuento));
            }else{
                Util.getSession().setAttribute(ConstantesSesion.SESSION_DESCUENTO,new Descuento());
                promotickResult.setStatus(Boolean.FALSE);
            }
        }catch (Exception e){
            promotickResult.setException(e);
        }

        return promotickResult;
    }

    @ResponseBody
    @GetMapping(value = "removerDescuento")
    public PromotickResult removerDescuento(PromotickResult promotickResult) {
        try{
            Util.getSession().setAttribute(ConstantesSesion.SESSION_DESCUENTO, null);
        } catch (Exception e) {
            promotickResult.setException(e);
        }

        return promotickResult;
    }

}
