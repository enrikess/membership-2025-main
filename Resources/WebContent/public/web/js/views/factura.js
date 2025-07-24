function Factura(uri) {
    this.uri = uri;
    this.formFactura = $('#formFactura');
    this.imagenFile = $('#imagenFile');
    this.btnGuardar = $('#btnGuardar');
}

Factura.prototype.init = function() {
    var obj = this;
    obj.handle();
};

Factura.prototype.handle = function() {

    var obj = this;

    obj.btnGuardar.on('click', function(e){
        e.preventDefault();

        if (obj.formFactura.valid()) {
            obj.cargaFactura();
        } else {
            obj.formFactura.validate().focusInvalid();
        }
    });

    obj.formFactura.validate({
        rules: {
            imagenFile: {
                required: true
            }
        },
        messages: {
            imagenFile: {
                required: "Seleccione un archivo"
            }
        },
        highlight: function (element, error, validClass) {
            $(element).addClass(error).removeClass(validClass);
            $(element.form).find("label[for=" + element.id + "]")
                .addClass(error);
        },
        unhighlight: function (element, error, validClass) {
            $(element).removeClass(error).addClass(validClass);
            $(element.form).find("label[for=" + element.id + "]")
                .removeClass(error);
        },
        errorClass: 'error-login'
    });
};

Factura.prototype.cargaFactura = function() {
    var obj = this;

    var data = new FormData();
    $.each($('#imagenFile')[0].files, function(i, file) {
        data.append('imagenFile', file);
    });

    $('#form-factura').block({
        message: '<h1>Procesando</h1>',
        css: { border: '3px solid #a00' }
    });

    $.ajax({
        url : obj.uri + 'factura/cargar-comprobante',
        data : data,
        type: 'post',
        processData : false,
        contentType : false,
        before : function() {

        },
        success : function() {
            console.log('factura cargada');
            $("#factura").modal();
        },
        complete : function() {
            console.log('registrar codigo');
            $('#form-factura').unblock();
        }
    });
};