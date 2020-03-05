/**
 * Created by Administrator on 2017/6/10.
 */
var orderWaiting = {
    init:function () {
        orderWaiting.confirmPopup();
    },
    /*确认收货  弹窗事件*/
    confirmPopup:function(){
        var confirmBtn = document.querySelector("#undo");
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