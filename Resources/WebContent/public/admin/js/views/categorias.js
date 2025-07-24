var uriGeneral;
function Categorias(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#gridCategorias');
    this.slcOrden = $('#slcOrden');
    this.btnAccion = $('.btnAccion');
}

Categorias.prototype.init = function() {
    this.handle();
    this.listarCategorias();
};

Categorias.prototype.handle = function() {
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
        var idCategoria = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
                title: $.camelCase("-" + estado),
                text: 'Estas seguro de ' + estado + ' a esta categoria ?',
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
                    obj.actualizarEstado(idCategoria, checked, $checkbox);
                }
            }
        );
    });

};

Categorias.prototype.listarCategorias = function(){
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
            "url" : obj.uri + 'catalogos/categorias/listar-categorias/',
            "type": "POST",
            "data" : function(d) {
                d.orden = obj.slcOrden.val();
                // d.nombreCategoria = obj.inptNombre.val();
            }
        },
        "columns" : [
            {"data" : "idCategoria", "orderable": false, "sClass" : "text-center"},
            {"data" : "nombreCategoria", "orderable": false, "render": obj._formatNombre},
            {"data" : "imagenCategoria", "orderable": false, "render": obj._formatImagen},
            {"data" : null, "orderable": false, "class":"estado", "render": obj.formatTipoCategoria},
            {"data" : "estadoCategoria", "orderable": false, "class":"estado", "render": obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

Categorias.prototype.formatTipoCategoria = function(data, type, full) {
    if (full.idCategoriaParent === undefined || full.idCategoriaParent === null) {
        return 'CATEGORIA';
    }
    return 'SUB-CATEGORIA';
};

Categorias.prototype._formatNombre = function(data, type, full) {
    var obj = this;
    return '<a href="' + uriGeneral + 'catalogos/categorias/' + full.idCategoria + '"><u>' + data + '</u></a>';
};

Categorias.prototype._formatEstado = function(data, type, full) {
    if(data === 1){
        return '<label class="label label-sm label-success">ACTIVO</label>';
    } else {
        return '<label class="label label-sm label-danger">INACTIVO</label>';
    }
};

Categorias.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idCategoria + '" data-id="' + full.idCategoria + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idCategoria + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

Categorias.prototype.actualizarEstado = function(idCategoria, checked, $checkbox) {
    var obj = this;

    var request = {
        idCategoria : idCategoria,
        estadoCategoria : checked ? 1 : 0
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'catalogos/categorias/actualizar-estado',
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

Categorias.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

Categorias.prototype._formatImagen = function(data, type, full) {
    var obj = this;
    if (data != null && data != '') {
        return '<a class="image-popup" href="' +  data + '"><img src="' +  data + '" height="60px"/></a>';
    }
    return 'Sin imagen';
};


