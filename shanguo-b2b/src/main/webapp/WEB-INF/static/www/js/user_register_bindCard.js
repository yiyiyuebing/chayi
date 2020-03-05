/**
 * Created by Administrator on 2017/7/31.
 */
var BindCard = {
    init:function(){
        BindCard.selectBank();
    },
    /*下拉   选择银行*/
    selectBank:function(){
        $("#bankClassifyBtn").on("click",function(){
            if($(this).hasClass('active')){
                $("#bankClassifyList").slideUp("200");
                $(this).removeClass("active");
            }else{
                $("#bankClassifyList").slideDown("200");
                $(this).addClass("active");
            }
        });

        $("#bankClassifyList").on("click","li",function(){
            var bankKind = $(this).attr("data-bankKind");
            $("#bankClassifyBtn").html(bankKind);
            $("input[name=bankClassify]").val(bankKind);
            $("#bankClassifyList").slideUp("200");
            $("#bankClassifyBtn").removeClass("active");
        });
    }
};
$(function(){
   BindCard.init();
});