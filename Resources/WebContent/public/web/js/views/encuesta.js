function Encuesta(uri) {
    this.uri = uri;
    this.formEncuesta = $("#frm-encuesta");
    this.btnEnviar = $("#btnEnviar");
    this.btnSaltar = $("#btnSaltar");
    this.idPedido = $("#idPedido");
    this.preguntaUno = $("#preguntaUno");
    this.preguntaDos = $("#preguntaDos");
    this.preguntaTres = $("#preguntaTres");
}

Encuesta.prototype.init = function() {
    this.handler();
};

Encuesta.prototype.handler = function() {

    var obj = this;

    obj.btnEnviar.on('click', function(e) {
        e.preventDefault();
        var value = $('input[name="rptaUno"]:checked').val();
        var valueDos = $('input[name="rptaDos"]:checked').val();
        if(value === undefined || valueDos === undefined || value === '' || valueDos === ''){
            Promotick.toast.warning('Indique respuestas', 'Encuesta');
            return;
        }
        if (obj.formEncuesta.valid()) {
            obj.guardarEncuesta();
        }
    });

    obj.btnSaltar.on('click', function(e) {
        e.preventDefault();
        obj.saltarEncuesta();
    });

    obj.formEncuesta.validate({
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
};

Encuesta.prototype.guardarEncuesta = function() {
    var obj = this;
    var request = {
        idTipoEncuesta: 1, // Encuesta tipo pedido
        pedido: {
            idPedido: obj.idPedido.val(),
        },
        detalles: [
            {
                pregunta: obj.preguntaUno.html(),
                respuesta: $('input[name="rptaUno"]:checked').val()
            },
            {
                pregunta: obj.preguntaDos.html(),
                respuesta: $('input[name="rptaDos"]:checked').val()
            },
            {
                pregunta: obj.preguntaTres.html(),
                respuesta: $("#txtComentarios").val()
            },
        ],
    }

    Promotick.ajax.post({
        url : obj.uri + 'encuesta/guardarDatos',
        data : JSON.stringify(request),
        messageError: true,
        messageTitle: 'Encuesta',
        before : function() {
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Encuesta', {
                    timeOut : 2000,
                    onHidden : function() {
                        $.LoadingOverlay('hide', true);
                        window.location.href = obj.uri;
                    }
                })
            } else {
                $.LoadingOverlay('hide', true);
            }
        },
    });
}

Encuesta.prototype.saltarEncuesta = function() {
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'encuesta/saltarEncuesta',
        messageError: true,
        messageTitle: 'Encuesta',
        before : function() {
        },
        success : function(response) {
            if (response.status) {
                window.location.href = obj.uri;
            }
        },
    });
}
