$(function() {
    PersonCommon.showNav();
});

var PersonCommon = {
    showNav : function() {
        var itemTagt = $("#person-left-nav .first-item");
        itemTagt.click(function() {
            $(this).toggleClass("active");
        });
    },

    delTipsFun : function() {
        $(".del-tagt").click(function() {
            layer.confirm('确定删除?', {title:'提示'}, function(index){
                layer.close(index);
            });
        });
    },

    checkFun : function() {
        $(".check-tagt").click(function() {
            $(this).toggleClass("active");
        });
    },

    cutNav : function() {
        var navTagt = $("#top-nav li");
        navTagt.click(function() {
            var tagt = $(this);
            navTagt.removeClass("active");
            tagt.addClass("active");
        });
    }
}