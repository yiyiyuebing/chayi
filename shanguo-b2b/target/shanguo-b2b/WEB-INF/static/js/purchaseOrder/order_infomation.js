/**
 * Created by Administrator on 2017/5/9.
 */
var orderInformation = {
    main: $(".order-body"),
    init:function(){
        if (!$.checkLogin()) { //验证登录
            return;
        }
        orderInformation.isDefaultAddress($(".order-address-group"));
        orderInformation.selectAddress();
        //orderInformation.setDefaultAddress();
        orderInformation.isPayRouter();
        orderInformation.setPayRouter();
        orderInformation.billSelect();


        //orderInformation.chooseDestination();
        orderInformation.addressSpread();
        orderInformation.buildNewAddress();
        orderInformation.editorAddress();

        orderInformation.billChange();
        orderInformation.billCategory();
        orderInformation.getReceiveAddressList();

        $.initCitySelect({
            selectId: $("#add-order-info-city-select"),
            clickId: $("#add-order-info-city-click"),
            selectArea:orderInformation.getAddAreaName,
            selectAreaCode:orderInformation.getAddAreaCode
        });
        $.initCitySelect({
            selectId: $("#edit-order-info-city-select"),
            clickId: $("#edit-order-info-city-click"),
            selectArea:orderInformation.getEditAreaName,
            selectAreaCode:orderInformation.getEditAreaCode
        });

        orderInformation.getOrderInfoList();
    },

    changeNum: function(target, num, operator) {
        var changeGoodNumQuery = {};
        changeGoodNumQuery.nowNum = $(target).siblings(".ipt-sku-num").eq(0).val();
        changeGoodNumQuery.isSample = $(target).siblings(".ipt-sku-num").attr("data-is-sample") == "1" ? "T" : "F";
        changeGoodNumQuery.skuId = $(target).siblings(".ipt-sku-num").eq(0).attr("data-sku-id");
        changeGoodNumQuery.operation = operator;
        $.ajax({
            type: "POST",
            data: {queryJson: JSON.stringify(changeGoodNumQuery)},
            url: "/purchase/changeGoodNum",
            success: function(result) {
                if (result.success) {
                    $(target).siblings(".ipt-sku-num").eq(0).val(result.data);
                    orderInformation.calGoodsPrice(target, result.data);
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    },


    calGoodsPrice: function(target, num) {
        var result;
        var orderData = {};
        var orderList = [];
        $(target).siblings(".ipt-sku-num").each(function(i, perInput) {
            var orderListInfo = {};
            if (parseInt($(perInput).val())) {
                orderListInfo.number = $(perInput).val();
                orderListInfo.purGoodsSkuId = $(perInput).attr("data-sku-id");
                orderList.push(orderListInfo);
            }
        });
        orderData.orderListVos = orderList;
        $.ajax({
            type: "POST",
            data: {orderJson: JSON.stringify(orderData)},
            url: "/cart/calGoodsPriceInfo",
            success: function(result) {
                if (result.success) {
                    var orderVo = result.data;
                    var orderListVo = result.data.orderListVos[0];
                    var price = Number(orderListVo.finalAmount) / Number(orderListVo.buyNum);
                    console.log(price);
                    target.parents("div").siblings(".product-price").find(".price-span").html(price.toFixed(2));
                    target.closest(".product-count").siblings(".product-cost").html((price * num).toFixed(2));
                    if (!target.siblings('.ipt-sku-num').attr("data-is-sample")) {
                        orderInformation.calGiftRule();
                        orderInformation.getCarriageInfo(); //计算费用合计
                    } else {
                        orderInformation.calTotalPrice();
                    }

                } else {
                    layer.msg(result.msg);
                }
            }
        });


    },

    getOrderInfoList: function() {
        var purchaseOrder = {};
        var orderList = [];
        var isSample = null;
        var purGoodsId = null;
        $("#orderInfo").find("input[name=skuInfo]").each(function(i, perInput) {
            var orderInfo = {};
            orderInfo.purGoodsSkuId = $(perInput).attr("data-sku-id");
            orderInfo.number = $(perInput).attr("data-number");
            orderInfo.isSample = $(perInput).attr("data-isSample") ? Number($(perInput).attr("data-isSample")) : null;
            orderInfo.purGoodsId = $(perInput).attr("data-goods-id");
            orderList.push(orderInfo);
            isSample = orderInfo.isSample;
            purGoodsId = orderInfo.purGoodsId;
        });
        if (isSample) {
            purchaseOrder.purGoodsId = purGoodsId;
            purchaseOrder.isSample = isSample;
        }
        purchaseOrder.orderListVos = orderList;
        $.ajax({
            type: "POST",
            data: {orderJson: JSON.stringify(purchaseOrder)},
            url: "/order/getOrderInfoList",
            async:true,
            success: function(result) {
                console.log(result);
                if (result.success && result.data.baseGoodList) {
                    orderInformation.main.find(".order-info").empty();
                    var isSample = null;
                    $.each(result.data.baseGoodList, function(i, perGoods) {
                        var number = perGoods.number ? perGoods.number : 0;
                        var price = perGoods.price ? perGoods.price : 0;
                        if (perGoods.isSancha === "T" && perGoods.supplyPriceList) {
                            for (var j = 0; j < perGoods.supplyPriceList.length; j++) {
                                var sectionEnd = perGoods.supplyPriceList[j].sectionEnd ? perGoods.supplyPriceList[j].sectionEnd : 999999;
                                var sectionStart = perGoods.supplyPriceList[j].sectionStart ? perGoods.supplyPriceList[j].sectionStart : 0;
                                if (number >= sectionStart && number < sectionEnd) {
                                    price = perGoods.supplyPriceList[j].supplyPrice;
                                    perGoods.price = perGoods.supplyPriceList[j].supplyPrice;
                                    break;
                                }
                            }
                        }
                        if (perGoods.isSample) {
                            isSample = 1;
                        }

                        if (perGoods.cargoSkuStock <= perGoods.onSalesNo) {
                            perGoods.onSalesNo = perGoods.cargoSkuStock;
                        }

                        if (perGoods.onSalesNo < 0) {
                            perGoods.onSalesNo = 0;
                        }

                        var totalPrice = Number(number * price).toFixed(2);
                        perGoods.totalPrice = totalPrice;
                        var tpl_product_info = template("tpl-product-info", perGoods);
                        orderInformation.main.find(".order-info").append($(tpl_product_info));
                    });
                    orderInformation.favorableList();
                    orderInformation.amountFn();
                    if (!isSample) {
                        orderInformation.calGiftRule();
                        orderInformation.getCarriageInfo(); //查询运费相关
                    } else {
                        orderInformation.calTotalPrice();
                    }
                }
            }
        });

    },

    getReceiveAddressList: function() {
        $.ajax({
            type: "POST",
            url: "/user/address",
            async:true,
            success: function(result) {
                if (result.success && result.data.graList) {
                    orderInformation.main.find(".order-address").empty();
                    $.each(result.data.graList, function(i, perAddr) {

                        var addressInfo = JSON.stringify(perAddr);
                        perAddr.addressInfo = addressInfo;
                        var tpl_order_address_group = template("tpl-order-address-group", perAddr);


                        orderInformation.main.find(".order-address").append($(tpl_order_address_group));
                    });
                    orderInformation.isDefaultAddress(orderInformation.main.find(".order-address-group"));
                    orderInformation.selectAddress();
                    //orderInformation.setDefaultAddress();
                    orderInformation.addressSpread();
                    orderInformation.getReceiveAddress();
                }
            }
        });
    },


    setAddressDefault: function(target) {
        var id = $(target).attr("data-id");
        $.ajax({
            type: "POST",
            data: {addrId: id},
            url: "/user/updateDefaultAddr",
            success: function(result) {
                if (result.success) {
                    $(target).closest(".order-address-group").siblings().find("input[type=radio]").removeAttr("checked");
                    $(target).closest(".order-address-group").siblings().find("input[type=radio]").attr("data-address-default",'false');
                    $(target).closest(".order-address-group").siblings().find("input[type=radio]").attr('data-checked', "false");
                    $(target).closest(".order-address-group").find("input[type=radio]").attr('data-checked', "true");
                    $(target).closest(".order-address-group").find("input[type=radio]").attr("data-address-default",'true');
                    $(target).closest(".order-address-group").find("input[type=radio]").attr('checked',"checked");
                    orderInformation.isDefaultAddress(orderInformation.main.find(".order-address-group"));
                    orderInformation.getReceiveAddress();
                }
            }
        });


    },
    /*初始化默认地址*/
    isDefaultAddress:function(obj) {
        for(var i=0; i < obj.length; i++) {
            /*已选地址*/
            if(obj.eq(i).find("input[type=radio]").attr("data-checked") == "true"){
                obj.eq(i).find("label").addClass("address-active");
            }else{
                obj.eq(i).find("label").removeClass("address-active");
            }
            /*默认地址*/
            if(obj.eq(i).find("input[type=radio]").attr("data-address-default") == "true"){
                obj.eq(i).find(".address-default").show();
                obj.eq(i).find(".address-set-default").addClass("hide");
            } else {
                obj.eq(i).find(".address-default").hide();
                obj.eq(i).find(".address-set-default").removeClass("hide");
            }
        }
    },
    /*选择地址事件*/
    selectAddress:function() {
        orderInformation.main.find(".order-address-group").find(".input-mask").click(function(){
            $(this).closest(".order-address-group").siblings().find('input[type=radio]').attr("data-checked",'false');
            //$(this).closest(".order-address-group").siblings().find('input[type=radio]').attr("data-address-default",'false');
            $(this).closest(".order-address-group").siblings().find('input[type=radio]').removeAttr("checked");
            $(this).siblings('input[type=radio]').attr('data-checked',"true");
            //$(this).siblings('input[type=radio]').attr("data-address-default",'true');
            $(this).siblings('input[type=radio]').attr('checked',"checked");
            orderInformation.isDefaultAddress(orderInformation.main.find(".order-address-group"));
            orderInformation.getCarriageInfo(); //计算费用合计
            orderInformation.getReceiveAddress();
        });
    },


    getReceiveAddress: function() {
        var addressInput = orderInformation.main.find("input[name=address]:checked");
        var province = addressInput.attr("data-province") ? addressInput.attr("data-province") : "";
        var city = addressInput.attr("data-city") ? addressInput.attr("data-city") : "";
        var area = addressInput.attr("data-area") ? addressInput.attr("data-area") : "";
        var address = addressInput.attr("data-address") ? addressInput.attr("data-address") : "";

        var mobile = addressInput.attr("data-mobile") ? addressInput.attr("data-mobile") : "";
        var receiptname = addressInput.attr("data-receiptname") ? addressInput.attr("data-receiptname") : "";
        $(".order-footer-address").find(".address-info").html("寄送至：" + province + city + area + address);
        $(".order-footer-address").find(".receive-name").html("收货人：" + receiptname + " " + mobile);
    },

    /*初始化支付方式*/
    isPayRouter:function(){
        /*初始化*/
        for(var i=0;i<orderInformation.main.find(".order-pay").find("label").length;i++){
            if( orderInformation.main.find(".order-pay").find("label").eq(i).find("input[type=radio]").attr("data-checked") == "true"){
                orderInformation.main.find(".order-pay").find("label").eq(i).addClass("order-pay-active");
            }else{
                orderInformation.main.find(".order-pay").find("label").eq(i).removeClass("order-pay-active");
            }
        }
    },
    /*点击选择支付方式*/
    setPayRouter:function(){
        orderInformation.main.find(".order-pay").find("label").click(function(){
            $(this).find("input[type=radio]").attr("data-checked","true");
            $(this).siblings().find("input[type=radio]").attr("data-checked","false");
            orderInformation.isPayRouter();
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
        $(".product-favorable-list").find("li").click(function(ev) {
            var ev = ev || event;
            ev.stopPropagation();
            var html = $(this).attr("data-activity-name");

            var $product_favorable_btn = $(this).closest(".product-favorable-list").siblings(".product-favorable-btn");
            if ($(this).attr("data-activity-type") == "presell") {
                $product_favorable_btn.attr("data-activity-type", $(this).attr("data-activity-type"));
                $product_favorable_btn.attr("data-limit-num", $(this).attr("data-limit-num"));
                $product_favorable_btn.attr("data-precell-type", $(this).attr("data-precell-type"));
                $product_favorable_btn.attr("data-activity-type", $(this).attr("data-limit-flag"));
                var presellPrice = 0;
                if ($(this).attr("data-precell-type") === "one") {
                    presellPrice = $(this).attr("data-presell-amount") ? Number($(this).attr("data-presell-amount")) : 0;
                } else {
                    presellPrice = $(this).attr("data-first-amount") ? Number($(this).attr("data-first-amount")) : 0;
                }

                $(this).closest(".product-favorable").siblings(".product-count").find(".plus").attr("data-onsaleno", $(this).attr("data-limit-num")); //设置限购数量
                $(this).closest(".product-favorable").siblings(".product-count").find("input[name=number]").attr("data-price", presellPrice); //设置单价

                $(this).closest(".product-favorable").siblings(".product-price").find(".price-span").html(presellPrice); //设置单价

                var number = $(this).closest(".product-favorable").siblings(".product-count").find("input[name=number]").val() ?
                    Number($(this).closest(".product-favorable").siblings(".product-count").find("input[name=number]").val()) : 0; //获取当前sku的数量

                $(this).closest(".product-favorable").siblings(".product-cost").html((number * presellPrice).toFixed(2)); //设置每个sku的总计价格

            } else {
                $product_favorable_btn.attr("data-activity-type", $(this).attr("data-activity-type"));
            }

             $(this).closest(".product-favorable-list").siblings(".product-favorable-btn").html(html + "<i></i>");
             $(this).closest(".product-favorable-list").hide();
            $(this).closest(".product-favorable-title").removeClass("product-favorable-title-active");

            orderInformation.getCarriageInfo();
        });
        $("body").click(function(){
            $(".product-favorable-list").hide();
            $(".product-favorable-title").removeClass("product-favorable-title-active");
        });
    },

    calTotalPrice: function() {

        var carriage = $(".customer-message-group").find("input[name=buyerCarriage]").val() ? Number($(".customer-message-group").find("input[name=buyerCarriage]").val()) : 0;
        var totalPrice = 0;
        orderInformation.main.find(".product-count").find("input[name=number]").each(function(i, perInput) {
            var price = $(perInput).attr("data-price") ? Number($(perInput).attr("data-price")) : 0;
            var number = $(perInput).val() ? Number($(perInput).val()) : 0;
            totalPrice += price * number;
        });
        totalPrice = (totalPrice + carriage).toFixed(2); //计算总价
        orderInformation.main.find(".order-total-price").html("¥" + totalPrice); //设置总价
        $(".order-footer-cost").find(".total-payment").html("¥" + totalPrice); //设置总价
    },

    submitOrderInfo: function() {

        var orderData = {};
        var receiverInfo = $("input[name=address]:checked");

        if (!(receiverInfo.length > 0)) {
            layer.msg("请选择收货地址");
            return;
        }

        orderData.province = receiverInfo.attr("data-province") ? receiverInfo.attr("data-province") : "";
        orderData.city = receiverInfo.attr("data-city") ? receiverInfo.attr("data-city") : "";
        orderData.town = receiverInfo.attr("data-area") ? receiverInfo.attr("data-area") : "";
        orderData.address = receiverInfo.attr("data-address") ? receiverInfo.attr("data-address") : "";

        orderData.receiver = receiverInfo.attr("data-receiptname") ? receiverInfo.attr("data-receiptname") : "";
        orderData.receiverPhone = receiverInfo.attr("data-mobile") ? receiverInfo.attr("data-mobile") : "";

        orderData.buyerCarriage = $(".customer-message-group").find("input[name=buyerCarriage]").val();
        orderData.expressId = $(".customer-message-group").find("input[name=expressId]").val();
        orderData.expressCompany = $(".customer-message-group").find("input[name=expressCompany]").val();

        orderData.buyerRemark = $(".customer-message").find("textarea[name=buyerRemark]").val();

        orderData.needInvoice = $(".order-bill").find("input[name=needInvoice]").val();
        if(orderData.needInvoice != 0) {
            orderData.needInvoice = true;
            orderData.invoiceName = $(".bill-change-body").find("input[name=invoiceName]:checked").val();
            var invoiceNameType = $(".bill-change-body").find("input[name=invoiceName]:checked").attr("data-value");
            if (invoiceNameType == "company") {
                orderData.invoiceDutyParagraph = $(".bill-change-body").find("input[name=invoiceDutyParagraph]").val();
                orderData.invoiceContent = $(".bill-change-body").find("input[name=invoiceContent]").val();
            }
        } else {
            orderData.needInvoice = false;
        }

        var orderList = [];
        $(".order-info").find("input[name=number]").each(function(i, perInput) {
            var orderListInfo = {};
            if (parseInt($(perInput).val()) != 0) {
                orderListInfo.number = $(perInput).val();
                orderListInfo.purGoodsSkuId = $(perInput).attr("data-sku-id");
                orderListInfo.isSample = $(perInput).attr("data-is-sample") == "1" ? "T" : "F";
                orderList.push(orderListInfo);
            }
        });
        orderData.orderListVos = orderList;

        var createOrderUrl = "/order/createNormal";

        if ($("input[name=orderType]").val() == "presell") { //预售提交订单接口
            createOrderUrl = "/order/createPresell";
        }
        var ii = layer.load();
        $.ajax({
            type: "POST",
            data: {orderJson: JSON.stringify(orderData)},
            url: createOrderUrl,
            async: true,
            success: function(result) {
                layer.close(ii);
                if (result.success) {
                    window.location.href = "/order/submit/" + result.data.id;
                } else {
                    layer.msg(result.msg)
                }
            },
            error: function(){
                layer.msg("创建订单失败！")
                layer.close(ii);
            }
        });
    },

    /**
     * 获取运费信息
     * @param city
     * @param area
     */
    getCarriageInfo: function() {
        var carriage = 0;
        var addressInput = orderInformation.main.find("input[name=address]:checked");
        var city = addressInput.attr("data-city");
        var area = addressInput.attr("data-area");
        var tradeContext = {
            "city": city,
            "area": area
        }
        var skuList = [];
        orderInformation.main.find(".product-count").find("input[name=number]").each(function(i, perSkuTr) {
            var perSku = {
                purGoodsSkuId: $(perSkuTr).attr("data-sku-id"),
                number: $(perSkuTr).val(),
                finalAmount: Number($(perSkuTr).val() ? $(perSkuTr).val() : 0) * Number($(perSkuTr).attr("data-price") ? $(perSkuTr).attr("data-price") : 0),
                isSample: "0"
            };
            skuList.push(perSku);
        });

        $.ajax({
            type: "POST",
            data: {listJson: JSON.stringify(skuList), tradeContextJson: JSON.stringify(tradeContext)},
            url: "/feright/showCarriageOptions",
            async: true,
            success: function(result) {
                if (result.success && result.data.fvo) {
                    var fvo = result.data.fvo;
                    if (fvo.servicerList) {
                        var freightTplInfo = fvo.servicerList[0];

                        var html = "普通配送 ";
                            html += freightTplInfo.servicerName;
                        if (freightTplInfo.totalFreight) {
                            html += "  ¥"+ freightTplInfo.totalFreight +"";
                        } else {
                            html += "免运费";
                        }
                        $(".customer-message-group").find(".post-service").html(html);
                        $(".customer-message-group").find("input[name=buyerCarriage]").val(freightTplInfo.totalFreight);
                        $(".customer-message-group").find("input[name=expressId]").val(freightTplInfo.servicerId);
                        $(".customer-message-group").find("input[name=expressCompany]").val(freightTplInfo.servicerName);

                    }
                }
                orderInformation.calTotalPrice(); //计算费用合计
            }
        });
    },

    /*数量加减*/
    amountFn:function() {
        $(".subtraction").click(function(){
            var count = parseInt( $(this).siblings('.ipt-sku-num').val() );
            orderInformation.changeNum($(this), count, "sub");

            /*var startNum = parseInt($(this).attr("data-start-num") ? $(this).attr("data-start-num") : 0);
            var mulNumFlag = $(this).attr("data-mul-num-flag") && $(this).attr("data-mul-num-flag") == "T" ? true : false;

            if(count > startNum) {
                if (mulNumFlag) {
                    count = count - startNum;
                } else {
                    count--;
                }
                $(this).siblings(".plus").removeClass("disabled");
            }else if(count == startNum){
                count = startNum;
                $(this).siblings(".plus").removeClass("disabled");
                $(this).addClass("disabled");
            }

            //if(count > 1){
            //    count--;
            //    $(this).siblings(".plus").removeClass("disabled");
            //}else if(count == 1){
            //    count=0;
            //    $(this).siblings(".plus").removeClass("disabled");
            //    $(this).addClass("disabled");
            //}



            $(this).siblings('.ipt-sku-num').val(count);


            if ($(this).siblings('.ipt-sku-num').attr("data-sancha-flag")
                && $(this).siblings('.ipt-sku-num').attr("data-sancha-flag") === "T") {
                orderInformation.getSanchSupplyPrice(this, count);
            }

            var price = $(this).siblings('.ipt-sku-num').attr("data-price") ? Number($(this).siblings('.ipt-sku-num').attr("data-price")) : 0;
            $(this).closest(".product-count").siblings(".product-cost").html((price * count).toFixed(2));
            if (!$(this).siblings('.ipt-sku-num').attr("data-is-sample")) {
                orderInformation.calGiftRule();
                orderInformation.getCarriageInfo(); //计算费用合计
            } else {
                orderInformation.calTotalPrice();
            }
*/

        });
        $(".plus").click(function(){

            var onSaleNo = $(this).attr("data-onsaleno") ? Number($(this).attr("data-onsaleno")) : 0;
            var count = parseInt( $(this).siblings('.ipt-sku-num').val() );

            orderInformation.changeNum($(this), count, "add");

            /*var startNum = parseInt($(this).attr("data-start-num") ? $(this).attr("data-start-num") : 0);
            var mulNumFlag = $(this).attr("data-mul-num-flag") && $(this).attr("data-mul-num-flag") == "T" ? true : false;

            if (mulNumFlag) {
                count += startNum;
            } else {
                count++;
            }
            if (count < onSaleNo) {
                $(this).siblings('.ipt-sku-num').val(count);
                $(this).siblings(".subtraction").removeClass("disabled");

                if ($(this).siblings('.ipt-sku-num').attr("data-sancha-flag")
                    && $(this).siblings('.ipt-sku-num').attr("data-sancha-flag") === "T") {
                    orderInformation.getSanchSupplyPrice(this, count);
                }

                var price = $(this).siblings('.ipt-sku-num').attr("data-price") ? Number($(this).siblings('.ipt-sku-num').attr("data-price")) : 0;
                $(this).closest(".product-count").siblings(".product-cost").html((price * count).toFixed(2));
            } else if (count == onSaleNo) {
                $(this).siblings('.ipt-sku-num').val(count);
                $(this).addClass("disabled");

                if ($(this).siblings('.ipt-sku-num').attr("data-sancha-flag")
                    && $(this).siblings('.ipt-sku-num').attr("data-sancha-flag") === "T") {
                    orderInformation.getSanchSupplyPrice(this, count);
                }

                var price = $(this).siblings('.ipt-sku-num').attr("data-price") ? Number($(this).siblings('.ipt-sku-num').attr("data-price")) : 0;
                $(this).closest(".product-count").siblings(".product-cost").html((price * count).toFixed(2));
            } else {
                $(this).addClass("disabled");
            }
            if (!$(this).siblings('.ipt-sku-num').attr("data-is-sample")) {
                orderInformation.calGiftRule();
                orderInformation.getCarriageInfo(); //计算费用合计
            } else {
                orderInformation.calTotalPrice();
            }*/
        });
    },

    getSanchSupplyPrice: function(target, count) {
        $(target).siblings("input[name=sancha-price]").each(function(i, perInput) {
            var dataPrice = Number($(perInput).attr("data-price"));
            var sectionStart = Number($(perInput).attr("data-section-start"));
            var sectionEnd = Number($(perInput).attr("data-section-end") ? $(perInput).attr("data-section-end") : 999999999);
            if (count >= sectionStart && count <= sectionEnd) {
                $(target).closest(".product-count").siblings(".product-price").find(".price-span").html(dataPrice.toFixed(2));
                $(target).siblings(".ipt-sku-num").attr("data-price", dataPrice.toFixed(2));
                return false;
            }
        });
    },

    getAddAreaName: function (province, city, area) {
        $("#add-provinceName").val(province);
        $("#add-cityName").val(city);
        $("#add-areaName").val(area);
    },

    getAddAreaCode: function (provinceCode, cityCode, areaCode) {
        $("#add-provinceCode").val(provinceCode);
        $("#add-cityCode").val(cityCode);
        $("#add-areaCode").val(areaCode);
    },

    getEditAreaName: function (province, city, area) {
        $("#edit-provinceName").val(province);
        $("#edit-cityName").val(city);
        $("#edit-areaName").val(area);
    },

    getEditAreaCode: function (provinceCode, cityCode, areaCode) {
        $("#edit-provinceCode").val(provinceCode);
        $("#edit-cityCode").val(cityCode);
        $("#edit-areaCode").val(areaCode);
    },

    validateForm: function($form) {
        var flag = true;
        $form.find("input").each(function(i, perInput) {

            if($(perInput).attr("data-rule")) {
                if ($(perInput).attr("data-rule") === "required") {
                    if (!$(perInput).val()) {
                        layer.msg($(perInput).attr("data-validate-info"));
                        flag = false;
                        return false;
                    }
                }
                if ($(perInput).attr("data-rule") === "phone") {
                    if (!$(perInput).val()) {
                        layer.msg("手机号不能为空");
                        flag = false;
                        return false;
                    }
                    if (!$.validatePhone($(perInput).val())) {
                        layer.msg($(perInput).attr("data-validate-info"));
                        flag = false;
                        return false;
                    }
                }
                if ($(perInput).attr("data-rule") === "tel") {
                    if ($(perInput).val()) {
                        if (!$.validateTel($(perInput).val())) {
                            layer.msg($(perInput).attr("data-validate-info"));
                            flag = false;
                            return false;
                        }
                    }
                }
            }
        });

        return flag;
    },

    delReceiveAddresInfo: function(target) {
        if(!$(target).attr("data-id")) {
            return;
        }
        $.ajax({
            type: "POST",
            data: {addrId: $(target).attr("data-id")},
            url: "/user/delAddress",
            success: function(result) {
                if (result.success) {
                    layer.msg("删除成功");
                    orderInformation.getReceiveAddressList();
                } else {
                    layer.msg("删除失败");
                }
            }
        });

    },

    saveReceiveAddressInfo: function(target) {
        var url = "/user/addAddress";
        if ($(target).attr("data-form-id") == "edit-receive-address-info") {
            url = "/user/updateAddress";
        }

        var $form = $("#" + $(target).attr("data-form-id")).serializeArray();
        var data = {};
        var flag = orderInformation.validateForm($("#" + $(target).attr("data-form-id")));
        if (!flag) {
            return;
        }

        $("#" + $(target).attr("data-form-id")).find("input[name=]");


        $.each($form, function() {
            data[this.name] = this.value;
        });

        $.ajax({
            type: "POST",
            data: {modelJsonStr: JSON.stringify(data)},
            url: url,
            success: function(result) {
                if (result.success) {
                    if ($(target).attr("data-form-id") == "edit-receive-address-info") {
                        layer.msg("编辑成功");
                        orderInformation.getReceiveAddressList();
                        $("#editorAddress").hide();
                    } else {
                        layer.msg("新增成功");
                        orderInformation.getReceiveAddressList();
                        $("#newAddress").hide();
                    }
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    },


    calGiftRule: function() {
        $(".good-sku-item").each(function(i, perLiItem) {
            var countInput = $(perLiItem).find(".product-count").find("input[name=number]");
            var count = $(perLiItem).find(".product-count").find("input[name=number]").val()
                ? Number($(perLiItem).find(".product-count").find("input[name=number]").val()) : 0;
            countInput.closest(".product-count").siblings(".gift").find("tr").each(function(i, perTr) {
                var condType = $(perTr).find("input[name=condType]").val();
                var condNum = $(perTr).find("input[name=condNum]").val() ? Number($(perTr).find("input[name=condNum]").val()) : 0;
                var giftNum = $(perTr).find(".gift-num").html() ? Number($(perTr).find(".gift-num").html()) : 0;
                var initNum = $(perTr).find("input[name=initNum]").val() ? Number($(perTr).find("input[name=initNum]").val()) : 0;
                if (condType === "one") {
                    if (count < condNum) { //限送1
                        $(perTr).hide();
                    } else {
                        $(perTr).show();
                    }
                } else if (condType === "mulriple") { //按倍数送 -- 赠品规则为买2送1，当购买数量为4和6等时，赠品数量才为2和3等
                    if (count < condNum) { //限送1
                        $(perTr).hide();
                    } else if (count % condNum == 0) {
                        $(perTr).show();
                        giftNum = parseInt(count/condNum);
                        $(perTr).find(".gift-num").html(giftNum);
                        console.log(giftNum);
                    }
                } else if (condType === "range") { //按区间送 --赠品规则为买4送2，倍数为买8送4，买12送6。当购买数量为1-4时，赠品数量为2；当购买数量为5-8时，赠品数量为4；当购买数量为9-12时，赠品数量为6
                    //console.log(((condNum * parseInt(count/condNum)) + 1) + "-"+ condNum * (parseInt(count/condNum) + 1))
                    var startNum = ((condNum * parseInt(count/condNum)) + 1);
                    var endNum = condNum * (parseInt(count/condNum) + 1);
                    if (startNum <= count && count <= endNum) {
                        giftNum = initNum * parseInt(endNum/condNum);
                        $(perTr).find(".gift-num").html(giftNum);
                        console.log(giftNum);
                    }
                } else if (condType === "overlay") { //按叠加送 --赠品规则为买2送1，当购买数量达到2时开始赠送对应数量的赠品数量1；当购买数量为3，4，5等时，赠品数量为2，3，4叠加上去
                    if (count < condNum) { //限送1
                        $(perTr).hide();
                    } else {
                        $(perTr).show();
                        giftNum = (count - condNum) + 1;
                        $(perTr).find(".gift-num").html(giftNum);
                    }

                }
            });

        });

        /*target.find("tr").each(function(i, perTr) {
            var condType = $(perTr).find("input[name=condType]").val();
            var condNum = $(perTr).find("input[name=condNum]").val() ? Number($(perTr).find("input[name=condNum]").val()) : 0;
            var giftNum = $(perTr).find(".gift-num").html() ? Number($(perTr).find(".gift-num").html()) : 0;
            var initNum = $(perTr).find("input[name=initNum]").val() ? Number($(perTr).find("input[name=initNum]").val()) : 0;
            if (condType === "one") {
                if (count < condNum) { //限送1
                    $(perTr).hide();
                } else {
                    $(perTr).show();
                }
            } else if (condType === "mulriple") { //按倍数送 -- 赠品规则为买2送1，当购买数量为4和6等时，赠品数量才为2和3等
                if (count < condNum) { //限送1
                    $(perTr).hide();
                } else if (count % condNum == 0) {
                    $(perTr).show();
                    giftNum = parseInt(count/condNum);
                    $(perTr).find(".gift-num").html(giftNum);
                    console.log(giftNum);
                }
            } else if (condType === "range") { //按区间送 --赠品规则为买4送2，倍数为买8送4，买12送6。当购买数量为1-4时，赠品数量为2；当购买数量为5-8时，赠品数量为4；当购买数量为9-12时，赠品数量为6
                //console.log(((condNum * parseInt(count/condNum)) + 1) + "-"+ condNum * (parseInt(count/condNum) + 1))
                var startNum = ((condNum * parseInt(count/condNum)) + 1);
                var endNum = condNum * (parseInt(count/condNum) + 1);
                if (startNum <= count && count <= endNum) {
                    giftNum = initNum * parseInt(endNum/condNum);
                    $(perTr).find(".gift-num").html(giftNum);
                    console.log(giftNum);
                }
            } else if (condType === "overlay") { //按叠加送 --赠品规则为买2送1，当购买数量达到2时开始赠送对应数量的赠品数量1；当购买数量为3，4，5等时，赠品数量为2，3，4叠加上去
                if (count < condNum) { //限送1
                    $(perTr).hide();
                } else {
                    $(perTr).show();
                    giftNum = (count - initNum);
                    $(perTr).find(".gift-num").html(giftNum);
                }

            }
        });*/
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
            $("#add-recevie-address-info")[0].reset(); //清空表单
            $("#add-recevie-address-info").find("#add-order-info-city-click").attr("data-provice", "");
            $("#add-recevie-address-info").find("#add-order-info-city-click").attr("data-city", "");
            $("#add-recevie-address-info").find("#add-order-info-city-click").attr("data-area", "");
            $("#add-recevie-address-info").find("input:hidden").val("");
            $("#add-recevie-address-info").find("#add-order-info-city-click").html("请选择省市区<i></i>");
            $("#newAddress").show();
            $("body, html").css("overflow","hidden");
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
            $("body, html").css("overflow","auto");
        });
        $("#newAddress").click(function(){
            $("#newAddress").hide();
            $("body,html").css("overflow","auto");
        });
    },

    openEditAddress: function(target) {
        var addressInfo = $(target).attr("data-address-info");
        if (!addressInfo) {
            return;
        }
        var addressInfoObj = $.parseJSON(addressInfo);
        $.form.load({
            formId: "#edit-receive-address-info",
            data: addressInfoObj
        });
        $("#edit-receive-address-info").find("#edit-order-info-city-click").html(addressInfoObj.provinceName + addressInfoObj.cityName + addressInfoObj.areaName + "<i></i>");
        $("#editorAddress").show();
    },

    /*编辑地址  弹窗*/
    editorAddress:function(){
        $("#editorAddress").hide();
        /*for(var i=0;i<$(".address-edit").length;i++){
            $("#editorAddress").hide();
            $(".address-edit").eq(i).click(function(ev){
                var ev = ev || event;
                ev.stopPropagation();
                $("#editorAddress").show();
            });
        };*/
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
        $("#orderAddress").css("max-height", minHeight);
        $(".address-up").hide();
        $(".address-down").show();
        $(".address-down").click(function() {
            $(this).hide();
            $(".address-up").show();
            $("#orderAddress").css("max-height", minHeight * len);
        });
        $(".address-up").click(function() {
            $(this).hide();
            $(".address-down").show();
            $("#orderAddress").css("max-height", minHeight);
        });
    },

    /*选择发票*/
    billSelect:function(){
        $(".bill-list").hide();
        $(".bill-category").hide();
        $(".bill-editor").hide();
        $(".bill-title").click(function(ev) {
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
                $("input[name=needInvoice]").val(0);
                $("input[name=invoiceName]").removeAttr("checked");
            } else {
                $("input[name=invoiceName]:eq(0)").attr("checked", "checked");
                $("input[name=invoiceName]:eq(0)").closest(".bill-change-category-group").addClass("bill-change-category-group-active").siblings().removeClass("bill-change-category-group-active");
                $("input[name=invoiceContent]").val("");
                $("input[name=needInvoice]").val(1);
                $(".bill-category").show();
                $(".bill-editor").show();
            }
            $(".bill-title").html(html + "<i></i>");
            $(".bill-list").hide();
        });
        $("body").click(function(){
            $(".bill-list").hide();
        });
    },

    selectedBill: function(ev) {
        var ev = ev || event;
        ev.stopPropagation();
        var invoiceType = $(".bill-change-category").find("input[name=invoiceName]:checked").val();
        $(".bill-category").attr("data-invoice-name", $(".bill-change-category").find("input[name=invoiceName]:checked").attr("data-value"));
        $(".bill-category").html(invoiceType);
        $("#billChange").hide();
        $("body,html").css("overflow","auto");
    },

    /*修改发票弹窗*/
    billChange:function(){
        $("#billChange").hide();
        $(".bill-editor").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $("#billChange").show();
            var invoiceName = $(".bill-category").attr("data-invoice-name");
            if (invoiceName != "person") {
                $(".invoice-company-css").removeClass("hide");
                $(".bill-change-wrap").css({"height": "300px"});
            } else {
                if (!$(".invoice-company-css").hasClass("hide")) {
                    $(".invoice-company-css").addClass("hide");
                }
                $(".bill-change-wrap").css({"height": "200px"});
                $("input[name=invoiceDutyParagraph]").val("");
                $("input[name=invoiceContent]").val("");
            }
            $("input[name=invoiceName][data-value="+ invoiceName +"]").attr("checked", "checked");
            $("input[name=invoiceName][data-value="+ invoiceName +"]").closest(".bill-change-category-group").addClass("bill-change-category-group-active").siblings().removeClass("bill-change-category-group-active");
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
        $(".bill-change-category-group").click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            var invoiceName = $(this).find("input[name=invoiceName]").attr("data-value");
            if (invoiceName == "company") {
                $(".invoice-company-css").removeClass("hide");
                $(".bill-change-wrap").css({"height": "300px"});
            } else {
                if (!$(".invoice-company-css").hasClass("hide")) {
                    $(".invoice-company-css").addClass("hide");
                }
                $(".bill-change-wrap").css({"height": "200px"});
                $("input[name=invoiceDutyParagraph]").val("");
                $("input[name=invoiceContent]").val("");
            }
            $(this).addClass("bill-change-category-group-active").siblings().removeClass("bill-change-category-group-active");
        });
    }

};
$(function(){
    orderInformation.init();
});