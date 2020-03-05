/**
 * Created by dy on 2017/7/5.
 */
($.extend({
    /*设置cookie*/
    setCookie: function(name,value) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ value + ";expires=" + exp.toGMTString() + "; path= '/'";
        /*+ window.location.pathname;*/
    },
    /*获取cookie*/
    getCookie: function(name) {
        var search = name + "="//查询检索的值
        var returnvalue = null;//返回值
        if (document.cookie.length > 0) {
            sd = document.cookie.indexOf(search);
            if (sd!= -1) {
                sd += search.length;
                end = document.cookie.indexOf(";", sd);
                if (end == -1)
                    end = document.cookie.length;
                //unescape() 函数可对通过 escape() 编码的字符串进行解码。
                returnvalue=unescape(document.cookie.substring(sd, end))
            }
        }
        return returnvalue;
    },
    /*删除cookie*/
    delCookie: function (name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval= $.getCookie(name);
        if(cval!=null)
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }, //使用示例 setCookie("name","hayden"); alert(getCookie("name")); //如果需要设定自定义过期时间 //那么把上面的setCookie　函数换成下面两个函数就ok; //程序代码 function setCookie(name,value,time) { var strsec = getsec(time); var exp = new Date(); exp.setTime(exp.getTime() + strsec*1); document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); } function getsec(str) { alert(str); var str1=str.substring(1,str.length)*1; var str2=str.substring(0,1); if (str2=="s") { return str1*1000; } else if (str2=="h") { return str1*60*60*1000; } else if (str2=="d") { return str1*24*60*60*1000; } }
    /*手机号码合法性*/
    validatePhone: function(phone) {
        if (!(/^1[34578]\d{9}$/.test(phone))) {
            return false;
        }
        return true;
    },
    /*电话号码合法性*/
    validateTel: function(tel) {
        if (!(/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(tel))) {
            return false;
        }
        return true;
    },
    /*密码合法性*/
    validatePassword: function(password) {
        if (!(/^[a-zA-Z0-9\x21-\x7e]{6,20}$/.test(password))) {
            return false;
        }
        return true;
    },
    /*空值判断*/
    validateNull: function(value) {
        if ($.trim(value).length > 0) {
            return true;
        } else {
            return false;
        }
    },
    /*检测是否登录*/
    checkLogin: function() {
        if (!$("input[name=shopId]").val() || $("input[name=shopId]").val() == "null") {
            window.location.href = "/passport/login";
        } else {
            return true;
        }
        /*$.ajax({
         type: "POST",
         url: "/passport/checkLogin",
         success: function(result) {
         if (!result.success) {
         var url = window.location.href;
         $.setCookie("href", url);
         window.location.href = "/passport/login";
         }
         }
         });*/

    }
}));