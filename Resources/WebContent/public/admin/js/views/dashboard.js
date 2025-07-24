function Dashboard(uri) {
    this.uri = uri;
    this.chartBar = $('#chartBar');
    this.filterDate = $('#filterDate');
    this.inptDate = $('#inptDate');
    this.nombreMes = $('#nombreMes');
    this.graficoVisitas = null;
    this.months = [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre" ];
}

Dashboard.prototype.init = function() {
    this.handler();
    this.obtenerGrafico();
};

Dashboard.prototype.handler = function() {
    var obj = this;

    obj.filterDate.datepicker( {
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months",
        todayBtn: false,
        startView: 1,
        setDate: new Date(),
        endDate: "0m"
    }).on('changeDate', function (ev) {
        obj.obtenerGrafico();
        obj.fijarMes();
    });

    obj.fijarMes();
};

Dashboard.prototype.fijarMes = function() {
    var obj = this;
    var selectedMonthName = obj.months[obj.filterDate.datepicker('getDate').getMonth()];
    obj.nombreMes.html(selectedMonthName);
};

Dashboard.prototype.obtenerGrafico = function() {
    var obj = this;

    $.promotick.ajax.get({
        url : obj.uri + 'obtenerGraficoVisitas/' + obj.inptDate.val(),
        loader : '#graph-content',
        success : function(response) {
            var barData = {
                labels: response.data.columnas,
                datasets: [
                    {
                        label: "Visitas",
                        backgroundColor: 'rgba(26,179,148,0.5)',
                        borderColor: "rgba(26,179,148,0.7)",
                        pointBackgroundColor: "rgba(26,179,148,1)",
                        pointBorderColor: "#fff",
                        borderWidth: 2,
                        data: response.data.valores
                    }
                ]
            };

            var barOptions = {
                responsive: true
            };

            if (obj.graficoVisitas != null) {
                obj.graficoVisitas.destroy();
            }
            obj.graficoVisitas = new Chart(obj.chartBar, {type: 'bar', data: barData, options: barOptions});
        }
    })

};