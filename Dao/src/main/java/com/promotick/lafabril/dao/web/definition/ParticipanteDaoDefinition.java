package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Direccion;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ParticipanteDaoDefinition extends DaoDefinition<Participante> {

    private CatalogoDaoDefinition catalogoDaoDefinition;
    private BrokerDaoDefinition brokerDaoDefinition;
    private RegionalDaoDefinition regionalDaoDefinition;
    private DireccionDaoDefinition direccionDaoDefinition;
    private AuditoriaDaoDefinition auditoriaDaoDefinition;
    private RazonSocialDaoDefinition razonSocialDaoDefinition;
    private TipoDocumentoDaoDefinition tipoDocumentoDaoDefinition;
    private ConcesionarioDaoDefinition concesionarioDaoDefinition;
    private SubtipoParticipanteDaoDefinition subtipoParticipanteDaoDefinition;
    private RegionDaoDefinition regionDaoDefinition;

    @Autowired
    public ParticipanteDaoDefinition(RegionDaoDefinition regionDaoDefinition,RazonSocialDaoDefinition razonSocialDaoDefinition,CatalogoDaoDefinition catalogoDaoDefinition, DireccionDaoDefinition direccionDaoDefinition, AuditoriaDaoDefinition auditoriaDaoDefinition, TipoDocumentoDaoDefinition tipoDocumentoDaoDefinition, ConcesionarioDaoDefinition concesionarioDaoDefinition, SubtipoParticipanteDaoDefinition subtipoParticipanteDaoDefinition, BrokerDaoDefinition brokerDaoDefinition, RegionalDaoDefinition regionalDaoDefinition) {
        super(Participante.class);
        this.catalogoDaoDefinition = catalogoDaoDefinition;
        this.razonSocialDaoDefinition = razonSocialDaoDefinition;
        this.regionDaoDefinition = regionDaoDefinition;
        this.direccionDaoDefinition = direccionDaoDefinition;
        this.auditoriaDaoDefinition = auditoriaDaoDefinition;
        this.tipoDocumentoDaoDefinition = tipoDocumentoDaoDefinition;
        this.concesionarioDaoDefinition = concesionarioDaoDefinition;
        this.subtipoParticipanteDaoDefinition = subtipoParticipanteDaoDefinition;
        this.brokerDaoDefinition = brokerDaoDefinition;
        this.regionalDaoDefinition = regionalDaoDefinition;
    }

    @Override
    public Participante mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Participante participante = super.mapRow(rs, rowNumber);
        if (participante != null) {
            Integer idDireccionCasa = valInt("id_direccion_casa", rs);
            if (idDireccionCasa != null && idDireccionCasa > 0) {
                Direccion casa = new Direccion();
                casa.setIdDireccion(idDireccionCasa);
                casa.setDireccionCalle(valString("descripcion_direccion_casa", rs));
                casa.setReferencia(valString("referencia_casa", rs));
                participante.setDireccionCasa(casa);
            }

            Integer idDireccionOficina = valInt("id_direccion_oficina", rs);
            if (idDireccionOficina != null && idDireccionOficina > 0) {
                Direccion oficina = new Direccion();
                oficina.setIdDireccion(idDireccionOficina);
                oficina.setDireccionCalle(valString("descripcion_direccion_oficina", rs));
                oficina.setReferencia(valString("referencia_oficina", rs));
                participante.setDireccionOficina(oficina);
            }

            Integer idDireccionCasaCampo = valInt("id_direccion_casa_campo", rs);
            if (idDireccionCasaCampo != null && idDireccionCasaCampo > 0) {
                Direccion casaCampo = new Direccion();
                casaCampo.setIdDireccion(idDireccionCasaCampo);
                casaCampo.setDireccionCalle(valString("descripcion_direccion_casa_campo", rs));
                casaCampo.setReferencia(valString("referencia_casa_campo", rs));
                participante.setDireccionCasaCampo(casaCampo);
            }

            Integer idDireccionCasaPlaya = valInt("id_direccion_casa_playa", rs);
            if (idDireccionCasaPlaya != null && idDireccionCasaPlaya > 0) {
                Direccion casaPlaya = new Direccion();
                casaPlaya.setIdDireccion(idDireccionCasaPlaya);
                casaPlaya.setDireccionCalle(valString("descripcion_direccion_casa_playa", rs));
                casaPlaya.setReferencia(valString("referencia_casa_playa", rs));
                participante.setDireccionCasaPlaya(casaPlaya);
            }

            Integer idDireccionOtro = valInt("id_direccion_otro", rs);
            if (idDireccionOtro != null && idDireccionOtro > 0) {
                Direccion otro = new Direccion();
                otro.setIdDireccion(idDireccionOtro);
                otro.setDireccionCalle(valString("descripcion_direccion_otro", rs));
                otro.setReferencia(valString("referencia_otro", rs));
                participante.setDireccionOtro(otro);
            }

            participante.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
            participante.setBroker(brokerDaoDefinition.mapRow(rs, rowNumber));
            participante.setRegional(regionalDaoDefinition.mapRow(rs, rowNumber));
            participante.setTipoRazonSocial(razonSocialDaoDefinition.mapRow(rs, rowNumber));
            participante.setRegion(regionDaoDefinition.mapRow(rs, rowNumber));
            participante.setDireccion(direccionDaoDefinition.mapRow(rs, rowNumber));
            participante.setAuditoria(auditoriaDaoDefinition.mapRow(rs, rowNumber));
            participante.setTipoDocumento(tipoDocumentoDaoDefinition.mapRow(rs, rowNumber));
            participante.setConcesionario(concesionarioDaoDefinition.mapRow(rs, rowNumber));
            participante.setSubtipoParticipante(subtipoParticipanteDaoDefinition.mapRow(rs, rowNumber));
        }
        return participante;
    }

    public Integer valInt(String columnName, ResultSet rs) throws SQLException {
        return hasColumn(rs, columnName) ? rs.getInt(columnName) : null;
    }

    public String valString(String columnName, ResultSet rs) throws SQLException {
        return hasColumn(rs, columnName) ? rs.getString(columnName) : null;
    }
}
