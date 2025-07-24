function ReporteDescuento(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#grid');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.inptBuscar = $('#buscar');
    this.btnBuscar = $('#btnBuscar');
}

ReporteDescuento.prototype.init = function() {
    this.handle();
    this.listar();
};

ReporteDescuento.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.btnBuscar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

};

ReporteDescuento.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/descuentos/listar',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "idPedido", "orderable": false, "sClass": "text-center"},
            {"data" : "fechaPedido", "orderable": false},
            {"data" : "nrodocumentoPedido", "orderable": false},
            {"data" : "nombrePedido", "orderable": false, "sClass": "text-center"},
            {"data" : "apellidosPedido", "orderable": false},
            {"data" : "puntosTotal", "orderable": false},
            {"data" : "codigoDescuento", "orderable": false},
            {"data" : "puntosDescuento", "orderable": false},
            {"data" : "puntosSubtotal", "orderable": false},

        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteDescuento.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteDescuento.prototype._formatArchivo = function (data, type, full) {
    return '<a href="' + data + '" target="_blank" class="btn btn-sm btn-success"><i class="fa fa-download" aria-hidden="true"></i> Descargar</a>';
};