package com.promotick.membership.model.web;

import com.promotick.membership.model.util.BeanBase;
import lombok.Data;


@Data
public class TxPasarela extends BeanBase {

	private static final long serialVersionUID = 8934628996423307687L;

	/*
	 * id_paquete, estado_tx, id_participante, merchant_id, reference_code,
	 * description, amount, signature, account_id, currency, buyer_full_name,
	 * buyer_email, telephone, fecha_registro, fecha_proceso
	 */

	private Integer idTxPasarela;
	private Paquete paquete;
	private String ipClient;
	private String estadoTx;
	private Participante participante;
	private String tarjeta;
	private Integer merchantId;
	private String referenceCode;
	private String description;
	private Double amount;
	private String signature;
	private Integer accountId;
	private String currency;
	private String buyerFullName;
	private String buyerEmail;
	private String telephone;
	private String result;
	private String resultDetail;

}
