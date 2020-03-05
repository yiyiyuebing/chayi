/**
 * Created by Administrator on 2017/5/23.
 */
var register = {
    init:function(){
        register.policyCheck();
        register.searchFn();
    },
    /*勾选条款政策*/
    policyCheck:function () {
        $("#policy").click(function(){
            if( $(this).find("input[type=checkbox]").is(":checked") ){
                $(this).addClass("policy-active");
            }else{
                $(this).removeClass("policy-active");
            }
        });
    },
    /*检索*/
    searchFn:function(){
        $(".radio-group").click(function(){
            if(!$(this).find("input[type=radio]").checked){
                $(this).addClass("radio-group-active").siblings(".radio-group").removeClass("radio-group-active");
            }else{
                $(this).removeClass("radio-group-active").siblings(".radio-group").addClass("radio-group-active");
            }
        });
    }
};
$(function(){
    register.init();
});