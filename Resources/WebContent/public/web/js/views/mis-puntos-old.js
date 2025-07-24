function MisPuntos(uri) {
    this.uri = uri;
    this.contentHistorialPuntos = $('#contentHistorialPuntos');
    this.contentResumenMeta = $('#contentResumenMeta');
    this.contentDetallePedido = $('#contentDetallePedido');
    this.facturasAgrupadasAnio = $('#facturasAgrupadasAnio');
    this.tablePosibles = $('#tablePosibles');
    this.tableAcreditados = $('#tableAcreditados');
    this.tableCanjeados = $('#tableCanjeados');
    this.fechaInicioPosibles = $('#fechaInicioPosibles');
    this.fechaFinPosibles = $('#fechaFinPosibles');
    this.fechaInicioAcreditados = $('#fechaInicioAcreditados');
    this.fechaFinAcreditados = $('#fechaFinAcreditados');
    this.fechaInicioCanjeados = $('#fechaInicioCanjeados');
    this.fechaFinCanjeados = $('#fechaFinCanjeados');
    this.tabPuntos = $('li.tab-puntos');
}

MisPuntos.prototype.init = function() {
    var obj = this;
    obj.handle();
    obj.listarResumenMeta();
    obj.listarHistorialPuntos();

    obj.listarTablaPosibles();
    obj.listarTablaAcreditados();
    obj.listarCanjeados();

};

MisPuntos.prototype.handle = function() {
    var obj = this;

    obj.facturasAgrupadasAnio.on('change', function() {
        obj.listarResumenMeta();
        obj.listarHistorialPuntos(null);
    });

    obj.fechaInicioPosibles.on('change', function (e) {
        e.preventDefault();
        obj.tablePosibles.DataTable().ajax.reload();
    });

    obj.fechaFinPosibles.on('change', function (e) {
        e.preventDefault();
        obj.tablePosibles.DataTable().ajax.reload();
    });

    obj.fechaInicioAcreditados.on('change', function (e) {
        e.preventDefault();
        obj.tableAcreditados.DataTable().ajax.reload();
    });

    obj.fechaFinAcreditados.on('change', function (e) {
        e.preventDefault();
        obj.tableAcreditados.DataTable().ajax.reload();
    });

    obj.fechaInicioCanjeados.on('change', function (e) {
        e.preventDefault();
        obj.tableCanjeados.DataTable().ajax.reload();
    });

    obj.fechaFinCanjeados.on('change', function (e) {
        e.preventDefault();
        obj.tableCanjeados.DataTable().ajax.reload();
    });

    obj.tableCanjeados.on('click', 'a.show_pedido1', function(e) {
        e.preventDefault();
        var data = obj.tableCanjeados.dataTable().fnGetData($(this).parents('tr'));

        obj.obtenerDetallePedido(data.idEntidad);
    });

    obj.tabPuntos.on('click', function(e) {
        e.preventDefault();
        obj.contentDetallePedido.html('');
    })
};

MisPuntos.prototype.listarHistorialPuntos = function() {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'mis-puntos/viewHistorialPuntos/' + obj.facturasAgrupadasAnio.val(),
        context : obj.contentHistorialPuntos,
        complete : function() {

        }
    });
};

MisPuntos.prototype.obtenerTotalPosibles = function(request) {
    var obj = this;

    var $content = obj.tablePosibles.find('td#tdCountPosibles');

    Promotick.ajax.post({
        url : obj.uri + 'mis-puntos/obtenerTotalPosibles',
        messageError : true,
        messageTitle : 'Error Puntos posibles',
        data : JSON.stringify(request),
        before : function() {
            $content.LoadingOverlay('show');
        },
        success : function (response) {
            if (response.status) {
                $content.find('span.spanCountPosibles').html(response.data + ' Pts.');
            }
        },
        complete : function() {
            $content.LoadingOverlay('hide', true);
        }
    })
};

MisPuntos.prototype.obtenerTotalAcreditados = function(request) {
    var obj = this;

    var $content = obj.tableAcreditados.find('td#tdCountAcreditados');

    Promotick.ajax.post({
        url : obj.uri + 'mis-puntos/obtenerTotalAcreditados',
        messageError : true,
        messageTitle : 'Error Puntos acreditados',
        data : JSON.stringify(request),
        before : function() {
            $content.LoadingOverlay('show');
        },
        success : function (response) {
            if (response.status) {
                $content.find('span.spanCountAcreditados').html(response.data + ' Pts.');
            }
        },
        complete : function() {
            $content.LoadingOverlay('hide', true);
        }
    })
};

MisPuntos.prototype.obtenerTotalCanjeados = function(request) {
    var obj = this;

    var $content = obj.tableCanjeados.find('td#tdCountCanjeados');

    Promotick.ajax.post({
        url : obj.uri + 'mis-puntos/obtenerTotalCanjeados',
        messageError : true,
        messageTitle : 'Error Puntos canjeados',
        data : JSON.stringify(request),
        before : function() {
            $content.LoadingOverlay('show');
        },
        success : function (response) {
            if (response.status) {
                $content.find('span.spanCountCanjeados').html(response.data + ' Pts.');
            }
        },
        complete : function() {
            $content.LoadingOverlay('hide', true);
        }
    })
};

MisPuntos.prototype.listarResumenMeta = function() {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'mis-puntos/viewResumenMeta/' + obj.facturasAgrupadasAnio.val(),
        context : obj.contentResumenMeta
    });
};

MisPuntos.prototype.listarTablaPosibles = function() {
    var obj = this;

    var request = {
        finicio : obj.fechaInicioPosibles.val(),
        ffin : obj.fechaFinPosibles.val()
    };

    obj.tablePosibles.dataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": true,
        "bInfo" : false,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'mis-puntos/listarPosibles',
            "type": "POST",
            "data" : function(d) {
                d.finicio = request.finicio;
                d.ffin = request.ffin;
            }
        },
        "columns" : [
            {"data" : "fechaOperacionString", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Fecha', data);
                }},
           /* {"data" : "factura.nroFactura", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Nro factura', data);
                }},*/
            {"data" : "descripcion", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Detalle', data);
                }},
            {"data" : "idEstadoTransaccion", "orderable": false, "render" : function(data, type, full) {
                    var estado = parseInt(data);
                    if (full.idOrigen === 11 && (estado === 1 || estado === 2)) {
                        return obj.printValue('Estado', '<label class="label label-success">Acreditado ' + full.saldoPuntos + ' Pts.</label>');
                    } else {
                        return obj.printValue('Estado', '<label class="label label-default">Ejecutado</label>');
                    }
                }},
            {"data" : null, "orderable": false, "render" : function(data, type, full) {
                    if (full.idOrigen === 11) {
                        var label = '';
                        return obj.printValue('Puntos', full.factura.puntosPosibles + ' Pts.');
                    } else{
                        return obj.printValue('Puntos', '- ' + full.factura.puntosRestados + ' Pts.');
                    }

                }}
        ],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
            $('#tablePosibles_paginate').appendTo('.div-tabla')
                .addClass('pull-right spad')
                .css('margin-right','10px');
        },
        "fnDrawCallback": function(){
              obj.tablePosibles.find('tbody').append('<tr role="row"><td colspan="5" id="tdCountPosibles">Puntos posibles total: <span style="font-weight: bold" class="spanCountPosibles"></span></td></tr>');
              obj.obtenerTotalPosibles(request);
        }
    });
};

MisPuntos.prototype.listarTablaAcreditados = function() {
    var obj = this;

    var request = {
        finicio : obj.fechaInicioAcreditados.val(),
        ffin : obj.fechaFinAcreditados.val()
    };

    obj.tableAcreditados.dataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": true,
        "bInfo" : false,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'mis-puntos/listarAcumulados',
            "type": "POST",
            "data" : function(d) {
                d.finicio = request.finicio;
                d.ffin = request.ffin;
            }
        },
        "columns" : [
            {"data" : "fechaOperacionString", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Fecha', data);
                }},
            {"data" : null, "orderable": false, "render" : function(data, type, full) {
                var periodoParticipante = full.factura.periodoParticipante;
                    if (periodoParticipante.periodo.nombrePeriodo !== null) {
                        if (periodoParticipante.periodo.tipoPeriodo.idTipoPeriodo === 1) { // Anual
                            return obj.printValue('Detalle', periodoParticipante.periodo.nombrePeriodo + ' - Meta(' + periodoParticipante.periodoMeta + ")");
                        } else { // Trimestral
                            return obj.printValue('Detalle', periodoParticipante.periodo.periodoAnio + ' - Trimestre ' + periodoParticipante.periodo.datoPeriodo + ' - Meta(' + periodoParticipante.periodoMeta + ")");
                        }
                    }
                    return obj.printValue('Tipo', 'Puntos acreditados');
                }},
            {"data" : "descripcion", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Detalle', data);
                }},
            {"data" : "idEstadoTransaccion", "orderable": false, "render" : function(data, type, full) {
                    if (full.factura.tipoFactura.idTipoFactura === 2 || full.factura.tipoFactura.idTipoFactura === 5 || full.factura.tipoFactura.idTipoFactura === 6) {
                        return obj.printValue('Estado', '<label class="label label-sm label-danger">RESTADO</label>');
                    } else {
                        if (parseInt(data) === -1 || parseInt(full.idOrigen) === 5) {
                            return obj.printValue('Estado', '<label class="label label-sm label-danger">VENCIDO</label>');
                        } else if (parseInt(data) === 2){
                            return obj.printValue('Estado', '<label class="label label-sm label-primary">USADO</label>');
                        } else {
                            return obj.printValue('Estado', '<label class="label label-sm label-success">DISPONIBLE</label>');
                        }
                    }

                }},
            {"data" : "valorPuntos", "orderable": false, "render" : function(data, type, full) {
                    if (full.idOrigen === 11) {
                        return obj.printValue('Puntos', data + ' Pts.');
                    } else {
                        if (full.factura.tipoFactura.idTipoFactura === 2 || full.factura.tipoFactura.idTipoFactura === 5 || full.factura.tipoFactura.idTipoFactura === 6) { // Restas
                            return obj.printValue('Puntos', "- " + data + ' Pts.');
                        }
                        return obj.printValue('Puntos', data + ' Pts.');
                    }

                }}
        ],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
            $('#tableAcreditados_paginate').appendTo('.div-tabla')
                .addClass('pull-right spad')
                .css('margin-right','10px');
        },
        "fnDrawCallback": function(){
            obj.tableAcreditados.find('tbody').append('<tr role="row"><td colspan="5" id="tdCountAcreditados">Puntos acreditados total: <span style="font-weight: bold" class="spanCountAcreditados"></span></td></tr>');
            obj.obtenerTotalAcreditados(request);
        }
    });
};

MisPuntos.prototype.listarCanjeados = function() {
    var obj = this;

    var request = {
        finicio : obj.fechaInicioCanjeados.val(),
        ffin : obj.fechaFinCanjeados.val()
    };

    obj.tableCanjeados.dataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": true,
        "bInfo" : false,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : this.uri + 'mis-puntos/listarCanjeados',
            "type": "POST",
            "data" : function(d) {
                d.finicio = request.finicio;
                d.ffin = request.ffin;
            }
        },
        "columns" : [
            {"data" : "auditoria.fechaCreacionString", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Fecha', data);
                }},
            {"data" : "valorPuntos", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('Puntos', data + ' Pts.');
                }},
            {"data" : "idEntidad", "orderable": false, "render" : function(data, type, full) {
                    return obj.printValue('ID Pedido', data);
                }},
            {"data" : null, "orderable": false, "render" : function(data, type, full) {
                    return '<a class="show_pedido1"><span class="lnr lnr-magnifier"></span></a>';
                }}
        ],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
            $('#tableCanjeados_paginate').appendTo('.div-tabla')
                .addClass('pull-right spad')
                .css('margin-right','10px');
        },
        "fnDrawCallback": function(){
            obj.tableCanjeados.find('tbody').append('<tr role="row"><td colspan="4" id="tdCountCanjeados">Puntos canjeados total: <span style="font-weight: bold" class="spanCountCanjeados"></span></td></tr>');
            obj.obtenerTotalCanjeados(request);
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
                $('html, body').animate({
                    scrollTop: $cabecera.offset().top
                }, 1000);
            }
        }
    })
};

MisPuntos.prototype.printValue = function(titulo, data) {
    return  '<span class="labely">' + titulo +': </span> ' + data;
};