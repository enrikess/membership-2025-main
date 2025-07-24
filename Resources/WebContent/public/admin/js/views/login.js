function Login(uri) {
    this.uri = uri;
    this.frmLogin = $('#frmLogin');
    this.btnLogin = $('#btnLogin');
    this.btnLoader = null;
}

Login.prototype.init = function() {
    this.handler();
};

Login.prototype.handler = function() {
    var obj = this;

    obj.frmLogin.validate({
        rules : {
            username : {
                required : true
            },
            password : {
                required : true
            }
        },
        messages : {
            username : {
                required : 'Ingrese su usuario'
            },
            password : {
                required : 'Ingrese su contraseña'
            }
        }
    });

    obj.btnLogin.on('click', function(e) {
        e.preventDefault();
        var button = this;

        if (obj.frmLogin.valid()) {

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
                },
                success : function(response) {
                    obj.btnLoader.stop();
                    if(!response.status) {
                        Promotick.toast.error(response.message, 'Inicio de sesion');
                    } else {
                        location.reload();
                    }
                },
                complete : function() {
                    obj.frmLogin.find('input[type=password]').val('').focus();
                    obj.btnLoader.stop();
                }
            });
        }

    });

};

