function Resultado(uri, tp) {
    this.uri = uri;
    this.tipo = parseInt(tp);
    this.contentAnual = $('#contentAnual');
    this.contentMensual = $('#contentMensual');
    this.COLOR_VERDE = "#ED4E4E";
    this.COLOR_AMBAR = "#A5DF00";
    this.COLOR_ROJO = "#66D054";
}

Resultado.prototype.init = function() {
    this.handle();
    if (this.tipo === 2) {
        this.viewMeta(1, this.contentAnual); // Externo con meta Anual
    } else if (this.tipo === 1) {
        this.listarResumenMeta(); // Interno con meta mensual
    }
};

Resultado.prototype.handle = function() {
    var obj = this;
};

Resultado.prototype.viewMeta = function(tipo, context) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'mi-resultado/viewInformacionMeta/' + tipo,
        context : context,
        complete : function() {
            obj.obtenerMeta(tipo);
        }
    });
};

Resultado.prototype.obtenerMeta = function(tipo) {
    var obj = this;

    var mostrarGrafico = parseInt($(document).find('input[id=meta-' + tipo + ']').val());

    if (mostrarGrafico === 1) {
        var load = 'chartAnio';
        if (tipo === 2) {
            load = 'chartSemestre';
        }

        Promotick.ajax.get({
            url : obj.uri +  'mi-resultado/getMetaParticipante/' + tipo,
            messageError : false,
            before : function() {
                $(document).find("#" + load).LoadingOverlay('show');
            },
            success : function(response) {
                if (response.status) {
                    $(document).find("#" + load).hide().fadeIn('slow');
                    obj.renderChart(response.data, tipo);
                }
            },
            complete : function() {
                $(document).find("#" + load).LoadingOverlay('hide', true);
            }
        });
    }

};

Resultado.prototype.renderChart = function(response, tipo) {
    var obj = this;

    var valorParticion = response.periodoMeta / 3;
    var metasColor = [
        {
            color : obj.COLOR_VERDE,
            startValue : 0,
            endValue : valorParticion,
            innerRadius : "97%"
        },
        {
            color : obj.COLOR_AMBAR,
            startValue : valorParticion,
            endValue : valorParticion * 2,
            innerRadius : "97%"
        },
        {
            color : obj.COLOR_ROJO,
            startValue : valorParticion * 2,
            endValue : valorParticion * 3,
            innerRadius : "95%"
        }
    ];

    var marcador = parseFloat(response.totalVenta);
    var meta = parseFloat(response.periodoMeta);

    if (marcador > meta) {
        marcador = meta;
    }

    AmCharts.makeChart(tipo === 1 ? 'chartAnio' : 'chartSemestre', {
        "type": "gauge",
        "theme": "light",
        "axes": [ {
            "axisThickness": 1,
            "axisAlpha": 0.2,
            "tickAlpha": 0.2,
            "valueInterval": "20%",
            "bands": metasColor,
            "bottomTextFontSize" : 20,
            "bottomTextYOffset" : -20,
            "bottomText": (parseFloat(response.teFalta).toFixed(2)) + " Pts.",
            "endValue": meta.toFixed(2),
            "startValue": 0,
            "fontSize" : 9,
            "labelFunction": function(valueText, serialDataItem, categoryAxis) {
                return parseFloat(valueText).toFixed(2);
            }
            ,
            "labelOffset" : 2
        } ]
        ,
        "arrows": [{
            "color": "#000000",
            "nailRadius": 4,
            "startWidth": 3,
            "innerRadius": 0,
            "clockWiseOnly": true,
            "nailAlpha": 1,
            "value": marcador.toFixed(2)
        }]
    } );

};

Resultado.prototype.listarResumenMeta = function() {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'mis-puntos/viewHistorialPuntos/1',
        context : obj.contentMensual,
        complete : function() {

        }
    });
};
