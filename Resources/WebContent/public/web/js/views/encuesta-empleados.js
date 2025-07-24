function EncuestaEmpleados(uri){
    this.uri = uri;
    this.contentEncuesta = $('#contentEncuesta');
    this.btnEmpezar = $('#btnEmpezar');
    this.descripcionEncuesta = $('#descripcionEncuesta');
    this.idCampania = $('#idCampania').val();
    this.idEncuesta = $('#idEncuesta').val();
    this.imagenDinamica = $('#imagenDinamica');
    this.imagenEncuesta1 = $('#imagenEncuesta1').val();
    this.imagenEncuesta2 = $('#imagenEncuesta2').val();
    this.imagenEncuesta3 = $('#imagenEncuesta3').val();
}

EncuestaEmpleados.prototype.init = function() {
    this.handler();
};

EncuestaEmpleados.prototype.handler = function() {
    var obj = this;

    obj.cambiarImagen(1);

    obj.btnEmpezar.on('click', function(e) {
        e.preventDefault();
        obj.viewPreguntasEncuesta();
    })
};

EncuestaEmpleados.prototype.viewPreguntasEncuesta = function() {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'encuesta-empleados/viewEncuestaPreguntas/' + obj.idEncuesta,
        context : obj.contentEncuesta,
        complete : function() {
            obj.cambiarImagen(2);
            obj.initPreguntasEncuesta();
        }
    });

};


EncuestaEmpleados.prototype.initPreguntasEncuesta = function() {
    var obj = this;

    var selects = obj.contentEncuesta.find('.respuestaTexto');

    $.each(selects, function(i, e){
        $(e).select2();
    });

    obj.contentEncuesta.find('#btnEnviarRespuestas').on('click', function(e){
        e.preventDefault();
        if (!obj.validar(selects)) {
            Promotick.toast.warning('Debe responder todas las preguntas', 'Encuesta')
        } else {
            obj.enviarEncuesta(obj.contentEncuesta.find('.preguntaObj'));
        }
    })
};

EncuestaEmpleados.prototype.validar = function(selects) {
    var obj = this;
    var result = true;
    $.each(selects, function(i, e){
        if ($(e).val() === '') {
            result = false;
            return false;
        }
    });
    return result;
};

EncuestaEmpleados.prototype.enviarEncuesta = function(preguntaObj) {
    var obj = this;
    var detalles = []

    $.each(preguntaObj, function(i, e){
        var pregunta = $(e).find('.preguntaTexto').text();
        var respuesta = $(e).find('.respuestaTexto').val();

        detalles.push({
            textoPregunta: pregunta,
            textoRespuesta: respuesta,
        })
    });

    var request = {
        idEncuesta: obj.idEncuesta,
        idCampania: obj.idCampania,
        detalles: detalles,
    }

    Promotick.ajax.post({
        url : obj.uri + 'encuesta-empleados/guardarEncuesta',
        data : JSON.stringify(request),
        messageError: true,
        messageTitle: 'Registro de Encuesta',
        before : function() {
            $.LoadingOverlay('show')
        },
        success : function(response) {
            if (response.status) {
                obj.cambiarImagen(3);
                obj.viewGracias();
            }
        },
        complete : function () {
            $.LoadingOverlay('hide', true)
        }
    });
};

EncuestaEmpleados.prototype.viewGracias = function() {
    var obj = this;

    obj.descripcionEncuesta.hide();

    Promotick.render.get({
        url : obj.uri + 'encuesta-empleados/viewGracias',
        context : obj.contentEncuesta,
        complete : function() {
        }
    });
};

EncuestaEmpleados.prototype.cambiarImagen = function(pos) {
    var obj = this;
    if (pos === 1 && obj.imagenEncuesta1) {
        obj.imagenDinamica.attr("src", obj.imagenEncuesta1);
    } else if (pos === 2 && obj.imagenEncuesta2) {
        obj.imagenDinamica.attr("src", obj.imagenEncuesta2);
    } else if (pos === 3 && obj.imagenEncuesta3) {
        obj.imagenDinamica.attr("src", obj.imagenEncuesta3);
    } else {
        obj.imagenDinamica.hide();
    }
};