/**
 * Created by Administrator on 2017/5/23.
 */
var register = {
    init:function(){
        register.policyCheck();
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
    }
};
$(function(){
    register.init();
});