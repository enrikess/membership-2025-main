function Wishlist(uri) {
    this.uri = uri;
    this.contentWishlist = $('#contentWishlist');
}

Wishlist.prototype.init = function() {
    this.handle();
    this.listarWishlist();
};

Wishlist.prototype.handle = function() {
    var obj = this;

    $(document).on('click', '.remove-button', function(){
        var idProductoWishlist = $(this).attr('id');
        obj.eliminarProductoWishlist(idProductoWishlist);
    });
};

Wishlist.prototype.listarWishlist = function() {
    var obj = this;
    Promotick.render.get({
        url : obj.uri + 'wishlist/viewWishlists',
        context : obj.contentWishlist
    });
};

Wishlist.prototype.eliminarProductoWishlist = function(idProductoWishlist){
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'wishlist/eliminarWishlist/' + idProductoWishlist,
        messageError : true,
        messageTitle : 'Eliminar producto favorito',
        before : function() {
            obj.contentWishlist.html('');
            obj.contentWishlist.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message);
                obj.listarWishlist();
            }
        },
        complete : function() {
        }
    });
};