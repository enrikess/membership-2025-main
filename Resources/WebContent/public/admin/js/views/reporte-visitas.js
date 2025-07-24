function ReporteVisitas(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridVisitas');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.btnDescargarDetallado = $('#btnDescargarDetallado');
}

ReporteVisitas.prototype.init = function() {
    this.handle();
    this.listarProductosVisitados();
};

ReporteVisitas.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

    obj.btnDescargarDetallado.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

};


ReporteVisitas.prototype.listarProductosVisitados = function(){
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
            "url" : obj.uri + 'reportes/visitas/listar-visitas',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "idParticipante", "orderable": false},
            {"data" : "nombreCatalogo", "orderable": false},
            {"data" : "nroDocumento", "orderable": false},
            {"data" : "nombresParticipante", "orderable": false},
            {"data" : "appaternoParticipante", "orderable": false},
            {"data" : "apmaternoParticipante", "orderable": false},
            // {"data" : "emailParticipante", "orderable": false},
            {"data" : "estadoParticipante", "orderable": false, "render" : this._formatEstado},
            {"data" : "conteo", "orderable": false}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteVisitas.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteVisitas.prototype._formatEstado = function(data, type, full) {
    if (parseInt(data) === 1) {
        return '<label class="label label-primary">ACTIVO</label>';
    }
    return '<label class="label label-danger">INACTIVO</label>';
};
