/**
 * Created by dy on 2017/6/11.
 */
var Login = {
    formFlag: true,
    init: function() {
        Login.checkPhone();
        $.delCookie("b2b_token");
    },

    checkPhone: function() {
        $("#login-form").find("input[name=phone]").on("keyup", function() {
            if (!$.validatePhone($(this).val())) {
                Login.formFlag = false;
                $(this).siblings(".input-tips").show();
            } else {
                Login.formFlag = true;
                $(this).siblings(".input-tips").hide();
            }
        });
        $("#login-form").find("input[name=password]").on("keyup", function() {
            $(this).siblings(".input-tips").hide();
        });
    },

    doLogin: function() {

        if (!$.validatePhone($("#login-form").find("input[name=phone]").val())) {
            Login.formFlag = false;
            $("#login-form").find("input[name=phone]").siblings(".input-tips").show();
            return;
        }

        if ($("#login-form").find("input[name=password]").val() <= 0) {
            Login.formFlag = false;
            $("#login-form").find("input[name=password]").siblings(".input-tips").show();
            return;
        }
        $.ajax({
            type:"POST",
            url:"/passport/doLogin",
            data:{
                "phone":$("#login-form").find("input[name=phone]").val(),
                "password":hex_md5($("#login-form").find("input[name=password]").val())
            },
            success:function(result) {
                if (result.success) {
                    var referer = document.referrer;
                    var refererEnd = referer.substr(referer.lastIndexOf("/") + 1, referer.length);
                    if (refererEnd == "login") {
                        window.location.href = "/index";
                    } else {
                        if (referer.indexOf("afterSale") < 0 && referer.indexOf("order") < 0 && referer.indexOf("restPasswordSuccess") < 0) {
                            window.location.href = document.referrer;
                        } else {
                            window.location.href = "/index";
                        }
                    }
                } else {
                    if ("500013" == result.errorCode) {
                        window.location.href = "/passport/registerAudit";
                    } else if ("500014" == result.errorCode) {
                        window.location.href = "/passport/registerInfoComplete/" + result.data;
                    } else {
                        layer.msg(result.msg);
                    }

                }
            },
            error: function() {
                layer.msg("登录失败！");
            }
        });


        //$.form.submit({
        //    formId: "#login-form",
        //    url: "/login/doLogin"
        //});
    }
};
$(function() {
  Login.init();
})