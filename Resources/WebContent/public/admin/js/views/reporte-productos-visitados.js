function ReporteProductosVisitados(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridProductosVisitados');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
}

ReporteProductosVisitados.prototype.init = function() {
    this.handle();
    this.listarProductosVisitados();
};

ReporteProductosVisitados.prototype.handle = function() {
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

};


ReporteProductosVisitados.prototype.listarProductosVisitados = function(){
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
            "url" : obj.uri + 'reportes/productos-visitados/listar-prod-visitados',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "idProducto", "orderable": false},
            {"data" : "nombreCatalogo", "orderable": false},
            {"data" : "codigoWeb", "orderable": false},
            {"data" : "nombreProducto", "orderable": false},
            // {"data" : "nombreCategoria", "orderable": false},
            // {"data" : "nombreSubCategoria", "orderable": false},
            // {"data" : "puntosProducto", "orderable": false},
            {"data" : "nombreMarca", "orderable": false},
            {"data" : "conteo", "orderable": false}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteProductosVisitados.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
