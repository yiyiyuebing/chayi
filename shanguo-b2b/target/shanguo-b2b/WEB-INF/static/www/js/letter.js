$(function() {
    PersonCommon.delTipsFun();
    PersonCommon.checkFun();
    Letter.init();
});

var Letter = {
    init : function() {
        Letter.tabFun();
        Letter.toWriteFun();
        Letter.sendFun();
        Letter.cutNavFun();
    },

    tabFun : function() {
        var liTagt = $("#letter-tab li");
        liTagt.click(function() {
            var tagt = $(this),
                index = tagt.index();
            liTagt.removeClass("active");
            tagt.addClass("active");

            var listCont = $(".notice-list-wrap .list-cont");
            listCont.addClass("hide");
            listCont.eq(index).removeClass("hide");
        });
    },

    toWriteFun : function() {
        $("#to-write").click(function()  {
            var listCont = $(".notice-list-wrap .list-cont");
            listCont.addClass("hide");
            listCont.eq(2).removeClass("hide");
        });
    },

    sendFun : function() {
        $("#send-btn").click(function() {
            $("#success-box").removeClass("hide");
        });
    },

    cutNavFun : function() {
        var navTagt = $("#letter-nav-box .letter-nav");
        navTagt.click(function() {
            var tagt = $(this);
            navTagt.removeClass("active");
            tagt.addClass("active");
        });
    }
}