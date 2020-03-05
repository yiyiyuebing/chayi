$(function(){
    Feedback.init();
});

var Feedback = {
    init : function() {
        Feedback.sendFun();
    },

    sendFun : function() {
        $("#send-btn").click(function() {
           $("#success-box").removeClass("hide");
        });
    }
}