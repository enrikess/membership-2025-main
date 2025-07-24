var uriGeneral;
function Segmentos(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#gridSegmentos');
    this.slcOrden = $('#slcOrden');
    this.btnAccion = $('.btnAccion');
    this.alert = null;
}

Segmentos.prototype.init = function() {
    this.handle();
    this.listarSegmentos();
};

Segmentos.prototype.handle = function() {
    var obj = this;

    obj.slcOrden.select2({
        minimumResultsForSearch: -1
    });

    obj.slcOrden.on('change', function() {
        obj.gridReload();
    });

    obj.btnAccion.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-url');
    });

    $(document).on('click', '.onoffswitch', function(e) {

        var $checkbox = $(this).parent().find('input[type=checkbox]');
        var checked = $checkbox.is(':checked');
        var idCatalogo = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
            title: $.camelCase("-" + estado),
            text: 'Estas seguro de ' + estado + ' a este segmento ? ' + (checked ? 'Tener en cuenta: Al desactivar un segmento, los usuarios y/o productos ya no estaran disponibles' : ''),
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
                    obj.actualizarEstado(idCatalogo, checked, $checkbox);
                }
            }
        );
    });
};

Segmentos.prototype.actualizarEstado = function(idCatalogo, checked, $checkbox) {
    var obj = this;

    var request = {
        idCatalogo : idCatalogo,
        estadoCatalogo : checked ? 1 : -1
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/segmentos/actualizar-estado',
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

Segmentos.prototype.listarSegmentos = function(){
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
            "url" : obj.uri + 'administracion/segmentos/listar/' + obj.slcOrden.val(),
            "type": "GET",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "idCatalogo", "orderable": false, "sClass" : "text-center"},
            {"data" : "nombreCatalogo", "orderable": false, "render" : this._formatNombre},
            {"data" : "diasEnvio", "orderable": false, "render" : this._formatDias},
            {"data" : "idCatalogoNetsuite", "orderable": false, "sClass" : "text-center"},
            {"data" : "estadoCatalogo", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

Segmentos.prototype._formatNombre = function(data, type, full) {
    var obj = this;
    return '<a href="' + uriGeneral + 'administracion/segmentos/editar/' + full.idCatalogo + '"><u>' + data + '</u></a>';
};

Segmentos.prototype._formatDias = function(data, type, full) {
    return data + ' dias habiles.';
};

Segmentos.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idCatalogo + '" data-id="' + full.idCatalogo + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idCatalogo + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

Segmentos.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
