function CapacitacionCuestionario(uri) {
    this.uri = uri;
    this.frmCuestionario = $('#frmCuestionario');
    this.btnEnviar = $('#btnEnviar');
    this.divCuestionario = $('#divCuestionario');
    this.divCorrecto = $('#divCorrecto');
    this.divError = $('#divError');
}

CapacitacionCuestionario.prototype.init = function() {
    this.handler();
};

CapacitacionCuestionario.prototype.handler = function() {
    var obj = this;
    var config = {
        rules: {},
        messages: {}
    };

    $('input').each(function() {
        var name = $(this).attr('name');
        if (name !== undefined && config.rules[name] === undefined) {
            config.rules[name] = {
                required: true
            };
            config.messages[name] = {
                required: 'Obligatorio'
            };
        }
    });

    obj.frmCuestionario.validate(config);

    obj.btnEnviar.on('click', function(e) {
        e.preventDefault();
        if (obj.frmCuestionario.valid()) {
            obj.enviarCuestionario(config);
        } else {
            obj.frmCuestionario.validate().focusInvalid();
        }
    })

};

CapacitacionCuestionario.prototype.enviarCuestionario = function(config) {
    var obj = this;

    var detalles = [];

    $.each($('.pregunta'), function() {

        var pregunta = {
            idCapacitacionPregunta: $(this).attr('data-id-pregunta'),
            respuestas: []
        };

        $.each($(this).find('input:checked'), function() {
            var respuesta = {
                idCapacitacionRespuesta: $(this).val()
            };
            pregunta.respuestas.push(respuesta);
        });

        detalles.push(pregunta);
    });

    var request = {
        idCapacitacion: obj.frmCuestionario.attr('data-id-capacitacion'),
        preguntasRespondidas: detalles
    };

    Promotick.ajax.post({
        url : obj.uri + 'capacitacion/guardarCuestionario',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Enviar cuestionario',
        before : function() {
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success('El cuestionario fue enviado correctamente', 'Enviar cuestionario');
                $('html, body').animate({scrollTop:0}, 'slow');
                obj.frmCuestionario[0].reset();
                obj.divCuestionario.hide();
                if (response.data.calificacion <= 7) {
                    obj.divError.show();
                } else {
                    obj.divCorrecto.show();
                }
            }
        },
        complete : function() {
            $.LoadingOverlay('hide');
        }
    });

};