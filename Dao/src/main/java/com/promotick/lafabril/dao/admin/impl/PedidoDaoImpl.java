package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.PedidoDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.definition.PedidoDaoDefinition;
import com.promotick.lafabril.dao.web.definition.PedidoDetalleDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.FiltroPedidoNetsuiteError;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PedidoDaoImpl extends DaoGenerator implements PedidoDao {

    private PedidoDetalleDaoDefinition pedidoDetalleDaoDefinition;
    private PedidoDaoDefinition pedidoDaoDefinition;

    @Autowired
    public PedidoDaoImpl(JdbcTemplate jdbcTemplate, PedidoDetalleDaoDefinition pedidoDetalleDaoDefinition, PedidoDaoDefinition pedidoDaoDefinition) {
        super(jdbcTemplate);
        this.pedidoDetalleDaoDefinition = pedidoDetalleDaoDefinition;
        this.pedidoDaoDefinition = pedidoDaoDefinition;
    }

    @Override
    public List<PedidoDetalle> listarDetallePedido(Integer idPedido) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PEDIDO_DETALLE_LISTAR)
                .addParameter("var_id_pedido", Types.INTEGER, idPedido)
                .setDaoDefinition(pedidoDetalleDaoDefinition)
                .build();

    }

    @Override
    public Integer registrarPedido(Pedido pedido) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PEDIDO_REGISTRAR)
                .addParameter("var_id_participante", Types.INTEGER, pedido.getParticipante().getIdParticipante())
                .addParameter("var_puntos_total", Types.INTEGER, pedido.getPuntosTotal())
                .addParameter("var_nombre_pedido", Types.VARCHAR, pedido.getNombrePedido())
                .addParameter("var_apellido_paterno_pedido", Types.VARCHAR, pedido.getApellidoPaternoPedido())
                .addParameter("var_apellido_materno_pedido", Types.VARCHAR, pedido.getApellidoMaternoPedido())
                .addParameter("var_email_pedido", Types.VARCHAR, pedido.getEmailPedido())
                .addParameter("var_telefono_pedido", Types.VARCHAR, pedido.getTelefonoPedido())
                .addParameter("var_movil_pedido", Types.VARCHAR, pedido.getMovilPedido())
                .addParameter("var_id_tipo_documento_pedido", Types.INTEGER, pedido.getIdTipoDocumentoPedido())
                .addParameter("var_nrodocumento_pedido", Types.VARCHAR, pedido.getNroDocumentoPedido())
                .addParameter("var_usuario_creacion", Types.VARCHAR, pedido.getAuditoria().getUsuarioCreacion())
                .addParameter("var_id_direccion", Types.INTEGER, pedido.getDireccion().getIdDireccion())
                .addParameter("var_codigo_descuento", Types.VARCHAR, pedido.getDescuento() != null ? pedido.getDescuento().getCodigoDescuento() : "")
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarDetallePedido(PedidoDetalle pedidoDetalle) {

        String campo;
        if (pedidoDetalle.getNroCelular() == null){
            campo = pedidoDetalle.getCorreo();
        }else{
            campo = pedidoDetalle.getNroCelular();
        }

        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PEDIDO_DETALLE_REGISTRAR)
                .addParameter("var_id_pedido", Types.INTEGER, pedidoDetalle.getIdPedido())
                .addParameter("var_id_producto", Types.INTEGER, pedidoDetalle.getProducto().getIdProducto())
                .addParameter("var_cantidad", Types.INTEGER, pedidoDetalle.getCantidad())
                .addParameter("var_puntos_unitario", Types.INTEGER, pedidoDetalle.getPuntosUnitario())
                .addParameter("var_estado_pedido_detalle", Types.INTEGER, pedidoDetalle.getEstadoPedidoDetalle())
                .addParameter("var_puntos_total", Types.INTEGER, pedidoDetalle.getPuntosTotal())
                .addParameter("var_usuario_creacion", Types.VARCHAR, pedidoDetalle.getAuditoria().getUsuarioCreacion())
                .addParameter("var_nro_celular", Types.VARCHAR, campo)
                .addParameter("var_nro_decodificador", Types.VARCHAR, pedidoDetalle.getNroDecodificador())
                .addParameter("var_color1", Types.VARCHAR, pedidoDetalle.getColor1())
                .addParameter("var_color2", Types.VARCHAR, pedidoDetalle.getColor2())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarCanje(Pedido pedido, UtilEnum.TIPO_ORIGEN origen) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PEDIDO_CANJE_REGISTRAR)
                .addParameter("var_id_participante", Types.INTEGER, pedido.getParticipante().getIdParticipante())
                .addParameter("var_id_pedido", Types.INTEGER, pedido.getIdPedido())
                .addParameter("var_origen", Types.INTEGER, origen.getCodigo())
                .addParameter("var_valorpuntaje", Types.INTEGER, pedido.getPuntosTotal())
                .addParameter("var_usuario_creacion", Types.VARCHAR, pedido.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Pedido listarPedidoById(Integer idPedido) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PEDIDO_LISTAR_BY_ID)
                .addParameter("var_id_pedido", Types.INTEGER, idPedido)
                .setDaoDefinition(pedidoDaoDefinition)
                .build(Pedido.class);
    }

    @Override
    public Integer actualizarPedidoNetsuite(Pedido pedido) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PEDIDO_NETSUITE_ACTUALIZAR)
                .addParameter("var_id_pedido", Types.INTEGER, pedido.getIdPedido())
                .addParameter("var_response", Types.VARCHAR, pedido.getNetsuiteResponse().getMensaje())
                .addParameter("var_procesado_netsuite", Types.BOOLEAN, pedido.getProcesadoNetsuite())
                .addParameter("var_errores", Types.VARCHAR, pedido.getNetsuiteResponse().getErrorList() != null && !pedido.getNetsuiteResponse().getErrorList().isEmpty() ? pedido.getNetsuiteResponse().getErrorList().stream().map(e -> "(" + e.getCodigo() + ") " + e.getDescripcion()).collect(Collectors.joining("|")) : null)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Pedido> pedidosNetsuiteErrorListar(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PEDIDO_NETSUITE_ERROR_LISTAR)
                .addParameter("var_orden", Types.INTEGER, filtroPedidoNetsuiteError.getOrden())
                .addParameter("var_buscar", Types.VARCHAR, filtroPedidoNetsuiteError.getBuscar())
                .addParameter("var_inicio", Types.INTEGER, filtroPedidoNetsuiteError.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroPedidoNetsuiteError.getLength())
                .setDaoDefinition(pedidoDaoDefinition)
                .build();
    }

    @Override
    public Integer pedidosNetsuiteErrorContar(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PEDIDO_NETSUITE_ERROR_CONTAR)
                .addParameter("var_orden", Types.INTEGER, filtroPedidoNetsuiteError.getOrden())
                .addParameter("var_buscar", Types.VARCHAR, filtroPedidoNetsuiteError.getBuscar())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarInfoPedidoNetsuite(Pedido pedido) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PEDIDO_NETSUITE_ACTUALIZAR)
                .addParameter("var_id_pedido", Types.INTEGER, pedido.getIdPedido())
                .addParameter("var_nro_documento", Types.VARCHAR, pedido.getNroDocumentoPedido())
                .addParameter("var_nombre_pedido", Types.VARCHAR, pedido.getNombrePedido())
                .addParameter("var_apellido_paterno_pedido", Types.VARCHAR, pedido.getApellidoPaternoPedido())
                .addParameter("var_apellido_materno_pedido", Types.VARCHAR, pedido.getApellidoMaternoPedido())
                .addParameter("var_email_pedido", Types.VARCHAR, pedido.getEmailPedido())
                .addParameter("var_telefono_pedido", Types.VARCHAR, pedido.getTelefonoPedido())
                .addParameter("var_movil_pedido", Types.VARCHAR, pedido.getMovilPedido())
                .addParameter("var_direccion_calle", Types.VARCHAR, pedido.getDireccion().getDireccionCalle())
                .addParameter("var_referencia", Types.VARCHAR, pedido.getDireccion().getReferencia())
                .addParameter("var_codpais", Types.VARCHAR, pedido.getDireccion().getUbigeo().getCodpais())
                .addParameter("var_coddep", Types.VARCHAR, pedido.getDireccion().getUbigeo().getCoddep())
                .addParameter("var_codprov", Types.VARCHAR, pedido.getDireccion().getUbigeo().getCodprov())
                .addParameter("var_coddist", Types.VARCHAR, pedido.getDireccion().getUbigeo().getCoddist())
                .addParameter("var_id_zona", Types.INTEGER, pedido.getDireccion().getZona().getIdZona())
                .addParameter("var_id_tipo_vivienda", Types.INTEGER, pedido.getDireccion().getTipoVivienda().getIdTipoVivienda())
                .addParameter("var_usuario_actualizacion", Types.VARCHAR, pedido.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Pedido> listarPedidosNetsuiteReenvio() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PEDIDO_NETSUITE_REENVIO)
                .setDaoDefinition(pedidoDaoDefinition)
                .build();
    }

    @Override
    public Integer validarDescuento(String descuento, Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.FN_PEDIDO_DESCUENTO_VALIDAR)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_codigo_descuento", Types.VARCHAR, descuento)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer obtenerMontoDescuento(Integer idDescuento, Integer montoTotal) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.FN_PEDIDO_DESCUENTO_OBTENER)
                .addParameter("var_id_descuento", Types.INTEGER, idDescuento)
                .addParameter("var_monto_total", Types.INTEGER, montoTotal)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
