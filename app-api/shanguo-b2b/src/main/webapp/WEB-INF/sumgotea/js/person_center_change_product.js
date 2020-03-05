/**
 * Created by Administrator on 2017/6/13.
 */
var changeProduct = {
    init:function(){
        changeProduct.textareaHeightAuto();
        changeProduct.serverPopup();
        changeProduct.reasonPopup();
        changeProduct.submitSuccess();
    },
    /*textarea  不出现滚动条*/
    textareaHeightAuto:function(){
        var textareaBox = document.getElementById('textareaBox');
        textareaBox.addEventListener("propertychange",change);
        textareaBox.addEventListener("input",change);
        textareaBox.addEventListener("focus",change);
        function change(){
            var scrollHeight = this.scrollHeight;
            if(scrollHeight > 0){
                $(this).css("height",scrollHeight);
            }
        }
    },
    /*申请服务 弹窗*/
    serverPopup:function(){
        var serverCategory = document.querySelector("#serverCategory");
        var categoryPopup = document.querySelector("#categoryPopup");
        var inputWrap = document.querySelector(".input-wrap");
        var cancel =document.querySelector("#cancel");
        serverCategory.addEventListener("touchstart",popupShow);
        categoryPopup.addEventListener("touchstart",popupHide);
        inputWrap.addEventListener("touchstart",popupShow);
        cancel.addEventListener("touchstart",popupHide);
      
        function popupShow(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#categoryPopup").show();
            $("body,html").css({"overflow":"hidden","height":"100vh"});
        };
        function popupHide(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#categoryPopup").hide();
            $("body,html").css({"overflow":"auto","height":"auto"});
        }
    },
    /*换货原因  弹窗*/
    reasonPopup:function(){
        $("#serverReason").bind("touchstart",function(){
            popupShow();
        });

        $("#reasonPopup").bind("touchstart",function(){
            popupHide();
        });

        $("#close").bind("touchstart",function(){
            popupHide();
        });
        
        $("#reasonChoose").find("li").bind("touchstart",function(){
            var html = $(this).find("span").html();
            $(this).addClass("active").siblings().removeClass("active");
            $("#serverReason").find("input").val(html);
            popupHide();
        });
        function popupShow(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#reasonPopup").show();
            $("body,html").css({"overflow":"hidden","height":"100vh"});
        };
        function popupHide(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#reasonPopup").hide();
            $("body,html").css({"overflow":"auto","height":"auto"});
        }
    },
    /*提交成功   弹出提示*/
    submitSuccess:function(){
        $("#submit-btn").bind("touchstart",function(){
            $("#popupSubmit").show();
            setTimeout(function(){
                $("#popupSubmit").hide();
            },1000);
        })
    }   
};
$(function(){
    changeProduct.init();
});