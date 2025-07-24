package com.promotick.lafabril.web.service.impl;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.SMSRequest;
import com.promotick.apiclient.model.response.NetsuiteResponse;
import com.promotick.apiclient.service.ApiNetsuiteService;
import com.promotick.lafabril.common.ConstantesApi;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.UtilSMS;
import com.promotick.lafabril.model.util.bean.massend.MassendRequest;
import com.promotick.lafabril.model.util.bean.massend.MassendResponse;
import com.promotick.lafabril.model.web.*;
import com.promotick.lafabril.dao.admin.PedidoDao;
import com.promotick.lafabril.dao.web.DireccionDao;
import com.promotick.lafabril.dao.web.ParticipanteDao;
import com.promotick.lafabril.dao.web.ProductoDao;
import com.promotick.lafabril.dao.web.UbigeoDao;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.web.service.*;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ResultadoProcesoPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoProcesoServiceImpl extends BaseController implements PedidoProcesoService {

    private final DireccionDao direccionDao;
    private final PedidoDao pedidoDao;
    private final ParticipanteDao participanteDao;
    private final UbigeoDao ubigeoDao;
    private final EmailPedidoService emailPedidoService;
    private final ApiNetsuiteService apiNetsuiteService;
    private final EncuestaWebService encuestaWebService;
    private final ProductoDao productoDao;
    private final UsuarioPruebaWebService usuarioPruebaWebService;
    private final MensajeWebService mensajeWebService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResultadoProcesoPedido procesarPedido(Pedido pedido) throws Exception {

        //Registro direccionchecko
        this.registroDireccionPedido(pedido);

        //Registro Pedido
        this.registroPedido(pedido);

        //Registro de Detalle
        this.registroPedidoDetalle(pedido);

        //Actualizacion Stock
        this.actualizacionStock(pedido);

        //Envio a netsuite
        this.envioNetsuite(pedido); //test

        //Actualizacion respuesta netsuite
        this.actualizarPedidoNetsuite(pedido); //test

        //Registro de canje
        this.registroRegistrarCanje(pedido);

        //Registrar encuesta
        this.registrarEncuesta(pedido); //test

        //Envio de Email
        Integer puntosDisponibles = participanteDao.puntosDisponiblesParticipante(pedido.getParticipante().getIdParticipante());
        this.emailPedidoService.enviarEmailPedido(pedido, puntosDisponibles); //test

        //Envio de SMS
//        this.enviarSMS(pedido.getParticipante(), pedido); //test

        ResultadoProcesoPedido resultadoProcesoPedido = new ResultadoProcesoPedido();
        resultadoProcesoPedido.setIdPedido(pedido.getIdPedido());
        resultadoProcesoPedido.setPuntosDisponibles(puntosDisponibles);
        resultadoProcesoPedido.setPedido(pedido);

        return resultadoProcesoPedido;
    }


    /**
     * @param pedido
     * @throws Exception
     */
    private void registrarEncuesta(Pedido pedido) throws Exception {

        if(pedido.getEncuestaPedido() != null){
            pedido.getEncuestaPedido().setParticipante(pedido.getParticipante());
            pedido.getEncuestaPedido().setPedido(new Pedido().setIdPedido(pedido.getIdPedido()));
            encuestaWebService.registrarEncuestaRegular(pedido.getEncuestaPedido());
        }
    }


    /**
     * @param pedido
     * @throws Exception
     */
    private void registroDireccionPedido(Pedido pedido) throws Exception {
        pedido.getDireccion().setAuditoria(pedido.getAuditoria());
        Integer idDireccion = direccionDao.registrarDireccion(pedido.getDireccion());
        if (idDireccion == null || idDireccion <= 0) {
            throw new Exception("Error al registrar direccion de pedido: " + idDireccion);
        }
        pedido.getDireccion().setIdDireccion(idDireccion);
    }

    /**
     * @param pedido
     * @throws Exception
     */
    private void registroPedido(Pedido pedido) throws Exception {
        //Registro Pedido
        Integer idPedido = pedidoDao.registrarPedido(pedido);
        if (idPedido == null) {
            throw new Exception("Error al registrar el pedido: " + idPedido);
        }
        switch (idPedido) {
            case -50:
                throw new Exception("Error al Evaluacion de participante");
            case -60:
                throw new Exception("Participante esta deshabilitado");
        }

        if (idPedido <= 0) {
            throw new Exception("Error generico al registrar el pedido");
        }

        pedido.setIdPedido(idPedido);
    }

    /**
     * @param pedido
     * @throws Exception
     */
    private void registroPedidoDetalle(Pedido pedido) throws Exception {
        for (PedidoDetalle pedidoDetalle : pedido.getPedidoDetalles()) {
            pedidoDetalle.setIdPedido(pedido.getIdPedido());
            pedidoDetalle.setAuditoria(pedido.getAuditoria());
            Integer registroDetalle = pedidoDao.registrarDetallePedido(pedidoDetalle);
            if (registroDetalle == null || registroDetalle <= 0) {
                throw new Exception("Error al registrar el Detalle de pedido");
            }
            pedidoDetalle.setIdPedidoDetalle(registroDetalle);
        }
    }

    /**
     * @param pedido
     * @throws Exception -- * -1: No existe participante
     *                   -- * -2: Origen no valido (Canje normal: 2 | Canje manual: 3 | Canje de millas: 11)
     *                   -- * -3: No existen puntos disponibles para restarle
     *                   -- * -4: Error al registrar participante_transaccion
     */
    private void registroRegistrarCanje(Pedido pedido) throws Exception {
        Integer registrarCanje = pedidoDao.registrarCanje(pedido, UtilEnum.TIPO_ORIGEN.CANJE_NORMAL);
        if (registrarCanje == null) {
            throw new Exception("Error al registrar consumo de puntos");
        }

        switch (registrarCanje) {
            case -1:
                throw new Exception("Validacion consumo puntos - Participante no existe");
            case -2:
                throw new Exception("Validacion consumo puntos - Origen no valido");
            case -3:
                throw new Exception("Validacion consumo puntos - No existe puntos disponibles");
            case -4:
                throw new Exception("Validacion consumo puntos - Error al registrar transaccion");
        }
    }

    /**
     * @param pedido
     */
    private void envioNetsuite(Pedido pedido) {
        try {

            Pedido pedidoDB = pedidoDao.listarPedidoById(pedido.getIdPedido());
            if (pedidoDB == null) {
                throw new Exception("No se pudo extraer el pedido almacenado");
            }

            Ubigeo ubigeo = ubigeoDao.obtenerUbigeoID(pedidoDB.getDireccion().getUbigeo().getIdUbigeo());
            if (ubigeo == null) {
                throw new Exception("No se pudo extraer la informacion del ubigeo");
            }


            pedido.setFechaEntrega(pedidoDB.getFechaEntrega());
            pedido.setFechaPedido(pedidoDB.getFechaPedido());
            pedido.setDireccion(pedidoDB.getDireccion());
            pedido.getDireccion().setUbigeo(ubigeo);

            /*
            if(true){
                pedido.setProcesadoNetsuite(true);
                pedido.setNetsuiteResponse(new NetsuiteResponse().setIsSussess(true).setMensaje("Pedido de prueba, no se envia a netsuite"));
                return;
            }
            */
            if (!usuarioPruebaWebService.usuariosPrueba().contains(pedido.getParticipante().getNroDocumento())) {

                AbstractResponse<NetsuiteResponse> response = apiNetsuiteService.registerNetsuite(pedido.parseNetsuiteRequest());

                if (!response.isStatus()) {
                    pedido.setNetsuiteResponse(new NetsuiteResponse().setIsSussess(false).setMensaje(response.getMessage()));
                    pedido.setProcesadoNetsuite(false);
                    throw new Exception(response.getMessage());
                }

                NetsuiteResponse netsuiteResponse = response.getData();
                pedido.setNetsuiteResponse(netsuiteResponse);

                if (!netsuiteResponse.getIsSussess()) {
                    pedido.setProcesadoNetsuite(false);
                } else {
                    pedido.setProcesadoNetsuite(true);
                }

            } else {
                pedido.setProcesadoNetsuite(true);
                pedido.setNetsuiteResponse(new NetsuiteResponse().setIsSussess(true).setMensaje("Pedido de prueba, no se envia a netsuite"));
            }


        } catch (Exception e) {
            log.error("Error netsuite", e);
            pedido.setNetsuiteResponse(new NetsuiteResponse().setIsSussess(false).setMensaje(e.getMessage()));
            pedido.setProcesadoNetsuite(false);
        }
    }

    /**
     * @param pedido
     * @throws Exception
     */
    private void actualizarPedidoNetsuite(Pedido pedido) throws Exception {
        Integer actualizacion = pedidoDao.actualizarPedidoNetsuite(pedido);
        if (actualizacion == null || actualizacion <= 0) {
            throw new Exception("No se pudo actualizar la respuesta de Netsuite");
        }
    }

    /**
     * @param pedido
     * @throws Exception
     */
    private void actualizacionStock(Pedido pedido) throws Exception {
        for (PedidoDetalle pedidoDetalle : pedido.getPedidoDetalles()) {
            Integer actualizar = productoDao.actualizarStock(pedidoDetalle, pedido.getParticipante().getCatalogo(), pedidoDetalle.getProducto());
            if (actualizar == null) {
                throw new Exception("Ocurrio un error al actualizar stock");
            }

            switch (actualizar) {
                case -1:
                    throw new Exception("No se pudo actualizar stock de producto catalogo");
                case -2:
                    throw new Exception("No se pudo actualizar stock de producto");
                case -3:
                    throw new Exception("Stock producto catalogo excedido");
                case -4:
                    throw new Exception("Stock producto excedido");

            }
        }
    }

    private void enviarSMS(Participante participante, Pedido pedido) {
        try {

            if (org.apache.commons.lang3.StringUtils.isEmpty(participante.getMovilParticipante())) {
                log.error("SMS: El participante no tiene movil");
                return;
            }

//            Mensaje mensaje = mensajeWebService.obtenerMensajeXTipo(UtilEnum.TIPO_MENSAJE.CANJE_PRODUCTO.getTipo());
//            if (mensaje == null) {
//                log.error("SMS: Mensaje con tipo " + UtilEnum.TIPO_MENSAJE.CANJE_PRODUCTO.getTipo() + " no encontrado");
//                return;
//            }
//            SMSRequest smsRequest = new SMSRequest();
//            smsRequest.setMessage(UtilSMS.reemplazarMensaje(mensaje, participante, pedido, null));
//            apiSmsService.sendSmsAsync(sMSRequest);
            String url = properties.getProperty(ConstantesApi.SMS_URL);

            MassendRequest massendRequest = new MassendRequest();
            massendRequest.setUser(properties.getProperty(ConstantesApi.SMS_USER));
            massendRequest.setPass(properties.getProperty(ConstantesApi.SMS_PASS));
            massendRequest.setMensajeid(properties.getProperty(ConstantesApi.SMS_ID_CANJE));
            massendRequest.setCampana("");
            massendRequest.setTelefono(participante.getMovilParticipante());
            massendRequest.setTipo(properties.getProperty(ConstantesApi.SMS_TIPO_VARIABLE));
            massendRequest.setRuta(properties.getProperty(ConstantesApi.SMS_RUTA_INFORMATIVO));
            massendRequest.setDatos(participante.getAppaternoParticipante() + "," + (pedido !=null ? pedido.getIdPedido().toString() : " - ") + ",1800 CANJES");

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
//            headers.add("program", this.getApiProperties().getClient().getProgramID().toString());
            HttpEntity<MassendRequest> entity = new HttpEntity<>(massendRequest, headers);
            System.out.println("Request send sms-----> " + massendRequest.toString());

            ResponseEntity<MassendResponse> result = restTemplate.exchange(url, HttpMethod.POST,entity, MassendResponse.class );
            if (result.getBody().getRefError().getCod_error().equals(ConstantesCommon.CODIGO_SMS_EXITO))
                System.out.println("Respuesta-----> " + result.getBody().getRefEnvio().getMensaje());
            else
                System.out.println("Respuesta-----> " + result.getBody().getRefError().getErrorinfo());

        } catch (Exception e) {
            log.error("Error", e);
        }

    }

}
