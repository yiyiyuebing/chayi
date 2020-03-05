$(function() {
    PersonCommon.delTipsFun();
    PersonCommon.checkFun();
    PersonCommon.cutNav();
    Pug.init();
});

var Pug = {
    init : function() {
        Pug.showBatchBox();
        Pug.hideBatchBox();
    },

    showBatchBox : function() {
        $("#batch-btn").click(function() {
            $("#title-batch-box").addClass("check");
            $("#pug-list-box").addClass("check");

            Pug.checkActive();
        });
    },

    hideBatchBox :function() {
        $("#complete-btn").click(function () {
            $("#title-batch-box").removeClass("check");
            $("#pug-list-box").removeClass("check");
            $("#pug-list-box .list-item").removeClass("active");

            Pug.unBindCheckActive();
        });
    },

    checkActive : function() {
        $("#pug-list-box .list-item").click(function() {
           $(this).toggleClass("active");
        });
    },

    unBindCheckActive : function() {
        $("#pug-list-box .list-item").unbind("click");
    }
}