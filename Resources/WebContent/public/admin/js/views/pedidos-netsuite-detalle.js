function PedidosNetsuiteDetalle(uri) {
    this.uri = uri;
    this.ecuador = '02';
    this.ecuadorCoddep = '01';
    this.slcProvincia = $('#slcProvincia');
    this.slcCiudad = $('#slcCiudad');
    this.slcZona = $('#slcZona');
    this.slcTipoVivienda = $('#slcTipoVivienda');
    this.contentDetalle = $('#contentDetalle');
    this.idPedido = $('#idPedido');
    this.formRegistro = $('#form-registro');
    this.nroDocumentoPedido = $('#nroDocumentoPedido');
    this.nombrePedido = $('#nombrePedido');
    this.apellidoPaternoPedido = $('#apellidoPaternoPedido');
    this.apellidoMaternoPedido = $('#apellidoMaternoPedido');
    this.emailPedido = $('#emailPedido');
    this.telefonoPedido = $('#telefonoPedido');
    this.movilPedido = $('#movilPedido');
    this.direccionCalle = $('#direccionCalle');
    this.referencia = $('#referencia');
    this.btnGuardar = $('#btnGuardar');
}

PedidosNetsuiteDetalle.prototype.init = function() {
    this.handle();
    this.listarDetalle();
    this.initValidate();
};

PedidosNetsuiteDetalle.prototype.handle = function() {
    var obj = this;

    obj.slcProvincia.select2({
        placeholder: "Seleccionar"
    });
    obj.slcCiudad.select2({
        placeholder: "Seleccionar"
    });
    obj.slcZona.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });
    obj.slcTipoVivienda.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        if (obj.formRegistro.valid()) {
            obj.guardarPedido(this);
        } else {
            obj.formRegistro.validate().focusInvalid();
        }
    });
};

PedidosNetsuiteDetalle.prototype.initValidate = function() {
    var obj = this;

    obj.formRegistro.validate({
        rules : {
            nroDocumentoPedido : {
                required : true,
                min : 8
            },
            nombrePedido : {
                required : true
            },
            apellidoPaternoPedido : {
                required : true
            },
            apellidoMaternoPedido : {
                required : true
            },
            emailPedido : {
                required : true,
                email :true
            },
            telefonoPedido : {
                required : {
                    depends: function (element) {
                        return obj.movilPedido.val() === '';
                    }
                }
            },
            movilPedido : {
                required : {
                    depends: function (element) {
                        return obj.telefonoPedido.val() === '';
                    }
                }
            },
            direccionCalle : {
                required : true,
                maxlength : 150
            },
            referencia : {
                required : true,
                maxlength : 150
            },
            slcProvincia : {
                required : true,
                min : 1
            },
            slcCiudad : {
                required : true,
                min : 1
            },
            slcZona : {
                required : true,
                min : 1
            },
            slcTipoVivienda : {
                required : true,
                min : 1
            }
        } ,
        messages : {
            nroDocumentoPedido : {
                required : 'Ingrese el numero de documento',
                min : 'Ingrese al menos 8 caracteres'
            },
            nombrePedido : {
                required : 'Ingrese el nombre del participante'
            },
            apellidoPaternoPedido : {
                required : 'Ingrese el apellido paterno del participante'
            },
            apellidoMaternoPedido : {
                required : 'Ingrese el apellido materno del participante'
            },
            emailPedido : {
                required : 'Ingrese el email del participante',
                email : 'Formato de email incorrecto'
            },
            telefonoPedido : {
                required : 'Ingrese el telefono fijo del participante'
            },
            movilPedido : {
                required : 'Ingrese el telefono movil del participante'
            },
            direccionCalle : {
                required : 'Ingrese la direccion de entrega',
                maxlength : 'Ingrese como maximo 150 caracteres'
            },
            referencia : {
                required : 'Ingrese una referencia de entrega',
                maxlength : 'Ingrese como maximo 150 caracteres'
            },
            slcProvincia : {
                required : 'Seleccione una provincia',
                min : 'Seleccione una provincia'
            },
            slcCiudad : {
                required : 'Seleccione una ciudad',
                min : 'Seleccione una ciudad'
            },
            slcZona : {
                required : 'Seleccione una zona',
                min : 'Seleccione una zona'
            },
            slcTipoVivienda : {
                required : 'Seleccione un tipo de vivienda',
                min : 'Seleccione un tipo de vivienda'
            }
        }
    });
};

PedidosNetsuiteDetalle.prototype.guardarPedido = function(handle) {
    var obj = this;
    var loader = null;

    var request = {
        idPedido : obj.idPedido.val(),
        nroDocumentoPedido : obj.nroDocumentoPedido.val(),
        nombrePedido : obj.nombrePedido.val(),
        apellidoPaternoPedido : obj.apellidoPaternoPedido.val(),
        apellidoMaternoPedido : obj.apellidoMaternoPedido.val(),
        emailPedido : obj.emailPedido.val(),
        telefonoPedido : obj.telefonoPedido.val(),
        movilPedido : obj.movilPedido.val(),
        direccion : {
            direccionCalle : obj.direccionCalle.val(),
            referencia : obj.referencia.val(),
            ubigeo : {
                codpais : obj.ecuador,
                coddep : obj.ecuadorCoddep,
                codprov : obj.slcProvincia.val(),
                coddist : obj.slcCiudad.val()
            },
            zona : {
                idZona : obj.slcZona.val()
            },
            tipoVivienda : {
                idTipoVivienda : obj.slcTipoVivienda.val()
            }
        }
    };

    Promotick.ajax.post({
        url : obj.uri + 'pedidos/netsuite/actualizarPedido',
        messageError : true,
        messageTitle : 'Actualizacion de pedido',
        data : JSON.stringify(request),
        before : function() {
            loader = Ladda.create(handle);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion de pedido');
                $('html, body').animate({scrollTop:0}, 'slow');
            }
        },
        complete : function() {
            loader.stop();
        }
    });
};

PedidosNetsuiteDetalle.prototype.listarDetalle = function() {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'pedidos/netsuite/listarDetallePedido/' + obj.idPedido.val(),
        context : obj.contentDetalle
    });
};