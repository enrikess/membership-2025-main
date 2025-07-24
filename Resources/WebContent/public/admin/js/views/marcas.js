var uriGeneral;
var urlImagenMobile = "";
function Marcas(uri, uriImagenes) {
    this.uri = uri;
    uriGeneral = uri;
    urlImagenMobile = uriImagenes;
    this.grid = $('#gridMarcas');
    this.btnFiltrar = $('#btnFiltrar');
    this.buscarMarca = $('#buscarMarca');
    this.slcOrden = $('#slcOrden');
    this.btnAccion = $('.btnAccion');
    this.alert = null;
}

Marcas.prototype.init = function() {
    this.handle();
    this.listarMarcas();
};

Marcas.prototype.handle = function() {
    var obj = this;

    obj.slcOrden.select2({
        minimumResultsForSearch: -1
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
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
        var idMarca = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
            title: $.camelCase("-" + estado),
            text: 'Estas seguro de ' + estado + ' a esta marca ?',
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
                    obj.actualizarEstado(idMarca, checked, $checkbox);
                }
            }
        );
    });
};

Marcas.prototype.actualizarEstado = function(idMarca, checked, $checkbox) {
    var obj = this;

    var request = {
        idMarca : idMarca,
        estadoMarca : checked ? 1 : 0
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'catalogos/marcas/actualizar-estado',
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

Marcas.prototype.listarMarcas = function(){
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
            "url" : obj.uri + 'catalogos/marcas/listar-marcas',
            "type": "POST",
            "data" : function(d) {
                d.nombreMarca = obj.buscarMarca.val();
                d.orden = obj.slcOrden.val();
            }
        },
        "columns" : [
            {"data" : "idMarca", "orderable": false},
            {"data" : "nombreMarca", "orderable": false, "render" : this._formatNombre},
            {"data" : "descripcionLarga", "orderable": false},
            // {"data" : "imagenLogo", "orderable": false, "render" : this._formatImagenMobile},
            {"data" : "estadoMarca", "orderable": false, "class":"estado", "render": obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

Marcas.prototype._formatNombre = function(data, type, full) {
    var obj = this;
    return '<a href="' + uriGeneral + 'catalogos/marcas/' + full.idMarca + '"><u>' + data + '</u></a>';
};

Marcas.prototype._formatImagenMobile = function(data, type, full) {
    var obj = this;
    if (data != null && data != '') {
        return '<a class="image-popup" href="' + urlImagenMobile + data + '"><img src="' + urlImagenMobile + data + '" height="60px"/></a>';
    }
    return 'Sin imagen';
};

Marcas.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idMarca + '" data-id="' + full.idMarca + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idMarca + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

Marcas.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
