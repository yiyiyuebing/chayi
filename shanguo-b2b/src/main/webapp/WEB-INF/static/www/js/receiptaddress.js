$(function() {
    PersonCommon.delTipsFun();
    ReceiptAddress.init();
});

var ReceiptAddress = {
    init : function() {
        ReceiptAddress.openDlg();
        ReceiptAddress.closeDlg();
    },

    openDlg : function() {
        $(".open-dialog-btn").click(function() {
            $("#dialog-box").removeClass("hide");
        });
    },

    closeDlg :function() {
        $(".close-dialog-btn").click(function() {
            $("#dialog-box").addClass("hide");
        });
    }
}