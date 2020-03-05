$(function() {
    PersonCommon.checkFun();
    RateManage.init();
});

var RateManage = {
    init : function() {
        RateManage.checkItemFun();
    },

    checkItemFun : function() {
        $("#dt-items .item").click(function() {
            $(this).toggleClass("active");
        });
    }
}