function MisCompras(uri) {
    this.uri = uri;
    this.tableFacturas = $('#tableFacturas');
    this.nroFactura = $('#nroFactura');
}

MisCompras.prototype.init = function() {
    this.handle();
    this.listarTablaFacturas();
};

MisCompras.prototype.handle = function() {
    var obj = this;

    obj.nroFactura.keypress(function (e) {
        if (e.which === 13) {
            obj.tableFacturas.DataTable().ajax.reload();
        }
    });
};

MisCompras.prototype.listarTablaFacturas = function() {
    var obj = this;
    obj.tableFacturas.dataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": true,
        "bInfo" : false,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'mis-compras/listarFacturas',
            "type": "POST",
            "data" : function(d) {
                d.nroFactura = obj.nroFactura.val();
            }
        },
        "columns" : [
            {"data" : "fechaGeneradoString", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Fecha generado', data);
                }},
            {"data" : "tipoFactura.nombreTipoFactura", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Evento', data);
                }},
            {"data" : "nroFactura", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Nro factura', data);
                }},
            {"data" : "fechaEmisionString", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Fecha facturacion', data);
                }},
            {"data" : "montoFactura", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Monto facturacion', '$ ' + data);
                }},
            {"data" : "puntosPosibles", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Puntos posibles', data + ' Pts.');
                }}
        ],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
            $('#tableFacturas_paginate').appendTo('.div-tabla')
                .addClass('pull-right spad')
                .css('margin-right','10px');
        },
        "fnDrawCallback": function(){
        }
    });
};

MisCompras.prototype.printValue = function(titulo, data) {
    return  '<span class="labely">' + titulo +': </span> ' + data;
};