var uriGeneral;
function ParticipantesEstado(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#gridParticipantes');
    this.btnFiltrar = $('#btnFiltrar');
    this.buscarParticipante = $('#buscarParticipante');
    this.slcOrden = $('#slcOrden');
    this.slcCatalogo = $('#slcCatalogo');
    this.btnAccion = $('.btnAccion');
    this.alert = null;
}

ParticipantesEstado.prototype.init = function() {
    this.handle();
    this.listarParticipantes();
};

ParticipantesEstado.prototype.handle = function() {
    var obj = this;

    obj.slcOrden.select2({
        minimumResultsForSearch: -1
    });
    obj.slcCatalogo.select2({
        minimumResultsForSearch: -1
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.slcOrden.on('change', function() {
        obj.gridReload();
    });

    obj.slcCatalogo.on('change', function() {
        obj.gridReload();
    });

    obj.btnAccion.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-url');
    });

    $(document).on('click', '.btnPendiente', function(e) {
        e.preventDefault();
        var id = $(this).attr('data-id');
        var aprobacion = JSON.parse($(this).attr('data-aprobacion'));

        var estado = 'aprobar';
        if (!aprobacion) {
            estado = 'desaprobar';
        }

        swal({
                title: $.camelCase("-" + estado),
                text: 'Estas seguro de ' + estado + ' a este participante ?',
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
                    obj.aprobarParticipante(id, aprobacion);
                }
            }
        );

    });

    $(document).on('click', '.onoffswitch', function(e) {
        console.log('estado');
        var $checkbox = $(this).parent().find('input[type=checkbox]');
        var checked = $checkbox.is(':checked');
        var idParticipante = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
            title: $.camelCase("-" + estado),
            text: 'Estas seguro de ' + estado + ' a este participante ?',
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
                    obj.actualizarEstado(idParticipante, checked, $checkbox);
                }
            }
        );
    });

    $(document).on('click', '.onoffswitchCanje', function(e) {
        console.log('canje');
        var $checkbox = $(this).parent().find('input[type=checkbox]');
        var checked = $checkbox.is(':checked');
        var idParticipante = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
                title: $.camelCase("-" + estado),
                text: 'Estas seguro de ' + estado + ' a este participante ?',
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
                    obj.actualizarEstadoCanje(idParticipante, checked, $checkbox);
                }
            }
        );
    });
};

ParticipantesEstado.prototype.actualizarEstado = function(idParticipante, checked, $checkbox) {
    var obj = this;

    var request = {
        idParticipante : idParticipante,
        estadoParticipante : checked ? 1 : 0,
        estadoCanjes : checked ? 1 : -1
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'participantes/actualizar-estado',
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

ParticipantesEstado.prototype.actualizarEstadoCanje = function(idParticipante, checked, $checkbox) {
    var obj = this;

    var request = {
        idParticipante : idParticipante,
        estadoParticipante : checked ? 1 : -1,
        estadoCanjes : checked ? 1 : -1
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'participantes/actualizar-estado-canje',
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

ParticipantesEstado.prototype.listarParticipantes = function(){
    var obj = this;
    obj.grid.DataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": true,
        "bInfo" : true,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'participantes/listar-participantes',
            "type": "POST",
            "data" : function(d) {
                d.buscar = obj.buscarParticipante.val();
                d.orden = obj.slcOrden.val();
                d.idCatalogo = obj.slcCatalogo.val();
            }
        },
        "columns" : [
            {"data" : "nroDocumento", "orderable": false, "render" : obj._formatDocumento},
            {"data" : "nombresParticipante", "orderable": false},
            {"data" : "appaternoParticipante", "orderable": false},
            {"data" : "apmaternoParticipante", "orderable": false},
            {"data" : "emailParticipante", "orderable": false},
            {"data" : "estadoParticipante", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch},
            {"data" : "estadoCanjes", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitchCanje}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

ParticipantesEstado.prototype._formatDocumento = function(data, type, full) {
    var obj = this;
    return data;
};

ParticipantesEstado.prototype._formatPuntos = function(data, type, full) {
    return data + ' Pts.';
};

ParticipantesEstado.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if (data === 0) {
        return '<label class="label label-success btnPendiente" data-id="' + full.idParticipante + '" data-aprobacion="true" style="padding: 10px 8px; cursor: pointer; margin-right: 10px">Aprobar</label>' +
            '<label class="label label-danger btnPendiente" data-id="' + full.idParticipante + '" data-aprobacion="false" style="padding: 10px 8px; cursor: pointer">Desaprobar</label>';
    }

    if (data === -2) {
        return 'Desaprobado';
    }

    if(data === 1 || data === true){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idParticipante + '" data-id="' + full.idParticipante + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idParticipante + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

ParticipantesEstado.prototype._formatEstadoSwitchCanje = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1 || data === true){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitchCanje">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idParticipante + '" data-id="' + full.idParticipante + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idParticipante + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

ParticipantesEstado.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ParticipantesEstado.prototype.aprobarParticipante = function(idParticipante, aprobar) {
    var obj = this;

    var request = {
        idParticipante: idParticipante,
        aprobar: aprobar
    };

    var $button = document.querySelector('button.confirm');
    var loader = null;
    var texto = aprobar ? 'Aprobar' : 'Desaprobar';

    Promotick.ajax.post({
        url : obj.uri + 'participantes/aprobar-participante',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : texto + ' participante',
        before : function() {
            loader = Ladda.create($button);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, texto + ' participante');
                obj.gridReload();
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};