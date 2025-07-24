var urlGeneral;
function Capacitaciones(uri) {
    this.uri = uri;
    urlGeneral = uri;
    this.grid = $('#grid');
    this.btnDescargar = $('.btnDescargar');
    this.inptBuscar = $('#buscar');
    this.btnBuscar = $('#btnBuscar');
}

Capacitaciones.prototype.init = function() {
    this.handle();
    this.listar();
};

Capacitaciones.prototype.handle = function() {
    var obj = this;

    obj.btnBuscar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    });

    $(document).on('click', '.onoffswitch', function(e) {

        var $checkbox = $(this).parent().find('input[type=checkbox]');
        var checked = $checkbox.is(':checked');
        var idCapacitacion = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
                title: $.camelCase("-" + estado),
                text: 'Estas seguro de ' + estado + ' a esta capacitación ?',
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
                    obj.actualizarEstado(idCapacitacion, checked, $checkbox);
                }
            }
        );
    });

};

Capacitaciones.prototype.listar = function(){
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
            "url" : obj.uri + 'capacitaciones/listar',
            "type": "POST",
            "data" : function(d) {
                d.buscar = obj.inptBuscar.val();
            }
        },
        "columns" : [
            {"data" : "idCapacitacion", "orderable": false, "sClass": "text-center", "render": this._formatID},
            {"data" : "nombreCapacitacion", "orderable": false, "sClass": "text-center"},
            {"data" : "nombreCatalogo", "orderable": false, "sClass": "text-center"},
            {"data" : "fechaInicioString", "orderable": false, "sClass": "text-center"},
            {"data" : "fechaFinString", "orderable": false, "sClass": "text-center"},
            {"data" : "conteoRecursos", "orderable": false, "sClass": "text-center", "render": this._formatRecursos},
            {"data" : "conteoPreguntas", "orderable": false, "sClass": "text-center", "render": this._formatPreguntas},
            {"data" : "estadoCapacitacionString", "orderable": false, "sClass": "text-center", "render": this._formatEstado},
            {"data" : "puntosCapacitacion", "orderable": false, "sClass": "text-center"},
            {"data" : "estadoCapacitacion", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch},
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

Capacitaciones.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

Capacitaciones.prototype._formatID = function (data, type, full) {
    return '<a href="' + urlGeneral + 'capacitaciones/' + data + '">' + data + '</a>'
};

Capacitaciones.prototype._formatRecursos = function (data, type, full) {
    return '<a href="' + urlGeneral + 'capacitaciones/recursos?idCapacitacion=' + full.idCapacitacion + '"><label class="label label-success" style="cursor: pointer">' + data + ' recursos</label></a>'
};

Capacitaciones.prototype._formatPreguntas = function (data, type, full) {
    return '<a href="' + urlGeneral + 'capacitaciones/preguntas?idCapacitacion=' + full.idCapacitacion + '"><label class="label label-sm label-success" style="cursor: pointer">' + data + ' preguntas</label></a>'
};

Capacitaciones.prototype._formatEstado = function (data, type, full) {
    if (data === 'DESHABILITADO') {
        return '<label class="label label-danger">DESHABILITADO</label>'
    } else if (data === 'VENCIDO') {
        return '<label class="label label-warning">VENCIDO</label>'
    } else if (data === 'VIGENTE') {
        return '<label class="label label-primary">VIGENTE</label>'
    } else {
        return 'Estado no identificado';
    }
};

Capacitaciones.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idCapacitacion + '" data-id="' + full.idCapacitacion + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idCapacitacion + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

Capacitaciones.prototype.actualizarEstado = function(idCapacitacion, checked, $checkbox) {
    var obj = this;

    var request = {
        idCapacitacion : idCapacitacion,
        estadoCapacitacion : !!checked
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'capacitaciones/actualizar-estado',
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
                obj.gridReload();
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};