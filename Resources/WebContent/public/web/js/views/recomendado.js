function Recomendado(uri){
    this.uri = uri;
    this.contentRecomendados = $('#contentRecomendados');
    this.grid =$('#tblRecomendados');
}

Recomendado.prototype.init = function(){
    this.handle();
    // this.listarReferidos();
};

Recomendado.prototype.handle = function() {
    var obj = this;

    obj.listarReferidos();
};

Recomendado.prototype.listarReferidos = function(){
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
            "url" : obj.uri + 'recomendado/listarRecomendados',
            "type": "GET",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "nroDocumentoRecomendado","orderable" : false,  "sClass": "text-center" },
            {"data" : "nombresRecomendado", "orderable": false,  "sClass": "text-center" },
            {"data" : "apellidosRecomendado", "orderable": false,  "sClass": "text-center" },
            {"data" : "celularRecomendado", "orderable": false,  "sClass": "text-center" },
            {"data" : "emailRecomendado", "orderable": false,  "sClass": "text-center" },
            {"data" : "estadoRecomendadoString", "orderable": false,  "sClass": "text-center" }
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function (settings,json) {

        },
        "fnDrawCallback": function () {

        }
    });



};

Recomendado.prototype.gridReload = function(){
    this.tblRecomendados.DataTable().ajax.reload();
};
