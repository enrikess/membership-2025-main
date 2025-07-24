var uriGeneral;
function UsuariosPrueba(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#gridUsuarios');
    this.slcOrden = $('#slcOrden');
    this.slcCatalogo = $('#slcCatalogo');
    this.slcTipoDocumento = $('#slcTipoDocumento');
    this.btnModalRegistrar = $('#btnModalRegistrar');
    this.modalRegistro = $('#modalRegistro');
    this.formBuscarParticipante = $('#formBuscarParticipante');
    this.formParticipante = $('#formParticipante');
    this.tabModal = $('.tabModal');
    this.btnModalAceptar = $('#btnModalAceptar');
    this.nroDocumentoBuscar = $('#nroDocumentoBuscar');
    this.nroDocumentoRegistro = $('#nroDocumentoRegistro');
    this.nombresParticipante = $('#nombresParticipante');
    this.appaternoParticipante = $('#appaternoParticipante');
    this.apmaternoParticipante = $('#apmaternoParticipante');
    this.tab = 1;
}

UsuariosPrueba.prototype.init = function() {
    this.handle();
    this.initValidate();
    this.listarUsuariosPrueba();
};

UsuariosPrueba.prototype.handle = function() {
    var obj = this;

    obj.slcOrden.select2({
        minimumResultsForSearch: -1
    });

    obj.slcCatalogo.select2({
        dropdownParent: $('#modalRegistro .modal-content'),
        dropdownAutoWidth : true,
        width: '100%',
        placeholder : 'Seleccionar',
        minimumResultsForSearch: -1
    });

    obj.slcTipoDocumento.select2({
        dropdownParent: $('#modalRegistro .modal-content'),
        dropdownAutoWidth : true,
        width: '100%',
        placeholder : 'Seleccionar',
        minimumResultsForSearch: -1
    });

    obj.slcOrden.on('change', function() {
        obj.gridReload();
    });

    obj.btnModalRegistrar.on('click', function (e) {
        e.preventDefault();
        obj.modalRegistro.modal();
    });

    $(document).on('click', '.onoffswitch', function(e) {

        var $checkbox = $(this).parent().find('input[type=checkbox]');
        var checked = $checkbox.is(':checked');
        var idUsuarioPrueba = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
            title: $.camelCase("-" + estado),
            text: 'Estas seguro de ' + estado + ' a este usuario de prueba?',
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
                    obj.actualizarEstado(idUsuarioPrueba, checked, $checkbox);
                }
            }
        );
    });

    obj.tabModal.on('click', function(e) {
        e.preventDefault();
        obj.tab = parseInt($(this).attr('data-id'));
    });

    obj.btnModalAceptar.on('click', function(e) {
        e.preventDefault();

        if (obj.tab === 1) {
            if (obj.formBuscarParticipante.valid()) {
                obj.nuevoUsuarioPrueba(this);
            } else {
                obj.formBuscarParticipante.validate().focusInvalid();
            }
        } else if (obj.tab === 2) {
            if (obj.formParticipante.valid()) {
                obj.nuevoParticipantePrueba(this);
            } else {
                obj.formParticipante.validate().focusInvalid();
            }
        }
    });

    obj.modalRegistro.on('hidden.bs.modal', function () {
        obj.formBuscarParticipante[0].reset();
        obj.formParticipante[0].reset();
        $('.tabModal[data-id=1]').click();
        obj.slcCatalogo.select2('val', '');
        obj.slcTipoDocumento.select2('val', '');
        obj.tab = 1;
    });
};

UsuariosPrueba.prototype.actualizarEstado = function(idUsuarioPrueba, checked, $checkbox) {
    var obj = this;

    var request = {
        idUsuarioPrueba : idUsuarioPrueba,
        estadoUsuarioPrueba : checked ? 1 : -1
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/usuario-prueba/actualizar-estado',
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
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};

UsuariosPrueba.prototype.listarUsuariosPrueba = function(){
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
            "url" : obj.uri + 'administracion/usuario-prueba/listar-usuarios/' + obj.slcOrden.val(),
            "type": "GET",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "participante.idParticipante", "orderable": false, "sClass" : "text-center"},
            {"data" : "participante.nroDocumento", "orderable": false},
            {"data" : "participante.nombresParticipante", "orderable": false},
            {"data" : "estadoUsuarioPrueba", "orderable": false, "class":"estado", "render": this._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

UsuariosPrueba.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked = '';

    if (data === 1) {
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado += '<div class="onoffswitch">';
    estado += '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idUsuarioPrueba + '" data-id="' + full.idUsuarioPrueba + '">';
    estado += '<label class="onoffswitch-label" for="s-' + full.idUsuarioPrueba + '">';
    estado += '<span class="onoffswitch-inner"></span>';
    estado += '<span class="onoffswitch-switch"></span>';
    estado += '</label>';
    estado += '</div>';
    estado += '</div>';
    return estado;
};

UsuariosPrueba.prototype.initValidate = function() {
    var obj = this;

    obj.formBuscarParticipante.validate( {
        rules : {
            nroDocumentoBuscar : {
                required : true,
                minlength : 6
            }
        },
        messages : {
            nroDocumentoBuscar : {
                required : 'Ingrese nro de documento a buscar',
                minlength : 'Ingrese al menos 6 digitos'
            }
        }
    });

    obj.formParticipante.validate({
        rules : {
            slcCatalogo : {
                required : true,
                min : 1
            },
            slcTipoDocumento : {
                required : true,
                min : 1
            },
            nroDocumentoRegistro : {
                required : true,
                minlength : 6
            },
            nombresParticipante : {
                required : true
            },
            appaternoParticipante : {
                required : true
            },
            apmaternoParticipante : {
                required : true
            }
        } ,
        messages : {
            slcCatalogo : {
                required : 'Seleccione un catalogo',
                min : 'Seleccione un catalogo'
            },
            slcTipoDocumento : {
                required : 'Seleccione un tipo de documento',
                min : 'Seleccione un tipo de documento'
            },
            nroDocumentoRegistro : {
                required : 'Ingrese un nro de documento',
                minlength : 'Ingrese al menos 6 caracteres'
            },
            nombresParticipante : {
                required : 'Ingrese el nombre'
            },
            appaternoParticipante : {
                required : 'Ingrese el apellido paterno'
            },
            apmaternoParticipante : {
                required : 'Ingrese el apellido materno'
            }
        }
    });
};

UsuariosPrueba.prototype.nuevoUsuarioPrueba = function(handle) {
    var obj = this;
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/usuario-prueba/nuevoUsuarioPrueba',
        data : {
            nroDocumento : obj.nroDocumentoBuscar.val()
        },
        contentType : 'application/x-www-form-urlencoded',
        messageTitle : 'Registro usuario de prueba',
        messageError : true,
        before : function() {
            loader = Ladda.create(handle);
            loader.start();
            obj.modalRegistro.find('.modal-content').LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Registro usuario de prueba');
                obj.modalRegistro.modal('hide');
                obj.gridReload();
            }
        },
        complete : function() {
            obj.modalRegistro.find('.modal-content').LoadingOverlay('hide', true);
            loader.stop();
        }
    });
};

UsuariosPrueba.prototype.nuevoParticipantePrueba = function(handle) {
    var obj = this;
    var loader = null;

    var request = {
        catalogo : {
            idCatalogo : obj.slcCatalogo.val()
        },
        tipoDocumento : {
            idTipoDocumento : obj.slcTipoDocumento.val()
        },
        nombresParticipante : obj.nombresParticipante.val(),
        appaternoParticipante : obj.appaternoParticipante.val(),
        apmaternoParticipante : obj.apmaternoParticipante.val(),
        nroDocumento : obj.nroDocumentoRegistro.val()
    };

    Promotick.ajax.post({
        url : obj.uri + 'administracion/usuario-prueba/nuevoParticipante',
        data : JSON.stringify(request),
        messageTitle : 'Registro participante de prueba',
        messageError : true,
        before : function() {
            loader = Ladda.create(handle);
            loader.start();
            obj.modalRegistro.find('.modal-content').LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Registro participante de prueba');
                obj.modalRegistro.modal('hide');
                obj.gridReload();
            }
        },
        complete : function() {
            obj.modalRegistro.find('.modal-content').LoadingOverlay('hide', true);
            loader.stop();
        }
    });
};

UsuariosPrueba.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
