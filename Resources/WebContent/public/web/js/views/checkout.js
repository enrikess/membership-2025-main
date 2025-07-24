function Checkout(uri) {
    this.uri = uri;
    this.ecuador = '02';
    this.ecuadorCoddep = '01';
    this.contentCheckout = $('#contentCheckout');
    this.countPedido = $('.count-pedido');
    this.puntosDisponibles = $('.puntos_disponibles');
    this.modalDireccion = $('#modalDireccion');
    this.modalDireccionContent = $('#modalDireccionContent');
    this.$titleResumenCheckout = null;
    this.$titleInfoCheckout = null;
    this.$titleDireccionCheckout = null;
    this.$titlePuntosCheckout = null;
    this.infoCheckoutLoad = false;
    this.direccionCheckoutLoad = false;
    this.puntosCheckoutLoad = false;
    this.$frmInfoCheckout = null;
    this.$frmDireccionCheckout = null;
    this.$frmDireccionesCheckout = null;
    this.$frmEncuestaCheckout = null;
    this.$btnConfirmaCanje = null;
    this.encuestaEnviada = true;
    this.modalEncuesta = $('#modalEncuesta');
    this.modalEncuestaContent = $('#modalEncuestaContent');

    this.applicationPayments = null;
    this.modalCompra = $('#modalCompra');
    this.modalCompraContent = $('#modalCompraContent');
    this.puntosSubTotal = 0;
    this.puntosParticipante = 0;
    this.dvCompraPuntos = $('#dvCompraPuntos');
    this.dvMensajeComponente = $('#dvMensajeComponente');
    this.dvMensajePuntos = $('#dvMensajePuntos');
    this.modalDescuento = $("#modalDescuento");
}

Checkout.prototype.init = function () {
    this.handle();
    this.viewCheckout();
};

Checkout.prototype.handle = function () {
    var obj = this;

    $(document).on('click', '.remove-button', function(){
        var idProducto = $(this).attr('id');
        obj.eliminarCarrito(idProducto);
    });

    $(document).on('click', '#btnComprarPuntos', function(e) {
        console.log("btn-compra");
        e.preventDefault();
        obj.comprarPuntos();
    });

    $(document).on('click', '#btnResumenOK', function(e) {
        console.log("bton0");
        obj.$btnConfirmaCanje = $(document).find('#btnResumenOK');

        e.preventDefault();
        obj.enviarPedido(false);

    });

    $(document).on('click', '#btnRemoverDescuento', function(e) {
        console.log("btn-remover-descuento");
        e.preventDefault();
        obj.removerDescuento();
    });

    $(document).on('click', '#btnAceptarCompra', function(e) {
        if(parseInt(obj.puntosParticipante * 1.5) >= obj.puntosSubTotal){
            $('.close-popup').click();
            $('#mensaje-pay').hide();

            if (obj.applicationPayments != null) {
                obj.modalCompra.modal('hide');
                var request = {
                    user_id: obj.datosVenta.uid,
                    user_email: obj.datosVenta.buyer_email, //optional
                    user_phone: obj.datosVenta.buyer_phone, //optional
                    order_description: obj.datosVenta.product_description,
                    order_amount: parseFloat(obj.datosVenta.montoMasIva),
                    order_vat: 0,
                    order_reference: obj.datosVenta.dev_reference
                };
                obj.applicationPayments.open(request);
            } else {
                obj.dvMensajeComponente.show();
            }
        }else{
            obj.dvMensajePuntos.show();
        }
    });

    $(document).on('click', '#btnInfoCheckout', function(e) {
        e.preventDefault();

        if (!obj.direccionCheckoutLoad) {
            if (obj.$frmInfoCheckout == null) {
                Promotick.toast.error('Formulario de informacion no se ha inicializado', 'Checkout');
            } else if (obj.$frmInfoCheckout.valid()) {
                obj.viewDireccionCheckout()
            }
        } else {
            obj.next(obj.$titleInfoCheckout, obj.$titleDireccionCheckout);
        }

    });

    $(document).on('click', '#btnDireccionCheckout', function(e) {
        e.preventDefault();

        if (!obj.puntosCheckoutLoad) {
            if (obj.$frmDireccionCheckout == null) {
                Promotick.toast.error('Formulario de direccion no se ha inicializado', 'Checkout');
            } else if (obj.$frmDireccionCheckout.valid()) {
                obj.viewPuntosCheckout();
            }
        } else {
            obj.next(obj.$titleDireccionCheckout, obj.$titlePuntosCheckout);
        }
    });

    obj.modalDireccion.on('hidden.bs.modal', function () {
        obj.modalDireccionContent.html('');
    });

    obj.modalEncuesta.on('hidden.bs.modal', function () {
        obj.modalEncuestaContent.html('');
        obj.encuestaEnviada = false;
    });

    obj.modalCompra.on('hidden.bs.modal', function () {
        obj.modalCompraContent.html('');
    });

    $(document).on('click', '#btnCodigo', function(e) {
        obj.modalDescuento.modal('show');
    });

    $(document).on('click', '#btnValidarDescuento', function(e) {
        var objeto = {codigoDescuento : $("#inpt-descuento").val() };

        if(objeto.codigoDescuento === ""){
            $(".error-descuento").text("Ingrese código");
            return;
        }

        obj.validarDescuento(objeto);
    });
};

Checkout.prototype.eliminarCarrito = function(idProducto) {
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'carrito/eliminarItemCarrito/' + idProducto,
        messageError : true,
        messageTitle : 'Eliminar item checkout',
        before : function() {
            obj.contentCheckout.LoadingOverlay('show', Promotick.defaults.LoadingOverlay);
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Checkout');
                obj.viewCheckout();
                obj.cantidadCarrito();
            }
        }
    });

};

Checkout.prototype.viewCheckout = function () {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'checkout/viewCheckout',
        context: obj.contentCheckout,
        complete : function() {
            obj.viewResumenCheckout();
            obj.initCheckout();
            // obj.viewDireccionCheckout();
            obj.viewDireccionesCheckout();
        }
    });
};

Checkout.prototype.viewResumenCheckout = function () {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'checkout/viewResumenCheckout',
        context: $(document).find('#contentResumenCheckout'),
        complete : function() {
        }
    });
};

Checkout.prototype.viewDireccionesCheckout = function () {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'checkout/viewDireccionesCheckout',
        context: $(document).find('#contentDireccionesCheckout'),
        complete : function() {
            obj.initDireccionesCheckout();
        }
    });
};

Checkout.prototype.viewInfoCheckout = function () {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'checkout/viewInfoCheckout',
        context: $(document).find('#contentInfoCheckout'),
        loader : obj.$titleInfoCheckout,
        complete : function() {
            obj.initFormInfo();
        }
    });
};

Checkout.prototype.viewDireccionCheckout = function () {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'checkout/viewDireccionCheckout',
        context: $(document).find('#contentDireccionCheckout'),
        loader : obj.$titleDireccionCheckout,
        complete : function() {
            obj.initFormDireccion();
        }
    });
};

Checkout.prototype.viewPuntosCheckout = function () {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'checkout/viewPuntosCheckout',
        context: $(document).find('#contentPuntosCheckout'),
        loader : obj.$titlePuntosCheckout,
        complete : function() {
            obj.initFormPuntos();
        }
    });
};

Checkout.prototype.viewDireccionFormCheckout = function (idParticipanteDireccion) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'checkout/viewDireccionFormCheckout/' + idParticipanteDireccion,
        context: obj.modalDireccionContent,
        complete : function() {
            obj.initFormDirecciones();
        }
    });
};

Checkout.prototype.initCheckout = function() {
    var obj = this;

    obj.$titleResumenCheckout = $(document).find('#titleResumenCheckout');
    obj.$titleInfoCheckout = $(document).find('#titleInfoCheckout');
    obj.$titleDireccionCheckout = $(document).find('#titleDireccionCheckout');
    obj.$titlePuntosCheckout = $(document).find('#titlePuntosCheckout');

    obj.$titleInfoCheckout.css('pointer-events', 'none');
    obj.$titleDireccionCheckout.css('pointer-events', 'none');
    obj.$titlePuntosCheckout.css('pointer-events', 'none');
};

Checkout.prototype.initDireccionesCheckout = function() {
    var obj = this;
    var btnAgregarDireccion = $(document).find('#btnAgregarDireccion');

    btnAgregarDireccion.on('click', function(e) {
        e.preventDefault();
        obj.viewDireccionFormCheckout(0);
        obj.modalDireccion.modal();
    });

    $(document).find('.editarDireccion').on('click', function(e) {
       e.preventDefault();
       var id = $(this).attr('data-id');
       obj.viewDireccionFormCheckout(id);
        obj.modalDireccion.modal();
    });
};

Checkout.prototype.initFormInfo = function() {
    var obj = this;
    obj.next(obj.$titleResumenCheckout, obj.$titleInfoCheckout);
    obj.$titleInfoCheckout.css('pointer-events', 'auto');
    obj.infoCheckoutLoad = true;
    obj.scrollTo(obj.$titleInfoCheckout);

    obj.$frmInfoCheckout = $(document).find('#frmInfoCheckout');
    obj.$frmInfoCheckout.validate({
        ignore: "",
        rules : {
            infoNombres : {
                required : true
            },
            infoApPaterno : {
                required : true
            },
            infoApMaterno : {
                required : true
            },
            infoEmail : {
                required : true,
                email: true
            },
            infoTelefono : {
                required : true
            },
            infoCelular : {
                required : true
            }
        },
        messages : {
            infoNombres : {
                required : 'Ingrese su nombre'
            },
            infoApPaterno : {
                required : 'Ingrese su apellido paterno'
            },
            infoApMaterno : {
                required : 'Ingrese su apellido materno'
            },
            infoEmail : {
                required : 'Ingrese su correo electronico',
                email: 'Formato de correo electronico'
            },
            infoTelefono : {
                required : 'Ingrese su telefono fijo'
            },
            infoCelular : {
                required : 'Ingrese su telefono celular'
            }
        }
    });
};

Checkout.prototype.initFormDireccion = function() {
    var obj = this;

    // obj.$frmDireccionCheckout.find('select[name=dirProvincia]').select2();

    // obj.$frmDireccionCheckout.find('select[name=dirProvincia]').on('change', function() {
    //     obj.obtenerCiudad($(this).val());
    // });

    obj.next(obj.$titleInfoCheckout, obj.$titleDireccionCheckout);
    obj.$titleDireccionCheckout.css('pointer-events', 'auto');
    obj.direccionCheckoutLoad = true;

    obj.scrollTo(obj.$titleDireccionCheckout);

    obj.$frmDireccionCheckout = $(document).find('#frmDireccionCheckout');
    obj.$frmDireccionCheckout.validate({
        ignore: "",
        rules : {
            dirProvincia : {
                required : true,
                min : 1
            },
            dirCiudad : {
                required : true,
                min : 1
            },
            dirZona : {
                required : true,
                min : 1
            },
            dirVivienda : {
                required : true,
                min : 1
            },
            dirDireccion : {
                required : true
            },
            dirReferencia : {
                required : true,
                maxlength : 150
            }
        },
        messages : {
            dirProvincia : {
                required : 'Seleccione una provincia',
                min : 'Seleccione una provincia'
            },
            dirCiudad : {
                required : 'Seleccione una ciudad',
                min : 'Seleccione una ciudad'
            },
            dirZona : {
                required : 'Seleccione una zona',
                min : 'Seleccione una zona'
            },
            dirVivienda : {
                required : 'Seleccione un tipo de vivienda',
                min : 'Seleccione un tipo de vivienda'
            },
            dirDireccion : {
                required : 'Ingrese su direccion'
            },
            dirReferencia : {
                required : 'Ingrese una referencia',
                maxlength : 'Maximo 150 caracteres'
            }
        }
    });

    obj.$frmDireccionCheckout.find('select[name=dirProvincia]').select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });
    obj.$frmDireccionCheckout.find('select[name=dirCiudad]').select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });
    obj.$frmDireccionCheckout.find('select[name=dirZona]').select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });
    obj.$frmDireccionCheckout.find('select[name=dirVivienda]').select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.$frmDireccionCheckout.find('select[name=dirProvincia]').on('change', function() {
        obj.obtenerCiudad($(this).val());
    });
};

Checkout.prototype.initFormPuntos = function() {
    var obj = this;
    obj.next(obj.$titleDireccionCheckout, obj.$titlePuntosCheckout);
    obj.$titlePuntosCheckout.css('pointer-events', 'auto');
    obj.puntosCheckoutLoad = true;

    obj.scrollTo(obj.$titlePuntosCheckout);

    obj.$btnConfirmaCanje = $(document).find('#btnConfirmaCanje');

    obj.$btnConfirmaCanje.on('click', function(e) {
        e.preventDefault();
        obj.enviarPedido(false);
    });
};

Checkout.prototype.next = function(pasoActual, pasoSiguiente) {
    pasoActual.find('span.block-title').addClass('check');
    pasoActual.parent().find('div.accordeon-entry').hide();
    pasoSiguiente.parent().find('div.accordeon-entry').show('slow');
};

Checkout.prototype.cantidadCarrito = function() {
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

Checkout.prototype.obtenerCiudad = function(coddist) {
    var obj = this;

    Promotick.ajax.get({
        url : 'obtenerDistritos/' + obj.ecuadorCoddep + '/' + coddist,
        messageError : true,
        messageTitle : 'Checkout',
        before : function() {
            obj.$frmDireccionCheckout.find('select[name=dirCiudad]')
                .find('.select2-container')
                .LoadingOverlay("show");
        },
        success : function(response) {
            if (response.status) {

                obj.$frmDireccionCheckout.find('select[name=dirCiudad]')
                    .val(null)
                    .trigger('change')
                    .html('');

                $.each(response.data, function(i, d) {
                    option = new Option(d.nombreUbigeo, d.coddist, false, false);
                    obj.$frmDireccionCheckout.find('select[name=dirCiudad]').append(option).trigger('change');
                });

            } else {
                obj.$frmDireccionCheckout.find('select[name=dirCiudad]')
                    .val(null)
                    .trigger('change')
                    .html('');
            }
        },
        complete : function() {
            obj.$frmDireccionCheckout.find('select[name=dirCiudad]')
                .find('.select2-container')
                .LoadingOverlay("hide", true);
        }
    });
};

Checkout.prototype.enviarPedido = function(incluyeEncuesta) {
    var obj = this;


    var direccion = $(document).find('input.radioDireccion:checked');
    if (direccion.length === 0) {
        Promotick.toast.warning('Debe seleccionar/crear una direccion para el pedido', 'Direccion de pedido');
    } else {
        if (obj.encuestaEnviada) {
            var requestDireccion = {
                ubigeo : {
                    codpais : obj.ecuador,
                    coddep : obj.ecuadorCoddep,
                    codprov : direccion.attr('data-codprov'),
                    coddist : direccion.attr('data-coddist')
                },
                zona : {
                    idZona : direccion.attr('data-idzona')
                },
                tipoVivienda : {
                    idTipoVivienda : direccion.attr('data-idVivienda')
                },
                direccionCalle : direccion.attr('data-direccion'),
                referencia : direccion.attr('data-referencia')
            };

            var requestPedido = {
                direccion : requestDireccion,
                encuestaPedido : obj.obtenerDatosEncuesta(incluyeEncuesta)
            };

            console.log(requestPedido)

            Promotick.ajax.post({
                url : obj.uri + 'pedido/registrarPedido',
                messageError : true,
                messageTitle : 'Checkout',
                data : JSON.stringify(requestPedido),
                before : function() {
                    $.LoadingOverlay('show');
                },
                success : function(response) {
                    if (response.status) {
                        Promotick.toast.success(response.message, 'Chechout');

                        obj.countPedido.html(0);
                        obj.puntosDisponibles.html(response.extra1);
                        obj.mostrarGracias();
                    } else {
                        obj.$btnConfirmaCanje.text('REINTENTAR!')
                    }
                },
                complete : function() {
                    $.LoadingOverlay('hide', true);
                }
            });
        } else {
            obj.mostrarEncuesta();
            obj.modalEncuesta.modal();
        }

    }

};

Checkout.prototype.mostrarGracias = function() {
    var obj = this;
    Promotick.render.get({
        url : obj.uri + 'checkout/viewGraciasCheckout',
        context: obj.contentCheckout,
        complete : function() {
        }
    })
};

Checkout.prototype.comprarPuntos = function() {
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'payment/getUrlV2',
        messageError : true,
        messageTitle : 'error al comprar puntos',
        before : function() {
            $.LoadingOverlay('show');
        },
        success : function(response) {
            $.LoadingOverlay('hide', true);
            if (response.status) {
                // Promotick.toast.success(response.message, 'comprar puntos');
                obj.mostrarModalCompra(response.data);

                if(parseInt(response.data.puntosParticipante * 1.5) >= response.data.puntosSubTotal){
                    obj.initPayments(response.data);
                }
            }
        }
    });
};

Checkout.prototype.initPayments = function(data) {
    var obj = this;
    obj.datosVenta = data;
    obj.applicationPayments = new PaymentezCheckout.modal({
        client_app_code: data.application_code,
        client_app_key: data.application_key,
        locale: 'es',
        env_mode: data.application_mode, // `prod`, `stg` to change environment. Default is `stg`
        onOpen: function () {
        },
        onClose: function () {
            obj.applicationPayments = null;
            obj.datosVenta = null;
        },
        onResponse: function (response) {
            if (response.hasOwnProperty("error")) {
                alert(response.type + ": " + response.description + " - " + response.help);
            } else {
                Promotick.ajax.post({
                    url: obj.uri + 'payment/responsePay',
                    data: JSON.stringify({
                        authorization_code: response.transaction.authorization_code,
                        carrier: response.transaction.carrier_code,
                        transaction_id: response.transaction.id,
                        paid_date: response.transaction.payment_date,
                        status: response.transaction.status,
                        status_detail: response.transaction.status_detail,
                        dev_reference: response.transaction.dev_reference,
                        message: response.transaction.message,
                        transaction_reference: response.card.transaction_reference
                    }),
                    dataType: "json",
                    beforeSend: function () {
                        $('.home').block({
                            message: '<h2><img src=\"' + obj.rutaRecursos + 'images/loading.gif" />Se esta Procesando su Compra de puntos...</h2>',
                            css: {fontSize: '25px'}
                        });
                    },
                    error: function (a, b) {
                        alert('Ocurrio error');
                    },
                    success: function (response) {
                        if (response.status) {
                            //redirect
                            //window.location.replace(obj.uri + 'payment/confirmacion/' + dataPay.mensaje);
                            window.location.replace(obj.uri + 'checkout');
                        } else {
                            alert(response.mensaje);
                            //$('.add-payment-dinamico-error').html('');
                            //$('.add-payment-dinamico-error').append(templatePaymentError(dataPay));
                            //$('#add-payment-error').addClass('visible active');
                        }
                    },
                    complete: function () {
                        $('.div-cuerpo').unblock();
                    }
                });

            }
        }
    })
};


Checkout.prototype.mostrarModalCompra = function(data) {
    var obj = this;
    Promotick.render.get({
        url : obj.uri + 'checkout/viewCompraPuntos',
        context: obj.modalCompraContent,
        complete : function() {
            obj.modalCompra.modal();
            $("#spnPuntos").text(data.puntos);
            $("#spnMonto").text(data.montoMasIva);
            $("#spnIVA").text(data.iva);
            obj.dvMensajePuntos.hide();
            obj.dvMensajeComponente.hide();
            obj.puntosSubTotal = data.puntosSubTotal;
            obj.puntosParticipante = data.puntosParticipante;
        }
    })
};


Checkout.prototype.cleanField = function($input) {
    var value = $input.val();
    if (value === '') {
        return null;
    }
    return value;
};

Checkout.prototype.scrollTo = function(context) {
    $('html, body').animate({
        scrollTop: (context.offset().top - 120)
    }, 1000);
};


Checkout.prototype.initFormDirecciones = function() {
    var obj = this;

    obj.$frmDireccionesCheckout = $(document).find('#frmDirecciones');
    obj.$frmDireccionesCheckout.validate({
        ignore: "",
        rules : {
            // inptNombreDireccion : {
            //     required : true,
            // },
            dirProvincia : {
                required : true,
                min : 1
            },
            dirCiudad : {
                required : true,
                min : 1
            },
            dirTipoZona : {
                required : true,
                min : 1
            },
            dirTipoConstruccion : {
                required : true,
                min : 1
            },
            inptDireccion : {
                required : true
            },
            inptReferencia : {
                required : true,
                maxlength : 150
            }
        },
        messages : {
            // inptNombreDireccion : {
            //     required : 'Ingrese un nombre para esta direccion',
            // },
            dirProvincia : {
                required : 'Seleccione una provincia',
                min : 'Seleccione una provincia'
            },
            dirCiudad : {
                required : 'Seleccione una ciudad',
                min : 'Seleccione una ciudad'
            },
            dirTipoZona : {
                required : 'Seleccione una zona',
                min : 'Seleccione una zona'
            },
            dirTipoConstruccion : {
                required : 'Seleccione un tipo de vivienda',
                min : 'Seleccione un tipo de vivienda'
            },
            inptDireccion : {
                required : 'Ingrese su direccion'
            },
            inptReferencia : {
                required : 'Ingrese una referencia',
                maxlength : 'Maximo 150 caracteres'
            }
        }
    });

    obj.$frmDireccionesCheckout.find('select[name=dirProvincia]').select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1,
        width: '100%'
    });
    obj.$frmDireccionesCheckout.find('select[name=dirCiudad]').select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1,
        width: '100%'
    });
    obj.$frmDireccionesCheckout.find('select[name=dirTipoZona]').select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1,
        width: '100%'
    });
    obj.$frmDireccionesCheckout.find('select[name=dirTipoConstruccion]').select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1,
        width: '100%'
    });

    obj.$frmDireccionesCheckout.find('select[name=dirProvincia]').on('change', function() {
        obj.obtenerCiudadDireccion($(this).val());
    });

    obj.$frmDireccionesCheckout.find('#btnGuardarDireccion').on('click', function(e) {
        debugger;;
        var idParticipanteDireccion = obj.$frmDireccionesCheckout.find('#idParticipanteDireccion');
        if (obj.$frmDireccionesCheckout.valid()) {

            obj.guardarDireccion(idParticipanteDireccion);

        } else {
            obj.$frmDireccionesCheckout.validate().focusInvalid();
        }
    });
};

Checkout.prototype.obtenerCiudadDireccion = function(coddist) {
    var obj = this;

    Promotick.ajax.get({
        url : 'obtenerDistritos/' + obj.ecuadorCoddep + '/' + coddist,
        messageError : true,
        messageTitle : 'Checkout',
        before : function() {
            obj.$frmDireccionesCheckout.find('select[name=dirCiudad]')
                .find('.select2-container')
                .LoadingOverlay("show");
        },
        success : function(response) {
            if (response.status) {

                obj.$frmDireccionesCheckout.find('select[name=dirCiudad]')
                    .val(null)
                    .trigger('change')
                    .html('');

                $.each(response.data, function(i, d) {
                    option = new Option(d.nombreUbigeo, d.coddist, false, false);
                    obj.$frmDireccionesCheckout.find('select[name=dirCiudad]').append(option).trigger('change');
                });

            } else {
                obj.$frmDireccionesCheckout.find('select[name=dirCiudad]')
                    .val(null)
                    .trigger('change')
                    .html('');
            }
        },
        complete : function() {
            obj.$frmDireccionesCheckout.find('select[name=dirCiudad]')
                .find('.select2-container')
                .LoadingOverlay("hide", true);
        }
    });
};

Checkout.prototype.guardarDireccion = function(idParticipanteDireccion) {
    var obj = this;
    var request = {
        idParticipanteDireccion: obj.cleanField(idParticipanteDireccion),
        tagDireccion: obj.cleanField(obj.$frmDireccionesCheckout.find('input[name=inptNombreDireccion]')),
        direccion: {
            ubigeo : {
                codpais : obj.ecuador,
                coddep : obj.ecuadorCoddep,
                codprov : obj.cleanField(obj.$frmDireccionesCheckout.find('select[name=dirProvincia]')),
                coddist : obj.cleanField(obj.$frmDireccionesCheckout.find('select[name=dirCiudad]'))
            },
            zona : {
                idZona : obj.cleanField(obj.$frmDireccionesCheckout.find('select[name=dirTipoZona]'))
            },
            tipoVivienda : {
                idTipoVivienda : obj.cleanField(obj.$frmDireccionesCheckout.find('select[name=dirTipoConstruccion]'))
            },
            direccionCalle : obj.cleanField(obj.$frmDireccionesCheckout.find('input[name=inptDireccion]')),
            referencia : obj.cleanField(obj.$frmDireccionesCheckout.find('input[name=inptReferencia]')),
        }
    };

    Promotick.ajax.post({
        url : obj.uri + 'pedido/registrarDireccion',
        messageError : true,
        messageTitle : 'Checkout',
        data : JSON.stringify(request),
        before : function() {
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Chechout');
            }
        },
        complete : function() {
            $.LoadingOverlay('hide', true);
            obj.modalDireccion.modal('hide');
            obj.viewDireccionesCheckout();
        }
    });

};

Checkout.prototype.mostrarEncuesta = function() {
    var obj = this;
    Promotick.render.get({
        url : obj.uri + 'checkout/viewEncuestaProcesoCanje',
        context: obj.modalEncuestaContent,
        complete : function() {
            obj.initEncuesta();
        }
    })
};

Checkout.prototype.initEncuesta = function() {

    var obj = this;

    obj.$frmEncuestaCheckout = $(document).find('#frmEncuesta');
    obj.$frmEncuestaCheckout.validate({
        rules: {
            txtComentarios: {
                maxlength: 150,
                required: false
            }
        },
        messages : {
            txtComentarios: {
                maxlength: 'Ingrese comentarios',
                required: 'Maximo 150 caracteres'
            }
        }
    });

    obj.$frmEncuestaCheckout.find('#btnEnviar').on('click', function(e) {
        e.preventDefault();
        var value = obj.$frmEncuestaCheckout.find('input[name="rptaUno"]:checked').val();
        var valueDos = obj.$frmEncuestaCheckout.find('input[name="rptaDos"]:checked').val();
        if(value === undefined || valueDos === undefined || value === '' || valueDos === ''){
            Promotick.toast.warning('Indique respuestas', 'Encuesta');
            return;
        }
        if (obj.$frmEncuestaCheckout.valid()) {
            obj.guardarEncuesta();
        }
    });

    obj.$frmEncuestaCheckout.find('#btnSaltar').on('click', function(e) {
        e.preventDefault();
        obj.saltarEncuesta();
    });

};

Checkout.prototype.obtenerDatosEncuesta = function (incluye){
    var obj = this;
    var request = null;
    if(incluye) {
         request = {
            idTipoEncuesta: 2, // Encuesta tipo proceso canje
            detalles: [
                {
                    pregunta: obj.$frmEncuestaCheckout.find('#preguntaUno').html(),
                    respuesta: obj.$frmEncuestaCheckout.find('input[name="rptaUno"]:checked').val()
                },
                {
                    pregunta: obj.$frmEncuestaCheckout.find('#preguntaDos').html(),
                    respuesta: obj.$frmEncuestaCheckout.find('input[name="rptaDos"]:checked').val()
                },
                {
                    pregunta: obj.$frmEncuestaCheckout.find('#preguntaTres').html(),
                    respuesta: obj.$frmEncuestaCheckout.find('#txtComentarios').val()
                },
            ],
        }
    }
    return request;
};

Checkout.prototype.guardarEncuesta = function() {
    var obj = this;

    obj.modalEncuesta.modal('hide');
    obj.encuestaEnviada = true;
    obj.enviarPedido(true);

    /*
    Promotick.ajax.post({
        url : obj.uri + 'encuesta/guardarDatos',
        data : JSON.stringify(obj.obtenerDatosEncuesta(true)),
        messageError: true,
        messageTitle: 'Checkout',
        before : function() {
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                obj.modalEncuesta.modal('hide');
                obj.encuestaEnviada = true;
                obj.enviarPedido();
            }
        },
        complete: function() {
            $.LoadingOverlay('hide', true);
        }
    });
    */
};

Checkout.prototype.saltarEncuesta = function() {
    var obj = this;

    obj.modalEncuesta.modal('hide');
    obj.encuestaEnviada = true;
    obj.enviarPedido(false);

    /*
    Promotick.ajax.post({
        url : obj.uri + 'encuesta/saltarEncuesta',
        messageError: true,
        messageTitle: 'Encuesta',
        before : function() {
        },
        success : function(response) {
            if (response.status) {
                obj.modalEncuesta.modal('hide');
                obj.encuestaEnviada = true;
                obj.enviarPedido();
            }
        },
    });
    */
};

Checkout.prototype.validarDescuento = function(descuento) {
    var obj = this;


    Promotick.ajax.post({
        url : obj.uri + 'pedido/validarDescuento',
        data : JSON.stringify(descuento),
        messageError: true,
        messageTitle: 'Descuento',
        before : function() {
            $(".error-descuento").text("");
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                $(".success-descuento").text("Descuento validado con éxito");
                setTimeout(() => {
                    location.reload();
                }, 350);

            }else{
                $(".error-descuento").text("Descuento no disponible");
            }
        },
        complete: function() {
            $.LoadingOverlay('hide', true);
        }
    });

};

Checkout.prototype.removerDescuento = function() {
    var obj = this;


    Promotick.ajax.get({
        url : obj.uri + 'pedido/removerDescuento',
        messageError: true,
        messageTitle: 'Remover Descuento',
        before : function() {
            $(".error-descuento").text("");
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                setTimeout(() => {
                    location.reload();
                }, 200);

            }
        },
        complete: function() {
            $.LoadingOverlay('hide', true);
        }
    });

};