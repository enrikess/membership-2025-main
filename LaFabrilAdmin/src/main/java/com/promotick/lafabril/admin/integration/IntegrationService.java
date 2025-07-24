package com.promotick.lafabril.admin.integration;

import com.promotick.lafabril.admin.service.CampaniaIntegrationAdminService;
import com.promotick.lafabril.admin.service.CategoriaIntegrationAdminService;
import com.promotick.lafabril.admin.service.MarcaIntegrationAdminService;
import com.promotick.lafabril.admin.service.ProductoIntegrationAdminService;
 import com.promotick.lafabril.model.web.*;
import com.promotick.lafabril.model.web.Categoria;
import com.promotick.ecuador.admin.integration.models.CampaniaAdmin;
import com.promotick.ecuador.admin.integration.models.CategoriaAdmin;
import com.promotick.ecuador.admin.integration.models.MarcaAdmin;
import com.promotick.ecuador.admin.integration.models.ProductoAdmin;
import com.promotick.ecuador.admin.integration.models.generics.AdminResponse;
import com.promotick.ecuador.admin.integration.services.AdminIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService implements AdminIntegrationService{

    private MarcaIntegrationAdminService marcaIntegrationAdminService;
    private CategoriaIntegrationAdminService categoriaIntegrationAdminService;
    private ProductoIntegrationAdminService productoIntegrationAdminService;
    private CampaniaIntegrationAdminService campaniaIntegrationAdminService;


    @Autowired
    public IntegrationService(MarcaIntegrationAdminService marcaIntegrationAdminService, ProductoIntegrationAdminService productoIntegrationAdminService, CategoriaIntegrationAdminService categoriaIntegrationAdminService, CampaniaIntegrationAdminService campaniaIntegrationAdminService) {
        this.marcaIntegrationAdminService = marcaIntegrationAdminService;
        this.productoIntegrationAdminService = productoIntegrationAdminService;
        this.categoriaIntegrationAdminService = categoriaIntegrationAdminService;
        this.campaniaIntegrationAdminService = campaniaIntegrationAdminService;
    }
    @Override
    public void cargaCategoriaAdmin(CategoriaAdmin categoriaAdmin, AdminResponse adminResponse) throws Exception {
        Categoria categoria = new Categoria();
        categoria.setCodigoCategoria(categoriaAdmin.getCodigoCategoria());
        categoria.setNombreCategoria(categoriaAdmin.getNombreCategoria());
        categoria.setEstadoCategoria(categoriaAdmin.getEstadoCategoria());
        categoria.setOrdenCategoria(categoriaAdmin.getOrdenCategoria());
        categoria.setIdTipoCategoria(categoriaAdmin.getIdTipoCategoria());
        categoria.setCodigoCategoriaParent(categoriaAdmin.getCodigoCategoriaParent());
        categoria.setAccion(categoriaAdmin.getEstadoCategoria());
        Integer resultado = categoriaIntegrationAdminService.actualizarCategoria(categoria);
        adminResponse.setStatus(true);
        adminResponse.setMessage("Categoria registrada con exito: "+resultado);
    }

    @Override
    public void cargaMarcaAdmin(MarcaAdmin marcaAdmin, AdminResponse adminResponse) throws Exception {
        Marca marca = new Marca();
        marca.setCodigoMarca(marcaAdmin.getCodigoMarca());
        marca.setNombreMarca(marcaAdmin.getNombreMarca());
        marca.setDescripcionCorta(marcaAdmin.getDescripcion());
        marca.setDescripcionLarga(marcaAdmin.getDescripcion());
        marca.setEstadoMarca(marcaAdmin.getEstadoMarca());
        marca.setAccion(marcaAdmin.getEstadoMarca());
        Integer resultado = marcaIntegrationAdminService.actualizarMarca(marca);
        adminResponse.setStatus(true);
        adminResponse.setMessage("Marca registrada con exito: "+resultado);

    }

    @Override
    public void cargaProductoAdmin(ProductoAdmin productoAdmin, AdminResponse adminResponse) throws Exception {
        Producto producto = new Producto();
        producto.setCodigoProducto(productoAdmin.getCodigoProducto());
        producto.setCodigoWeb(productoAdmin.getCodigoWeb());
        producto.setNombreTag(productoAdmin.getNombreTag());
        producto.setDescripcionProducto(productoAdmin.getDescripcionProducto());
        producto.setNombreProducto(productoAdmin.getNombreProducto());
        producto.setMarca(new Marca().setCodigoMarca(productoAdmin.getCodigoMarca()));
        producto.setCategoria(new Categoria().setCodigoCategoria(productoAdmin.getCodigoCategoria()));
        producto.setPuntosRegular(productoAdmin.getPuntosRegular());
        producto.setPuntosProducto(productoAdmin.getPuntosProducto());
        producto.setPrecioProducto(productoAdmin.getPrecioProducto());
        producto.setEspecificacionesProducto(productoAdmin.getEspecificacionesProducto());
        producto.setTerminosProducto(productoAdmin.getTerminosProducto());
        producto.setStockProducto(productoAdmin.getStockProducto());
        producto.setTipoProducto(new TipoProducto().setIdTipoProducto(productoAdmin.getTipoProducto()));
        producto.setTagProducto(new TagProducto().setIdTagProducto(productoAdmin.getTagProducto()));
        producto.setCodigoCatalogo(productoAdmin.getCodigoCatalogo());
        producto.setTags(productoAdmin.getTags());
        producto.setAccion(productoAdmin.getEstadoProducto());
        producto.setEstadoProducto(productoAdmin.getEstadoProducto());
        producto.setIdNetsuite(productoAdmin.getIdNetsuite());
        producto.setImagenUno(productoAdmin.getImagenUno());
        producto.setImagenDetalle(productoAdmin.getImagenDetalle());
        //producto.setImagenEnvioExpress(productoAdmin.getImagenEnvioExpress());
        Integer resultado = productoIntegrationAdminService.actualizarProducto(producto);
        adminResponse.setStatus(true);
        adminResponse.setMessage("Producto registrado con exito: "+resultado);
    }

    @Override
    public void cargaCampaniaAdmin(CampaniaAdmin campaniaAdmin, AdminResponse adminResponse) throws Exception {
        Producto producto = new Producto();
        producto.setCodigoProducto(campaniaAdmin.getCodigoProducto());
        producto.setCategoria(new Categoria().setCodigoCategoria(campaniaAdmin.getCodigoCategoria()));
        producto.setPuntosProducto(campaniaAdmin.getPuntos());
        producto.setNombreTag(campaniaAdmin.getNombreEtiqueta());
        Integer resultado = campaniaIntegrationAdminService.actualizarCampania(producto);
        adminResponse.setStatus(true);
        adminResponse.setMessage("Campaña registrada con exito: "+resultado);
    }

}
