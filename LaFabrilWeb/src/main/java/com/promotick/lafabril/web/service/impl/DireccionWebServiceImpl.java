package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.model.web.MultiDireccionCustom;
import com.promotick.lafabril.model.web.ParticipanteDireccion;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Zona;
import com.promotick.lafabril.dao.web.DireccionDao;
import com.promotick.lafabril.web.service.DireccionWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DireccionWebServiceImpl implements DireccionWebService {

    private DireccionDao direccionDao;

    @Autowired
    public DireccionWebServiceImpl(DireccionDao direccionDao) {
        this.direccionDao = direccionDao;
    }

    @Override
    public List<Zona> listarZonas() {
        return direccionDao.listarZonas();
    }

    @Override
    public List<TipoVivienda> listarTipoViviendas() {
        return direccionDao.listarTipoViviendas();
    }

    @Override
    public MultiDireccionCustom direccionesParticipante(Integer idParticipante, Integer idDireccion) {

        MultiDireccionCustom obj = new MultiDireccionCustom();
//        List<Direccion> direcciones = new ArrayList<>();
//        List<TipoDireccion> tipos = new ArrayList<>();
//        Direccion principal = null;
//        Boolean existe = false;
//
//        List<Direccion> direccionestmp = direccionDao.listarDireccionesParticipante(idParticipante);
//        UtilEnum.TIPO_DIRECCION[] tipoDirecciones = UtilEnum.TIPO_DIRECCION.values();
//        for(UtilEnum.TIPO_DIRECCION ob : tipoDirecciones){
//            existe = false;
//            for(Direccion objDireccion : direccionestmp){
//                if(ob.getId().equals(objDireccion.getTipo())){
//                    direcciones.add(objDireccion.setTipoDireccion(ob.getNombre()));
//                    existe = true;
//                }
//            }
//
//            if(!existe){
//                tipos.add(new TipoDireccion().setId(ob.getId()).setNombre(ob.getNombre()));
//            }
//        }
//
//        if(!idDireccion.equals(ConstantesCommon.ZERO_VALUE)){
//            principal = direcciones.stream().filter(d -> d.getIdDireccion().equals(idDireccion)).findAny().orElse(null);
//            if(principal != null){
//                UtilEnum.TIPO_DIRECCION tipo_direccion = UtilEnum.TIPO_DIRECCION.fromCodigo(principal.getTipo());
//                if(tipo_direccion != null){
//                    tipos =  new ArrayList<>();
//                    tipos.add(new TipoDireccion().setId(tipo_direccion.getId()).setNombre(tipo_direccion.getNombre()));
//                }
//            }
//        }
//
//        obj.setDirecciones(direcciones).setPrincipal(principal).setDisponibles(tipos);
        return obj;
    }

    @Override
    public List<ParticipanteDireccion> listarDireccionesParticipante(Integer idParticipante) {
        return direccionDao.listarDireccionesParticipante(idParticipante);
    }

    @Override
    @Transactional
    public Integer registrarDireccionParticipante(ParticipanteDireccion direccion) {
        return direccionDao.registrarDireccionParticipante(direccion);
    }

    @Override
    @Transactional
    public Integer eliminarDireccionParticipante(Integer idParticipante, Integer idParticipanteDireccion) {
        return direccionDao.eliminarDireccionParticipante(idParticipante, idParticipanteDireccion);
    }
}
