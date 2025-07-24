function CapacitacionesPreguntas(uri) {
    this.uri = uri;
    this.slcCapacitacion = $('#slcCapacitacion');
    this.divPreguntas = $('#divPreguntas');
    this.btnNuevaPregunta = $('#btnNuevaPregunta');
    this.alertPreguntas = $('#alertPreguntas');
    this.modalPregunta = $('#modalPregunta');
    this.modalPreguntaContent = $('#modalPreguntaContent');
    this.modalPreguntaAceptar = $('#modalPreguntaAceptar');
    this.grid = $('#grid');
    this.$frmIdCapacitacionPregunta = null;
    this.$frmIdCapacitacion = null;
    this.$frmTipoPregunta = null;
    this.$frmPregunta = null;
    this.$frmBtnAgregarRespuesta = null;
    this.$frmDivRespuestas = null;
    this.$frmDivRespuestasNuevas = null;
    this.$sortableRespuestas = null;
    this.$btnRespuestaEliminar = null;
    this.$btnRespuestaCrear = null;
    this.$frmFormPregunta = null;
    this.$btnPreguntaActualizar = null;
    this.$frmErrorValidate = null;
    this.$btnRespuestaActualizar = null;
    this.flagActualizarGrid = false;
}

CapacitacionesPreguntas.prototype.init = function() {
    this.handler();
    this.listarPreguntas();
};

CapacitacionesPreguntas.prototype.handler = function() {
    var obj = this;

    obj.slcCapacitacion.select2();

    obj.slcCapacitacion.on('change', function() {
        obj.seleccionCapacitacion();
    });

    obj.modalPregunta.on('hidden.bs.modal', function () {
        obj.modalPreguntaContent.html('');
        obj.modalPreguntaAceptar.hide();
        if (obj.flagActualizarGrid) {
            obj.gridReload();
            obj.flagActualizarGrid = false;
        }
    });

    $(document).on('click', '.onoffswitch', function(e) {

        var $checkbox = $(this).parent().find('input[type=checkbox]');
        var checked = $checkbox.is(':checked');
        var idCapacitacionPregunta = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
                title: $.camelCase("-" + estado),
                text: 'Estas seguro de ' + estado + ' a esta pregunta ?',
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: 'Si, ' + estado + '!',
                cancelButtonText: "No, cancelar!",
                closeOnConfirm: false,
                closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm){
                    obj.actualizarEstado(idCapacitacionPregunta, checked, $checkbox);
                }
            }
        );
    });

    obj.btnNuevaPregunta.on('click', function(e) {
        e.preventDefault();
        obj.viewCapacitacionPreguntaForm({});
        obj.modalPregunta.modal({
            backdrop: 'static',
            keyboard: false
        });
    });

    $(document).on('click', '.showPregunta', function(e) {
        e.preventDefault();
        var id = $(this).attr('data-id');

        var rows = obj.grid.DataTable().rows().data();
        var data = null;
        $.each(rows, function(i, e) {
            if (e.idCapacitacionPregunta === parseInt(id)) {
                data = e;
            }
        });

        if (data == null) {
            Promotick.toast.warning('No se pudo obtener la informacion de la pregunta', 'Mostrar Pregunta');
        } else {
            obj.viewCapacitacionPreguntaForm(data);
            obj.modalPregunta.modal({
                backdrop: 'static',
                keyboard: false
            });
        }
    });
};

CapacitacionesPreguntas.prototype.seleccionCapacitacion = function() {
    var obj = this;
    var idCapacitacion = obj.slcCapacitacion.val();
    if (idCapacitacion === '0') {
        obj.divPreguntas.hide();
    } else {
        obj.divPreguntas.show();
        obj.gridReload();
    }
};

CapacitacionesPreguntas.prototype.listarPreguntas = function(){
    var obj = this;
    obj.grid.DataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": false,
        "bInfo" : true,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'capacitaciones/preguntas/listar',
            "type": "POST",
            "data" : function(d) {
                d.idCapacitacion = obj.slcCapacitacion.val();
            }
        },
        createdRow: function (row, data, dataIndex) {
            $(row).attr('data-id', data.idCapacitacionPregunta);
        },
        "columns" : [
            {"data" : "idCapacitacionPregunta", "orderable": false, render: this._formatID, "sClass" : "text-center"},
            {"data" : "tipoPreguntaEnum", "orderable": false},
            {"data" : "pregunta", "orderable": false},
            {"data" : "conteoRespuestas", "orderable": false, "sClass" : "text-center", render: this._formatRespuestas},
            {"data" : "estadoCapacitacionPregunta", "orderable": false, "class":"estado", "render": obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){
            var sortable = Sortable.create(document.getElementById('gridContent'), {
                animation: 150,
                handle: ".draggable-item",
                store: {
                    set: function (sortable) {
                        var orderArray = sortable.toArray();
                        if (orderArray.length > 1) {
                            var orderArrayBody = [];

                            $.each(orderArray, function(i, e) {
                                orderArrayBody.push({
                                    idCapacitacionPregunta: e,
                                    ordenPregunta: i
                                });
                            });

                            obj.guardarOrden(orderArrayBody);
                        }
                    }
                }

            });

            var d = obj.grid.DataTable().rows().data();
            var count = 0;
            $.each(d, function(i, e) {
                if (e.estadoCapacitacionPregunta) {
                    count++;
                }
            });

            if (count < 10) {
                obj.alertPreguntas.html('Es necesario almenos 10 preguntas activas para registrar la premiacion, actualmente tienes ' + count + ' preguntas');
                obj.alertPreguntas.show();
            } else {
                obj.alertPreguntas.html('');
                obj.alertPreguntas.hide();
            }


        }
    });
};

CapacitacionesPreguntas.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

CapacitacionesPreguntas.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idCapacitacionPregunta + '" data-id="' + full.idCapacitacionPregunta + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idCapacitacionPregunta + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

CapacitacionesPreguntas.prototype._formatID = function (data, type, full) {
    return '<i class="fa fa-align-justify draggable-item"></i> <a class="showPregunta" href="#" data-id="' + full.idCapacitacionPregunta + '">' + data + '</a>'
};

CapacitacionesPreguntas.prototype._formatRespuestas = function (data, type, full) {
    return '<label class="label label-sm label-success">' + data + ' respuestas</label>'
};

CapacitacionesPreguntas.prototype.actualizarEstado = function(idCapacitacionPregunta, checked, $checkbox) {
    var obj = this;

    var request = {
        idCapacitacionPregunta : idCapacitacionPregunta,
        estadoCapacitacionPregunta : !!checked
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/preguntas/actualizar-estado',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Actualizacion Estado',
        before : function() {
            loader = Ladda.create($button);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion Estado');
                $checkbox.prop('checked', !checked);
                obj.gridReload();
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};

CapacitacionesPreguntas.prototype.guardarOrden = function(list) {
    var obj = this;

    var request = {
        preguntas: list
    };

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/preguntas/guardarOrden',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Preguntas Orden',
        before : function() {
        },
        success : function(response) {
            if (response.status) {

            }
        },
        complete : function() {
        }
    });
};

CapacitacionesPreguntas.prototype.viewCapacitacionPreguntaForm = function(capacitacionPregunta) {
    var obj = this;

    Promotick.render.post({
        url : obj.uri + 'capacitaciones/preguntas/viewCapacitacionPreguntaForm',
        data: JSON.stringify(capacitacionPregunta),
        context : obj.modalPreguntaContent,
        before: function() {
            obj.modalPreguntaContent.html('');
        },
        complete : function() {
            obj.initCapacitacionPregunta();
        }
    });

};

CapacitacionesPreguntas.prototype.viewCapacitacionRespuestaItem = function(idTipoPregunta, esNuevoPregunta, content) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'capacitaciones/preguntas/viewCapacitacionRespuestaForm/' + idTipoPregunta + '/' + esNuevoPregunta,
        loader : content,
        before: function() {
        },
        success: function(result) {
            content.append(result).fadeIn('slow');
            if (!esNuevoPregunta) {
                obj.$frmBtnAgregarRespuesta.prop('disabled', true);
            }
        },
        complete : function() {
            obj.initCapacitacionRespuesta();
        }
    });

};

CapacitacionesPreguntas.prototype.initCapacitacionPregunta = function() {
    var obj = this;

    obj.$frmIdCapacitacionPregunta = obj.modalPreguntaContent.find('#frmIdCapacitacionPregunta');
    obj.$frmIdCapacitacion = obj.modalPreguntaContent.find('#frmIdCapacitacion');
    obj.$frmTipoPregunta = obj.modalPreguntaContent.find('#frmTipoPregunta');
    obj.$frmPregunta = obj.modalPreguntaContent.find('#frmPregunta');
    obj.$frmBtnAgregarRespuesta = obj.modalPreguntaContent.find('#frmBtnAgregarRespuesta');
    obj.$frmDivRespuestas = obj.modalPreguntaContent.find('#frmDivRespuestas');
    obj.$frmDivRespuestasNuevas = obj.modalPreguntaContent.find('#frmDivRespuestasNuevas');
    obj.$frmFormPregunta = obj.modalPreguntaContent.find('#frmFormPregunta');
    obj.$btnPreguntaActualizar = obj.modalPreguntaContent.find('#btnPreguntaActualizar');
    obj.$frmErrorValidate = obj.modalPreguntaContent.find('#frmErrorValidate');
    obj.$btnRespuestaActualizar = obj.modalPreguntaContent.find('.btnRespuestaActualizar');

    var esNuevaPregunta = obj.$frmIdCapacitacionPregunta.val() == null || obj.$frmIdCapacitacionPregunta.val() === '';

    if (esNuevaPregunta) {
        obj.modalPreguntaAceptar.show();
    } else {
        obj.modalPreguntaAceptar.hide();
    }

    obj.$frmTipoPregunta.select2({
        minimumResultsForSearch: -1,
        width: '100%',
        dropdownParent: $('#modalPregunta .modal-content')
    });

    obj.$frmTipoPregunta.on('change', function() {
         obj.$frmDivRespuestasNuevas.html('');
    });

    obj.$frmBtnAgregarRespuesta.on('click', function(e) {
        e.preventDefault();
        obj.viewCapacitacionRespuestaItem(obj.$frmTipoPregunta.val(), esNuevaPregunta, obj.$frmDivRespuestasNuevas);
    });

    obj.initSortableRespuestas(false);
    obj.initValidateFormPregunta();
    obj.initFormValidationPregunta(esNuevaPregunta);

    //Actualizar pregunta
    obj.$btnRespuestaActualizar.on('click', function(e) {
        e.preventDefault();

        var $parent = $(this).parent().parent();

        obj.evalItemValid($parent, 'respuesta-saved');
    });
};

CapacitacionesPreguntas.prototype.initCapacitacionRespuesta = function() {
    var obj = this;
    obj.initSortableRespuestas(true);

    obj.$btnRespuestaEliminar = obj.modalPreguntaContent.find('.btnRespuestaEliminar');
    obj.$btnRespuestaCrear = obj.modalPreguntaContent.find('.btnRespuestaCrear');

    obj.$btnRespuestaEliminar.on('click', function(e) {
        e.preventDefault();
        $(this).parent().parent().remove();
        obj.$frmBtnAgregarRespuesta.prop('disabled', false);
    });

    //Crear pregunta
    obj.$btnRespuestaCrear.on('click', function(e) {
        e.preventDefault();

        var $parent = $(this).parent().parent();

        obj.evalItemValid($parent, 'respuesta-new');
    });
};

CapacitacionesPreguntas.prototype.evalItemValid = function(context, className) {
    var obj = this;

    var item = context.find('input.' + className);
    var selector = context.find('input[name=esCorrecta]');

    if (item != null && item.valid()) {
        var id = context.attr('data-id')
        var nuevo = JSON.parse(context.attr('data-nuevo'));
        obj.mantenimientoRespuesta(nuevo, id, item.val(), selector.is(':checked'));
    }
}

CapacitacionesPreguntas.prototype.initSortableRespuestas = function(destroy) {
    var obj = this;

    if (destroy) {
        obj.$sortableRespuestas.destroy();
    }

    obj.$sortableRespuestas = Sortable.create(document.getElementById('frmDivRespuestas'), {
        animation: 150,
        handle: ".draggable-item-pregunta",
        filter: ".item-nuevo",
        store: {
            set: function (sortable) {
                var orderArray = sortable.toArray();
                if (orderArray.length > 1) {
                    var orderArrayBody = [];
                    $.each(orderArray, function(i, e) {
                        orderArrayBody.push({
                            idCapacitacionRespuesta: e,
                            ordenRespuesta: i
                        });
                    });

                    obj.guardarOrdenRespuestas(orderArrayBody);
                }
            }
        }

    });
};

CapacitacionesPreguntas.prototype.initFormValidationPregunta = function(esNuevo) {
    var obj = this;

    if (esNuevo) {
        obj.modalPreguntaAceptar.unbind();
        obj.modalPreguntaAceptar.on('click', function(e) {
            e.preventDefault();

            var config = {
                rules: {
                    frmPregunta: {
                        required: true
                    },
                },
            };

            obj.$frmFormPregunta.unbind();
            obj.initValidateFormPregunta(config);

            var inptRespuestas = obj.$frmFormPregunta.find('input.respuesta-new');
            var conteoRespuestas = inptRespuestas.length;
            var conteoCorrectas = 0;
            var preguntas = true;
            var request = {
                idCapacitacion: obj.slcCapacitacion.val(),
                idTipoPregunta: obj.$frmTipoPregunta.val(),
                pregunta: obj.$frmPregunta.val(),
                respuestas: []
            }

            $.each(inptRespuestas, function(i, e) {
                var input = $(e);
                var esCorrecta = input.parent().find('input[name=esCorrecta]').is(':checked');
                if (esCorrecta) {
                    conteoCorrectas++;
                }

                if (!input.valid()) {
                    preguntas = false;
                    return false;
                }

                request.respuestas.push({
                    idCapacitacion: request.idCapacitacion,
                    respuesta: input.val(),
                    esCorrecta : esCorrecta
                });
            });

            if (obj.$frmFormPregunta.valid() && preguntas) {
                if (conteoRespuestas <= 0) {
                    obj.mostrarErrorPregunta('Es necesario agregar al menos una respuesta');
                } else if (conteoCorrectas <= 0) {
                    obj.mostrarErrorPregunta('Es necesario seleccionar al menos una respuesta correcta');
                } else {
                    obj.enviarNuevaPregunta(request);
                }
            }
        });
    } else {

        obj.$btnPreguntaActualizar.on('click', function(e) {
            e.preventDefault();

            if (obj.$frmPregunta.valid()) {
                obj.enviarActualizarPregunta();
            }
        });
    }
};

CapacitacionesPreguntas.prototype.enviarActualizarPregunta = function() {
    var obj = this;
    var request = {
        idCapacitacionPregunta: obj.$frmIdCapacitacionPregunta.val(),
        pregunta: obj.$frmPregunta.val()
    };

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/preguntas/preguntaActualizar',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Actualizacion Pregunta',
        before : function() {
            obj.modalPregunta.find('.modal-body').LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                obj.flagActualizarGrid = true;
                Promotick.toast.success('Se actualizo la pregunta correctamente', 'Preguntas');
                obj.viewCapacitacionPreguntaForm(response.data);
            }
        },
        complete : function() {
            obj.modalPregunta.find('.modal-body').LoadingOverlay('hide', true);
        }
    });
};

CapacitacionesPreguntas.prototype.initValidateFormPregunta = function(config) {
    var obj = this;

    obj.$frmFormPregunta.validate({
        rules: config != null ? config.rules : {},
        messages: config != null ? config.messages : {},
        showErrors: function(errorMap, errorList) {
            var $ul = obj.$frmErrorValidate.find('.panel-body').find('ul');
            $ul.html('');

            obj.$frmErrorValidate.hide();
            var hasError = false;
            $.each(errorMap, function(i, e) {
                $ul.append('<li style="color: red;">' + e + '</li>');
                hasError = true;
            });

            if (hasError) {
                obj.$frmErrorValidate.show();
            }

            this.defaultShowErrors();
        },
        errorPlacement: function(error, element) {
        }
    });
};

CapacitacionesPreguntas.prototype.mantenimientoRespuesta = function(esNuevo, id, respuesta, esCorrecta) {
    var obj = this;

    var request = {
        respuesta: respuesta,
        esCorrecta: esCorrecta,
        idCapacitacionPregunta: obj.$frmIdCapacitacionPregunta.val(),
        idCapacitacion: obj.$frmIdCapacitacion.val(),
        idTipoPregunta: obj.$frmTipoPregunta.val(),
    }

    if (!esNuevo) {
        request.idCapacitacionRespuesta = id
    }

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/preguntas/respuestaMantenimiento',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Gestion de respuestas',
        before : function() {
            obj.modalPregunta.find('.modal-body').LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                obj.flagActualizarGrid = true;
                if (esNuevo) {
                    Promotick.toast.success('Se agregó la respuesta correctamente', 'Gestion de respuestas');
                } else {
                    Promotick.toast.success('Se actualizo la respuesta correctamente', 'Gestion de respuestas');
                }
                obj.viewCapacitacionPreguntaForm(response.data);
            }
        },
        complete : function() {
            obj.modalPregunta.find('.modal-body').LoadingOverlay('hide', true);
        }
    });
};

CapacitacionesPreguntas.prototype.guardarOrdenRespuestas = function(list) {
    var obj = this;

    var request = {
        respuestas: list
    };

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/preguntas/guardarOrdenRespuestas',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Respuestas Orden',
        before : function() {
        },
        success : function(response) {
            if (response.status) {
                obj.flagActualizarGrid = true;
            }
        },
        complete : function() {
        }
    });
};

CapacitacionesPreguntas.prototype.mostrarErrorPregunta = function(mensaje) {
    var obj = this;
    var $ul = obj.$frmErrorValidate.find('.panel-body').find('ul');
    $ul.html('');
    $ul.append('<li style="color: red;">' + mensaje + '</li>');
    obj.$frmErrorValidate.show();
};

CapacitacionesPreguntas.prototype.enviarNuevaPregunta = function(request) {
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/preguntas/nuevaPregunta',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Nueva pregunta',
        before : function() {
            obj.modalPregunta.find('.modal-body').LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                obj.flagActualizarGrid = true;
                obj.modalPregunta.modal('hide');
            }
        },
        complete : function() {
            obj.modalPregunta.find('.modal-body').LoadingOverlay('hide', true);
        }
    });
};