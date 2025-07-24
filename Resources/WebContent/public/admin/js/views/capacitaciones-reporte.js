function CapacitacionesReporte(uri) {
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

CapacitacionesReporte.prototype.init = function() {
    this.handle();
    this.listar();
};

CapacitacionesReporte.prototype.handle = function() {
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

CapacitacionesReporte.prototype.listar = function(){
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
            "url" : obj.uri + 'capacitaciones/reporte/listar',
            "type": "POST",
            "data" : function(d) {
                d.fechaInicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.fechaFin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.buscar = obj.inptBuscar.val();
            }
        },
        "columns" : [
            {"data" : "nroDocumento", "orderable": false, "sClass": "text-center"},
            {"data" : "nombresParticipante", "orderable": false},
            {"data" : "appaternoParticipante", "orderable": false},
            {"data" : "apmaternoParticipante", "orderable": false},
            {"data" : "nombreCatalogo", "orderable": false},
            {"data" : "idCapacitacion", "orderable": false, "sClass": "text-center"},
            {"data" : "nombreCapacitacion", "orderable": false},
            {"data" : "calificacionCuestionario", "orderable": false, "sClass": "text-center"},
            {"data" : "pregunta", "orderable": false},
            {"data" : "respuestaParticipante", "orderable": false, "sClass": "text-center"},
            {"data" : "resultadoPregunta", "orderable": false, "sClass": "text-center", "render": obj._formatResultado},
            {"data" : "fechaCreacionString", "orderable": false},
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

CapacitacionesReporte.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

CapacitacionesReporte.prototype._formatResultado = function (data, type, full) {
    if (data) {
        return '<label class="label label-sm label-primary">CORRECTO</label>'
    }
    return '<label class="label label-sm label-danger">INCORRECTO</label>'
};