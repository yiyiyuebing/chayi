$(function() {
    PersonCommon.showNav();
    PersonCommon.replace();
    PersonCommon.enterSearch();
});

var PersonCommon = {

    enterSearch:function () {

        $(".pull-left").keydown(function(event){
            var thisObj = $("input").siblings(".nav-search");
            if(event.keyCode == 13){
                PersonCommon.searchBar(thisObj);
            }
        })
    },

    searchBar: function(target) {
        var input = $(target).siblings(".person-keyword");
        var keyword = input.val() && $.trim(input.val()) ? $.trim(input.val()) :  input.attr("placeholder");
        var queryParam = {
            keyword: keyword
        };
        var queryParamStr = $.jsonToGetParamstr(queryParam);
        window.location.href = queryParamStr&& queryParamStr.length > 0 ? "/purchase/list?" + queryParamStr : "/purchase";

    },

    showNav: function () {
        var pathName = window.location.pathname.substring(1);
        var itemTagt = $("#person-left-nav .first-item");
        var itemATagt = $("#person-left-nav .first-item .child-item").find("a[href='/"+ pathName +"']");
        itemATagt.closest(".child-item").addClass("active");
        itemATagt.closest(".child-nav").closest(".first-item").addClass("active");
        itemTagt.click(function () {
            $(this).toggleClass("active");
        });
    },

    checkFun: function () {
        $(".check-tagt").click(function () {
            $(this).toggleClass("active");
        });
    },

    cutNav: function () {
        var navTagt = $("#top-nav li");
        navTagt.click(function () {
            var tagt = $(this);
            navTagt.removeClass("active");
            tagt.addClass("active");
        });
    },

    delTipsFun: function () {
        $(".del-tagt").click(function () {
            var id = $(this).attr("data-id");
            layer.confirm('确定删除?', {title: '提示'}, function (index) {
                PersonCommon.delAddress(id);
                layer.close(index);

            });
        });
    },

    delAddress: function (id) {
        var id = id;
        var userId = $("#userId").val();
        $.ajax({
            type: "POST",
            url: "/user/delAddress",
            data: {addrId: id, userId: userId},
            success: function (result) {
                if (result.success) {
                    layer.msg("删除成功");
                    ReceiptAddress.showAddress();
                } else {
                    layer.msg("删除失败！");
                }
            }
        });
    },

    defaultTipsFun: function () {
        $(".mr-tagt").click(function () {
            var id = $(this).attr("data-id");
            layer.confirm('是否默认?', {title: '提示'}, function (index) {
                PersonCommon.mrAddress(id);
                layer.close(index);

            });
        });
    },

    mrAddress: function (id) {
        var id = id;
        var userId = $("#userId").val();
        $.ajax({
            type: "POST",
            url: "/user/updateDefaultAddr",
            data: {addrId: id, userId: userId},
            success: function (result) {
                if (result.success) {
                    layer.msg("设置默认地址成功!");
                    ReceiptAddress.showAddress();
                } else {
                    layer.msg("设置默认地址失败!");
                }
            }
        });


    },

    replace: function () {
        $(".code-img-change").click(function () {
            $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
        });
    },

    captcha: function () {
        var yzm = $("#yzm").val();
        if (yzm.length <= 0) {
            layer.msg("请输入验证码");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/passport/checkImgCode",
            data: {valideCode: yzm},
            success: function (result) {
                if (result.success) {
                    PersonCommon.send();
                } else {
                    $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
                    layer.msg(result.msg);
                }
            }
        });
    },
    send: function () {
        var phone = $("#phone").html();
        $.ajax({
            type: "POST",
            url: "/user/send",
            data: {phoneNmuber: phone},
            success: function (result) {
                if (result.success) {
                    layer.msg("发送成功！");
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    },
    verify: function () {
        var phone = $("#phone").html();
        var phoneCode = $("#phoneCode").val();
        $.ajax({
            type: "POST",
            url: "/user/checkCode",
            data: {phoneNmuber: phone, yzm: phoneCode},
            success: function (result) {
                if (result.success) {
                    location.href = "/user/updatePassword2";
                } else {
                    layer.msg("验证失败！");
                    $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
                }
            }
        });

    },

    captcha2: function () {
        var yzm = $("#yzm").val();
        if (yzm.length <= 0) {
            layer.msg("请输入验证码");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/passport/checkImgCode",
            data: {valideCode: yzm},
            success: function (result) {
                if (result.success) {
                    PersonCommon.updatePassword();
                } else {
                    layer.msg(result.msg);
                    $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
                }
            }
        });
    },
    updatePassword : function () {

        var password = $("#password").val();
        var password2 = $("#password2").val();

        if (password == null || password == ""){
            layer.msg("密码不能为空！");
            return;
        }

        if (!$.validatePassword(password)){
            layer.msg("密码为6-20位数字及字母的组合");
            return;
        }

        if (password2 == null || password2 == ""){
            layer.msg("再次输入密码不能为空！");
            return;
        }
        if (password2 != password) {
            layer.msg("两次密码不同！");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/user/upPassword",
            data: {password: hex_md5(password)},
            success: function (result) {
                if (result.success) {
                    layer.msg("修改密码成功!");
                    location.href = "/user/updatePassword3";
                } else {
                    $("#imgs").attr("src", "/passport/createValidateCode?" + Math.random());
                    layer.msg(result.msg);
                }
            }
        });
    }

}