function addFav(id){
    var data = {
        _csrf: csrf.toString(),        
    };
    $.post(contextPath + "/ajax/fav/add/" + id, data, function(data){
        if (data.success) {
            $("#fav"+id+" span.glyphicon").removeClass("glyphicon-heart-empty").removeClass("icon-love").
                    addClass("glyphicon-heart").addClass("icon-favorite");
            $("#fav"+id+" span.label").removeClass("label-white").addClass("label-danger");
        }
    });
}

function delFav(id){
    var data = {
        _csrf: csrf.toString(),        
    };
    $.post(contextPath + "/ajax/fav/del/" + id, data, function(data){
        if (data.success) {
            $("#fav"+id+" span.glyphicon").addClass("glyphicon-heart-empty").addClass("icon-love").
                    removeClass("glyphicon-heart").removeClass("icon-favorite");
            $("#fav"+id+" span.label").addClass("label-white").removeClass("label-danger");
        }
    });
}

function toggleFav(id) {
     var data = {
        _csrf: csrf.toString(),        
    };
    $.post(contextPath + "/ajax/fav/toggle/" + id, data, function(data){
        if (data.success) {
            $("#fav"+id+" span.glyphicon").toggleClass("glyphicon-heart-empty").toggleClass("icon-love").
                    toggleClass("glyphicon-heart").toggleClass("icon-favorite");
            $("#fav"+id+" span.label").toggleClass("label-white").toggleClass("label-danger");
        }
    });
}