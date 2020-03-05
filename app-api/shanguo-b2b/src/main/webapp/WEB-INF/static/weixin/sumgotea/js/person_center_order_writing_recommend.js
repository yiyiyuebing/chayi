/**
 * Created by Administrator on 2017/6/10.
 */
var recommendWriting = {
    init:function () {
        recommendWriting.limitTxt($("#productName"));
        recommendWriting.recommendStar();
        recommendWriting.textareaHeightAuto();
        recommendWriting.isAnonymity();
        recommendWriting.cameraPopup();
    },
    /*产品名称限制字数*/
    limitTxt:function(obj){
        var html = obj.html().trim();
        obj.html(html.substring(0,19) + "...");
    },
    /*评价  点击星星*/
    recommendStar:function() {
        $(".star-group").find("li").on("touchstart",starNumber);
        function starNumber(){
            $(".star-group").find("li").removeClass("active");
            var index = $(this).index();
            for(var i=0;i<=index;i++){
                $(".star-group").find("li").eq(i).addClass("active");
            }
            $(".star-number").html(index + 1 + "分");
            $("#recommendStar").val(index + 1);
        }
    },
    /*文本框字适应高度*/
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
    /*是否匿名留言*/
    isAnonymity:function(){
        $(".anonymity-box").bind("touchstart",function(){
            if($(this).find("i").hasClass("active")){
                $(this).find("i").removeClass("active");
            }else{
                $(this).find("i").addClass("active");
            }
        })
    },
    /*点击照相机图标   出现弹窗*/
    cameraPopup:function(){
        var iconCamera = document.querySelector("#iconCamera");
        var popup = document.querySelector("#popup");
        var popupBox = document.querySelector(".popup-box");
        iconCamera.addEventListener("touchstart",popupShow);
        popup.addEventListener("touchstart",popupHide);
        popupBox.addEventListener("touchstart",popupShow);
        function popupShow(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#popup").show();
        };
        function popupHide(){
            $("#popup").hide();
        };
    }
};
$(function(){
    recommendWriting.init();
});