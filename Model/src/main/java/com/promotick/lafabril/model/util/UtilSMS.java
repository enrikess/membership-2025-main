package com.promotick.lafabril.model.util;

import com.promotick.lafabril.model.web.Mensaje;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Pedido;

public class UtilSMS {

    public static String reemplazarMensaje(Mensaje mensaje, Participante participante, Pedido pedido, Integer puntosCargados) {
        String sms = mensaje.getMensaje();
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.NOMBRE.getMensajes(),participante.getNombresParticipante());
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.CELULAR.getMensajes(),participante.getMovilParticipante());
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.PUNTOS_CARGA.getMensajes(),puntosCargados != null ? puntosCargados.toString() :" - " );
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.PUNTOS.getMensajes(),(participante.getPuntosDisponibles()!=null ? participante.getPuntosDisponibles().toString() :" - " ));
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.APELLIDOS.getMensajes(),participante.getAppaternoParticipante());
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.CORREO.getMensajes(),participante.getEmailParticipante());
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.TELEFONO.getMensajes(),participante.getTelefonoParticipante());
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.TIPO_DOCUMENTO.getMensajes(),( participante.getTipoDocumento() != null? participante.getTipoDocumento().toString():" - "));
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.NUMERO_DOCUMENTO.getMensajes(),participante.getNroDocumento());
        sms = reemplazarPalabra(sms, UtilEnum.MENSAJES.PEDIDO.getMensajes(),(pedido !=null ? pedido.getIdPedido().toString() : " - "));
        return sms;
    }

    public static String reemplazarPalabra(String texto, String palabraParaReemplazar, String palabraQueReemplaza){
        if(texto.contains(palabraParaReemplazar)){
            texto = texto.replaceAll(palabraParaReemplazar,palabraQueReemplaza);
        }
        return texto;
    }
}
