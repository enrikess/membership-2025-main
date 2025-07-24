var uriGeneral;
function Campanias(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#grid');
    this.slcOrden = $('#slcOrden');
    this.btnAccion = $('.btnAccion');
    this.alert = null;
}

Campanias.prototype.init = function() {
    this.handle();
    this.listarCampanias();
};

Campanias.prototype.handle = function() {
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
        var idCampania = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
            title: $.camelCase("-" + estado),
            text: 'Estas seguro de ' + estado + ' a esta campaña ? ',
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
                    obj.actualizarEstado(idCampania, checked, $checkbox);
                }
            }
        );
    });
};

Campanias.prototype.actualizarEstado = function(idCampania, checked, $checkbox) {
    var obj = this;

    var request = {
        idCampania : idCampania,
        estadoCampania : checked ? 1 : 0
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/campania/actualizar-estado',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Actualizacion Estado',
        before : function() {
            loader = Ladda.create($button);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion Campaña');
                $checkbox.prop('checked', !checked);
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};

Campanias.prototype.listarCampanias = function(){
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
            "url" : obj.uri + 'administracion/campania/listar',
            "type": "GET",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "idCampania", "orderable": false, "sClass" : "text-center"},
            {"data" : "nombreCampania", "orderable": false, "render" : this._formatNombre},
            {"data" : "numGanadores", "orderable": false},
            {"data" : "valorPuntos", "orderable": false, "sClass" : "text-center"},
            {"data" : "fechaInicio", "orderable": false,"class":"text-center"},
            {"data" : "fechaFin", "orderable": false,"class":"text-center"},
            // {"data" : "estadoCampania", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch},
            {"data" : "estadoCampania", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

Campanias.prototype._formatNombre = function(data, type, full) {
    var obj = this;
    return '<a href="' + uriGeneral + 'administracion/campania/editar/' + full.idCampania + '"><u>' + data + '</u></a>';
};

Campanias.prototype._formatDias = function(data, type, full) {
    return data + ' dias habiles.';
};

Campanias.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idCampania + '" data-id="' + full.idCampania + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idCampania + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

Campanias.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
