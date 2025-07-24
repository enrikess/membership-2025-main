var uriGeneral;
function Descuentos(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#grid');
    this.slcOrden = $('#slcOrden');
    this.btnAccion = $('.btnAccion');
    this.alert = null;
}

Descuentos.prototype.init = function() {
    this.handle();
    this.listarDescuentos();
};

Descuentos.prototype.handle = function() {
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
        var idDescuento = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
            title: $.camelCase("-" + estado),
            text: 'Estas seguro de ' + estado + ' a este descuento ? ',
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
                    obj.actualizarEstado(idDescuento, checked, $checkbox);
                }
            }
        );
    });
};

Descuentos.prototype.actualizarEstado = function(idDescuento, checked, $checkbox) {
    var obj = this;

    var request = {
        idDescuento : idDescuento,
        estadoDescuento : checked ? 1 : 0
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/descuento/actualizar-estado',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Actualizacion Estado',
        before : function() {
            loader = Ladda.create($button);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion Descuento');
                $checkbox.prop('checked', !checked);
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};

Descuentos.prototype.listarDescuentos = function(){
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
            "url" : obj.uri + 'administracion/descuento/listar',
            "type": "GET",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "idDescuento", "orderable": false, "sClass" : "text-center"},
            {"data" : "codigoDescuento", "orderable": false, "render" : this._formatNombre},
            {"data" : "tipoDescuento.nombreTipoDescuento", "orderable": false},
            {"data" : "valorDescuento", "orderable": false, "sClass" : "text-center"},
            {"data" : "fechaInicioString", "orderable": false,"class":"text-center"},
            {"data" : "fechaFinString", "orderable": false,"class":"text-center"},
            {"data" : "catalogo.nombreCatalogo", "orderable": false,"class":"text-center"},
            {"data" : "estadoDescuento", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

Descuentos.prototype._formatNombre = function(data, type, full) {
    var obj = this;
    return '<a href="' + uriGeneral + 'administracion/descuento/editar/' + full.idDescuento + '"><u>' + data + '</u></a>';
};

Descuentos.prototype._formatDias = function(data, type, full) {
    return data + ' dias habiles.';
};

Descuentos.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idDescuento + '" data-id="' + full.idDescuento + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idDescuento + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

Descuentos.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
