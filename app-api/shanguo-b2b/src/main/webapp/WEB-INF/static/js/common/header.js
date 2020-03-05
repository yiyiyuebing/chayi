/**
 * Created by Administrator on 2017/5/8.
 */
var Header = {
    init:function(){
        /*头部地址选择*/


        /*我的优茶联=*/
        Header.locationFn($(".mineYcl"),$(".minYcl-wrap"),"mineYcl-active");
        /*手机商城*/
        Header.locationFn($(".cooperation"),$(".cooperation-wrap"),"cooperation-active");
        /*message*/
        Header.locationFn($(".search-message"),$(".search-message-content"),"");
        Header.messageFn($(".message-content-title").find('a'),$(".message-content-body-group"))
        /*网站导航*/
        Header.locationFn($(".web-nav"),$(".web-nav-content"),"web-nav-active");

        /*if ($("#select-header-province").attr("data-id")) {
            $.setCookie("province_code", $("#select-header-province").attr("data-id"));
        }*/
        Header.welcomeSilder();

        $.initCitySelect({
            selectId: $("#header-location-select"),
            clickId: $("#select-header-province"),
            showLevel: 1,
            selectAreaCode: Header.getProvince
        });
        Header.loginOut();
        Header.initPurchaseClassify();
        Header.initArea();
    },

    loginOut: function() {
        $(".register-group").find(".login-out").click(function(event) {
            $.delCookie("b2b_token");
            window.location.href = "/passport/login";
        });
    },

    /**
     * 加载商品类别
     */
    initPurchaseClassify: function() {
        //if ($(".nav-product-aside-wrap").length > 0) {
        //    return;
        //}

        $.ajax({
            type: "POST",
            url: "/purchaseClassify/purchaseClassifyList",
            success: function(result) {
                if (result.success && result.data.purchaseClassifys) {
                    $.each(result.data.purchaseClassifys, function(i,perClassify) {
                        if ($(".nav-product-aside-wrap").length > 0) {
                            var nav_product_aside_group = template("tpl-nav-product-aside-group", perClassify);
                            $(".nav-product-aside-wrap").append($(nav_product_aside_group));
                        }
                        if ($(".nav-product-content").length > 0) {
                            var nav_product_content_group = template("tpl-nav-product-content-group", perClassify);
                            $(".nav-product-content").append($(nav_product_content_group));
                        }

                        Header.navFn($(".nav-product"));
                        //初始化商品类别切换
                        Header.swicthFn($(".nav-product-aside-group"), $(".nav-product-content-group"), "nav-product-aside-active");

                        if (i < 2 && $(".head-classify").length > 0) {
                            var tpl_head_classify_list = template("tpl-head-classify-list", perClassify);
                            $(".head-classify").append($(tpl_head_classify_list));
                        }
                        if ($(".more-classify").length > 0) {
                            var tpl_more_classify = template("tpl-more-classify", perClassify);
                            $(".more-classify").append($(tpl_more_classify));
                        }
                    });

                }
            }
        });
    },

    /*商品类别切换*/
    swicthFn:function(obj,target,className){
        target.hide();
        obj.hover(function(){
            $(this).addClass(className).siblings().removeClass(className);
            target.eq($(this).index()).show().siblings().hide();
            target.eq($(this).index()).hover(function(){
                $(this).show();
                obj.eq($(this).index()).addClass(className).siblings().removeClass(className);
            },function(){
                $(this).hide();
            });
        },function(){
            target.hide();
            $(this).removeClass(className);
        })
    },
    navFn:function(obj){
        obj.find(".nav-product-wrap").hide();
        obj.hover(function(){
            obj.find(".nav-product-wrap").show();
        },function(){
            obj.find(".nav-product-wrap").hide();
        });
    },

    initArea: function() {
        var provinceCode = $.getCookie("province_code");
        if (provinceCode) {
            var province = $.get(provinceCode);
            var provinceName = province.name;
            $.setArea({
                selectId: $("#header-location-select"),
                clickId: $("#select-header-province"),
                province: provinceName
            });
        }
    },

    getProvince: function(provinceCode) {
        if (provinceCode) {
            $.setCookie("province_code", provinceCode);
            window.location.href = window.location.href;
        }

    },
    loadCityData: function() {

        /*$.getJSON("/static/js/lib/cityData/citypcker.json", function(data){
            $.each(data, function(i, perData) {
                if (perData.level == 1) {
                    var tpl_location_group_li = template("tpl-location-group-li", perData);
                    $(".location-group").append($(tpl_location_group_li));
                }
            });
            Header.chooseLocation();
            Header.locationFn($(".location"), $(".location-wrap"), "wrap-active");
        });*/

    },


    //收藏网页
    addFavorite1 : function() {
        var url = window.location;
        var title = document.title;
        var ua = navigator.userAgent.toLowerCase();

        if (ua.indexOf("360se") > -1) {
            alert("由于360浏览器功能限制，请按 Ctrl+D 手动收藏！");
        }
        else if (ua.indexOf("msie 8") > -1) {
            window.external.AddToFavoritesBar(url, title); //IE8
        }
        else if (document.all) {//IE类浏览器
            try{
                window.external.addFavorite(url, title);
            }catch(e){
                alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
            }
        }
        else if (window.sidebar) {//firfox等浏览器；
            window.sidebar.addPanel(title, url, "");
        }
        else {
            alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
        }
    },

    /*鼠标经过  显示隐藏*/
    locationFn:function(obj, target, className) {
        //var selectCity = $(".loaction-show").find(".location-id").attr("data-id");
        //$(".location-group").find("li[data-id="+ selectCity +"]").addClass(className);
        target.hide();
        obj.hover(function(){
            obj.addClass(className);
            target.show();
        },function(){
            obj.removeClass(className);
            target.hide();
        });
    },
    /*点击  选择地址*/
    chooseLocation:function(){
        $(".location-group").find("li").click(function(){
            var html = $(this).html();
            $(this).closest(".location").find(".loaction-show .location-id").html(html);
            $(this).closest(".location").find(".loaction-show .location-id").attr("data-id", $(this).attr("data-id"));
            $(this).closest(".location-wrap").hide();
        });
    },


    /*message*/
    messageFn:function(obj,target){
        target.eq(0).show().siblings().hide();
        obj.hover(function(){
            $(this).addClass('active').siblings().removeClass("active");
            target.eq($(this).index()).show().siblings().hide();
        },function(){
            var index = $(this).index();
            $(".message-content-body").mouseenter(function(){
                target.eq(index).show().siblings().hide();
            });
        });
    },
    /*欢迎光临轮播*/
    welcomeSilder:function(){
        setInterval(function(){
            var width = $("#welcomeTxt").find('span').outerWidth();
            var left = parseInt( $("#welcomeTxt").css("left") );
            if( left <= -width){
                $("#welcomeTxt").last().after(  $("#welcomeTxt").first() );
                $("#welcomeTxt").css({"left":-16});
            }else{
                $("#welcomeTxt").css({"left":left - 16});
            }

        },400);
    }
};
$(function(){
   Header.init();
});

function fireKeyEvent(el, evtType, keyCode){
    var doc = el.ownerDocument,
        win = doc.defaultView || doc.parentWindow,
        evtObj;
    if(doc.createEvent){
        if(win.KeyEvent) {
            evtObj = doc.createEvent('KeyEvents');
            evtObj.initKeyEvent( evtType, true, true, win, false, false, false, false, keyCode, 0 );
        }
        else {
            evtObj = doc.createEvent('UIEvents');
            Object.defineProperty(evtObj, 'keyCode', {
                get : function() { return this.keyCodeVal; }
            });
            Object.defineProperty(evtObj, 'which', {
                get : function() { return this.keyCodeVal; }
            });
            evtObj.initUIEvent( evtType, true, true, win, 1 );
            evtObj.keyCodeVal = keyCode;
            if (evtObj.keyCode !== keyCode) {
                console.log("keyCode " + evtObj.keyCode + " 和 (" + evtObj.which + ") 不匹配");
            }
        }
        el.dispatchEvent(evtObj);
    }
    else if(doc.createEventObject){
        evtObj = doc.createEventObject();
        evtObj.keyCode = keyCode;
        el.fireEvent('on' + evtType, evtObj);
    }
}