/**
 * Created by dy on 2017/6/17.
 */
var wxPay = {
    init: function(){
        if (!$.checkLogin()) { //验证登录
            return;
        }
        wxPay.createQrcode();
        setInterval(wxPay.validatePaySuccess, 1500);
    },
    // weixin://wxpay/bizpayurl?pr=VzB38gs
    createQrcode: function() {
        var qrcode = $("input[name=qrcode]").val();
        $(".qrimg").qrcode({
            width: 200,
            height: 200,
            text: qrcode
        });
    },

    validatePaySuccess: function() {
        $.ajax({
            type: "POST",
            data: {orderId: $("input[name=orderId]").val(), stageNum: $("input[name=stageNum]").val() },
            url: "/order/checkPaySuccess",
            success: function(result) {
                if (result.success) {
                    window.location.href = "/order/success/" + $("input[name=orderId]").val();
                }
            }
        });
    }
};
$(function() {
    wxPay.init();
});