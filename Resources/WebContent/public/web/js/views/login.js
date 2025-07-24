function Login(uri) {
    this.uri = uri;
    this.frmLogin = $('#frmLogin');
    this.btnLogin = $('#btnLogin');
    this.usernameTipoDoc = $("#usernameTipoDoc");
    this.username = $("#username");
    this.inputPassword = $('#password');
    this.messageError = $('#messageError');
    this.btnLoader = null;
}

Login.prototype.init = function() {
    this.handler();

};

Login.prototype.handler = function() {
    var obj = this;

    obj.frmLogin.validate({
        ignore: "",
        rules : {
            username : {
                required : true,
                maxlength : 13,
                // number: true
            },
            password : {
                required : true
            },
        },
        messages : {
            username : {
                required : 'Ingrese su usuario',
                maxlength: 'Maximo 13 caracteres',
                // number: 'Ingrese solo números'
            },
            password : {
                required : 'Ingrese su contraseña'
            },
        }
    });

    obj.btnLogin.on('click', function(e) {
        e.preventDefault();
        var button = this;

        if (obj.frmLogin.valid()) {
            obj.submit(button);
        }

    });

    // Key Enter Password
    obj.inputPassword.keypress(function (e) {

        if (e.which == 13) {
            obj.btnLogin.click();
        }
    });

};


Login.prototype.submit = function (button) {
    var obj = this;

    obj.usernameTipoDoc.val(obj.username.val());

    Promotick.ajax.post({
        url : obj.uri +  obj.frmLogin.attr('action'),
        headers: {
            'X-Ajax-call' : true
        },
        messageTitle : 'Inicio de sesion',
        data : obj.frmLogin.serialize(),
        contentType : 'application/x-www-form-urlencoded',
        before : function() {
            obj.btnLoader = Ladda.create(button);
            obj.btnLoader.start();
            obj.messageError.hide();
        },
        success : function(response) {
            obj.btnLoader.stop();
            if(!response.status) {
                Promotick.toast.error(response.message, 'Inicio de sesion');
                obj.messageError.show();
            } else {
                location.reload();
            }
        },
        complete : function() {
            obj.frmLogin.find('input[type=password]').val('').focus();
            obj.btnLoader.stop();
        }
    });
};

