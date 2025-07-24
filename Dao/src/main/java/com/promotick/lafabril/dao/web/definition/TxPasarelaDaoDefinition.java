package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.TxPasarela;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class TxPasarelaDaoDefinition extends DaoDefinition<TxPasarela> {

	private PaqueteDaoDefinition paqueteDaoDefinition;
	private ParticipanteDaoDefinition participanteDaoDefinition;
	
	public TxPasarelaDaoDefinition(PaqueteDaoDefinition paqueteDaoDefinition, ParticipanteDaoDefinition participanteDaoDefinition) {
		super(TxPasarela.class);
		this.paqueteDaoDefinition = paqueteDaoDefinition;
		this.participanteDaoDefinition = participanteDaoDefinition;
	}

	@Override
	public TxPasarela mapRow(ResultSet rs, int rowNumber) throws SQLException {
		TxPasarela txPasarela = super.mapRow(rs, rowNumber);
		txPasarela.setPaquete(paqueteDaoDefinition.mapRow(rs, rowNumber));
		txPasarela.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
		return txPasarela;
	}

}