function ReporteVentas(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridVentas');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.cleanCheckboxs = $('#clean-checkboxs');
    this.containerEliminar = $('#container-eliminar');
    this.textEliminar = $('#text-eliminar');
    this.btnBorrar = $('#btnBorrar');
    this.modalBorrarConfirm = $('#modalBorrarConfirm');
    this.modalBorrarAceptar = $('#modalBorrarAceptar');
    this.fechaOperacionEditar = $('#inptFechaOperacionEditar');
    this.inptValorVenta = $('#inptValorVenta');
    this.modalEditarAceptar = $('#modalEditarAceptar');
    this.errorValorVenta = $('#errorValorVenta');
    this.errorFechaOperacionEditar = $('#errorFechaOperacionEditar');
    this.errorNroDoc = $('#errorNroDoc');
    this.inptNroDoc = $('#inptNroDoc');
    this.inptDescripcion = $('#inptDescripcion');
    this.errorDescripcion = $('#errorDescripcion');
    this.selectedidAvances = [];
}

ReporteVentas.prototype.init = function() {
    this.handle();
    this.listarVentas();
};

ReporteVentas.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.fechaOperacionEditar.datepicker( {
        format: 'dd/mm/yyyy',
    }).on('changeDate', function (ev) {

    });

    obj.inptValorVenta.on("input", function() {
        let value = $(this).val();
        value = value.replace(/[^0-9.]/g, ''); // Remove non-numeric characters

        // Prevent multiple dots
        if ((value.match(/\./g) || []).length > 1) {
            value = value.slice(0, -1);
        }

        $(this).val(value);
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.modalEditarAceptar.on('click', function(e){
        e.preventDefault();

        if (!obj.fechaOperacionEditar.val()) {
            obj.fechaOperacionEditar.css('border-color', 'red');
            obj.errorFechaOperacionEditar.text('Debe ingresar una fecha (DD/MM/YYYY).');
            return;
        } else {
            obj.fechaOperacionEditar.css('border-color', '#e5e6e7');
            obj.errorFechaOperacionEditar.text('');
        }

        if (!obj.inptValorVenta.val()) {
            obj.inptValorVenta.css('border-color', 'red');
            obj.errorValorVenta.text('Debe ingresar un valor de venta.');
            return;
        } else {
            obj.inptValorVenta.css('border-color', '#e5e6e7');
            obj.errorValorVenta.text('');
        }

        if (!obj.inptNroDoc.val()) {
            obj.inptNroDoc.css('border-color', 'red');
            obj.errorNroDoc.text('Debe ingresar un número de documento.');
            return;
        } else {
            obj.inptNroDoc.css('border-color', '#e5e6e7');
            obj.errorNroDoc.text('');
        }

        const fechaFormat = `${obj.fechaOperacionEditar.val().split('/')[2]}-${obj.fechaOperacionEditar.val().split('/')[1]}-${obj.fechaOperacionEditar.val().split('/')[0]}`
        obj.editarVenta({
            valorVenta: Number(obj.inptValorVenta.val()),
            nroDocumento: obj.inptNroDoc.val(),
            fecha: fechaFormat,
            descripcion: obj.inptDescripcion.val(),
            idParticipanteVenta: $(this).attr('idavance'),
        })
    });


    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

    obj.cleanCheckboxs.on('change', function(e) {

        if ($(this).is(":checked")) {
            $('.meta-checkbox').each(function() {
                obj.selectedidAvances.push($(this).attr("idAvance"));
                $(`.meta-checkbox`).prop("checked", true);
            } )
        } else {
            $('.meta-checkbox').each(function() {
                obj.selectedidAvances = obj.selectedidAvances.filter(meta => `${meta}` !== `${$(this).attr("idAvance")}`);
                $(`.meta-checkbox`).prop("checked", false);
            })
        }
        obj.actualizarSeleccionados();
    })

    $(document).on('click', '.btn-editar', function() {

        let idAvance = $(this).attr("idAvance");
        let avance = $(this).data();
        obj.inptDescripcion.val(avance.descripcion);
        obj.inptNroDoc.val(avance.documento);
        obj.inptValorVenta.val(avance.venta);
        let fecha = avance.fecha && avance.fecha.length > 10 ? avance.fecha.substring(0, 10) : avance.fecha;
        fecha = `${fecha.split('-')[2]}/${fecha.split('-')[1]}/${fecha.split('-')[0]}`;
        obj.fechaOperacionEditar.datepicker('update', fecha);
        obj.modalEditarAceptar.attr("idAvance", avance.idavance);
        //obj.fechaOperacionEditar.val(fecha);
        $('#modalEditar').modal({});
    })

    $(document).on("change", ".meta-checkbox", function() {
        let idAvance = $(this).attr("idAvance");

        if ($(this).is(":checked")) {
            // Add idAvance if checked
            if (!obj.selectedidAvances.includes(idAvance)) {
                obj.selectedidAvances.push(idAvance);
            }
        } else {
            // Remove idAvance if unchecked
            obj.selectedidAvances = obj.selectedidAvances.filter(meta => meta !== idAvance);
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
        obj.borrarVentas(obj.selectedidAvances.join(","));
    });

};

ReporteVentas.prototype.actualizarSeleccionados = function() {
    var obj = this;
    obj.selectedidAvances = [...new Set(obj.selectedidAvances)];
    if (obj.selectedidAvances.length) {
        obj.containerEliminar.css('display', 'flex');
        obj.textEliminar.text('Se seleccionaron: ' + obj.selectedidAvances.length);
    } else {
        obj.containerEliminar.css('display', 'none');
    }
}

ReporteVentas.prototype.editarVenta= function(venta){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/ventas/editar-venta',
        data : JSON.stringify(venta),
        messageError : true,
        messageTitle : 'Editar venta',
        before : function() {
            obj.modalEditarAceptar.text('Cargando...');
            obj.modalEditarAceptar.prop('disabled', true);
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Editar venta');
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

ReporteVentas.prototype.borrarVentas= function(idVentas){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/ventas/borrar-ventas',
        data : JSON.stringify({
            idVentas,
        }),
        messageError : true,
        messageTitle : 'Eliminar ventas',
        before : function() {
            obj.btnBorrar.text('Cargando...');
            obj.btnBorrar.prop('disabled', true);
            obj.modalBorrarConfirm.modal('hide');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Eliminar ventas');
            }
        },
        complete : function() {
            obj.btnBorrar.text(' Eliminar registros');
            obj.btnBorrar.prop('disabled', false);
            obj.selectedidAvances = [];
            $(`.meta-checkbox`).prop("checked", false);
            obj.gridReload();
            obj.actualizarSeleccionados();
        }
    });
}

ReporteVentas.prototype.listarVentas = function(){
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
            "url" : obj.uri + 'reportes/ventas/listar-ventas',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "idParticipanteAvance", "orderable": false, "render" : this._formatCheck, "sClass" : "text-center"},
            {"data" : "idParticipante", "orderable": false, "sClass" : "text-center"},
            {"data" : "nroDocumento", "orderable": false, "sClass" : "text-center"},
            {"data" : "fechaOperacion", "orderable": false, "render": this._formatDate, "sClass" : "text-center"},
            {"data" : "valorVenta", "orderable": false, "sClass" : "text-center"},
            {"data" : "descripcion", "orderable": false, "sClass" : "text-center"},
            {"data" : "idParticipanteAvance", "orderable": false, "render" : this._formatEditar, "sClass" : "text-center p-0"},
        ],
        "lengthMenu": [[10, 20, 30], [10, 20, 30]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){
            for (let i = 0; i < obj.selectedidAvances.length; i++) {
                $(`.meta-checkbox[idAvance='${obj.selectedidAvances[i]}']`).prop("checked", true);

            }
            $('#clean-checkboxs').prop("checked", false);
            obj.actualizarSeleccionados();
        }
    });
};

ReporteVentas.prototype._formatDate = function(data, type, full){
    return data && data.length > 10 ? data.substring(0, 10) : data;
};


ReporteVentas.prototype._formatCheck = function(data, type, full) {
    return `<input type="checkbox" class="meta-checkbox" idAvance="${data}"/>`;
};

ReporteVentas.prototype._formatEditar = function(data, type, full) {
    return `<button type="button" class="btn btn-primary btn-sm btn-editar" data-documento="${full.nroDocumento}" data-fecha="${full.fechaOperacion}" data-venta="${full.valorVenta}" data-descripcion="${full.descripcion}" data-idAvance="${data}">Editar</button>`;
};

ReporteVentas.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
