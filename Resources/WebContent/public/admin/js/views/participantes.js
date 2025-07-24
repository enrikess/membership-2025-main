var uriGeneral;
function Participantes(uri) {
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

Participantes.prototype.init = function() {
    this.handle();
    this.listarParticipantes();
};

Participantes.prototype.handle = function() {
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

    $(document).on('click', '.onoffswitch', function(e) {

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
};

Participantes.prototype.actualizarEstado = function(idParticipante, checked, $checkbox) {
    var obj = this;

    var request = {
        idParticipante : idParticipante,
        estadoParticipante : checked ? 1 : 0
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

Participantes.prototype.listarParticipantes = function(){
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
            {"data" : "catalogo.nombreCatalogo", "orderable": false},
            {"data" : "nombresParticipante", "orderable": false},
            {"data" : "appaternoParticipante", "orderable": false},
            {"data" : "apmaternoParticipante", "orderable": false},
            {"data" : "emailParticipante", "orderable": false},
            {"data" : "telefonoParticipante", "orderable": false},
            {"data" : "movilParticipante", "orderable": false},
            {"data" : "fechaRegistroString", "orderable": false},
            {"data" : "puntosDisponibles", "orderable": false, "render" : obj._formatPuntos},
            {"data" : "estadoParticipante", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

Participantes.prototype._formatDocumento = function(data, type, full) {
    var obj = this;
    // return data;
    return '<a href="' + uriGeneral + 'participantes/' + full.idParticipante + '"><u>' + data + '</u></a>';
};

Participantes.prototype._formatPuntos = function(data, type, full) {
    return data + ' Pts.';
};

Participantes.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1){
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

Participantes.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
