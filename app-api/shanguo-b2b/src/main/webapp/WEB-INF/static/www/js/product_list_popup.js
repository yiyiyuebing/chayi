/**
 * Created by dy on 2017/6/29.
 */
var Popup = {
    init:function(){
        Popup.popupControl();
    },
    /*弹窗控制*/
    popupControl:function(){
        $(".package").click(function(){
            var offsetTop = $(document).scrollTop();
            $("#cartPopup").show();
            $("body,html").css({
                "overflow":"hidden"
            });
            $(document).scrollTop(0);

            $("#cancelBtn").click(function(ev){
                var ev = ev || event;
                ev.stopPropagation();
                $("#cartPopup").hide();
                $("body,html").css({
                    "overflow":"auto"
                });
                $(document).scrollTop(offsetTop);
            });

            $("#popupClose").click(function(ev){
                var ev = ev || event;
                ev.stopPropagation();
                $("#cartPopup").hide();
                $("body,html").css({
                    "overflow":"auto"
                });
                $(document).scrollTop(offsetTop);
            });
            $("#cartPopup").click(function(){
                $(this).hide();
                $("body,html").css({
                    "overflow":"auto"
                });
                $(document).scrollTop(offsetTop);
            });
            $("#cartWrap").click(function(ev){
                var ev = ev || event;
                ev.stopPropagation();
                $("#cartPopup").show();
                $(document).scrollTop(0);
            });
        });
    }
};
$(function(){
    Popup.init()
})