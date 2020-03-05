/**
 * Created by Administrator on 2017/6/10.
 */
var orderWaiting = {
    init:function () {
        orderWaiting.limitTxt($("#productName"));
        orderWaiting.confirmPopup();
    },
    /*产品名称限制字数*/
    limitTxt:function(obj){
        var html = obj.html().trim();
        obj.html(html.substring(0,19) + "...")
    },
    /*确认收货  弹窗事件*/
    confirmPopup:function(){
        var confirmBtn = document.querySelector(".confirm");
        var popup = document.querySelector("#popup");
        var cancelBtn = document.querySelector(".cancel");
        confirmBtn.addEventListener("touchstart",popupShow);
        cancelBtn.addEventListener("touchstart",popupHide);
        function popupShow(){
            $("#popup").show();
            $("body,html").css({"overflow":"hidden","height":"100vh"});
        };
        function popupHide(){
            $("#popup").hide();
            $("body,html").css({"overflow":"auto","height":"auto"});
        }
    }
    
};
$(function(){
    orderWaiting.init();
})