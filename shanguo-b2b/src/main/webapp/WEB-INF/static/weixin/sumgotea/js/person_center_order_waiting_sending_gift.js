/**
 * Created by Administrator on 2017/6/10.
 */
var orderWaiting = {
    init:function () {
        orderWaiting.limitTxt($("#productName"));
        orderWaiting.limitTxt($("#giftName"));
    },
    /*产品名称限制字数*/
    limitTxt:function(obj){
        var html = obj.html().trim();
        obj.html(html.substring(0,19) + "...")
    }
};
$(function(){
    orderWaiting.init();
})