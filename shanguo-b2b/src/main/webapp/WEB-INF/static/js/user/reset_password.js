/**
 * Created by dy on 2017/6/26.
 */
var resetPassword = {
    init: function() {
        resetPassword.initValidate();
        resetPassword.initTagertEvent();
    },

    initValidate: function() {
        $("input[name=password1]").on("keyup", function() {
                if(!$.validatePassword($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }
            }
        );

        $("input[name=password2]").on("keyup", function() {
                if(!$.validatePassword($(this).val())) {
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }

                if ($(this).val() != $("input[name=password1]").val()) {
                    $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                } else {
                    $(this).closest(".input-group").siblings(".tips").addClass("hide");
                }
            }
        );
    },

    initTagertEvent: function() {
        $(".reset-complete").click(function() {
            if(!$.validatePassword($("input[name=password1]").val())) {
                $("input[name=password1]").closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            }
            if(!$.validatePassword($("input[name=password2]").val())) {
                $("input[name=password2]").closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            }

            if ($("input[name=password2]").val() != $("input[name=password1]").val()) {
                $("input[name=password2]").closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            }

            $.ajax({
                type: "POST",
                url: "/user/upPassword",
                data: {password: hex_md5($("input[name=password2]").val()), mobile: $("input[name=mobile]").val()},
                success: function (result) {
                    if (result.success) {
                        layer.msg("修改密码成功!");
                        location.href = "/passport/restPasswordSuccess";
                    } else {
                        layer.msg(result.msg);
                    }
                }
            });
        });
    }
}

$(function() {
    resetPassword.init();
});
