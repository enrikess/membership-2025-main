function ReporteEncuestaEmpleados(uri) {
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

ReporteEncuestaEmpleados.prototype.init = function() {
    this.handle();
    this.listar();
};

ReporteEncuestaEmpleados.prototype.handle = function() {
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

ReporteEncuestaEmpleados.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/encuesta-empleados/listar',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.buscar = obj.inptBuscar.val();
            }
        },
        "columns" : [
            {"data" : "idParticipante", "orderable": false},
            {"data" : "nroDocumento", "orderable": false},
            {"data" : "nombresParticipante", "orderable": false},
            {"data" : "appaternoParticipante", "orderable": false},
            {"data" : "apmaternoParticipante", "orderable": false},
            {"data" : "emailParticipante", "orderable": false},
            {"data" : "nombreEncuesta", "orderable": false},
            {"data" : "nombreCampania", "orderable": false},
            {"data" : "textoPregunta", "orderable": false},
            {"data" : "textoRespuesta", "orderable": false},
            {"data" : "fechaCreacionString", "orderable": false},

        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteEncuestaEmpleados.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteEncuestaEmpleados.prototype._formatArchivo = function (data, type, full) {
    return '<a href="' + data + '" target="_blank" class="btn btn-sm btn-success"><i class="fa fa-download" aria-hidden="true"></i> Descargar</a>';
};