var uriGeneral;
var esSuperUsuario = false;
function Usuarios(uri, superusuario) {
    this.uri = uri;
    esSuperUsuario = superusuario;
    uriGeneral = uri;
    this.grid = $('#gridUsuarios');
    this.slcOrden = $('#slcOrden');
    this.btnAccion = $('.btnAccion');
    this.alert = null;
}

Usuarios.prototype.init = function() {
    this.handle();
    this.listarUsuarios();
};

Usuarios.prototype.handle = function() {
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
        var idUsuario = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
            title: $.camelCase("-" + estado),
            text: 'Estas seguro de ' + estado + ' a este usuario ?',
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
                    obj.actualizarEstado(idUsuario, checked, $checkbox);
                }
            }
        );
    });
};

Usuarios.prototype.actualizarEstado = function(idUsuario, checked, $checkbox) {
    var obj = this;

    var request = {
        idUsuario : idUsuario,
        estadoUsuario : checked ? 1 : -1
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/usuarios/actualizar-estado',
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

Usuarios.prototype.listarUsuarios = function(){
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
            "url" : obj.uri + 'administracion/usuarios/listar-usuarios/' + obj.slcOrden.val(),
            "type": "GET",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "usuario", "orderable": false, "render" : this._formatNombre},
            {"data" : "rol.nombreRol", "orderable": false},
            {"data" : "nombresUsuario", "orderable": false},
            {"data" : "correoUsuario", "orderable": false},
            {"data" : "estadoUsuario", "orderable": false, "class":"estado", "render": this._formatEstadoSwitch}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

Usuarios.prototype._formatNombre = function(data, type, full) {
    if ((esSuperUsuario === 'true' || esSuperUsuario === true) || full.rol.esSuperUsuario === false) {
        return '<a href="' + uriGeneral + 'administracion/usuarios/' + full.idUsuario + '"><u>' + data + '</u></a>'
    } else {
        return data;
    }
};

Usuarios.prototype._formatDias = function(data, type, full) {
    return data + ' dias habiles.';
};

Usuarios.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    if (full.rol.esSuperUsuario === true) {
        if(data === 1){
            estado ='Activo';
        } else {
            estado ='Desactivado';
        }
    } else {
        var checked = '';

        if (data === 1) {
            checked = 'checked';
        }
        estado += '<div class="switch">';
        estado += '<div class="onoffswitch">';
        estado += '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idUsuario + '" data-id="' + full.idUsuario + '">';
        estado += '<label class="onoffswitch-label" for="s-' + full.idUsuario + '">';
        estado += '<span class="onoffswitch-inner"></span>';
        estado += '<span class="onoffswitch-switch"></span>';
        estado += '</label>';
        estado += '</div>';
        estado += '</div>';
    }
    return estado;
};

Usuarios.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
