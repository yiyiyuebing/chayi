/**
 * Created by Administrator on 2017/5/8.
 */
var AsideBar = {
    init:function(){
        AsideBar.hoverFn($(".code-wrap"),$(".code-content"));
        AsideBar.hoverFn($(".person-center"),$(".center-txt"));
        AsideBar.hoverFn($(".person-collect"),$(".collect-txt"));
        AsideBar.hoverFn($(".person-card"),$(".card-txt"));
        AsideBar.hoverFn($(".person-history"),$(".history-txt"));
        AsideBar.backHome($(".home-back-wrap"));
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
    }
};

$(function(){
    AsideBar.init();
});