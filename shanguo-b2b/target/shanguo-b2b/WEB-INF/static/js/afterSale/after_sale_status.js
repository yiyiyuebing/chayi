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
    inputSelect:function(obj, target){
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
                    if ($(this).attr("data-url")) {
                        window.location.href = $(this).attr("data-url");
                    }
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
    Popup:function(obj, target, close, content){
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

                if ($(this).attr("data-type") === "freight-info") {
                    Change01.getFregihtInfo($(this).attr("data-freight-no"), target.find(".info-body"));
                }

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

    },

    getFregihtInfo: function(freightNo, container) {
        container.empty();
        $.ajax({
            type: "POST",
            data: {num : freightNo},
            url: "/express/expressInfo",
            success: function(result) {
                if (result.success) {
                    if (result.data.data) {
                        var html = '';
                        $.each(result.data.data, function(i, perData) {
                            if (i == 0) {
                                html += '<li class="info-li active clearfix">';
                                html += '<i class="dot"></i>';
                                html += '<span class="date pull-left"></span>';
                                html += '<span class="time pull-left">'+ perData.time +'</span>';
                                html += '<span class="details pull-left">'+ perData.context +'</span>';
                                html += '</li>';

                            } else {
                                html += '<li class="info-li clearfix">';
                                html += '<i class="dot"></i>';
                                html += '<span class="date pull-left"></span>';
                                html += '<span class="time pull-left">'+ perData.time +'</span>';
                                html += '<span class="details pull-left">'+ perData.context +'</span>';
                                html += '</li>';
                            }
                        });
                        container.append($(html));
                    } else {
                        var html = '';
                        html += '<li class="info-li clearfix">';
                        html += '<i class="dot"></i>';
                        html += '<span class="date pull-left"></span>';
                        //html += '<span class="time pull-left">'+ perData.time +'</span>';
                        html += '<span class="details pull-left">暂无快递信息</span>';
                        html += '</li>';
                        container.append($(html));
                    }
                } else {
                    var html = '';
                    html += '<li class="info-li clearfix">';
                    html += '<i class="dot"></i>';
                    html += '<span class="date pull-left"></span>';
                    //html += '<span class="time pull-left">'+ perData.time +'</span>';
                    html += '<span class="details pull-left">暂无快递信息</span>';
                    html += '</li>';
                    container.append($(html));
                }
            }
        });
    }

};
$(function(){
    Change01.init();
})