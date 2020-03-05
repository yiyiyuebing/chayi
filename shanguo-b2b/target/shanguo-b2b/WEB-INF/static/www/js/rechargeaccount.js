$(function() {
    PersonCommon.checkFun();
    RechargeAccount.init();
});

var RechargeAccount = {
    init : function() {
        RechargeAccount.openRechargeDlg();
        RechargeAccount.closeRechargeDlg();
        RechargeAccount.openWithdrawDlg();
        RechargeAccount.closeWithdrawDlg();
        RechargeAccount.filterFun();
    },

    openRechargeDlg : function() {
        $("#recharge-btn").click(function() {
            $("#recharge-dlg").removeClass("hide");
        });
    },

    closeRechargeDlg :function() {
        $(".close-recharge-dlg").click(function() {
            $("#recharge-dlg").addClass("hide");
        });
    },

    openWithdrawDlg : function() {
        $("#withdraw-btn").click(function() {
            $("#withdraw-dlg").removeClass("hide");
        });
    },

    closeWithdrawDlg :function() {
        $(".close-withdraw-dlg").click(function() {
            $("#withdraw-dlg").addClass("hide");
        });
    },

    filterFun : function() {
        $(".filter-item").click(function() {
            $("#title-text").text($(this).text());
        });
    }
}