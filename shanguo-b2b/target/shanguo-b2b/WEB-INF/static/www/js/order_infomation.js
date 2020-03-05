/**
 * Created by Administrator on 2017/5/9.
 */
var orderInformation = {
    init:function(){
        orderInformation.isDefaultAddress($(".order-address-group"));
        orderInformation.selectAddress();
        orderInformation.setDefaultAddress();
        orderInformation.isPayRouter();
        orderInformation.setPayRouter();
        orderInformation.billSelect();
        orderInformation.favorableList();
        orderInformation.amountFn();
        orderInformation.chooseDestination();
        orderInformation.buildNewAddress();
        orderInformation.editorAddress();
        orderInformation.addressSpread();
        orderInformation.billChange();
        orderInformation.billCategory();
    },
    /*初始化默认地址*/
    isDefaultAddress:function(obj){
        for(var i=0;i<obj.length;i++){
            /*已选地址*/
            if(obj.eq(i).find("input[type=radio]").attr("data-checked")=="true"){
                obj.eq(i).find("label").addClass("address-active");
            }else{
                obj.eq(i).find("label").removeClass("address-active");
            }
            /*默认地址*/
            if(obj.eq(i).find("input[type=radio]").attr("data-address-default")=="true"){
                obj.eq(i).find(".address-default").show();
                obj.eq(i).find(".address-set-default").hide();
            }else{
                obj.eq(i).find(".address-default").hide();
                obj.eq(i).find(".address-set-default").show();
            }
        }
    },
    /*选择地址事件*/
    selectAddress:function(){
        $(".order-address-group").find(".input-mask").click(function(){
            $(this).siblings('input[type=radio]').attr('data-checked',"true");
            $(this).closest(".order-address-group").siblings().find('input[type=radio]').attr("data-checked",'false');
            orderInformation.isDefaultAddress($(".order-address-group"));
        });
    },
    /*设置默认地址*/
    setDefaultAddress:function(){
        $(".address-set-default").click(function(){
            $(this).closest(".order-address-group").find("input[type=radio]").attr("data-address-default",'true');
            $(this).closest(".order-address-group").siblings().find("input[type=radio]").attr("data-address-default",'false');
            orderInformation.isDefaultAddress($(".order-address-group"));
        });
    },
    /*初始化支付方式*/
    isPayRouter:function(){
        /*初始化*/
        for(var i=0;i<$(".order-pay").find("label").length;i++){
            if( $(".order-pay").find("label").eq(i).find("input[type=radio]").attr("data-checked") == "true"){
                $(".order-pay").find("label").eq(i).addClass("order-pay-active");
            }else{
                $(".order-pay").find("label").eq(i).removeClass("order-pay-active");
            }
        }
    },
    /*点击选择支付方式*/
    setPayRouter:function(){
        $(".order-pay").find("label").click(function(){
            $(this).find("input[type=radio]").attr("data-checked","true");
            $(this).siblings().find("input[type=radio]").attr("data-checked","false");
            orderInformation.isPayRouter();
        });
    },
    /*选择发票*/
    billSelect:function(){
        $(".bill-list").hide();
        $(".bill-category").hide();
        $(".bill-editor").hide();
        $(".bill-title").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            if( $(".bill-list").css("display") == "block" ){
                $(".bill-list").hide();
            }else{
                $(".bill-list").show();
            }
        });
        $(".bill-list").find("li").click(function(ev){
            var html = $(this).html();
            var ev = ev || event;
            ev.stopPropagation();
            if(html == "不要发票"){
                $(".bill-category").hide();
                $(".bill-editor").hide();
            }else{
                $(".bill-category").show();
                $(".bill-editor").show();
            }
            $(".bill-title").html(html+"<i></i>");
            $(".bill-list").hide();
        });
        $("body").click(function(){
            $(".bill-list").hide();
        });
    },
    /*优惠下拉选择*/
    favorableList:function(){
        $(".product-favorable-list").hide();
        $(".product-favorable-btn").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            if( $(this).siblings(".product-favorable-list").css("display") == "block" ){
                $(this).siblings(".product-favorable-list").hide();
                $(this).closest(".product-favorable-title").removeClass("product-favorable-title-active");
            }else{
                $(this).siblings(".product-favorable-list").show();
                $(this).closest("li").siblings("li").find(".product-favorable-title").removeClass("product-favorable-title-active");
                $(this).closest("li").siblings("li").find(".product-favorable-list").hide();
                $(this).closest(".product-favorable-title").addClass("product-favorable-title-active");
            }
        });
        $(".product-favorable-list").find("li").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            var html = $(this).html().substring(0,7);
             $(this).closest(".product-favorable-list").siblings(".product-favorable-btn").html(html + "<i></i>");
             $(this).closest(".product-favorable-list").hide();
            $(this).closest(".product-favorable-title").removeClass("product-favorable-title-active");
        });
        $("body").click(function(){
            $(".product-favorable-list").hide();
            $(".product-favorable-title").removeClass("product-favorable-title-active");
        });
    },
    /*数量加减*/
    amountFn:function(){
        $(".subtraction").click(function(){
            var count = parseInt( $(this).siblings('input').val() );
            if(count > 1){
                count--;
            }else if(count == 1){
                count=0;
                $(this).addClass("disabled");
            }
            $(this).siblings('input').val(count);
        });
        $(".plus").click(function(){
            var count = parseInt( $(this).siblings('input').val() );
            count++;
            $(this).siblings('input').val(count);
            $(this).siblings(".subtraction").removeClass("disabled");
        });
    },
    /*选择地址*/
    chooseDestination:function(){
        /*初始化状态*/
        $(".express-choose-datalist").hide();
        $(".gray-line").hide();
        $(".province-title").show().siblings().hide();
        $(".location-content").find(".province").show().siblings().hide();
        /*地址选择按钮*/
        $(".location-choose").click(function(){
            if($(this).hasClass('location-choose-active')){
                $(this).siblings(".express-choose-datalist").hide();
                $(this).removeClass("location-choose-active");
                $(this).siblings(".gray-line").hide();
            }else{
                $(this).siblings(".gray-line").show();
                $(this).siblings(".express-choose-datalist").show();
                $(this).addClass("location-choose-active");
            }
        });
        /*关闭按钮*/
        $(".express-close").click(function(){
            $(this).closest(".express-choose-datalist").hide();
            $(this).closest(".express-wrap").find(".locationChoose").removeClass("location-choose-active");
            $(this).closest(".express-wrap").find(".gray-line").hide();
        });
        /*省  市  区  选择点击时间*/
        var province;
        var city;
        var area;
        orderInformation.siblingsHide($(".province-title"));
        orderInformation.siblingsHide($(".city-title"));
        /*省列表点击*/
        for(var i=0;i<$(".province").find("li").length;i++){
            $(".province").find("li").eq(i).find('span').click(function(){
                /*获取省份信息*/
                province = $(this).html();
                $(this).closest(".location-content").siblings('.location-title').find(".province-title").html( province + '<i></i>' );
                $(this).closest(".location-content").siblings('.location-title').find(".city-title").html('请选择' + '<i></i>');
                $(this).closest(".location-content").siblings('.location-title').find(".city-title").show();
                $(this).closest(".location-content").siblings('.location-title').find(".city-title").addClass("active").siblings().removeClass("active");
                $(this).closest(".location-content").find(".city").show();
                console.log(province);
                $(this).closest(".province").hide();
                $(this).closest(".location-content").find(".city").show().siblings().hide();
                for(var i = 0 ;i<$(this).closest(".location-content").find(".city").find("ul").length;i++){
                    if($(this).closest(".location-content").find(".city").find("ul").eq(i).attr("data-category-province") == province){
                        $(this).closest(".location-content").find(".city").find("ul").eq(i).show();
                    }else {
                        $(this).closest(".location-content").find(".city").find("ul").eq(i).hide();
                    }
                }
            });
        };
        /*市列表点击*/
        for(var i=0;i<$(".city").find("li").length;i++){
            $(".city").find("li").eq(i).find('span').click(function(){
                /*获取城市信息*/
                city = $(this).html();
                $(this).closest(".location-content").siblings('.location-title').find(".city-title").html(city + '<i></i>');
                $(this).closest(".location-content").siblings('.location-title').find(".area-title").show();
                $(this).closest(".location-content").siblings('.location-title').find(".area-title").addClass("active").siblings().removeClass("active");
                $(this).closest(".location-group").siblings(".area").show();
                console.log(city);
                $(this).closest(".city").hide();
                $(this).closest(".location-group").siblings(".area").show().siblings().hide();
                for(var i = 0 ;i<$(this).closest(".location-group").siblings(".area").find("ul").length;i++){
                    if($(this).closest(".location-group").siblings(".area").find("ul").eq(i).attr("data-category-city") == city){
                        $(this).closest(".location-group").siblings(".area").find("ul").eq(i).show();
                    }else {
                        $(this).closest(".location-group").siblings(".area").find("ul").eq(i).hide();
                    }
                }
            });
        };
        /*区域列表点击*/
        for(var i=0;i<$(".area").find("li").length;i++){
            $(".area").find("li").eq(i).find('span').click(function(){
                /*获取区域信息*/
                area = $(this).html();
                $(this).closest(".express-wrap").find(".location-choose").html(province+ "/" + city + "/" +area + '<i></i>');
                $(this).closest(".express-choose-datalist").hide();
                $(this).closest(".express-choose-datalist").siblings(".gray-line").hide();
                $(this).closest(".express-choose-datalist").siblings(".location-choose").removeClass("location-choose-active");
            });
        };
        /*头部点击*/
        $(".province-title").click(function(){
            $(this).closest(".location-title").siblings(".location-content").find(".province").show().siblings().hide();
            $(this).html("请选择<i></i>");
            $(this).addClass("active").siblings().removeClass("active");
        });
        $(".city-title").click(function(){
            $(this).closest(".location-title").siblings(".location-content").find(".city").show().siblings().hide();
            $(this).html("请选择<i></i>");
            $(this).addClass("active").siblings().removeClass("active");
        });
    },
    /*点击  后面的兄弟标签消失*/
    siblingsHide:function(obj){
        obj.click(function(){
            $(this).nextAll().hide();
        });
    },
    /*新建地址弹窗*/
    buildNewAddress:function(){
        $("#newAddress").hide();
        $("#buildNewAddress").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#newAddress").show();
            $("body,html").css("overflow","hidden");
        });
        $(".address-build-mask").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $(this).closest("#newAddress").show();
            $("body,html").css("overflow","hidden");
        });
        $(".address-build-close").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $(this).closest("#newAddress").hide();
            $("body,html").css("overflow","auto");
        });
        $("#newAddress").click(function(){
            $("#newAddress").hide();
            $("body,html").css("overflow","auto");
        });
    },
    /*编辑地址  弹窗*/
    editorAddress:function(){
        for(var i=0;i<$(".address-edit").length;i++){
            $("#editorAddress").hide();
            $(".address-edit").eq(i).click(function(ev){
                var ev = ev || event;
                ev.stopPropagation();
                $("#editorAddress").show();
            });
        };
        $(".address-build-mask").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $(this).closest("#editorAddress").show();
            $("body,html").css("overflow","hidden");
        });
        $(".address-build-close").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $(this).closest("#editorAddress").hide();
            $("body,html").css("overflow","auto");
        });
        $("#editorAddress").click(function(){
            $("#editorAddress").hide();
            $("body,html").css("overflow","auto");
        });
    },
    /*收货地址  展开收起*/
    addressSpread:function(){
        /*初始化*/
        var minHeight = $("#orderAddress").find(".order-address-group").outerHeight(true);
        var len = $("#orderAddress").find(".order-address-group").length;
        $("#orderAddress").css("max-height",minHeight);
        $(".address-up").hide();
        $(".address-down").click(function(){
            $(this).hide();
            $(".address-up").show();
            $("#orderAddress").css("max-height",minHeight*len);
        });
        $(".address-up").click(function(){
            $(this).hide();
            $(".address-down").show();
            $("#orderAddress").css("max-height",minHeight);
        });
    },
    /*修改发票弹窗*/
    billChange:function(){
        $("#billChange").hide();
        $(".bill-editor").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#billChange").show();
            $("body,html").css("overflow","hidden");
        });
        $("body").click(function(){
            $("#billChange").hide();
            $("body,html").css("overflow","auto");
        });
        $(".bill-change-content").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#billChange").show();
            $("body,html").css("overflow","hidden");
        });
        $(".bill-close").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#billChange").hide();
            $("body,html").css("overflow","auto");
        });
    },
    /*选择发票类型*/
    billCategory:function(){
        $(".bill-change-category-group").click(function(){
            if(!$(this).find("input[type=radio]").checked){
                $(this).addClass("bill-change-category-group-active").siblings().removeClass("bill-change-category-group-active");
            }
        });
    }

};
$(function(){
    orderInformation.init();
});