package com.promotick.lafabril.dao.web;


import com.promotick.lafabril.model.reporte.ReporteMarca;
import com.promotick.lafabril.model.util.FiltroMarca;
import com.promotick.lafabril.model.web.Marca;

import java.util.List;

public interface MarcaDao {
    List<Marca> listarMarcas(FiltroMarca filtroMarca);

    Integer actualizarMarca(Marca marca);

    Integer registroMarca(Marca marca);

    Integer contarMarcas();

    List<Marca> listarMarcasFiltro(FiltroMarca filtroMarca);

    Integer contarMarcasFiltro(FiltroMarca filtroMarca);

    List<ReporteMarca> reporteMarcas(FiltroMarca filtroMarca);
}
