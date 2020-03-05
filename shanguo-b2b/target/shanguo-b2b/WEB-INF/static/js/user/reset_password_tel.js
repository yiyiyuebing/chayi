/**
 * Created by dy on 2017/6/26.
 */
var resetPasswordTel = {
    init: function() {
        resetPasswordTel.initValidate();
        resetPasswordTel.initTagEvent();
        resetPasswordTel.replace();
    },
    initValidate: function() {
        $("input[name=mobile]").on("keyup", function() {
                if(!$.validatePhone($(this).val())) {
                    $(this).closest(".input-tel").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-tel").siblings(".tips").addClass("hide");
                }
            }
        );
    },
    replace: function () {
        $(".code-img-change").click(function () {
            $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
        });
    },

    captcha: function () {
        var yzm = $("#yzm").val();
        if(!$.validatePhone($("input[name=mobile]").val())) {
            $("input[name=mobile]").closest(".input-tel").siblings(".tips").removeClass("hide");
            return;
        }
        if (yzm=="" || yzm == null) {
            layer.msg("验证码不能为空");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/passport/checkValidateCode1",
            data: {valideCode: yzm, mobile:$("input[name=mobile]").val()},
            success: function (result) {
                if (result.success) {
                    resetPasswordTel.select();
                } else {
                    $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
                    layer.msg(result.msg);
                }
            }
        });
    },

    select : function () {
            $.ajax({
                type: "POST",
                url: "/passport/validateMobile",
                data: {phone: $("input[name=mobile]").val()},
                success: function (result) {
                    if (result.success) {
                        resetPasswordTel.sentCode($("input[name=mobile]").val());
                    } else {
                        layer.msg(result.msg);
                    }
                }
            });
    },
    initTagEvent: function() {
        $(".next-btn").click(function() {
            if(!$.validatePhone($("input[name=mobile]").val())) {
                $("input[name=mobile]").closest(".input-tel").siblings(".tips").removeClass("hide");
                return;
            }
            if(!$.validateNull($("input[name=code]").val())) {
                layer.msg("验证码不能为空");
                return;
            }
            $.ajax({
                type: "POST",
                url: "/user/checkCode",
                data: {phoneNmuber: $("input[name=mobile]").val(), yzm: $("input[name=code]").val()},
                success: function (result) {
                    if (result.success) {
                       window.location.href = "/passport/resetPassword?mobile=" + $("input[name=mobile]").val() ;
                    } else {
                        layer.msg(result.msg);
                    }
                }
            });
        });
    },

    sentCode: function(mobile) {
        $.ajax({
            type: "POST",
            url: "/user/send",
            data: {phoneNmuber: $("input[name=mobile]").val()},
            success: function (result) {
                if (result.success) {
                    layer.msg("发送成功！");
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    }
};
$(function() {
    resetPasswordTel.init();
});
