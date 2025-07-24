function MiMeta(uri) {
    this.uri = uri;
    this.contentInfoMeta = $('#contentInfoMeta');
    this.COLOR_VERDE = "#ED4E4E";
    this.COLOR_AMBAR = "#A5DF00";
    this.COLOR_ROJO = "#66D054";

    this.avance = $('#avance');
    this.faltante = $('#faltante');
    this.metaText = $('#meta');
    this.porcentajeRebate = $('#porcentajeRebate');
    this.valorPago = $('#valorPago');
    this.orderby = $('#orderby');
    this.trimestreSelect = $('#trimestreSelect');
    this.accionesTable = $('#accionesTable');
}

MiMeta.prototype.init = function() {
    this.handle();
    this.viewMeta(1);
};

MiMeta.prototype.handle = function() {
    var obj = this;

    // obj.orderby.select2();

    obj.trimestreSelect.on('change', function (e) {
        var mesSelect = obj.trimestreSelect.val();
        obj.contentInfoMeta.html('');
        obj.viewMeta(mesSelect);
    });
};

MiMeta.prototype.viewMeta = function(mes) {
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'mi-resultado/viewInformacionMeta/' + mes,
        before : function() {
            obj.contentInfoMeta.LoadingOverlay('show');
        },
        success : function(response) {
            const meta = response[0][0];
            obj.avance.text(`$${meta.avance}`);
            obj.metaText.text(`$${meta.valorMeta}`);
            obj.porcentajeRebate.text(`${meta.porcentajeRebate * 100}%`);
            obj.valorPago.text(`$${meta.valorPago}`);
            obj.faltante.text(`$${meta.valorMeta - meta.avance}`);
            obj.contentInfoMeta.LoadingOverlay('hide', true);

            let avancePorcentaje = meta.valorMeta && meta.valorMeta > 0 ? Math.round(meta.avance * 100/meta.valorMeta) : 0;
            avancePorcentaje = avancePorcentaje > 100 ? 100 : avancePorcentaje;
            obj.initCharts("nexxt", "Avance", avancePorcentaje, 100); //Totalizado Metas

            obj.accionesTable.empty();
            response[1].forEach(function(item, i) {
                let fecha = item.fechaProducto && item.fechaProducto.length > 10 ? item.fechaProducto.substring(0,10) : item.fechaProducto;
                fecha = `${fecha.split('-')[2]}/${fecha.split('-')[1]}/${fecha.split('-')[0]}`;
                obj.accionesTable.append(`
                    <tr>
                        <td>${fecha}</td>
                        <td>${item.accionProducto}</td>
                        <td>${item.descripcion}</td>
                        <td>${item.cantidadProducto}</td>
                    </tr>
                `)
            })
        },
        complete : function() {

        }
    });
};

MiMeta.prototype.initCharts = function (div, titulo, avance, target) {


    am4core.ready(function() {

        // Themes begin
        am4core.useTheme(am4themes_animated);
        // Themes end



        var container = am4core.create(div, am4core.Container);
        container.width = am4core.percent(100);
        container.height = am4core.percent(100);
        container.layout = "vertical";

        createBulletChart(container, "solid");

        /* Create ranges */
        function createRange(axis, from, to, color) {
            var range = axis.axisRanges.create();
            range.value = from;
            range.endValue = to;
            range.axisFill.fill = color;
            range.axisFill.fillOpacity = 0.8;
            range.label.disabled = true;
            range.grid.disabled = true;
        }

        /* Create bullet chart with specified color type for background */
        function createBulletChart(parent, colorType) {
            var colors = ["#cfa47096", "#cfa470b8", "#cfa470cf", "#cfa470e8", "#cfa470"];

            /* Create chart instance */
            var chart = container.createChild(am4charts.XYChart);
            chart.paddingRight = 25;

            /* Add data */
            chart.data = [{
                "category": titulo,
                "value": avance,
                "target": target
            }];

            /* Create axes */
            var categoryAxis = chart.yAxes.push(new am4charts.CategoryAxis());
            categoryAxis.dataFields.category = "category";
            categoryAxis.renderer.minGridDistance = 30;
            categoryAxis.renderer.grid.template.disabled = true;

            var valueAxis = chart.xAxes.push(new am4charts.ValueAxis());
            valueAxis.renderer.minGridDistance = 30;
            valueAxis.renderer.grid.template.disabled = true;
            valueAxis.min = 0;
            valueAxis.max = 100;
            valueAxis.strictMinMax = true;
            valueAxis.numberFormatter.numberFormat = "#'%'";
            valueAxis.renderer.baseGrid.disabled = true;

            /*
                In order to create separate background colors for each range of values,
                you have to create multiple axisRange objects each with their own fill color
            */
            if (colorType === "solid") {
                var start = 0,
                    end = 20;
                for (var i = 0; i < 5; ++i) {
                    createRange(valueAxis, start, end, am4core.color(colors[i]));
                    start += 20;
                    end += 20;
                }
            }
            /*
                In order to create a gradient background, only one axisRange object is needed
                and a gradient object can be assigned to the axisRange's fill property.
            */
            else if (colorType === "gradient") {
                var gradient = new am4core.LinearGradient();
                for (var i = 0; i < 5; ++i) {
                    // add each color that makes up the gradient
                    gradient.addColor(am4core.color(colors[i]));
                }
                createRange(valueAxis, 0, 100, gradient);
            }

            /* Create series */
            var series = chart.series.push(new am4charts.ColumnSeries());
            series.dataFields.valueX = "value";
            series.dataFields.categoryY = "category";
            series.columns.template.fill = am4core.color("#000");
            series.columns.template.stroke = am4core.color("#fff");
            series.columns.template.strokeWidth = 1;
            series.columns.template.strokeOpacity = 0.5;
            series.columns.template.height = am4core.percent(25);
            series.tooltipText = "{value}"


            var series2 = chart.series.push(new am4charts.StepLineSeries());
            series2.dataFields.valueX = "target";
            series2.dataFields.categoryY = "category";
            series2.strokeWidth = 3;
            series2.noRisers = true;
            series2.startLocation = 0.15;
            series2.endLocation = 0.85;
            series2.tooltipText = "{valueX}"
            series2.stroke = am4core.color("#000");

            chart.cursor = new am4charts.XYCursor()
            chart.cursor.lineX.disabled = true;
            chart.cursor.lineY.disabled = true;

            valueAxis.cursorTooltipEnabled = false;
            chart.arrangeTooltips = false;
        }

    }); // end am4core.ready()
    // am4core.ready(function () {
    //     // Themes begin
    //     am4core.useTheme(am4themes_animated);
    //     // Themes end
    //
    //
    //     var container = am4core.create(div, am4core.Container);
    //     container.width = am4core.percent(100);
    //     container.height = am4core.percent(100);
    //     container.layout = "vertical";
    //
    //     createBulletChart(container, "solid");
    //     //            createBulletChart(container, "gradient");
    //
    //     /* Create ranges */
    //     function createRange(axis, from, to, color) {
    //         var range = axis.axisRanges.create();
    //         range.value = from;
    //         range.endValue = to;
    //         range.axisFill.fill = color;
    //         range.axisFill.fillOpacity = 0.8;
    //         range.label.disabled = true;
    //         range.grid.disabled = true;
    //     }
    //
    //     /* Create bullet chart with specified color type for background */
    //     function createBulletChart(parent, colorType) {
    //         var colors = ["#fff", "#fff", "#fff", "#fff", "#fff"];
    //
    //         /* Create chart instance */
    //         var chart = container.createChild(am4charts.XYChart);
    //         chart.paddingRight = 25;
    //
    //         /* Add data */
    //         chart.data = [{
    //             "category": titulo,
    //             "value": avance,
    //             "target": target
    //         }];
    //
    //         /* Create axes */
    //         var categoryAxis = chart.yAxes.push(new am4charts.CategoryAxis());
    //         categoryAxis.dataFields.category = "category";
    //         categoryAxis.renderer.minGridDistance = 30;
    //         categoryAxis.renderer.grid.template.disabled = true;
    //
    //         var valueAxis = chart.xAxes.push(new am4charts.ValueAxis());
    //         valueAxis.renderer.minGridDistance = 30;
    //         valueAxis.renderer.grid.template.disabled = true;
    //         valueAxis.min = 0;
    //         valueAxis.max = 100;
    //         valueAxis.strictMinMax = true;
    //         valueAxis.numberFormatter.numberFormat = "#'%'";
    //         valueAxis.renderer.baseGrid.disabled = true;
    //
    //         /*
    //           In order to create separate background colors for each range of values,
    //           you have to create multiple axisRange objects each with their own fill color
    //         */
    //         if (colorType === "solid") {
    //             var start = 0,
    //                 end = 20;
    //             for (var i = 0; i < 5; ++i) {
    //                 createRange(valueAxis, start, end, am4core.color(colors[i]));
    //                 start += 20;
    //                 end += 20;
    //             }
    //         }
    //         /*
    //           In order to create a gradient background, only one axisRange object is needed
    //           and a gradient object can be assigned to the axisRange's fill property.
    //         */
    //         else if (colorType === "gradient") {
    //             var gradient = new am4core.LinearGradient();
    //             for (var i = 0; i < 5; ++i) {
    //                 // add each color that makes up the gradient
    //                 gradient.addColor(am4core.color(colors[i]));
    //             }
    //             createRange(valueAxis, 0, 100, gradient);
    //         }
    //
    //         /* Create series */
    //         var series = chart.series.push(new am4charts.ColumnSeries());
    //         series.dataFields.valueX = "value";
    //         series.dataFields.categoryY = "category";
    //         series.columns.template.fill = am4core.color("#09a709");
    //         series.columns.template.stroke = am4core.color("#09a709");
    //         series.columns.template.strokeWidth = 1;
    //         series.columns.template.strokeOpacity = 0.5;
    //         series.columns.template.height = am4core.percent(25);
    //         series.tooltipText = "{value}"
    //
    //
    //         var series2 = chart.series.push(new am4charts.StepLineSeries());
    //         series2.dataFields.valueX = "target";
    //         series2.dataFields.categoryY = "category";
    //         series2.strokeWidth = 3;
    //         series2.noRisers = true;
    //         series2.startLocation = 0.15;
    //         series2.endLocation = 0.85;
    //         series2.tooltipText = "{valueX}"
    //         series2.stroke = am4core.color("#000");
    //
    //         chart.cursor = new am4charts.XYCursor()
    //         chart.cursor.lineX.disabled = true;
    //         chart.cursor.lineY.disabled = true;
    //
    //         valueAxis.cursorTooltipEnabled = false;
    //         chart.arrangeTooltips = false;
    //     }
    //
    // });
};