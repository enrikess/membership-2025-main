function CapacitacionesDetalle(uri, esNuevo) {
    this.uri = uri;
    this.esNuevo = JSON.parse(esNuevo);
    this.imagenPrincipal = $('#imagenPrincipal')
    this.imagenDetalle = $('#imagenDetalle')
    this.btnAceptar = $('#btnAceptar')
    this.frmCapacitacion = $('#frmCapacitacion')
    this.idCapacitacion = $('#idCapacitacion')
    this.inptNombreCapacitacion = $('#inptNombreCapacitacion')
    this.inptFechaInicio = $('#inptFechaInicio')
    this.inptFechaFin = $('#inptFechaFin')
    this.inptDescripcionCapacitacion = $('#inptDescripcionCapacitacion');
    this.chkCuestionario = $('#chkCuestionario');
    this.slcCatalogo = $('#slcCatalogo');
    this.inptPuntajeCapacitacion = $('#inptPuntajeCapacitacion');
    this.checks = $('.i-checks');
}

CapacitacionesDetalle.prototype.init = function () {
    this.handler();
};

CapacitacionesDetalle.prototype.handler = function () {
    var obj = this;

    obj.checks.iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });

    var imagenDefecto = obj.imagenPrincipal.attr('data-uri');
    var imagenDetalleDefecto = obj.imagenDetalle.attr('data-uri');

    obj.makeFileInput(obj.imagenPrincipal, '#principalError');
    obj.makeFileInput(obj.imagenDetalle, '#detalleError');

    obj.frmCapacitacion.validate({
        rules: {
            inptNombreCapacitacion: {
                required: true
            },
            inptFechaInicio: {
                required: true,
                dateISO: true
            },
            inptFechaFin: {
                required: true,
                dateISO: true
            },
            inptDescripcionCapacitacion: {
                required: true
            },
            slcCatalogo: {
                required: true
            },
            inptPuntajeCapacitacion: {
                required: true,
                digits: true
            }
        },
        messages: {
            inptNombreCapacitacion: {
                required: 'Ingrese un nombre de capacitacion'
            },
            inptFechaInicio: {
                required: 'Seleccione una fecha de inicio',
                dateISO: 'Formato de fecha incorrecto'
            },
            inptFechaFin: {
                required: 'Seleccione una fecha de inicio',
                dateISO: 'Formato de fecha incorrecto'
            },
            inptDescripcionCapacitacion: {
                required: 'Ingrese una descripcion'
            },
            slcCatalogo: {
                required: 'Ingrese un catalogo'
            },
            inptPuntajeCapacitacion: {
                required: 'Ingrese un puntaje',
                digits: 'Ingrese un numero entero positivo'
            },
        }
    });

    obj.btnAceptar.on('click', function(e) {
        e.preventDefault();
        if (obj.frmCapacitacion.valid() && obj.validarImagenes()) {
            obj.enviarCapacitacion();
        } else {
            obj.frmCapacitacion.validate().focusInvalid();
        }
    });
};

CapacitacionesDetalle.prototype.makeFileInput = function(instance, idError) {
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
        allowedFileExtensions: ["jpg", "png", "gif", "jpeg"]
    });

    var $objImg = instance.parent().parent().find('.file-preview').find('.file-default-preview').find('img');

    var image = new Image();
    image.src = imagen;
    image.onload = function() {
        $objImg.attr('src', imagen);
    };
    image.onerror = function() {
        $objImg.attr('src', imagenDefault);
    }
};

CapacitacionesDetalle.prototype.validarImagenes = function() {
    var obj = this;
    if (!obj.esNuevo) {
        return true;
    }

    if (obj.imagenPrincipal[0].files.length === 0) {
        Promotick.toast.warning('Seleccione una imagen principal', 'Gestion de mantenimiento');
        return false;
    }

    if (obj.imagenDetalle[0].files.length === 0) {
        Promotick.toast.warning('Seleccione una imagen detalle', 'Gestion de mantenimiento');
        return false;
    }

    return true;
};

CapacitacionesDetalle.prototype.enviarCapacitacion = function() {
    var obj = this;

    var data = new FormData();
    $.each(obj.imagenPrincipal[0].files, function(i, file) {
        data.append('imagenPrincipal', file);
    });

    $.each(obj.imagenDetalle[0].files, function(i, file) {
        data.append('imagenSecundaria', file);
    });

    if (!obj.esNuevo) {
        data.append('idCapacitacion', obj.idCapacitacion.val());
    }

    data.append('nombreCapacitacion', obj.inptNombreCapacitacion.val());
    data.append('fechaInicioString', obj.inptFechaInicio.val());
    data.append('fechaFinString', obj.inptFechaFin.val());
    data.append('descripcionCapacitacion', obj.inptDescripcionCapacitacion.val());
    data.append('estadoCuestionario', obj.chkCuestionario.is(':checked'));
    data.append('idCatalogo', obj.slcCatalogo.val());
    data.append('puntosCapacitacion', obj.inptPuntajeCapacitacion.val());

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/guardarCapacitacion',
        data : data,
        processData : false,
        contentType : false,
        messageError : true,
        messageTitle : 'Gestion de capacitacion',
        before : function() {
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success('Se registró la información con éxito', 'Gestion de capacitacion');
                $('html, body').animate({scrollTop:0}, 'slow');
                if (obj.esNuevo) {
                    obj.frmCapacitacion[0].reset();
                    window.location.href = obj.uri + 'capacitaciones/' + response.data + "?register=successful";
                }
            }
        },
        complete : function() {
            $.LoadingOverlay('hide', true);
        }
    });
};