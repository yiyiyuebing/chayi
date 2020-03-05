/**
 * Created by Administrator on 2017/5/26.
 */
var personCenterAside = {
    init:function(){
        personCenterAside.asideNav();
        personCenterAside.datalistFn();
    },
    asideNav:function(){
        $(".nav-list").hide();
        $(".person-aside-nav-group").click(function(){
            if($(this).find(".nav-list").css("display") == "none"){
                $(this).find(".nav-list").show();
            }else{
                $(this).find(".nav-list").hide();
            }
        });
    },
    /*下拉菜单*/
    datalistFn:function(){
        $(".input-select-list").hide();
        $(".input-select").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            if($(this).find(".input-select-list").css("display") == "none"){
                $(this).find(".input-select-list").show();
            }else{
                $(this).find(".input-select-list").hide();
            }
        });
        $("body").click(function(){
            $(".input-select-list").hide();
        });
        $(".input-select-list").find("li").click(function(){
            var html = $(this).html();
            $(this).closest(".input-select-list").siblings("span").html(html + "<i></i>");
        });
    }
};
$(function(){
   personCenterAside.init();
});