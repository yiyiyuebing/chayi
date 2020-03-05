/**
 * Created by Administrator on 2017/5/23.
 */
var register = {
    init:function(){
        register.initValidate();
        register.policyCheck();
        register.replace();
        register.initTagEvent();
        register.policyPop();
    },
    /*条款政策弹窗事件*/
    policyPop:function(){
        $("#policyBtn").on("click",function(){
            $("#serverPop").show();
            $("body,html").css({"overflow":"hidden"},{"height":"100%"});

        });
        $("#serverPopClose").on("click",function(){
            $("#serverPop").hide();
            $("body,html").css({"overflow":"auto"},{"height":"auto"});
        });
        $(".serverPop-sure").on("click",function(){
            $("#serverPop").hide();
            $("body,html").css({"overflow":"auto"},{"height":"auto"});
        });
    },
    /*勾选条款政策*/
    policyCheck:function () {
        $("#policy").click(function() {
            if( $(this).find("input[type=checkbox]").is(":checked") ) {
                $(".next").find("a").removeClass("disable");
                $(this).addClass("policy-active");
            } else {
                $(".next").find("a").addClass("disable");
                $(this).removeClass("policy-active");
            }
        });
    },

    replace: function () {
        $(".code-img-change").click(function () {
            $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
        });
    },

    captcha : function () {
        var inputMobileStr = $("input[name=mobile]").val();
        var invitePhoneStr = $("input[name=invitePhone]").val();
        var yzm = $("#yzm").val();
        if(!$.validatePhone($("input[name=mobile]").val())) {
            $(this).closest(".input-tel").siblings(".tips").removeClass("hide");
            return;
        }
        if(!$.validatePassword($("input[name=password]").val())) {
            $(this).closest(".input-password").siblings(".tips").removeClass("hide");
            return;
        }
        if (yzm=="" || yzm == null) {
            layer.msg("验证码不能为空");
            return;
        }
        if (invitePhoneStr == null || invitePhoneStr == "") {
            $.ajax({
                type: "POST",
                url: "/passport/checkValidateCode2",
                data: {valideCode: yzm, mobile: inputMobileStr},
                success: function (result) {
                    if (result.success) {
                        register.send();
                    } else{
                        layer.msg(result.msg);
                        $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
                    }
                }
            });
        } else {
            $.ajax({
                type: "POST",
                url: "/passport/checkValidateCode3",
                data: {valideCode: yzm, mobile: inputMobileStr, invitePhone: invitePhoneStr},
                success: function (result) {
                    if (result.success) {
                        register.send();
                    } else {
                        layer.msg(result.msg);
                        $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
                    }
                }
            });
        }

    },


    send : function () {
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
    },

    initTagEvent: function() {
        $(".next").find("a").click(function() {

            if($("input[name=policy]:checked").length == 0) {
                layer.msg("请选择服务条款及隐私政策！");
                return ;
            }

            if (!$("input[name=telCode]").val()) {
                layer.msg("验证码不能为空！");
                return ;
            }

            var data = {
                mobile: $("input[name=mobile]").val(),
                password: hex_md5($("input[name=password]").val()),
                recommendUser: $("input[name=invitePhone]").val()
            };

            $.ajax({
                type: "POST",
                url: "/passport/doRegister",
                data: {subJson: JSON.stringify(data), yzm: $("input[name=telCode]").val()},
                success: function (result) {
                    if (result.success) {
                        window.location.href = "/passport/registerInfo/" + result.data.mobile;
                    } else {
                        $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
                        layer.msg(result.msg);
                    }
                }
            });

        });
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
        $("input[name=password]").on("keyup", function() {
                if(!$.validatePassword($(this).val())) {
                    $(this).closest(".input-password").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-password").siblings(".tips").addClass("hide");
                }
            }
        );

        $("input[name=invitePhone]").on("keyup", function() {
                if ($(this).val()) {
                    if(!$.validatePhone($(this).val())) {
                        $(this).closest(".input-invite-tel").siblings(".tips").removeClass("hide");
                    } else {
                        $(this).closest(".input-invite-tel").siblings(".tips").addClass("hide");
                    }
                }
            }
        );
    }
};
$(function(){
    register.init();
});