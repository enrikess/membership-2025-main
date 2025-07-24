package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.admin.definition.ReporteCategoriaExcelDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.CategoriaDao;
import com.promotick.lafabril.dao.web.definition.CategoriaDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.reporte.ReporteCategoria;
import com.promotick.lafabril.model.util.FiltroCategoria;
import com.promotick.lafabril.model.web.Categoria;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class CategoriaDaoImpl extends DaoGenerator implements CategoriaDao {

    private CategoriaDaoDefinition categoriaDaoDefinition;
    private ReporteCategoriaExcelDaoDefinition reporteCategoriaExcelDaoDefinition;

    @Autowired
    public CategoriaDaoImpl(JdbcTemplate jdbcTemplate, CategoriaDaoDefinition categoriaDaoDefinition, ReporteCategoriaExcelDaoDefinition reporteCategoriaExcelDaoDefinition) {
        super(jdbcTemplate);
        this.categoriaDaoDefinition = categoriaDaoDefinition;
        this.reporteCategoriaExcelDaoDefinition = reporteCategoriaExcelDaoDefinition;
    }

    @Override
    public List<Categoria> obtenerCategoriasAdmin(FiltroCategoria filtroCategoria) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATEGORIA_LISTAR)
                .addParameter("var_orden", Types.INTEGER, filtroCategoria.getOrden())
                .addParameter("var_inicio", Types.INTEGER, filtroCategoria.getStart())
                .addParameter("var_id_categoria", Types.INTEGER, filtroCategoria.getIdCategoria())
                .addParameter("var_tamanio", Types.INTEGER, filtroCategoria.getLength())
                .addParameter("var_buscar", Types.VARCHAR, filtroCategoria.getNombreCategoria())
                .setDaoDefinition(categoriaDaoDefinition)
                .build();
    }

    @Override
    public List<Categoria> obtenerSubcategorias(Integer idCategoria) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CATEGORIA_LISTAR)
                .addParameter("var_idcategoria", Types.INTEGER, idCategoria)
                .setDaoDefinition(categoriaDaoDefinition)
                .build();
    }

    @Override
    public Integer registroCategoria(Categoria categoria) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATEGORIA_REGISTRAR)
                .addParameter("var_accion", Types.INTEGER, categoria.getAccion())
                .addParameter("var_id_categoria", Types.INTEGER, categoria.getIdCategoria())
                .addParameter("var_nombre_categoria", Types.VARCHAR, categoria.getNombreCategoria())
                .addParameter("var_estado_categoria", Types.INTEGER, categoria.getEstadoCategoria())
                .addParameter("var_orden", Types.INTEGER, categoria.getOrdenCategoria())
                .addParameter("var_tipo_categoria", Types.INTEGER, categoria.getIdTipoCategoria())
                .addParameter("var_id_categoria_parent", Types.INTEGER, categoria.getIdCategoriaParent())
                .addParameter("var_imagen_categoria", Types.VARCHAR, categoria.getImagenCategoria())
                .addParameter("var_usuario_creacion", Types.VARCHAR, categoria.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizacionOrdenSubcategoria(String cadena) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_SUBCATEGORIA_ORDEN)
                .addParameter("var_cadena", Types.VARCHAR, cadena)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizacionSubcategoria(Categoria subCategoria) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_SUBCATEGORIA_ACTUALIZAR)
                .addParameter("var_id_subcategoria", Types.INTEGER, subCategoria.getIdCategoria())
//                .addParameter("var_id_categoria", Types.INTEGER, subCategoria.getCategoria().getIdCategoria())
                .addParameter("var_nombre_subcategoria", Types.VARCHAR, subCategoria.getNombreCategoria())
                .addParameter("var_catalogos", Types.VARCHAR, subCategoria.getCatalogosString())
                .addParameter("var_usuario_creacion", Types.VARCHAR, subCategoria.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Categoria> obtenerCategoriasGeneral(Integer IdCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CATEGORIA_GENERAL_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, IdCatalogo)
                .setDaoDefinition(categoriaDaoDefinition)
                .build();
    }

    @Override
    public List<Categoria> listarSubCategoria(Integer idSubcategoria) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_SUBCATEGORIA_LISTAR)
                .addParameter("var_idcategoria", Types.INTEGER, idSubcategoria)
                .setDaoDefinition(categoriaDaoDefinition)
                .build();
    }

    @Override
    public Categoria obtenerCategoria(Integer idCatalogo, Integer idCategoria) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CATEGORIA_OBTENER)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_id_categoria", Types.INTEGER, idCategoria)
                .setDaoDefinition(categoriaDaoDefinition)
                .build(Categoria.class);
    }

    @Override
    public List<Categoria> listarCategoriasHijas(Integer idCatalogo, Integer idCategoriaPadre) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CATEGORIA_LISTAR_HIJAS)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_id_categoria_padre", Types.INTEGER, idCategoriaPadre)
                .setDaoDefinition(categoriaDaoDefinition)
                .build();
    }

    @Override
    public List<ReporteCategoria> reporteCategorias(FiltroCategoria filtroCategoria) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATEGORIAS_REPORTE_EXCEL)
                .setDaoDefinition(reporteCategoriaExcelDaoDefinition)
                .build();
    }
}
