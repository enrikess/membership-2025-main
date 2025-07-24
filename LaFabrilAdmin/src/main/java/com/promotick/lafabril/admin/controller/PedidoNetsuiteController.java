package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.response.NetsuiteResponse;
import com.promotick.apiclient.service.ApiNetsuiteService;
import com.promotick.lafabril.admin.service.DireccionAdminService;
import com.promotick.lafabril.admin.service.PedidoAdminService;
import com.promotick.lafabril.admin.service.UbigeoAdminService;
import com.promotick.lafabril.admin.service.UsuarioPruebaAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.Ubigeo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Controller
@RequestMapping("pedidos/netsuite")
public class PedidoNetsuiteController {

    private PedidoAdminService pedidoAdminService;
    private UbigeoAdminService ubigeoAdminService;
    private UsuarioPruebaAdminService usuarioPruebaAdminService;
    private ApiNetsuiteService apiNetsuiteService;
    private DireccionAdminService direccionAdminService;

    @Autowired
    public PedidoNetsuiteController(PedidoAdminService pedidoAdminService, UbigeoAdminService ubigeoAdminService, UsuarioPruebaAdminService usuarioPruebaAdminService, ApiNetsuiteService apiNetsuiteService, DireccionAdminService direccionAdminService) {
        this.pedidoAdminService = pedidoAdminService;
        this.ubigeoAdminService = ubigeoAdminService;
        this.usuarioPruebaAdminService = usuarioPruebaAdminService;
        this.apiNetsuiteService = apiNetsuiteService;
        this.direccionAdminService = direccionAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_PEDIDOS_NETSUITE;
    }

    @GetMapping("{idPedido}")
    public String detalle(@PathVariable Integer idPedido, Model model) {
        try {
            Pedido pedido = pedidoAdminService.listarPedidoById(idPedido);
            this.validatePedido(pedido);
            model.addAttribute("pedido", pedido);
            model.addAttribute("provinciaList", ubigeoAdminService.listarProvincias(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP));

            if (!StringUtils.isEmpty(pedido.getDireccion().getUbigeo().getCodprov())) {
                model.addAttribute("ciudadList", ubigeoAdminService.listarDistritos(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP, pedido.getDireccion().getUbigeo().getCodprov()));
            }
            model.addAttribute("zonaList", direccionAdminService.listarZonas());
            model.addAttribute("tipoViviendaList", direccionAdminService.listarTipoViviendas());
        } catch (Exception e) {
            log.error("Error validacion de pedido rebotado", e);
            return "redirect:/pedidos/netsuite";
        }

        return ConstantesAdminView.VIEW_PEDIDOS_NETSUITE_DETALLE;
    }

    @GetMapping("listarDetallePedido/{idPedido}")
    public String listarDetallePedido(@PathVariable Integer idPedido, Model model) {
        model.addAttribute("pedidoDetalles", pedidoAdminService.listarDetallePedido(idPedido));
        return ConstantesAdminView.VIEW_FRAGMENTS_NETSUITE_DETALLE_REBOTE;
    }

    @ResponseBody
    @PostMapping("listarPedidosNetsuite")
    public Datatable listarPedidosNetsuite(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError) {
        Datatable datatable = new Datatable();
        Integer conteo = pedidoAdminService.pedidosNetsuiteErrorContar(filtroPedidoNetsuiteError);
        datatable.setRecordsFiltered(conteo);
        datatable.setRecordsTotal(conteo);
        datatable.setData(pedidoAdminService.pedidosNetsuiteErrorListar(filtroPedidoNetsuiteError));
        return datatable;
    }

    @ResponseBody
    @PostMapping("actualizarPedido")
    public PromotickResult actualizarPedido(@RequestBody Pedido pedido, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            pedido.setAuditoria(auditoria);
            Integer result = pedidoAdminService.actualizarInfoPedidoNetsuite(pedido);
            UtilEnum.ACTUALIZACION_PEDIDO_NETSUITE actualizacionPedidoNetsuite = UtilEnum.ACTUALIZACION_PEDIDO_NETSUITE.getMensageFromCode(result);
            if (!actualizacionPedidoNetsuite.equals(UtilEnum.ACTUALIZACION_PEDIDO_NETSUITE.OK)) {
                throw new Exception(actualizacionPedidoNetsuite.getMensaje());
            }

            promotickResult.setMessage("El pedido fue actualizado correctamente, puede hacer el reenvio a netsuite desde la lista de pedidos.");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping("reenvioNetsuite/{idPedido}")
    public PromotickResult reenvioNetsuite(@PathVariable Integer idPedido, PromotickResult promotickResult) {
        Pedido pedido = null;
        try {
            pedido = pedidoAdminService.listarPedidoById(idPedido);

            this.validatePedido(pedido);

            if (!usuarioPruebaAdminService.usuariosPrueba().contains(pedido.getParticipante().getNroDocumento())) {

                AbstractResponse<NetsuiteResponse> response = apiNetsuiteService.registerNetsuite(pedido.parseNetsuiteRequest());

                if (!response.isStatus()) {
                    pedido.setNetsuiteResponse(new NetsuiteResponse().setIsSussess(false).setMensaje(response.getMessage()));
                    pedido.setProcesadoNetsuite(false);
                    throw new Exception(response.getMessage());
                }

                NetsuiteResponse netsuiteResponse = response.getData();
                pedido.setNetsuiteResponse(netsuiteResponse);

                if (netsuiteResponse != null) {
                    NetsuiteResponse.Error pedidoDuplicado = null;

                    if (netsuiteResponse.getErrorList() != null) {
                        pedidoDuplicado = netsuiteResponse.getErrorList().stream()
                                .filter(err -> err.getCodigo().equals(ConstantesCommon.CODIGO_PEDIDO_DUPLICADO)) // Pedido duplicado
                                .findAny()
                                .orElse(null);
                    }

                    if (pedidoDuplicado == null && !netsuiteResponse.getIsSussess()) {
                        pedido.setProcesadoNetsuite(false);
                        throw new Exception(netsuiteResponse.getMensaje());
                    }

                    pedido.setProcesadoNetsuite(true);
                    promotickResult.setMessage(netsuiteResponse.getMensaje());
                }

            } else {
                pedido.setProcesadoNetsuite(true);
                pedido.setNetsuiteResponse(new NetsuiteResponse().setIsSussess(true).setMensaje("Pedido de prueba, no se envia a netsuite"));
                throw new Exception(pedido.getNetsuiteResponse().getMensaje());
            }
        } catch (Exception e) {
            promotickResult.setException(e);
            promotickResult.setMessage(promotickResult.getMessage() + " Por favor revise la informacion del Pedido");
        } finally {
            if (pedido != null) {
                pedidoAdminService.actualizarPedidoNetsuite(pedido);
            }
        }
        return promotickResult;
    }

    private void validatePedido(Pedido pedido) throws Exception {
        if (pedido == null) {
            throw new Exception("El pedido no se encuentra registrado");
        }

        if (pedido.getProcesadoNetsuite()) {
            throw new Exception("El pedido ya se encuentra procesado");
        }

        Ubigeo ubigeo = ubigeoAdminService.obtenerUbigeoID(pedido.getDireccion().getUbigeo().getIdUbigeo());
        if (ubigeo == null) {
            throw new Exception("No se pudo extraer la informacion del ubigeo");
        }
        pedido.getDireccion().setUbigeo(ubigeo);
        pedido.setPedidoDetalles(pedidoAdminService.listarDetallePedido(pedido.getIdPedido()));
    }
}
