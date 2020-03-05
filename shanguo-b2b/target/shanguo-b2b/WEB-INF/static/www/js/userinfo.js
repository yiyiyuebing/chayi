$(function() {
    UserInfo.init();
});

var UserInfo = {
    init : function() {
        UserInfo.tabFun();
    },

    tabFun : function() {
        var liTagt = $("#user-nav li");
        liTagt.click(function () {
            var tagt = $(this),
                index = tagt.index();
            liTagt.removeClass("active");
            tagt.addClass("active");

            var listCont = $("#user-tab-box .user-info-wrap");
            listCont.addClass("hide");
            listCont.eq(index).removeClass("hide");
        });
    }
}