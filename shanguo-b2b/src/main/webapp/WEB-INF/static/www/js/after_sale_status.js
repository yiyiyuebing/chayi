/**
 * Created by dy on 2017/6/28.
 */
var Change01 = {
    init:function(){
        Change01.inputSelect($("#serverInput"),$(".server-list"));
        Change01.inputSelect($("#reasonInput"),$(".reason-list"));
        Change01.textareaLimit();
        Change01.Popup($("#editorLogistic"),$("#logisticPopup"),$("#popupClose"),$("#logisticWrap"));
        Change01.Popup($("#seeLocationBtn"),$("#seeLocationPopup"),$("#locationClose"),$("#seeLocationWrap"));
        Change01.Popup($("#seeChangeInfoBtn"),$("#logisticInfo"),$("#InfoClose"),$("#logisticInfoWrap"));
    },
    /*下拉选择事件*/
    inputSelect:function(obj,target){
        obj.click(function(){
            if($(this).hasClass("active")){
                $(this).removeClass("active");
                target.slideUp("200");
            }else{
                $(this).addClass("active");
                target.slideDown("200");
                target.find("li").click(function(){
                    var html = $(this).html();
                    target.slideUp("200");
                    obj.val(html);
                    obj.removeClass("active");
                });
            }
        });
    },
    textareaLimit:function(){
        $("#textContent").bind("input propertychange",function(){
            var length = $(this).val().length;
            var maxLength = $("#letter-all").html();
            if( length >= maxLength ){
                $(this).val($(this).val().substr(0,maxLength));
                $("#letter-actual").html(maxLength);
            }else{
                $("#letter-actual").html(length);
            }

        });
    },
    /*弹窗*/
    Popup:function(obj,target,close,content){
        obj.click(function(){
            if($(this).hasClass("active")){
                $(this).removeClass('active');
                target.hide();
                $("body,html").css({
                    'height':'auto',
                    'overflow':'auto'
                });
            }else{
                $(this).addClass('active');
                target.show();
                $("body,html").css({
                    'height':'100%',
                    'overflow':'hidden'
                });
            }
        });
        close.click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            target.hide();
            obj.removeClass('active');
            $("body,html").css({
                'height':'auto',
                'overflow':'auto'
            });
        });
        target.click(function(){
            target.hide();
            $("body,html").css({
                'height':'auto',
                'overflow':'auto'
            });
            obj.removeClass('active');
        });
        content.click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            obj.addClass('active');
            target.show();
            $("body,html").css({
                'height':'100%',
                'overflow':'hidden'
            });
        });
    }

};
$(function(){
    Change01.init();
})