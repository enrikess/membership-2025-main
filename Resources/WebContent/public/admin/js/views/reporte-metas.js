function ReporteMetas(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridVisitas');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.btnDescargarDetallado = $('#btnDescargarDetallado');
    this.cleanCheckboxs = $('#clean-checkboxs');
    this.containerEliminar = $('#container-eliminar');
    this.textEliminar = $('#text-eliminar');
    this.btnBorrar = $('#btnBorrar');
    this.modalBorrarConfirm = $('#modalBorrarConfirm');
    this.modalBorrarAceptar = $('#modalBorrarAceptar');
    this.selectedIdMetas = [];
}

ReporteMetas.prototype.init = function() {
    this.handle();
    this.listarMetas();
};

ReporteMetas.prototype.actualizarSeleccionados = function() {
    var obj = this;
    obj.selectedIdMetas = [...new Set(obj.selectedIdMetas)];
    if (obj.selectedIdMetas.length) {
        obj.containerEliminar.css('display', 'flex');
        obj.textEliminar.text('Se seleccionaron: ' + obj.selectedIdMetas.length);
    } else {
        obj.containerEliminar.css('display', 'none');
    }
}
ReporteMetas.prototype.handle = function() {
    var obj = this;
    $(document).on("change", ".meta-checkbox", function() {
        let idMeta = $(this).attr("idMeta");

        if ($(this).is(":checked")) {
            // Add idMeta if checked
            if (!obj.selectedIdMetas.includes(idMeta)) {
                obj.selectedIdMetas.push(idMeta);
            }
        } else {
            // Remove idMeta if unchecked
            obj.selectedIdMetas = obj.selectedIdMetas.filter(meta => meta !== idMeta);
        }
        obj.actualizarSeleccionados();
    });

    obj.btnBorrar.on('click', function(e){
        e.preventDefault();
        obj.modalBorrarConfirm.modal({
            backdrop: 'static',
            keyboard: false
        });
    });

    obj.modalBorrarAceptar.on('click', function(e){
        e.preventDefault();
        obj.borrarMetas(obj.selectedIdMetas.join(","));
    });

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

    obj.cleanCheckboxs.on('change', function(e) {

        if ($(this).is(":checked")) {
            $('.meta-checkbox').each(function() {
                obj.selectedIdMetas.push($(this).attr("idMeta"));
                $(`.meta-checkbox`).prop("checked", true);
            } )
        } else {
            $('.meta-checkbox').each(function() {
                obj.selectedIdMetas = obj.selectedIdMetas.filter(meta => `${meta}` !== `${$(this).attr("idMeta")}`);
                $(`.meta-checkbox`).prop("checked", false);
            })
        }
        obj.actualizarSeleccionados();
    })

};

ReporteMetas.prototype.borrarMetas = function(idMetas){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/metas/borrar-metas',
        data : JSON.stringify({
            idMetas,
        }),
        messageError : true,
        messageTitle : 'Eliminar metas',
        before : function() {
            obj.btnBorrar.text('Cargando...');
            obj.btnBorrar.prop('disabled', true);
            obj.modalBorrarConfirm.modal('hide');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Eliminar metas');
            }
        },
        complete : function() {
            obj.btnBorrar.text(' Eliminar registros');
            obj.btnBorrar.prop('disabled', false);
            obj.selectedIdMetas = [];
            $(`.meta-checkbox`).prop("checked", false);
            obj.gridReload();
            obj.actualizarSeleccionados();
        }
    });
}

ReporteMetas.prototype.listarMetas = function(){
    var obj = this;
    obj.grid.DataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": true,
        "bPaginate": true,
        "bInfo" : true,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'reportes/metas/listar-metas',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "idParticipanteMeta", "orderable": false, "render" : this._formatCheck},
            {"data" : "idParticipante", "orderable": false},
            {"data" : "nroDocumento", "orderable": false},
            {"data" : "anio", "orderable": false},
            {"data" : "mes", "orderable": false},
            {"data" : "valorMeta", "orderable": false},
            // {"data" : "emailParticipante", "orderable": false},
            {"data" : "descripcion", "orderable": false},
            {"data" : "porcentajeRebate", "orderable": false},
            {"data" : "puntosPremio", "orderable": false},
            {"data" : "valorPago", "orderable": false},
            {"data" : "fechaRegistro", "orderable": false, "render" : this._formatDate},
            {"data" : "accionProducto", "orderable": false, "render" : this._formatProducto},
        ],
        "lengthMenu": [[10, 20, 30], [10, 20, 30]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){
            for (let i = 0; i < obj.selectedIdMetas.length; i++) {
                $(`.meta-checkbox[idMeta='${obj.selectedIdMetas[i]}']`).prop("checked", true);

            }
            $('#clean-checkboxs').prop("checked", false);
            obj.actualizarSeleccionados();
        }
    });
};


ReporteMetas.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteMetas.prototype._formatDate = function(data, type, full) {
    return data && data.length > 10 ? data.substring(0, 10) : data;
};

ReporteMetas.prototype._formatCheck = function(data, type, full) {
    return `<input type="checkbox" class="meta-checkbox" idMeta="${data}"/>`;
};

ReporteMetas.prototype._formatProducto = function(data, type, full) {
    return data?.length > 1 ? data.substring(1, data.length - 1) : '';
};
