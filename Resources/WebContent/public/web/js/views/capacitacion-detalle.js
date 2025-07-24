function CapacitacionDetalle(uri) {
    this.uri = uri;
}

CapacitacionDetalle.prototype.init = function() {
    this.handler();
};

CapacitacionDetalle.prototype.handler = function() {
    var obj = this;

    //init video
    $('.video-item').each(function () {
        var id = $(this).attr('id');
        videojs('#'+ id);
    });

    //init document
    $('.post-format-document').each(function(){
        var urlArchivo = $(this).attr('data-file');
        var id = $(this).attr('id');
        $(this).css('height','300px')

        var options = {
            pdfOpenParams: {
                //view: "FitV"
            }
        };

        PDFObject.embed(urlArchivo, "#" + id, options);
    });
};