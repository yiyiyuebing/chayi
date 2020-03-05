$(function() {
    PersonCommon.delTipsFun();
    PersonCommon.checkFun();
    PersonCommon.cutNav();
    Collect.init();
});

var Collect = {
    init : function() {
        Collect.showBatchBox();
        Collect.hideBatchBox();
        Collect.prePage();
        Collect.nextPage();
    },

    showBatchBox : function() {
        $("#batch-btn").click(function() {
            $("#title-batch-box").addClass("check");
            $("#collect-list-box").addClass("check");

            Collect.checkActive();
        });
    },

    hideBatchBox :function() {
        $("#complete-btn").click(function () {
            $("#title-batch-box").removeClass("check");
            $("#collect-list-box").removeClass("check");
            $("#collect-list-box .list-item").removeClass("active");

            Collect.unBindCheckActive();
        });
    },

    checkActive : function() {
        $("#collect-list-box .list-item").click(function() {
           $(this).toggleClass("active");
        });
    },

    unBindCheckActive : function() {
        $("#collect-list-box .list-item").unbind("click");
    },

    prePage : function() {
        $("#page-pre").click(function() {
            $("#page-next").removeClass("disable");

            var currentTagt = $("#current-page"),
                currentPage = parseInt(currentTagt.text());
            if ((currentPage - 1) == 1) {
                $(this).addClass("disable");
            }
            else if (currentPage == 1) {
                return;
            }

            currentTagt.text(currentPage - 1);
        });
    },

    nextPage : function() {
        $("#page-next").click(function() {
            $("#page-pre").removeClass("disable");

            var currentTagt = $("#current-page"),
                currentPage = parseInt(currentTagt.text()),
                totalTagt = $("#total-page"),
                totalPage = parseInt(totalTagt.text());

            if ((currentPage + 1) == totalPage) {
                $(this).addClass("disable");
            }
            else if (currentPage == totalPage) {
                return;
            }

            currentTagt.text(currentPage + 1);
        });
    }
}