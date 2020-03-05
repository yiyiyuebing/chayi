/**
 * Created by Administrator on 2017/5/8.
 */
var Header = {
    init:function(){
        Header.locationFn($(".location"),$(".location-wrap"),"wrap-active");
        Header.locationCenter($(".location-pro-group"));
        Header.locationInner($(".location-pro-group>ul>li"));
        /*我的优茶联=*/
        Header.locationFn($(".mineYcl"),$(".minYcl-wrap"),"mineYcl-active");
        /*手机商城*/
        Header.locationFn($(".cooperation"),$(".cooperation-wrap"),"cooperation-active");
        /*message*/
        Header.locationFn($(".search-message"),$(".search-message-content"),"");
        Header.messageFn($(".message-content-title").find('span'),$(".message-content-body-group"))
    },
    /*选择地址 外层*/
    locationFn:function(obj,target,className){
        target.hide();
        obj.hover(function(){
            obj.addClass(className);
            target.show();
        },function(){
            obj.removeClass(className);
            target.hide();
        });
    },
    /*选择地址 中层*/
    locationCenter:function(obj){
        obj.find("ul").hide();
        obj.hover(function(){
            $(this).addClass('pro-active');
            $(this).find("ul").show();
        },function(){
            $(this).removeClass('pro-active');
            $(this).find("ul").hide();
        });
    },
    /*选择地址 内层*/
    locationInner:function(obj){
        obj.hover(function(){
            $(this).addClass('city-active');
        },function(){
            $(this).removeClass('city-active');
        });
    },
    /*message*/
    messageFn:function(obj,target){
        target.eq(0).show().siblings().hide();
        obj.hover(function(){
            $(this).addClass('active').siblings().removeClass("active");
            target.eq($(this).index()).show().siblings().hide();
        },function(){
            var index = $(this).index();
            $(".message-content-body").mouseenter(function(){
                target.eq(index).show().siblings().hide();
            });
        });
    }
};
$(function(){
   Header.init();
});