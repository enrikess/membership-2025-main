function Footer(uri) {
    this.uri = uri;
    this.modalUI = $('#modalUI');
    this.popupResultUI = $('#add-car');
    this.cartboxDinamico = $('.cartbox-dinamico');
    this.cartPopup = $('.open-cart-popup'); //NO USADO
    this.countPedido = $('.count-pedido');
    this.buscar = $('.search');
    this.buscarIcon = $('.buscarIcon');
    this.objForm = null;
    this.cartLink = $('.cart-link');
    this.modalUI2 = $('#modalUI-2');

}

Footer.prototype.init = function() {
    this.handle();
    this.openPopUp();
};

Footer.prototype.openPopUp = function() {
    var html = $("#bienvenida-popup").html();
    if (html !== null && html !== undefined) {
        $.magnificPopup.open({
            items: {
                src: html,
                type: 'inline'
            }
        });
    }
}

Footer.prototype.handle = function () {
    var obj = this;

    obj.modalUI.on('hidden.bs.modal', function () {
        $(this).find('.popup-container').html('');
        obj.objForm = null;
    });

    /***whatsapp***/

    $(document).on('click', '.open-button', function(){
        $('#myForm').show();
    });

    $(document).on('click', '.close-form', function(){
        $('#myForm').hide();
    });

    $('#txtWhatsApp').keyup(function(e){
        if(e.keyCode == 13)
        {
            obj.sendMessage();
        }
    });

    $(document).on('click','.btn-enviar', function(){
        obj.sendMessage();
    });

    /*** fin whatsapp ****/

    $(document).on('click', '.open-product', function(e){
        e.preventDefault();
        var id = $(this).attr('data-id');
        var tipo = $(this).attr('data-tipo');
        obj.popupProducto(id, tipo);
    });

    $(document).on('change', '.open-product', function(e){
        e.preventDefault();
        var id = $(this).attr('data-id');
        var tipo = $(this).attr('data-tipo');
        obj.popupProducto(id, tipo);
    });

    $(document).on('click','.open-addcar-modal', function(e){
        e.preventDefault();
        obj.agregarItemCarrito(this);
    });

    // obj.cartPopup.on('click', function(e) {
    //     e.preventDefault();
    //     obj.obtenerPopupCarrito();
    // });

    obj.cartPopup.on('mouseover', function(e) {
        e.preventDefault();
        obj.obtenerPopupCarrito();
    });

    $(document).on('click','.open-addcar-link', function(e){
        e.preventDefault();

        var id = $(this).attr('data-id');
        var tipo = $(this).attr('data-tipo');
        console.log("open-addcar-link############footer.js--" + tipo);

        switch (parseInt(tipo)) {
            case 1: // Regular
            case 2: // Primax
                var data = {
                    idProducto : id,
                    cantidad : 1
                };
                var $content = obj.modalUI.find('.modal-body');
                obj.agregarProducto($content, data);
                break;
            default:
                obj.popupProducto(id, tipo);
        }
    });

    $(document).on('click', '.open-addwishlist', function(e){
        e.preventDefault();
        obj.agregarWishlist(this);
    });

    $(document).on('click','.remove-cart-popup',function(e){
        e.preventDefault();
        var idProducto = $(this).attr('id');
        obj.eliminarCarritoPopup(idProducto);
    });

    $(document).on('keypress','.search',function(e){
        var $buscar = $(this).val();
        if (e.which == 13) {
            if ($buscar !== '') {
                window.location.href = obj.uri + "catalogo?buscar=" + $buscar;
            }
        }else{
            if($buscar !=undefined && $buscar != null && $buscar.trim().length > 3){
                obj.obtenerAutoCompletar($buscar);
            }
        }
    });

    $(document).on('click','.buscarIcon',function(e){
        var $buscar = $(this).parent().find('.search').val();
        if ($buscar !== '') {
            window.location.href = obj.uri + "catalogo?buscar=" + $buscar;
        }
    });

    $(document).on('click','.openOutside', function(e){
        var urlIOS = $(this).attr('data-ios');
        var urlAndroid = $(this).attr('data-android');
        var ua = navigator.userAgent.toLowerCase();
        var isAndroid = ua.indexOf("android") > -1; //&& ua.indexOf("mobile");
        var url = isAndroid ? urlAndroid : urlIOS;

        if(url !=undefined && url != null && url.trim().length > 0){
            window.open(url, '_blank').focus();
        }
    });


    $(document).on('click','.mfp-close', function(e){
        $(window).trigger('resize');
    });

    $(document).on('hidden.bs.modal','.modal', function(e){
        $(window).trigger('resize');
    });

};

Footer.prototype.eliminarCarritoPopup = function(idProducto) {
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'carrito/eliminarItemCarrito/' + idProducto,
        messageError : true,
        messageTitle : 'Eliminar item del carrito',
        before : function() {
            obj.cartboxDinamico.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Carrito de compras');
                obj.obtenerPopupCarrito();
            }
        }
    });

};

Footer.prototype.popupProducto = function(idProducto, tipo){
    var obj = this;
    var $body = obj.modalUI.find('.modal-body');
    var $content = obj.modalUI.find('.popup-container');

    Promotick.render.get({
        url : obj.uri + 'productos/viewProducto/' + idProducto,
        context : $body,
        before : function() {
            obj.modalUI.modal('show');
        },
        success : function() {
            obj.initValidations($body.find('form'), parseInt(tipo));
        }
    });

};


Footer.prototype.agregarItemCarrito = function(context) {
    var obj = this;

    var data = obj.obtenerDatosSegunTipo(context);

    if (data.validacion && !obj.objForm.valid()) {
        data.continua = false;
    }

    if (data.continua) {
        var $content = obj.modalUI.find('.modal-body');
        obj.agregarProducto($content, data);
    }
};

Footer.prototype.obtenerDatosSegunTipo = function(context) {
    var obj = this;

    var data = {
        continua : true,
        cantidad : 1,
        idProducto : $(context).attr('data-id'),
        tipo : $(context).attr('data-tipo'),
        nroCelular : null,
        nroDecodificador : null,
        color1 : null,
        color2 : null,
        correo: null,
        validacion : false
    };

    switch (parseInt(data.tipo)) {
        case 1: // Regular
        case 2: // Primax
            var $qty = $(context).parent().parent().find('.quantity>span.qty-val');
            if ($qty.length > 0) {
            } else {
                data.continua = false;
                Promotick.toast.error('Cantidad no especificada', 'Productos');
            }
            data.cantidad = parseInt($qty.text());
            break;
        case 3: // Recarga celular
            data.validacion = true;
            data.nroCelular = $(context).parent().parent().find('form').find('input').val();
            break;
        case 4: // Recarga TV
            data.validacion = true;
            data.nroDecodificador = $(context).parent().parent().find('form').find('input').val();
            break;
        case 5: // Colores
            data.validacion = true;
            data.color1 = $(context).parent().parent().find('form').find('input[name="color1"]').val();
            data.color2 = $(context).parent().parent().find('form').find('input[name="color2"]').val();
            break;
        case 7: // Correo
            data.validacion = true;
            data.correo = $(context).parent().parent().find('form').find('input').val();
            break;
    }

    return data;
};

Footer.prototype.agregarProducto = function(context, data) {
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'carrito/agregarItemCarrito/' + data.idProducto + '/' + data.cantidad,
        messageError : true,
        messageTitle : 'Agregar producto al carrito',
        data : JSON.stringify(data),
        before : function() {
            context.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                if (obj.objForm != null) {
                    obj.objForm[0].reset();
                }
                obj.modalUI.modal('hide');
                obj.actualizarCantidadPedido(response.extra1);

                setTimeout(function(){
                    obj.mostrarVistaProductoAgregado(response);
                }, 200);
            }
        },
        complete : function() {
            context.LoadingOverlay('hide', true);
        }
    });

};

Footer.prototype.mostrarVistaProductoAgregado = function(data) {
    var obj = this;

    Promotick.render.post({
        url : obj.uri + 'catalogo/viewProductoAgregado',
        data : JSON.stringify(data),
        success : function(response) {
            obj.modalUI2.find('.popup-container-dinamico').html(response);
            obj.modalUI2.modal('show');
        }
    });
};

Footer.prototype.agregarWishlist = function(context) {

    var obj = this;
    var request = {
        producto : {
            idProducto : $(context).attr('data-id')
        },
        cantidadProducto : 1
    };

    Promotick.ajax.post({
        url : obj.uri + 'wishlist/agregarWishlist',
        messageError : true,
        messageTitle : 'Agregar producto como favorito',
        data : JSON.stringify(request),
        before : function() {
        },
        success : function(response) {
            if (response.status) {

                setTimeout(function(){
                    obj.mostrarVistaWishlistAgregado(response);
                }, 200);
            }
        },
        complete : function() {
        }
    });

};


Footer.prototype.mostrarVistaWishlistAgregado = function(data) {
    var obj = this;

    Promotick.render.post({
        url : obj.uri + 'wishlist/viewWishlistAgregado',
        data : JSON.stringify(data),
        success : function(response) {
            obj.modalUI2.find('.popup-container-dinamico').html(response);
            obj.modalUI2.modal('show');
        }
    });

};

Footer.prototype.initValidations = function(objForm, tipo) {
    var obj = this;
    switch (tipo) {
        case 3: // Nro de celular
            obj.objForm = objForm;
            obj.objForm.validate({
                rules : {
                    nroCelular : {
                        required : true,
                        minlength : 9
                    }
                },
                messages : {
                    nroCelular : {
                        required : 'Ingrese numero de celular',
                        minlength : 'Ingrese al menos 9 digitos'
                    }
                }
            });
            break;
        case 4: // Nro de decodificador TV
            obj.objForm = objForm;
            obj.objForm.validate({
                rules : {
                    nroDeco : {
                        required : true,
                        minlength : 9
                    }
                },
                messages : {
                    nroDeco : {
                        required : 'Ingrese numero de decodificador',
                        minlength : 'Ingrese al menos 9 digitos'
                    }
                }
            });
            break;
        case 5: // Colores
            obj.objForm = objForm;
            obj.objForm.validate({
                rules : {
                    color1 : {
                        required : true
                    }
                },
                messages : {
                    color1 : {
                        required : 'Ingrese un color'
                    }
                }
            });
            break;
        case 7: // Correo
            obj.objForm = objForm;
            obj.objForm.validate({
                rules : {
                    correo : {
                        required : true,
                        email : true
                    }
                },
                messages : {
                    correo : {
                        required : 'Ingrese su email',
                        email : 'Formato de correo incorrecto'
                    }
                }
            });
            break;
    }

};

Footer.prototype.obtenerAutoCompletar = function(buscar) {
    var obj = this;
    Promotick.render.get({
        url : obj.uri + 'productos/viewAutoCompletar/' + buscar,
        context: $("#resultadosAutoComplete")
    });
};

Footer.prototype.obtenerPopupCarrito = function() {
    var obj = this;
    Promotick.render.get({
        url : obj.uri + 'carrito/viewPopupCarrito',
        context: obj.cartboxDinamico
    });
};

Footer.prototype.actualizarCantidadPedido = function(cantidad) {
    this.countPedido.html(cantidad);
};


Footer.prototype.sendMessage = function() {

    var url = $('#urlWhatsApp').val();
    var txt = $('#txtWhatsApp').val();
    window.open(url + " " + txt,'_blank');
};