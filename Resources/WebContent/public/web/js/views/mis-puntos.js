function MisPuntos(uri) {
    this.uri = uri;
    this.cargarDetalle = $('.cargar-detalle');
    this.contentPuntos = $('#contentPuntos');
    this.contentDetallePedido = $('#contentDetallePedido');
    this.loadPage = false;
    this.initDetalle = false;
    this.request = {
        finicio : $(document).find('#fechaInicio').val(),
        ffin : $(document).find('#fechaFin').val()
    };
}

MisPuntos.prototype.init = function() {
    var obj = this;
    obj.handle();
    obj.cargarDetalleView(1);
};

MisPuntos.prototype.handle = function() {
    var obj = this;

    obj.cargarDetalle.on('click', function(e) {
        e.preventDefault();
        var valor = parseInt($(this).attr('data-value'));
        obj.cargarDetalleView(valor);
    });

    $(document).on('change', '#fechaInicio', function() {
        obj.request.finicio = $(this).val();
        $(document).find('#tblPuntos').DataTable().ajax.reload();
    });

    $(document).on('change', '#fechaFin', function () {
        obj.request.ffin = $(this).val();
        $(document).find('#tblPuntos').DataTable().ajax.reload();
    });
};

MisPuntos.prototype.cargarDetalleView = function(tipo) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'mis-puntos/viewDetallePuntos/' + tipo,
        context : obj.contentPuntos,
        complete : function() {
            if (!obj.loadPage) {
                obj.loadPage = true;
            } else {
                Promotick.scrollTop(obj.contentPuntos, 1000);
            }
            obj.initControls(tipo);
        }
    })
};

MisPuntos.prototype.initControls = function(tipo) {
    var obj = this;

    obj.request.finicio = $(document).find('#fechaInicio').val();
    obj.request.ffin = $(document).find('#fechaFin').val();

    var $datatable = $(document).find('#tblPuntos');

    obj.contentDetallePedido.hide();

    switch (tipo) {
        // case 1: // Todos
        //     obj.initDatatableTodos($datatable);
        //     break;
        case 1: // Acumulados
            obj.initDatatableAcumulados($datatable);
            break;
        case 2: // Canjeados
            obj.initDatatableCanjeados($datatable);
            break;
        case 3: // Descargados
            obj.initDatatableDescargados($datatable);
            break;
    }
};

MisPuntos.prototype.initDatatableTodos = function($datatable) {
    var obj = this;

    obj.initDatatable('mis-puntos/listarTodoPuntos', $datatable, [
        {"data" : "fechaOperacionString", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('', data);
            }},
        {"data" : "descripcion", "orderable": false, "render" : function(data, type, full) {
                if (full.factura.nroFactura !== null && full.factura.nroFactura !== '') {
                    return obj.printValue('Detalle', '(' + full.factura.nroFactura + ') ' + data);
                }
                return obj.printValue('Detalle', data);
            }},
        {"data" : "valorPuntos", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('', data + ' Puntos');
            }}
    ]);
};

MisPuntos.prototype.initDatatableAcumulados = function($datatable) {
    var obj = this;

    obj.initDatatable('mis-puntos/listarAcumulados', $datatable, [
        {"data" : "fechaOperacionString", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('', data);
            }},
        {"data" : "descripcion", "orderable": false, "render" : function(data, type, full) {
                if (full.factura.nroFactura !== null && full.factura.nroFactura !== '') {
                    return obj.printValue('Detalle', '(' + full.factura.nroFactura + ') ' + data);
                }
                return obj.printValue('Detalle', data);
            }},
        {"data" : "valorPuntos", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('', data + ' Puntos');
            }}
    ]);

};

MisPuntos.prototype.initDatatableCanjeados = function($datatable) {
    var obj = this;
    obj.initDetalle = false;

    obj.initDatatable('mis-puntos/listarCanjeados', $datatable,[
        {"data" : "fechaOperacionString", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('', data);
            }},
        {"data" : "descripcion", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('Detalle', data);
            }},
        {"data" : "valorPuntos", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('', data + ' Puntos');
            }},
        {"data" : null, "orderable": false, "render" : function(data, type, full) {
                if(full.idOrigen == 2) {
                    return '<a class="show_pedido1"><span style="font-size:25px" class="icon pe-7s-news-paper"></span></a>';
                }else{
                    return '';
                }
            }}
    ], function() {
        if (!obj.initDetalle) {
            obj.initDetalle = true;
            $datatable.on('click', 'a.show_pedido1', function(e) {
                e.preventDefault();
                var data = $datatable.dataTable().fnGetData($(this).parents('tr'));

                obj.obtenerDetallePedido(data.idEntidad);
            });
        }
    });

};


MisPuntos.prototype.initDatatableVencidos = function($datatable) {
    var obj = this;

    obj.initDatatable('mis-puntos/listarVencidos', $datatable, [
        {"data" : "fechaOperacionString", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('Fecha', data);
            }},
        {"data" : "descripcion", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('Detalle', data);
            }},
        {"data" : "valorPuntos", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('Puntos', data + ' Pts.');
            }}
    ]);

};

MisPuntos.prototype.initDatatableDescargados = function($datatable) {
    var obj = this;

    obj.initDatatable('mis-puntos/listarDescargados', $datatable, [
        {"data" : "fechaOperacionString", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('Fecha', data);
            }},
        {"data" : "descripcion", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('Detalle', data);
            }},
        {"data" : "valorPuntos", "orderable": false, "render" : function(data, type, full) {
                return obj.printValue('Puntos', data + ' Pts.');
            }}
    ]);

};

MisPuntos.prototype.initDatatable = function(uri, $datatable, columns, callback) {
    var obj = this;

    $datatable.LoadingOverlay('show');

    $datatable.dataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": true,
        "bInfo" : false,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + uri,
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.request.finicio;
                d.ffin = obj.request.ffin;
            }
        },
        "columns" :columns,
        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
            $(document).find('#tblPuntos_paginate').appendTo('.div-tabla')
                .addClass('pull-right spad')
                .css('margin-right','10px');
        },
        "fnDrawCallback": function(){
            $datatable.LoadingOverlay('hide', false);
            if (callback !== undefined && callback !== null) {
                callback();
            }
        }
    });
};

MisPuntos.prototype.obtenerDetallePedido = function(idPedido) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'mis-puntos/viewDetallePedido/' + idPedido,
        context : obj.contentDetallePedido,
        complete : function() {

            var $estadoPedido = $('.estado_pedido');
            if (!$estadoPedido.is(':visible')) {
                $estadoPedido.slideToggle();
            }

            var $cabecera = $(document).find('.cabecera-detalle');
            if ($cabecera.length > 0) {
                Promotick.scrollTop($cabecera, 1000);
            }
        }
    })
};


MisPuntos.prototype.printValue = function(titulo, data) {
    if(titulo==='')
        return  '<span class="labely"></span> ' + data;
    return  '<span class="labely">' + titulo +': </span> ' + data;
};
