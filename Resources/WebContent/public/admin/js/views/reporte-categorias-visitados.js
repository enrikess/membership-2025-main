function ReporteCategoriasVisitados(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridCategoriasVisitadas');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
}

ReporteCategoriasVisitados.prototype.init = function() {
    this.handle();
    this.listarProductosVisitados();
};

ReporteCategoriasVisitados.prototype.handle = function() {
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


ReporteCategoriasVisitados.prototype.listarProductosVisitados = function(){
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
            "url" : obj.uri + 'reportes/categorias-visitadas/listar-cat-visitadas',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "nombreCatalogo", "orderable": false},
            {"data" : "nombreCategoria", "orderable": false},
            {"data" : "nombreSubCategoria", "orderable": false},
            // {"data" : null, "orderable": false, "render" : this.formatTipoCategoria},
            // {"data" : "nombreCategoria", "orderable": false},
            {"data" : "conteo", "orderable": false}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteCategoriasVisitados.prototype.formatTipoCategoria = function(data, type, full) {
    if (full.idCategoriaParent === undefined || full.idCategoriaParent === null) {
        return 'CATEGORIA';
    }
    return 'SUB-CATEGORIA';
};

ReporteCategoriasVisitados.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
