function ReporteAcciones(uri) {
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
    this.modalEditarAceptar = $('#modalEditarAceptar');
    this.inputEditarDescripcion = $('#inputEditarDescripcion');
    this.errorDescripcionEditar = $('#errorDescripcionEditar');
    this.inputEditarAccion = $('#inputEditarAccion');
    this.errorAccionEditar = $('#errorAccionEditar');
    this.inputEditarCantidad = $('#inputEditarCantidad');
    this.errorCantidadEditar = $('#errorCantidadEditar');
    this.inputEditarFecha = $('#inputEditarFecha');
    this.errorFechaEditar = $('#errorFechaEditar');
    this.selectedIdMetas = [];
}

ReporteAcciones.prototype.init = function() {
    this.handle();
    this.listarAcciones();
};

ReporteAcciones.prototype.actualizarSeleccionados = function() {
    var obj = this;
    obj.selectedIdMetas = [...new Set(obj.selectedIdMetas)];
    if (obj.selectedIdMetas.length) {
        obj.containerEliminar.css('display', 'flex');
        obj.textEliminar.text('Se seleccionaron: ' + obj.selectedIdMetas.length);
    } else {
        obj.containerEliminar.css('display', 'none');
    }
}
ReporteAcciones.prototype.handle = function() {
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

    obj.inputEditarFecha.datepicker( {
        format: 'dd/mm/yyyy',
    }).on('changeDate', function (ev) {

    });

    obj.modalBorrarAceptar.on('click', function(e){
        e.preventDefault();
        obj.borrar(obj.selectedIdMetas.join(","));
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

    $(document).on('click', '.btn-editar', function() {

        let accion = $(this).data();
        let fecha = accion.fecha && accion.fecha.length > 10 ? accion.fecha.substring(0, 10) : accion.fecha;
        fecha = `${fecha.split('-')[2]}/${fecha.split('-')[1]}/${fecha.split('-')[0]}`;
        obj.inputEditarFecha.datepicker('update', fecha);
        obj.inputEditarDescripcion.val(accion.descripcion);
        obj.inputEditarCantidad.val(accion.cantidad);
        obj.inputEditarAccion.val(accion.accion);
        obj.modalEditarAceptar.attr("id", accion.id);
        $('#modalEditar').modal({});
    })


    obj.modalEditarAceptar.on('click', function(e) {
        if (!obj.inputEditarFecha.val()) {
            obj.inputEditarFecha.css('border-color', 'red');
            obj.errorFechaEditar.text('Debe ingresar una fecha (DD/MM/YYYY).');
            return;
        } else {
            obj.inputEditarFecha.css('border-color', '#e5e6e7');
            obj.errorFechaEditar.text('');
        }

        if (!obj.inputEditarDescripcion.val()) {
            obj.inputEditarDescripcion.css('border-color', 'red');
            obj.errorDescripcionEditar.text('Debe ingresar una descripción.');
            return;
        } else {
            obj.inputEditarDescripcion.css('border-color', '#e5e6e7');
            obj.errorDescripcionEditar.text('');
        }

        if (!obj.inputEditarAccion.val()) {
            obj.inputEditarAccion.css('border-color', 'red');
            obj.errorAccionEditar.text('Debe ingresar una acción.');
            return;
        } else {
            obj.inputEditarAccion.css('border-color', '#e5e6e7');
            obj.errorAccionEditar.text('');
        }

        if (!obj.inputEditarCantidad.val()) {
            obj.inputEditarCantidad.css('border-color', 'red');
            obj.errorCantidadEditar.text('Debe ingresar una cantidad.');
            return;
        } else {
            obj.inputEditarCantidad.css('border-color', '#e5e6e7');
            obj.errorCantidadEditar.text('');
        }

        const fechaFormat = `${obj.inputEditarFecha.val().split('/')[2]}-${obj.inputEditarFecha.val().split('/')[1]}-${obj.inputEditarFecha.val().split('/')[0]}`
        obj.editar({
            idLafabrilProducto: $(this).attr('id'),
            fecha: fechaFormat,
            descripcion: obj.inputEditarDescripcion.val(),
            cantidad: obj.inputEditarCantidad.val(),
            accion: obj.inputEditarAccion.val(),
        })
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

ReporteAcciones.prototype.editar = function(accion){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/acciones/editar',
        data : JSON.stringify(accion),
        messageError : true,
        messageTitle : 'Editar registros',
        before : function() {
            obj.modalEditarAceptar.text('Cargando...');
            obj.modalEditarAceptar.prop('disabled', true);
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Editar registros');
                $('#modalEditar').modal('hide');
                obj.gridReload();
            }
        },
        complete : function() {
            obj.modalEditarAceptar.text('Aceptar');
            obj.modalEditarAceptar.prop('disabled', false);
        },
        error: function(status, error) {
            console.log('error ' + status + ': ' + error);
        }
    });
}

ReporteAcciones.prototype.borrar = function(idMetas){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/acciones/borrar',
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

ReporteAcciones.prototype.listarAcciones = function(){
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
            "url" : obj.uri + 'reportes/acciones/listar-acciones',
            "type": "POST",
            "data" : function(d) {
                // d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                // d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "idLafabrilProducto", "orderable": false, "render" : this._formatCheck},
            {"data" : "fechaProducto", "orderable": false, "render" : this._formatDate},
            {"data" : "descripcion", "orderable": false},
            {"data" : "accionProducto", "orderable": false},
            {"data" : "cantidadProducto", "orderable": false},
            {"data" : "idLafabrilProducto", "orderable": false, "render" : this._formatEditar},
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

ReporteAcciones.prototype._formatDate = function(data, type, full){
    return data && data.length > 10 ? data.substring(0, 10) : data;
};


ReporteAcciones.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteAcciones.prototype._formatCheck = function(data, type, full) {
    return `<input type="checkbox" class="meta-checkbox" idMeta="${data}"/>`;
};

ReporteAcciones.prototype._formatEditar = function(data, type, full) {
    return `<button type="button" class="btn btn-primary btn-sm btn-editar" data-fecha="${full.fechaProducto}" data-descripcion="${full.descripcion}" data-accion="${full.accionProducto}"  data-cantidad="${full.cantidadProducto}" data-id="${data}">Editar</button>`;
};
