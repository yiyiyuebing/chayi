/**
 * Created by Administrator on 2017/5/9.
 */
var Index = {
    init:function(){
        Index.swiper();
        Index.swicthFn($(".nav-product-aside-group"),$(".nav-product-content-group"),"nav-product-aside-active");
        Index.productSwitch($(".recommend-product-content-title").find("span"),$(".recommend-product-content-list-group"),"recommend-product-content-title-active");
        Index.productSwitch($(".recommend-product-news-title").find("span"),$(".recommend-product-news-content-group"),"news-title-active");
        Index.sectionSwitch();
    },
    /*轮播*/
    swiper:function(){
        var mySwiper = new Swiper('.swiper-container',{
            autoplay : 3000,
            pagination : '.pagination',
            paginationClickable :true,
            loop : true
        })
    },
    /*商品类别切换*/
    swicthFn:function(obj,target,className){
        target.hide();
        obj.hover(function(){
            $(this).addClass(className).siblings().removeClass(className);
            target.eq($(this).index()).show().siblings().hide();
            target.eq($(this).index()).hover(function(){
                $(this).show();
                obj.eq($(this).index()).addClass(className).siblings().removeClass(className);
            },function(){
                $(this).hide();
            });
        },function(){
            target.hide();
            $(this).removeClass(className);
        })
    },
    /*商品类别切换*/
    productSwitch:function(obj,target,className){
        target.eq(0).show().siblings().hide();
        obj.hover(function(){
            $(this).addClass(className).siblings().removeClass(className);
            target.eq($(this).index()).show().siblings().hide();
            target.eq($(this).index()).hover(function(){
                $(this).show();
                obj.eq($(this).index()).addClass(className).siblings().removeClass(className);
            },function(){
                $(this).hide();
                target.eq(0).show().siblings().hide();
                obj.eq(0).addClass(className).siblings().removeClass(className);
            });
        },function(){
            $(this).removeClass(className);
            obj.eq(0).addClass(className).siblings().removeClass(className);
        })
    },
    /*section商品切换*/
    sectionSwitch:function(){
        for(var i = 0;i<$(".section").length;i++){
            Index.productSwitch($(".section").eq(i).find(".section-right-title").find("span"),$(".section").eq(i).find(".section-right-content-group"),"section-right-active");
        }
    }
};
$(function(){
   Index.init();
});