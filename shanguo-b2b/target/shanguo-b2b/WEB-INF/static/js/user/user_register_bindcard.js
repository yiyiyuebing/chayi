/**
 * Created by Administrator on 2017/7/31.
 */
var BindCard = {
    init:function(){
        BindCard.selectBank();
        BindCard.validateInput();
        BindCard.doBindCard();
        $(".imgCode-img").click(function () {
            $(this).attr("src", "/passport/createValidateCode?" + Math.random());
        });
    },
    validateInput: function() {

        $("input[name=name]").on("keyup", function() {
                if(!$.validateNull($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }
            }
        );

        $("input[name=idCard]").on("keyup", function() {

                if(!$.validateNull($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").html("身份证号码不能为空");
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                    return;
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }

                if(!$.validateIdCard($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").html("身份证号码填写不正确");
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }
            }
        );
        $("input[name=bankCard]").on("keyup", function() {

                if(!$.validateNull($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").html("银行卡号不能为空");
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                    return;
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }

                if(!$.validateBankNo($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").html("银行卡号填写不正确");
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }
            }
        );

        $("input[name=mobile]").on("keyup", function() {

                if(!$.validateNull($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").html("手机号不能为空");
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                    return;
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }

                if(!$.validatePhone($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").html("手机号填写不正确");
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }
            }
        );
        $("input[name=codeImg]").on("keyup", function() {
                if(!$.validateNull($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }
            }
        );

        $("input[name=msgYzm]").on("keyup", function() {
                if(!$.validateNull($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }
            }
        );
    },
    checkInput: function() {
        if(!$.validateNull($("input[name=name]").val())) {
            $("input[name=name]").closest(".input-group").siblings(".tips").html("开户人不能为空");
            $("input[name=name]").closest(".input-group").siblings(".tips").removeClass("hide");
            return false;
        }

        if(!$.validateNull($("input[name=idCard]").val())) {
            $("input[name=idCard]").closest(".input-group").siblings(".tips").html("身份证号码不能为空");
            $("input[name=idCard]").closest(".input-group").siblings(".tips").removeClass("hide");
            return false;
        }
        if(!$.validateIdCard($("input[name=idCard]").val())) {
            $("input[name=idCard]").closest(".input-group").siblings(".tips").html("身份证号码填写不正确");
            $("input[name=idCard]").closest(".input-group").siblings(".tips").removeClass("hide");
            return false;
        }

        if(!$.validateNull($("input[name=bankCard]").val())) {
            $("input[name=bankCard]").closest(".input-group").siblings(".tips").html("银行卡号不能为空");
            $("input[name=bankCard]").closest(".input-group").siblings(".tips").removeClass("hide");
            return false;
        }
        if(!$.validateBankNo($("input[name=bankCard]").val())) {
            $("input[name=bankCard]").closest(".input-group").siblings(".tips").html("银行卡号填写不正确");
            $("input[name=bankCard]").closest(".input-group").siblings(".tips").removeClass("hide");
            return false;
        }


        if(!$.validateNull($("input[name=mobile]").val())) {
            $("input[name=mobile]").closest(".input-group").siblings(".tips").html("手机号不能为空");
            $("input[name=mobile]").closest(".input-group").siblings(".tips").removeClass("hide");
            return false;
        }

        if(!$.validateNull($("input[name=codeImg]").val())) {
            $("input[name=codeImg]").closest(".input-group").siblings(".tips").html("图片验证码不能为空");
            $("input[name=codeImg]").closest(".input-group").siblings(".tips").removeClass("hide");
            return false;
        }

        if(!$.validateNull($("input[name=msgYzm]").val())) {
            $("input[name=msgYzm]").closest(".input-group").siblings(".tips").html("验证码不能为空");
            $("input[name=msgYzm]").closest(".input-group").siblings(".tips").removeClass("hide");
            return false;
        }

        return true;
    },


    sendPhoneCode : function () {
        $.ajax({
            type: "POST",
            url: "/user/sendPhoneCode",
            data: {mobile: $("input[name=mobile]").val(), msgType: "bankcard"},
            success: function (result) {
                if (result.success) {
                    layer.msg("发送成功！");
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    },

    doBindCard: function() {

        $(".getYzm").on("click", function() {

            if(!$.validateNull($("input[name=mobile]").val())) {
                $("input[name=mobile]").closest(".input-group").siblings(".tips").html("手机号不能为空");
                $("input[name=mobile]").closest(".input-group").siblings(".tips").removeClass("hide");
                return false;
            }

            if(!$.validateNull($("input[name=codeImg]").val())) {
                $("input[name=codeImg]").closest(".input-group").siblings(".tips").html("图片验证码不能为空");
                $("input[name=codeImg]").closest(".input-group").siblings(".tips").removeClass("hide");
                return false;
            }

            $.ajax({
                type: "POST",
                url: "/passport/checkImgCode",
                data: {valideCode: $("input[name=codeImg]").val()},
                success: function (result) {
                    if (result.success) {
                        BindCard.sendPhoneCode();
                    } else{
                        layer.msg(result.msg);
                    }
                }
            });
        });

        $(".submit-info").on("click", function() {

            if (!BindCard.checkInput()) {
                return;
            }

            var name = $("input[name=name]").val();
            var idCard = $("input[name=idCard]").val();
            var bankName = $("input[name=bankName]").val();
            var bankCard = $("input[name=bankCard]").val();
            var mobile = $("input[name=mobile]").val();
            var shopId = $("input[name=shopId]").val();

            var codeImg = $("input[name=codeImg]").val();
            var msgYzm = $("input[name=msgYzm]").val();


            var data = {};
            data.name = name;
            data.card = idCard;
            data.bankName = bankName;
            data.bankCard = bankCard;
            data.mobile = mobile;
            data.verifyCode = msgYzm;
            data.shopId = shopId;

            console.log(data);
            $.ajax({
                type: "POST",
                url: "/user/bindBandCard",
                data: {modelJson: JSON.stringify(data), yzmCode: msgYzm},
                success: function (result) {
                    if (result.success) {
                        layer.msg("绑定成功，系统将自动跳转到登录页面", function() {
                            window.location.href = "/passport/login";
                        });
                    } else{
                        layer.msg(result.msg);
                    }
                }
            });
        });


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