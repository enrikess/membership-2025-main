function ProductoDetalle(uri, footer, idMarca) {
    this.uri = uri;
    this.footer = footer;
    this.idMarca = idMarca;
    this.idTipoProducto = parseInt($('#idTipoProducto').val());
    this.contentInteres = $('#contentInteres');
}

ProductoDetalle.prototype.init = function() {
    this.handle();
    this.obtenerProductosInteres('productos/viewProductosInteres/' + this.idMarca, this.contentInteres);
};

ProductoDetalle.prototype.handle = function() {
    var obj = this;

    obj.footer.initValidations($('.product-detail-box').find('form'), obj.idTipoProducto);
};

ProductoDetalle.prototype.obtenerProductosInteres = function(uri, context) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + uri,
        context : context
    });

};
