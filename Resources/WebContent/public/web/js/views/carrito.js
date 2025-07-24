function Carrito(uri) {
    this.uri = uri;
    this.contentCarrito = $('#contentCarrito');
}

Carrito.prototype.init = function() {
    this.handle();
    this.mostrarCarrito();
};

Carrito.prototype.handle = function() {
    var obj = this;

    $(document).on('click', '.updateTotal', function(e) {
        var cantidadText = $(this).parent().find('.qty-val').text();
        var puntosText = $(this).parent().parent().parent().find('.td-puntos .puntos').text();
        console.log(puntosText);
        $(this).parent().parent().parent().find('.subtotal .total').text(parseInt(cantidadText, 10) * parseInt(puntosText, 10));
    });

    $(document).on('click', '.remove-button', function(){
        var idProducto = $(this).attr('id');
        obj.eliminarCarrito(idProducto);
    });

    $(document).on('click', '#btnActualizar' ,function(e){
        e.preventDefault();

        obj.contentCarrito.LoadingOverlay('show');

        var $trs = obj.contentCarrito.find('table.shop_table>tbody>tr');
        var actualizables = $trs.length - 1;
        var actualizados = 0;
        $trs.each(function() {
            var idProducto = $(this).attr('data-id');
            if (idProducto !== undefined) {
                actualizados++;
                var cantidad = $('#number-' + idProducto).text();
                obj.actualizarCarrito(idProducto, cantidad, actualizables, actualizados);
            }
        });

        obj.contentCarrito.LoadingOverlay('hide');
    });


};

Carrito.prototype.mostrarCarrito = function() {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'carrito/viewPedido',
        context : obj.contentCarrito
    })
};

Carrito.prototype.eliminarCarrito = function(idProducto) {
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'carrito/eliminarItemCarrito/' + idProducto,
        messageError : true,
        messageTitle : 'Eliminar item del carrito',
        before : function() {
            obj.contentCarrito.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Carrito de compras');
                obj.mostrarCarrito();
                obj.cantidadCarrito();
            }
        }
    });

};

Carrito.prototype.cantidadCarrito = function() {
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'carrito/cantidadItemsCarrito',
        messageError : true,
        messageTitle : 'Obtener cantidad del carrito',
        success : function(response) {
            if (response.status) {
                $('.count-pedido').html(response.data);
            }
        }
    });

};

Carrito.prototype.actualizarCarrito = function(idProducto, cantidad, actualizables, actualizados) {
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'carrito/actualizarProducto/' + idProducto + '/' + cantidad,
        complete : function() {
            console.log(actualizables);
            console.log(actualizados);
            if (actualizables === actualizados) {
                obj.mostrarCarrito();
                obj.contentCarrito.LoadingOverlay('hide', true);
            }
        }
    });
};