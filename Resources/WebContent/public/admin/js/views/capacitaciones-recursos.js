function CapacitacionesRecursos(uri) {
    this.uri = uri;
    this.divRecursos = $('#divRecursos');
    this.divRecursosContent = $('#divRecursosContent');
    this.slcCapacitacion = $('#slcCapacitacion');
    this.modalRecurso = $('#modalRecurso');
    this.modalRecursoContent = $('#modalRecursoContent');
    this.modalRecursoAceptar = $('#modalRecursoAceptar');
    this.$btnAgregarRecurso = null;
    this.$resumenRecurso = null;
    this.$frmRecurso = null;
    this.$frmTipoRecurso = null;
    this.$frmNombreRecurso = null;
    this.$frmContenido = null;
    this.$frmTipoArchivo = null;
    this.$frmTipoTexto = null;
    this.$frmFileContenido = null;
    this.$frmIdCapacitacionRecurso = null;
    this.$frmBotonEliminar = null;
    this.$frmBotonActivar = null;
    this.flagActivarcarga = false;
    this.flagRefrescaRecursos = false;
}

CapacitacionesRecursos.prototype.init = function () {
    this.handler();
};

CapacitacionesRecursos.prototype.handler = function () {
    var obj = this;

    $.fn.modal.Constructor.prototype._enforceFocus = function() {};

    obj.slcCapacitacion.select2();

    obj.slcCapacitacion.on('change', function() {
        obj.seleccionCapacitacion();
    });

    obj.seleccionCapacitacion();

    obj.modalRecurso.on('hidden.bs.modal', function () {
        obj.modalRecursoContent.html('');
        obj.flagActivarcarga = false;
        if (obj.flagRefrescaRecursos) {
            obj.seleccionCapacitacion();
            obj.flagRefrescaRecursos = false;
        }
    });

    obj.modalRecursoAceptar.on('click', function(e) {
        e.preventDefault();
        if (obj.$frmRecurso.valid() && obj.validarCargaArchivo()) {
            obj.enviarRecurso();
        } else {
            obj.$frmRecurso.validate().focusInvalid();
        }
    });
};

CapacitacionesRecursos.prototype.seleccionCapacitacion = function() {
    var obj = this;
    var idCapacitacion = obj.slcCapacitacion.val();
    if (idCapacitacion === '0') {
        obj.divRecursos.hide();
        obj.divRecursosContent.html('');
    } else {
        obj.divRecursos.show();
        obj.mostrarRecursos(idCapacitacion);
    }
};

CapacitacionesRecursos.prototype.mostrarRecursos = function(idCapacitacion) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'capacitaciones/recursos/viewCapacitacionRecursos/' + idCapacitacion,
        context : obj.divRecursosContent,
        complete : function() {
            obj.initMostrarRecursos();
        }
    });
};

CapacitacionesRecursos.prototype.initMostrarRecursos = function() {

    var obj = this;
    obj.$btnAgregarRecurso = obj.divRecursosContent.find('#btnAgregarRecurso');
    obj.$resumenRecurso = obj.divRecursosContent.find('.resumenRecurso');

    obj.$resumenRecurso.on('click', function(e) {
        e.preventDefault();
        var id = $(this).attr('data-id');
        obj.showAgregarRecurso(id);
        obj.modalRecurso.modal({
            backdrop: 'static',
            keyboard: false
        });
    });

    obj.$btnAgregarRecurso.on('click', function(e) {
        e.preventDefault();
        obj.showAgregarRecurso(0);
        obj.modalRecurso.modal({
            backdrop: 'static',
            keyboard: false
        });
    });

    var sortable = Sortable.create(document.getElementById('listaRecursos'), {
        animation: 150,
        handle: ".draggable-item-recurso",
        store: {
            set: function (sortable) {
                var orderArray = sortable.toArray();

                if (orderArray.length > 1) {
                    var orderArrayBody = [];

                    $.each(orderArray, function(i, e) {
                        orderArrayBody.push({
                            idCapacitacionRecurso: e,
                            ordenRecurso: i
                        });
                    });

                    obj.guardarOrden(orderArrayBody);
                }
            }
        }

    });


};

CapacitacionesRecursos.prototype.showAgregarRecurso = function(idCapacitacionRecurso) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'capacitaciones/recursos/viewCapacitacionRecurso/' + idCapacitacionRecurso,
        context : obj.modalRecursoContent,
        complete : function() {
            obj.initAgregarRecursos();
        }
    });

};

CapacitacionesRecursos.prototype.initAgregarRecursos = function() {
    var obj = this;
    obj.$frmRecurso = obj.modalRecursoContent.find('#frmRecurso');
    obj.$frmTipoRecurso = obj.modalRecursoContent.find('#frmTipoRecurso');
    obj.$frmNombreRecurso = obj.modalRecursoContent.find('#frmNombreRecurso');
    obj.$frmContenido = obj.modalRecursoContent.find('#frmContenido');
    obj.$frmTipoTexto = obj.modalRecursoContent.find('#frmTipoTexto');
    obj.$frmTipoArchivo = obj.modalRecursoContent.find('#frmTipoArchivo');
    obj.$frmFileContenido = obj.modalRecursoContent.find('#frmFileContenido');
    obj.$frmIdCapacitacionRecurso = obj.modalRecursoContent.find('#frmIdCapacitacionRecurso');
    obj.$frmBotonEliminar = obj.modalRecursoContent.find('#frmBotonEliminar');
    obj.$frmBotonActivar = obj.modalRecursoContent.find('#frmBotonActivar');

    //init text
    obj.modalRecursoContent.find('.scroll_content').slimscroll({
        height: '200px'
    });

    //init video
    obj.modalRecursoContent.find('.video-item').each(function () {
        var id = $(this).attr('id');
        videojs('#'+ id);
    });

    //init document
    obj.modalRecursoContent.find('.post-format-document').each(function(){
        var urlArchivo = $(this).attr('data-file');
        var id = $(this).attr('id');
        $(this).css('height','300px')

        var options = {
            pdfOpenParams: {
                //view: "FitV"
            }
        };

        PDFObject.embed(urlArchivo, "#" + id, options);
    });

    if (obj.$frmTipoRecurso.hasClass('has-select')) {

        obj.$frmTipoRecurso.select2({
            minimumResultsForSearch: -1,
            width: '100%',
            dropdownParent: $('#modalRecurso .modal-content')
        });

        obj.$frmTipoRecurso.on('change', function() {
            var tipo = $(this).val();
            var format = obj.$frmTipoRecurso.select2('data')[0].element.attributes['data-format'].nodeValue;

            if (tipo === '1') { // texto
                obj.$frmTipoTexto.show();
                obj.$frmTipoArchivo.hide();
            } else { // carga
                obj.$frmTipoTexto.hide();
                obj.$frmTipoArchivo.show();
                if (obj.flagActivarcarga) {
                    obj.$frmFileContenido.fileinput('destroy');
                    obj.flagActivarcarga = false;
                }
                obj.activarCarga(obj.$frmFileContenido,'#frmFileContenidoError', format);
            }
        });
    }

    obj.$frmRecurso.validate({
        ignore: [],
        rules:{
            frmContenido: {
                required: function() {
                    return obj.$frmTipoRecurso.val() === '1';
                }
            },
            frmNombreRecurso: {
                required: true
            }
        },
        messages: {
            frmContenido: {
                required: 'Ingrese un texto de contenido',
            },
            frmNombreRecurso: {
                required: 'Ingrese un nombre de recurso',
            }
        }
    });

    obj.$frmBotonEliminar.on('click', function(e) {
        e.preventDefault();
        var id = $(this).attr('data-id');
        swal({
                title: 'Deshabilitar de recurso',
                text: 'Estas seguro de deshabilitar este recurso ?',
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: 'Si, deshabilitar!',
                cancelButtonText: "No, cancelar!",
                closeOnConfirm: false,
                closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm){
                    obj.estadoRecurso('eliminar', id);
                }
            }
        );
    });

    obj.$frmBotonActivar.on('click', function(e) {
        e.preventDefault();
        var id = $(this).attr('data-id');
        swal({
                title: 'Activacion de recurso',
                text: 'Estas seguro de habilitar este recurso ?',
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: 'Si, habilitar!',
                cancelButtonText: "No, cancelar!",
                closeOnConfirm: false,
                closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm){
                    obj.estadoRecurso('activar', id);
                }
            }
        );
    });
};

CapacitacionesRecursos.prototype.validarCargaArchivo = function() {
    var obj = this;
    if (obj.$frmIdCapacitacionRecurso !== '') {
        return true;
    }

    if (obj.$frmFileContenido[0].files.length === 0) {
        Promotick.toast.warning('Seleccione archivo', 'Carga de recurso');
        return false;
    }

    return true;
};

CapacitacionesRecursos.prototype.activarCarga = function(instance, idError, format) {
    var obj = this;

    if (!obj.flagActivarcarga) {

        var imagenDefault = instance.attr('data-default');
        var imagen = instance.attr('data-uri');
        instance.fileinput({
            overwriteInitial: true,
            maxFileSize: 5*1204,
            showClose: false,
            showCaption: false,
            browseLabel: '',
            removeLabel: '',
            browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
            removeTitle: 'Cancelar o limpiar cambios',
            browseClass : 'btn btn-primary btn-upload input-file-button',
            removeClass : 'btn btn-secondary btn-upload input-file-button',
            elErrorContainer: idError,
            msgErrorClass: 'alert alert-block alert-danger',
            defaultPreviewContent: '<img src="' + imagen + '" alt="" style="max-height: 250px">',
            layoutTemplates: {main2: '{preview} {remove} {browse}'},
            allowedFileExtensions: format.split(',')
        });

        var $objImg = instance.parent().parent().find('.file-preview').find('.file-default-preview').find('img');

        var image = new Image();
        image.src = imagen;
        image.onload = function() {
            $objImg.attr('src', imagen);
        };
        image.onerror = function() {
            $objImg.attr('src', imagenDefault);
        };

        obj.flagActivarcarga = true;
    }
};

CapacitacionesRecursos.prototype.guardarOrden = function(list) {
    var obj = this;

    var request = {
        recursos: list
    };

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/recursos/guardarOrden',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Recursos Orden',
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

CapacitacionesRecursos.prototype.estadoRecurso = function(path, id) {
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'capacitaciones/recursos/' + path + '/' + id,
        messageError : true,
        messageTitle : 'Recursos',
        before : function() {
            swal.close();
        },
        success : function(response) {
            if (response.status) {
                obj.modalRecurso.modal('hide');
                obj.flagRefrescaRecursos = true;
            }
        },
        complete : function() {
        }
    });
};

CapacitacionesRecursos.prototype.enviarRecurso = function() {
    var obj = this;

    var data = new FormData();
    if (obj.flagActivarcarga) {
        $.each(obj.$frmFileContenido[0].files, function(i, file) {
            data.append('archivo', file);
        });
    } else if (obj.$frmTipoRecurso.val() === '1') {
        data.append('contenido', obj.$frmContenido.val());
    }
    if (obj.$frmIdCapacitacionRecurso.val() !== '') {
        data.append('idCapacitacionRecurso', obj.$frmIdCapacitacionRecurso.val());
    }
    data.append('idCapacitacion', obj.slcCapacitacion.val());
    data.append('idTipoRecurso', obj.$frmTipoRecurso.val());
    data.append('nombreCapacitacionRecurso', obj.$frmNombreRecurso.val());

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/recursos/guardarCapacitacionRecurso',
        data : data,
        processData : false,
        contentType : false,
        messageError : true,
        messageTitle : 'Gestion de recursos',
        before : function() {
            obj.modalRecurso.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success('Se registró la información con éxito', 'Gestion de recursos');
                obj.modalRecurso.LoadingOverlay('hide', true);
                obj.modalRecurso.modal('hide');
                obj.seleccionCapacitacion();
            } else {
                obj.modalRecurso.LoadingOverlay('hide', true);
            }
        },
        complete : function() {
        }
    });

};