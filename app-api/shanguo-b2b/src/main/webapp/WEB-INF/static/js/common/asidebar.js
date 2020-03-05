/**
 * Created by Administrator on 2017/5/8.
 */
var AsideBar = {

    init:function(){
        if ($(".asidebar-wrap").length <= 0) {
            return;
        }
        AsideBar.hoverFn($(".code-wrap"),$(".code-content"));
        AsideBar.hoverFn($(".person-center"),$(".center-txt"));
        AsideBar.hoverFn($(".person-collect"),$(".collect-txt"));
        AsideBar.hoverFn($(".person-card"),$(".card-txt"));
        AsideBar.hoverFn($(".person-history"),$(".history-txt"));
        AsideBar.backHome($(".home-back-wrap"));
        AsideBar.cartCount();
        AsideBar.initQiyuConfig();
    },

    initQiyuConfig: function() {


        ysf.on({
            'onload': function(){
                if ($("input[name=shopId]").val().length > 0) {
                    $.ajax({
                        type:"POST",
                        url:"/user/shopInfo",
                        success:function(result){
                            if (result.success) {
                                var shopInfo = result.data.shopInfo;
                                ysf.config({
                                    uid: shopInfo.id,
                                    name: shopInfo.name,
                                    avatar: shopInfo.headImgUrl,
                                    mobile: shopInfo.mobile
                                });
                            }
                        }
                    });

                    if ($("input[name=product-id]").val().length > 0) {
                        ysf.product({
                            show : 1, // 1为打开， 其他参数为隐藏（包括非零元素）
                            title : $("input[name=product-name]").val() ? $("input[name=product-name]").val() : '',
                            desc : "商品ID：" + $("input[name=product-id]").val(),
                            picture : $("input[name=product-img]").val() ? $("input[name=product-img]").val() : "",
                            note : $("input[name=product-price]").val() ? $("input[name=product-price]").val()  + '   （PC采购）' : "",
                            url : window.location.href ? window.location.href : ""
                        });
                    }
                }
            }
        });

        $(".person-qq").on("click", function() {
            ysf.open();
            //location.href = ysf.url();
        });
    },
    /*鼠标经过效果*/
    hoverFn:function(obj,target){
        target.hide();
        obj.hover(function(){
            target.show();
        },function(){
            target.hide();
        });
    },
    /*backHome*/
    backHome:function(obj){
        obj.click(function(){
            $("body,html").animate({scrollTop:0},200);
        });
    },

    cartCount:function() {
        if ($("#num").length <= 0) return;
        $.ajax({
            type:"POST",
            url:"/cart/num",
            success:function(result){
                if (result.success && result.data) {
                    $("#num").html(result.data.num)
                }else {
                    return;
                }
            }
        });
    }
};

$(function(){
    AsideBar.init();
});