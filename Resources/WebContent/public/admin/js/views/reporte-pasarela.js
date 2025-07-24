function ReportePasarela(uri) {
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

ReportePasarela.prototype.init = function() {
    this.handle();
    this.listar();
};

ReportePasarela.prototype.handle = function() {
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

ReportePasarela.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/pasarela/listar-pasarela',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.buscar = obj.inptBuscar.val();
            }
        },
        "columns" : [
            {"data" : "nombresParticipante", "orderable": false, "sClass": "text-center"},
            {"data" : "nroDocumento", "orderable": false, "sClass": "text-center"},
            {"data" : "idTransaccion", "orderable": false},
            {"data" : "monto", "orderable": false},
            {"data" : "puntos", "orderable": false},
            {"data" : "fecha", "orderable": false},
            {"data" : "codigo", "orderable": false},
            {"data" : "estado", "orderable": false},
            {"data" : "moneda", "orderable": false},

        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReportePasarela.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReportePasarela.prototype._formatArchivo = function (data, type, full) {
    return '<a href="' + data + '" target="_blank" class="btn btn-sm btn-success"><i class="fa fa-download" aria-hidden="true"></i> Descargar</a>';
};