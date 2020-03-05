/**
 * Created by Administrator on 2017/6/14.
 */
var trackingNumber = {
    init:function(){
        trackingNumber.popupSubmit();
    },
    /*提交弹窗*/
    popupSubmit:function(){
        $(".submit-btn").bind("touchstart",function(){
            $(".popup").show();
            setTimeout(function(){
                $(".popup").hide();
            },1000)
        });
    }
};
$(function(){
    trackingNumber.init();
});