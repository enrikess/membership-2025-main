function VueloDetalle(uri) {
    this.uri = uri;
    this.formRegistro = $('#formRegistro');
    this.btnGuardar = $('#btnGuardar');
    this.clase = $('#clase');
    this.nroDocumento = $('#nroDocumento');
    this.puntos = $('#puntos');
    this.vuelo = $('#vuelo');
}

VueloDetalle.prototype.init = function () {
    this.handle();
    this.initValidate();
};

VueloDetalle.prototype.handle = function () {
    var obj = this;

    obj.clase.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.btnGuardar.on('click', function(e) {
         e.preventDefault();

         if (obj.formRegistro.valid()) {
            obj.registrar();
         } else {
             obj.formRegistro.validate().focusInvalid();
         }
    });
};

VueloDetalle.prototype.initValidate = function() {
    var obj = this;

    obj.formRegistro.validate({
        rules : {
            nroDocumento : {
                required: true
            },
            puntos : {
                required : true,
                digits : true,
                min : 1
            },
            vuelo : {
                required : true
            },
            clase : {
                required : true
            }
        },
        messages : {
            nroDocumento : {
                required: 'Ingrese un numero de documento'
            },
            puntos : {
                required : 'Ingrese la cantidad de puntos',
                digits : 'Ingrese un valor entero positivo',
                min : 'Ingrese como minimo 1 punto'
            },
            vuelo : {
                required : 'Ingrese una descripcion del vuelo'
            },
            clase : {
                required : 'Seleccione una clase de vuelo'
            }
        }
    });
};

VueloDetalle.prototype.registrar = function() {
    var obj = this;

    var request = {
        nroDocumento : obj.nroDocumento.val(),
        monto : obj.puntos.val(),
        descripcion : '(' + obj.clase.val() + ') ' + obj.vuelo.val()
    };

    Promotick.ajax.post({
        url : obj.uri + 'vuelos/registrar',
        data : JSON.stringify(request),
        messageTitle : 'Registro de vuelo',
        messageError : true,
        before : function() {
            obj.formRegistro.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Registro de vuelo');
                Promotick.scrollTop();
                obj.formRegistro[0].reset();
                obj.clase.select2('val', '');
            }
        },
        complete : function() {
            obj.formRegistro.LoadingOverlay('hide', true);
        }
    })
};