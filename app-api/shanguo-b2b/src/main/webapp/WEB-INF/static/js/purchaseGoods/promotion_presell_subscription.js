/**
 * Created by Administrator on 2017/5/11.
 */
var  PurchaseGoodsDetail = {
    main: $(".product-presell-detail"),
    init:function(){


        var activityStartDate = 0;
        var activityEndDate = 0;
        if (precellActivityEnd) {
            activityEndDate = new Date(precellActivityEnd).getTime();
            setInterval(PurchaseGoodsDetail.countDownTime, 0);
        }
        if (precellActivityStart) {
            activityStartDate = new Date(precellActivityStart).getTime();
            setInterval(PurchaseGoodsDetail.countDownTime1, 0);
        }

        var nowDate = new Date().getTime();

        if (activityStartDate > nowDate) {
            $(".presell-close").removeClass("hide");
            if ($(".presell-normal").hasClass("hide")) {
                $(".presell-normal").addClass("hide");
            }
        } else {
            $(".presell-normal").removeClass("hide");
            if ($(".presell-close").hasClass("hide")) {
                $(".presell-close").addClass("hide");
            }
        }

        //if (!$.checkLogin()) { //验证登录
        //    return;
        //}

        //PurchaseGoodsDetail.navFn($(".nav-product"));
        //PurchaseGoodsDetail.swicthFn($(".nav-product-aside-group"),$(".nav-product-content-group"),"nav-product-aside-active");
        PurchaseGoodsDetail.swiperZoom();
        PurchaseGoodsDetail.zoomFunction();
        PurchaseGoodsDetail.clickShowFn($(".favorable-content-title"),$(".favorable-datalist"),'up');
        PurchaseGoodsDetail.chooseDestination();
        PurchaseGoodsDetail.amountFn();
        PurchaseGoodsDetail.textLimit();
        PurchaseGoodsDetail.shareSwiper();
        PurchaseGoodsDetail.shareImgFn();
        PurchaseGoodsDetail.initSkuInput();
        PurchaseGoodsDetail.shareToFn();
        PurchaseGoodsDetail.popupFn();
        PurchaseGoodsDetail.recommendSwitch();
        PurchaseGoodsDetail.recommendStar();
        PurchaseGoodsDetail.initPageTpl();
        $.initCitySelect({
            selectId: PurchaseGoodsDetail.main.find("#precell-detal-location-select"),
            clickId: PurchaseGoodsDetail.main.find("#precell-detal-location-choose"),
            selectArea: PurchaseGoodsDetail.showCarriageOptions
        });
        PurchaseGoodsDetail.showCarriageOptions();
        PurchaseGoodsDetail.purchaseGoodsDetail();
        PurchaseGoodsDetail.purchaseGoodsEvaluationPageInfo();
        PurchaseGoodsDetail.initPurchaseEvaluation();
        PurchaseGoodsDetail.getGoodsPromotioinInfo();
        PurchaseGoodsDetail.initGoodsFavorite();
        PurchaseGoodsDetail.presellAgreement();



    },


    changeNum: function(target, num, operator) {
        var changeGoodNumQuery = {};
        changeGoodNumQuery.nowNum = $(target).siblings(".select-count").eq(0).val();
        changeGoodNumQuery.skuId = $(target).siblings(".select-count").eq(0).attr("data-sku-id");
        changeGoodNumQuery.operation = operator;
        $.ajax({
            type: "POST",
            data: {queryJson: JSON.stringify(changeGoodNumQuery)},
            url: "/purchase/changeGoodNum",
            success: function(result) {
                if (result.success) {
                    $(target).siblings(".select-count").eq(0).val(result.data);
                    if ($("input[name=shopId]").val()) {
                        PurchaseGoodsDetail.calGoodsPrice(target, result.data);
                    }
                } else {
                    if (operator == "sub") {
                        $(target).siblings(".select-count").eq(0).val(0);
                    } else {
                        layer.msg(result.msg);
                    }
                }
            }
        });
    },


    calGoodsPrice: function(target, num) {
        var result;
        var orderData = {};
        var orderList = [];
        $(target).siblings(".select-count").each(function(i, perInput) {
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
                    target.parents("td").siblings(".sku-supply-price").html(price.toFixed(2) + "元");
                } else {
                    layer.msg(result.msg);
                }
            }
        });


    },


    addToFavorite: function(obj) {

        if (!$.checkLogin()) { //验证登录
            return;
        }

        var data = {
            goodsId:purchaseGoodsId,
            goodsCount: 1,
            orderBizType: 'purchase'
        }

        if ($(obj).find("i").hasClass("active")) {
            $.ajax({
                type: "POST",
                data: {modelJsonStr: JSON.stringify(data)},
                url: "/favorite/cancelFavorite",
                success: function(result) {
                    if (result.success) {
                        layer.msg("取消收藏");
                        var count = $(".favorite-count").html() ? parseInt($(".favorite-count").html()) : 0;
                        $(".favorite-count").html(count - 1);
                        $(obj).find("i").removeClass("active");
                        $(obj).find("i").closest("span").css({color: ''});
                        $(obj).find("i").siblings("span").css({color: ''});
                    } else {
                        layer.msg(result.msg);

                    }
                },
                error: function() {
                    layer.msg("收藏失败");
                }
            });

        } else {
            $.ajax({
                type: "POST",
                data: {modelJsonStr: JSON.stringify(data)},
                url: "/favorite/addToFavorite",
                success: function(result) {
                    if (result.success) {
                        layer.msg(result.msg);
                        var count = $(".favorite-count").html() ? parseInt($(".favorite-count").html()) : 0;
                        $(".favorite-count").html(count + 1);
                        $(obj).find("i").addClass("active");
                        $(obj).find("i").closest("span").css({color: '#e32a32'});
                        $(obj).find("i").siblings("span").css({color: '#e32a32'});
                    } else {
                        layer.msg(result.msg);

                    }
                },
                error: function() {
                    layer.msg("收藏失败");
                }
            });
        }

        /*$.ajax({
            type: "POST",
            data: {modelJsonStr: JSON.stringify(data)},
            url: "/favorite/addToFavorite",
            success: function(result) {
                if (result.success) {
                    layer.msg(result.msg);
                    if ($(obj).hasClass("active")) {
                        var count = $(".favorite-count").html() ? parseInt($(".favorite-count").html()) : 0;
                        $(".favorite-count").html(count - 1);
                        $(obj).removeClass("active");
                    } else {
                        var count = $(".favorite-count").html() ? parseInt($(".favorite-count").html()) : 0;
                        $(".favorite-count").html(count + 1);
                        $(obj).addClass("active");
                    }
                } else {
                    layer.msg(result.msg);

                }
            },
            error: function() {
                layer.msg("收藏失败");
            }
        });*/
    },

    initGoodsFavorite:function() {
        var goodSkuIdList = [purchaseGoodsId];
        $.ajax({
            type: "POST",
            data: {goodsSkuIdListStr: goodSkuIdList.join(",")},
            url: "/favorite/getFavoriteListByGoodsSkuId",
            success: function(result) {
                if (result.success && result.data && result.data.length > 0) {
                    $(".product-collect-" + result.data[0].goodsId).addClass("active");
                    /* $.each(result.data, function(i, perFavorite) {
                     $(".collect-" + perFavorite.goodsId).addClass("active");
                     })*/
                }
            }
        });

        $.ajax({
            type: "POST",
            data: {modelJsonStr: JSON.stringify({goodsId:purchaseGoodsId})},
            url: "/favorite/getFavoriteAllCount",
            success: function(result) {
                if (result.success) {
                    $(".favorite-count").html(result.counts);
                }
            }
        });
    },


    createPreOrder: function() {

        if (!$.checkLogin()) { //验证登录
            return;
        }

        var data = {};
        var itemList = [];
        $(".purchase-goods-sku-list").find("input").each(function(i, perInput) {
            var item = {};
            if ($(perInput).val() && parseInt($(perInput).val()) > 0) {
                item.purGoodsSkuId = $(perInput).attr("data-sku-id");
                item.number = $(perInput).val();
                item.isSample = "0";
                item.purGoodsId = $(perInput).attr("data-good-id");
                itemList.push(item);
            }
        });

        if (itemList.length <= 0) {
            layer.msg("请确认数量");
            return ;
        }
        data.orderListVos = itemList;
        data.typeName = "presell";
        $.ajax({
            type: "POST",
            data: {orderJson: JSON.stringify(data)},
            url: "/order/createPreOrder",
            success: function(result) {
                console.log(result);
                if (result.success) {
                    window.location.href = "/order/info/" + result.data.orderKey;
                } else {
                    layer.msg(result.msg)
                }
            }
        });
    },



    getGoodsPromotioinInfo: function() {
        var goodList = [];
        PurchaseGoodsDetail.main.find(".purchase-goods-sku-list").find("input").each(function(i, perA) {
            var baseGoods = {};
            baseGoods.goodId = $(perA).attr("data-good-id");
            baseGoods.goodSkuId = $(perA).attr("data-sku-id");
            baseGoods.amount = $(perA).attr("data-sku-price");
            goodList.push(baseGoods);
        });

        var data = {
            goodList: goodList,
            orderBizType: "purchase",
            orderType: "normal"
        };
        $.ajax({
            type: "POST",
            data: {modelJsonStr: JSON.stringify(data)},
            url: "/purchase/getPromotionPriceInfo",
            success: function(result) {
                console.log(result);
                if (result.success) {

                }
            }
        });
    },


    /**
     * 初始化sku数量选择验证
     */
    initSkuInput: function() {
        $(".purchase-goods-sku-list").find("input").on("keyup", function() {
            var maxNum = $(this).attr("data-max-num") ? parseInt($(this).attr("data-max-num")) : 0;
            var limitFlt = $(this).attr("data-limt-flag");
            var limitNum = $(this).attr("data-limt-num") ? parseInt($(this).attr("data-limt-num")) : 0;
            var value = $(this).val();
            if (!value) { //非数字，则归为0
                value = 0;
                $(this).val(value);
                return;
            }
            value = parseInt(value);
            if (limitFlt == "T") {
                if (limitNum < maxNum) {
                    if (value >= limitNum) {
                        value = limitNum;
                        $(this).val(value);
                        return;
                    }
                } else {
                    if (value >= maxNum) {  //大于0的数值，并大于最大数，则设置为最大数
                        value = maxNum;
                        $(this).val(value);
                        return;
                    }
                }
            }

            if (value >= maxNum) {  //大于0的数值，并大于最大数，则设置为最大数
                value = maxNum;
                $(this).val(value);
                return;
            }

        });

    },

    /**
     * 初始化页面模版
     */
    initPageTpl: function() {

        var skuIds = "";
        $(".size-content").find("tr").each(function(i, perSku) {
            skuIds += $(perSku).attr("data-sku-id") + ",";
        });

        if (skuIds.length > 0) {
            skuIds = skuIds.substring(0, skuIds.length - 1);
        }
        var data = {
            classifyId: groupId,
            goodSkuId: skuIds,
            orderBizType: "purchase",
            pageTplClassify: "pcdetail"
        };
        $.ajax({
            type: "POST",
            data: {modelJsonStr: JSON.stringify(data)},
            url: "/purchase/getPageTpl",
            success: function(result) {
                if (result.success && result.data.modelList) {
                    $.each(result.data.modelList, function(i, perModel) {
                        perModel.shopId = $("input[name=shopId]").val();
                        console.log(perModel);
                        //$(".center-xqad").hide();
                        //$(".bottom-xqad").hide();
                        if (perModel.postCode == "ad") {
                            var adObj = $.parseJSON(perModel.adUrl);
                            if (adObj.type == "good") {
                                perModel.adUrl = "/purchase/detail/" + adObj.goodId + ".html";
                            } else if (adObj.type == "classify") {
                                perModel.adUrl = "/purchase/list?classifyId=" + adObj.id;
                            } else if (adObj.type == "diy") {
                                if (perModel.linkDescribe) {
                                    perModel.adUrl = perModel.linkDescribe;
                                } else {
                                    perModel.adUrl = "#";
                                }
                            } else {
                                perModel.adUrl = "#";
                            }

                            perModel['adImgUrl'] = perModel.adImgList && perModel.adImgList.length > 0 ? perModel.adImgList[0].url : "";


                            if (perModel.adImgUrl) {
                                var tpl_img_ad = template("tpl-left-ad", perModel);
                                $(".product-intro").find(".intro-aside").append($(tpl_img_ad));
                            }
                        }

                        if (perModel.postCode == "xqad") {
                            if ($(".intro-content").find(".intro-content-group").find(".center-xqad").children().length <= 0) {
                                $(".center-xqad").removeClass("hide");
                                var tpl_info_banner_1 = template("tpl-info-banner-1", perModel);
                                $(".intro-content").find(".intro-content-group").find(".center-xqad").append($(tpl_info_banner_1));
                            } else {
                                $(".bottom-xqad").removeClass("hide");
                                var tpl_info_banner_2 = template("tpl-info-banner-2", perModel);
                                $(".intro-content").find(".intro-content-group").find(".bottom-xqad").append($(tpl_info_banner_2));
                            }
                        }

                        if (perModel.postCode == "sp") {
                            var tpl_left_tpl = template("tpl-left-product-list", perModel);
                            $(".product-intro").find(".intro-aside").append($(tpl_left_tpl));
                        }

                         if (perModel.postCode == "dsp") {
                             $(".bottom-product-list").removeClass("hide");
                             var tpl_bottom_tpl_dsp = template("tpl-bottom-tpl-dsp", perModel);
                             $(".bottom-product-list").append($(tpl_bottom_tpl_dsp));
                         }
                    });
                }
            }
        });

    },


    /**
     * 切换地区获取运费信息
     * @param province
     * @param city
     * @param area
     */
    showCarriageOptions: function(province, city, area) {
        if (!province) {
            var provinceCode = $.getCookie("province_code") ? $.getCookie("province_code") : "350000";
            var province = $.get(provinceCode);
            var provinceName = province.name;
            var cityName;
            var areaName;
            if (province.children.length > 0) {
                cityName = province.children[0].name;
                if (province.children[0].children.length > 0) {
                    areaName =  province.children[0].children[0].name;
                    $.setArea({
                        selectId: PurchaseGoodsDetail.main.find("#precell-detal-location-select"),
                        clickId: PurchaseGoodsDetail.main.find("#precell-detal-location-choose"),
                        province: provinceName,
                        city: cityName,
                        area:areaName
                    });
                    PurchaseGoodsDetail.getCarriageInfo(cityName, areaName);
                }
            }

        } else {
            PurchaseGoodsDetail.getCarriageInfo(city, area);
        }
    },

    /**
     * 获取运费信息
     * @param city
     * @param area
     */
    getCarriageInfo: function(city, area) {

        var tradeContext = {
            "city": city,
            "area": area
        }

        var skuList = [];
        PurchaseGoodsDetail.main.find(".purchase-goods-sku-list").find("tr").each(function(i, perSkuTr) {
            var perSku = {
                purGoodsSkuId: $(perSkuTr).attr("data-sku-id"),
                finalAmount: $(perSkuTr).attr("data-sku-price"),
                number: 1,
                sSample: "0"
            };
            skuList.push(perSku);
        });

        $.ajax({
            type: "POST",
            data: {listJson: JSON.stringify(skuList), tradeContextJson: JSON.stringify(tradeContext)},
            url: "/feright/showCarriageOptions",
            success: function(result) {
                if (result.success && result.data.fvo) {
                    var fvo = result.data.fvo;
                    if (fvo.servicerList) {
                        var freightTplInfo = fvo.servicerList[0];
                        PurchaseGoodsDetail.main.find(".express-cost").html(freightTplInfo.servicerName + "<mark>¥"+ freightTplInfo.totalFreight +"</mark>");
                    }

                }
            }
        });
    },

    /**
     * 异步获取商品详情信息
     */
    purchaseGoodsDetail: function() {
        $.ajax({
            type: "POST",
            data: {goodsId: purchaseGoodsId},
            url: "/purchase/goodDetail",
            success: function(result) {
                if (result.success && result.data.goods) {
                    var goods = result.data.goods;
                    if (goods.cargoBasePropertysList) {
                        var j = 0;
                        $.each(goods.cargoBasePropertysList, function(i, perProperty) {
                            if (perProperty.pvalue) {
                                $(".intro-section").find(".intro-info").append($('<li class="pull-left">' + perProperty.pname + '：' + perProperty.pvalue + '</li>'));
                            }
                        });
                        if (j == 0) {
                            $(".intro-info").hide();
                        }
                    } else {
                        $(".intro-info").hide();
                    }
                    if (goods.pcDetailInfo) {
                        $(".intro-section").find(".product-info-img").empty();
                        $(".intro-section").find(".product-info-img").html(goods.pcDetailInfo);
                    } else {
                        $(".intro-section").find(".product-info-img").empty();
                        var detailImags = goods.detailImages
                        && goods.detailImages.images
                        && goods.detailImages.images.length > 0 ? goods.detailImages.images : [];
                        $.each(detailImags, function(i, perImg) {
                            $(".intro-section").find(".product-info-img").append($('<img src="'+ perImg.url +'">'));
                        });
                    }

                }
            }
        });

    },


    /**
     * 商品评论数量相关
     */
    purchaseGoodsEvaluationPageInfo: function (){
        $.ajax({
            type: "POST",
            data: {goodsId: purchaseGoodsId},
            async: false,
            url: "/purchase/purchaseEvaluationPageInfo",
            success: function(result) {
                if (result.success && result.data.evaluationPageInfo) {
                    console.log(result.data.evaluationPageInfo);
                    var evaluationPageInfo = result.data.evaluationPageInfo;
                    if (evaluationPageInfo.avgScore) {

                        $(".product-recommend-star").find(".star-mask").css("width", Number(evaluationPageInfo.avgScore)/5 * 100 + "%");
                        /*
                         if (evaluationPageInfo.avgScore > 0 && evaluationPageInfo.avgScore < 1) {
                         $(".product-recommend-star").append('<i class="recommend-star recommend-star01 pull-left"></i>');
                         $(".sailsVolume-content").find(".star").addClass("star01");
                         } else if (evaluationPageInfo.avgScore > 1 && evaluationPageInfo.avgScore < 2) {
                         $(".product-recommend-star").append('<i class="recommend-star recommend-star02 pull-left"></i>');
                         $(".sailsVolume-content").find(".star").addClass("star02");
                         } else if (evaluationPageInfo.avgScore > 2 && evaluationPageInfo.avgScore < 3) {
                         $(".product-recommend-star").append('<i class="recommend-star recommend-star03 pull-left"></i>');
                         $(".sailsVolume-content").find(".star").addClass("star03");
                         } else if (evaluationPageInfo.avgScore > 3 && evaluationPageInfo.avgScore < 4) {
                         $(".product-recommend-star").append('<i class="recommend-star recommend-star04 pull-left"></i>');
                         $(".sailsVolume-content").find(".star").addClass("star04");
                         } else if (evaluationPageInfo.avgScore > 4 && evaluationPageInfo.avgScore < 5) {
                         $(".product-recommend-star").append('<i class="recommend-star recommend-star05 pull-left"></i>');
                         $(".sailsVolume-content").find(".star").addClass("star05");
                         }*/
                    }

                    $("#avgScore").html(evaluationPageInfo.avgScore? evaluationPageInfo.avgScore : 0);
                    $("#totalCount").html(evaluationPageInfo.totalCount? evaluationPageInfo.totalCount : 0);
                    $(".intro-title").find(".intro-evaluat-count").html(evaluationPageInfo.totalCount? "评论（" + evaluationPageInfo.totalCount + "）" : "评论（0）");
                    $(".sailsVolume-content").find(".evaluate-total-count").find("mark").html(evaluationPageInfo.totalCount? evaluationPageInfo.totalCount : 0);
                    $("#goodCount").html(evaluationPageInfo.goodCount? evaluationPageInfo.goodCount : 0);
                    $("#imageCount").html(evaluationPageInfo.imageCount? evaluationPageInfo.imageCount : 0);
                    $("#middleCount").html(evaluationPageInfo.middleCount? evaluationPageInfo.middleCount : 0);
                    $("#badCount").html(evaluationPageInfo.badCount? evaluationPageInfo.badCount : 0);
                }
            }
        });
    },

    /**
     * 初始化商品评论
     */
    initPurchaseEvaluation: function() {
        var evaluationType = $(".recommend-info-title").find(".recommend-info-title-active").attr("data-type");
        var totalCounts = $(".recommend-info-title").find(".recommend-info-title-active").find("span").html();
        totalCounts = totalCounts? Number(totalCounts): 0;
        var pageSize = 10;
        var totalPageNo = totalCounts % pageSize > 0 ? (totalCounts / pageSize) + 1: totalCounts / pageSize;
        var pageNum = 1;
        PurchaseGoodsDetail.purchaseGoodsEvaluationList(evaluationType, pageNum, totalPageNo, totalCounts);
        PurchaseGoodsDetail.initKKpage(pageNum, totalPageNo, totalCounts, evaluationType);
        $(".recommend-info-title").find("a").click(function() {
            $(".recommend-info-title").find("a").removeClass("recommend-info-title-active");
            $(this).addClass("recommend-info-title-active");
            evaluationType = $(this).attr("data-type");
            totalCounts = $(this).find("span").html();
            totalCounts = totalCounts? Number(totalCounts): 0;
            totalPageNo = totalCounts % pageSize > 0 ? (totalCounts / pageSize) + 1: totalCounts / pageSize;
            PurchaseGoodsDetail.purchaseGoodsEvaluationList(evaluationType, pageNum, totalPageNo, totalCounts);
            PurchaseGoodsDetail.initKKpage(pageNum, totalPageNo, totalCounts, evaluationType);
        });
    },

    /**
     * 倒计时计算
     * @param time
     * @returns {string}
     * setInterval(countDownTime, 0);
     */
    countDownTime: function() {
        var EndTime = new Date(precellActivityEnd);
        var StartTime = new Date(precellActivityStart);
        var NowTime = new Date();
        var t =EndTime.getTime() - NowTime.getTime();
        var t1 = StartTime.getTime() - NowTime.getTime();
        var d=0;
        var h=0;
        var m=0;
        var s=0;

        if(t1 <= 0 && t >= 0) {
            d=Math.floor(t/1000/60/60/24);
            h=Math.floor(t/1000/60/60%24);
            m=Math.floor(t/1000/60%60);
            s=Math.floor(t/1000%60);
        }
        PurchaseGoodsDetail.main.find(".activity-end-time").html(d +"天"+ h +"时"+ m +"分"+ s +"秒");
    },


    /**
     * 倒计时计算
     * @param time
     * @returns {string}
     * setInterval(countDownTime, 0);
     */
    countDownTime1: function() {
        var EndTime = new Date(precellActivityStart);
        var NowTime = new Date();
        var t =EndTime.getTime() - NowTime.getTime();
        var d=0;
        var h=0;
        var m=0;
        var s=0;
        if(t>=0){
            d=Math.floor(t/1000/60/60/24);
            h=Math.floor(t/1000/60/60%24);
            m=Math.floor(t/1000/60%60);
            s=Math.floor(t/1000%60);
        }
        PurchaseGoodsDetail.main.find(".activity-start-time").html(d +"天"+ h +"时"+ m +"分"+ s +"秒");
    },

    /**
     * 商品评价列表数据
     * @param type
     * @param pageNum
     */
    purchaseGoodsEvaluationList: function(type, pageNum) {

        $.ajax({
            type: "POST",
            data: {goodsId: purchaseGoodsId, type: type, pageNum: pageNum, pageSize: 10},
            url: "/purchase/purchaseEvaluationPageList",
            success: function(result) {
                if (result.success ) {
                    $(".recommend-info-content-list").empty();
                    if (result.data.evaluationPageList) {
                        $.each(result.data.evaluationPageList, function(i, perEvaluation) {
                            var evaluateTime = perEvaluation.evaluateTime ? new Date(perEvaluation.evaluateTime) : "";
                            perEvaluation.evaluateTime = $.formatDate("yyyy-MM-dd HH:mm:ss", evaluateTime);
                            var tpl_details_evaluat = template("tpl-details-evaluat", perEvaluation);
                            $(".recommend-info-content-list").append(tpl_details_evaluat);
                        });
                    }
                    PurchaseGoodsDetail.recommandImgSwtich();
                }
            }
        });

    },

    /**
     * 初始化分页
     * @param pageNum
     * @param totalPageNo
     * @param totalRecods
     * @param evaluationType
     */
    initKKpage: function(pageNum, totalPageNo, totalRecods, evaluationType) {
        //生成分页控件
        kkpager.generPageHtml({
            pno : pageNum,
            mode : 'click', //可选，默认就是link
            //总页码
            total : totalPageNo,
            //总数据条数
            totalRecords : totalRecods,
            click : function(n){
                PurchaseGoodsDetail.purchaseGoodsEvaluationList(evaluationType, n);
                //处理完后可以手动条用selectPage进行页码选中切换
                this.selectPage(n);
            }
        }, true);

    },



    /*放大镜效果*/
     zoomFunction:function(){
        $('.jqzoom').jqzoom({
            zoomType: 'standard',
            lens:true,
            preloadImages: true,
            alwaysOn:false,
            zoomWidth:500,
            zoomHeight:500
        });
     },
    /*swiper*/
    swiperZoom:function(){
        var mySwiper = new Swiper('.product-swiper .swiper-container',{
            autoPlay:3000,
            slidesPerView : 5
        });
        $('.swiper-btn-prev').on('click', function(e){
            e.preventDefault();
            mySwiper.swipePrev();
        });
        $('.swiper-btn-next').on('click', function(e){
            e.preventDefault();
            mySwiper.swipeNext();
        });
    },
    clickShowFn:function(obj,target,classname){
        target.hide();
        obj.click(function(){
            if($(this).find("i").hasClass(classname)){
                target.hide();
                $(this).find("i").removeClass(classname);
            }else{
                target.show();
                $(this).find("i").addClass(classname);
            }
        });
    },
    /*选择地址*/
    chooseDestination:function(){
        /*初始化状态*/
        $(".express-choose-datalist").hide();
        $(".province-title").show().siblings().hide();
        $(".location-content").find(".province").show().siblings().hide();
        /*地址选择按钮*/
        $("#locationChoose").click(function(){
            if($(this).hasClass('location-choose-active')){
                $(".express-choose-datalist").hide();
                $(this).removeClass("location-choose-active");
            }else{
                $(".express-choose-datalist").show();
                $(this).addClass("location-choose-active");
            }
        });
        /*关闭按钮*/
        $("#expressClose").click(function(){
            $(".express-choose-datalist").hide();
            $("#locationChoose").removeClass("active");
        });
        /*省  市  区  选择点击时间*/
        var province;
        var city;
        var area;
        PurchaseGoodsDetail.siblingsHide($("#provinceTitle"));
        PurchaseGoodsDetail.siblingsHide($("#cityTitle"));
        /*省列表点击*/
        for(var i=0;i<$("#province").find("li").length;i++){
            $("#province").find("li").eq(i).find('span').click(function(){
                /*获取省份信息*/
                province = $(this).html();
                $(".province-title").html( province + '<i></i>' );
                $("#cityTitle").html('请选择' + '<i></i>');
                $(".city-title").show();
                $(".city-title").addClass("active").siblings().removeClass("active");
                $("#city").show();
                console.log(province);
                $("#province").hide();
                $("#city").show().siblings().hide();
                for(var i = 0 ;i<$("#city").find("ul").length;i++){
                    if($("#city").find("ul").eq(i).attr("data-category-province") == province){
                        $("#city").find("ul").eq(i).show();
                    }else {
                        $("#city").find("ul").eq(i).hide();
                    }
                }
            });
        };
        /*市列表点击*/
        for(var i=0;i<$("#city").find("li").length;i++){
            $("#city").find("li").eq(i).find('span').click(function(){
                /*获取城市信息*/
                city = $(this).html();
                $(".city-title").html(city + '<i></i>');
                $(".area-title").show();
                $(".area-title").addClass("active").siblings().removeClass("active");
                $("#area").show();
                console.log(city);
                $("#city").hide();
                $("#area").show().siblings().hide();
                for(var i = 0 ;i<$("#area").find("ul").length;i++){
                    if($("#area").find("ul").eq(i).attr("data-category-city") == city){
                        $("#area").find("ul").eq(i).show();
                    }else {
                        $("#area").find("ul").eq(i).hide();
                    }
                }
            });
        };
        /*区域列表点击*/
        for(var i=0;i<$("#area").find("li").length;i++){
            $("#area").find("li").eq(i).find('span').click(function(){
                /*获取区域信息*/
                area = $(this).html();
                $(".location-choose").html(province + city +area + '<i></i>');
                $(".express-choose-datalist").hide();
                $("#locationChoose").removeClass("location-choose-active");
            });
        };
        /*头部点击*/
        $("#provinceTitle").click(function(){
            $("#province").show().siblings().hide();
            $(this).html("请选择<i></i>");
            $(this).addClass("active").siblings().removeClass("active");
        });
        $("#cityTitle").click(function(){
            $("#city").show().siblings().hide();
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
    /*数量加减*/
    amountFn:function() {
        $(".subtraction").click(function(){
            var count = parseInt( $(this).siblings('input').val() );

            PurchaseGoodsDetail.changeNum($(this), count, "sub");
            /*var startNum = parseInt($(this).siblings('input').attr("data-sku-start-num") ? $(this).siblings('input').attr("data-sku-start-num") : 0);
            var mulNumFlag = $(this).siblings('input').attr("data-sku-numflag") && $(this).siblings('input').attr("data-sku-numflag") == "T" ? true : false;
            if(count > startNum){
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
            $(this).siblings('input').val(count);*/
        });
        $(".plus").click(function() {
            //var maxNum = $(this).attr("data-max-num") ? parseInt($(this).attr("data-max-num")) : 0;
            //var limitNum = $(this).attr("data-limt-num") ? parseInt($(this).attr("data-limt-num")) : 0;
            //var limitFlag = $(this).attr("data-limt-flag");
            //if (!maxNum) {
            //    maxNum = 0;
            //}
            var count = parseInt($(this).siblings('input').val());
            PurchaseGoodsDetail.changeNum($(this), count, "add");
            /*var startNum = parseInt($(this).siblings('input').attr("data-sku-start-num") ? $(this).siblings('input').attr("data-sku-start-num") : 0);
            var mulNumFlag = $(this).siblings('input').attr("data-sku-numflag") && $(this).siblings('input').attr("data-sku-numflag") == "T" ? true : false;

            if (mulNumFlag) {
                count += startNum;
            } else {
                count++;
            }

            if (limitFlag == "T") {
                if (limitNum < maxNum) {
                    if (count < limitNum) {
                        $(this).siblings('input').val(count);
                        return;
                    } else {
                        $(this).siblings('input').val(limitNum);
                        return;
                    }
                } else {
                    if (count < maxNum) {
                        $(this).siblings('input').val(count);
                        $(this).siblings(".subtraction").removeClass("disabled");
                        return;
                    } else if (count == maxNum) {
                        $(this).siblings('input').val(count);
                        $(this).addClass("disabled");
                        return;
                    } else {
                        $(this).addClass("disabled");
                        return;
                    }
                }
            }


            if (count < maxNum) {
                $(this).siblings('input').val(count);
                $(this).siblings(".subtraction").removeClass("disabled");
            } else if (count == maxNum) {
                $(this).siblings('input').val(count);
                $(this).addClass("disabled");
            } else {
                $(this).addClass("disabled");
            }*/
        });
    },
    /*分享框字数限制*/
    textLimit:function(){
        /*获取最大输入值*/
        var maxLength = parseInt( $(".number-limit").html() );
        $("#shareTxt").bind("propertychange input",function(){
            var len = $(this).val().length;
            var txt = $(this).val();
            if(maxLength >= len) {
                $(".number-limit").html(maxLength - len);
            }else{
                $(".number-limit").html(0);
                $(this).val(txt.substring(0,maxLength));
            }
        });
    },
    /*分享swiper*/
    shareSwiper:function(){
        var mySwiper = new Swiper('.share-swiper',{
            autoPlay:3000,
            slidesPerView : 4
        });
        $('.share-img-prev').on('click', function(e){
            e.preventDefault();
            mySwiper.swipePrev();
        });
        $('.share-img-next').on('click', function(e){
            e.preventDefault();
            mySwiper.swipeNext();
        });
    },
    /*选择分享图片*/
    shareImgFn:function(){
        /*初始化*/
        $("#shareImg").find(".swiper-slide").eq(0).find("input[type=radio]").attr("checked","checked");
        $("#shareImg").find(".swiper-slide").eq(0).find(".share-img-box").addClass("share-img-active");
        $("#shareImg").find("label").click(function(){
            if($(this).find("input[type=radio]").attr("checked")){
                for(var i=0;i<$(".share-img-box").length;i++){
                    $(".share-img-box").eq(i).removeClass("share-img-active");
                }
                $(this).find(".share-img-box").addClass("share-img-active");
            }else{
                $(this).find(".share-img-box").removeClass("share-img-active");
            }
        });
    },
    /*分享到*/
    shareToFn:function(){
        $("#shareTo").find("label").click(function(){
            if($(this).find("input[type=checkbox]").attr("checked")){
                $(this).find(".share-input-mask").addClass("share-to-active");
            }else{
                $(this).find(".share-input-mask").removeClass("share-to-active");
            }
        });
    },
    /*分享弹窗*/
    popupFn:function(){
        $("#popup").hide();
        $("#shareBtn").click(function(ev){
            var ev = event || ev;
            ev.stopPropagation();
            $("#popup").show();
            $("body,html").css({
                "height":"100%",
                "overflow":"hidden"
            });
        });
        $(".popup-box").click(function(ev){
            var ev = event || ev;
            ev.stopPropagation();
            $("#popup").show();
            $("body,html").css({
                "height":"100%",
                "overflow":"hidden"
            });
        });
        $("#popClose").click(function(ev){
            var ev = event || ev;
            ev.stopPropagation();
            $("#popup").hide();
            $("body,html").css({
                "height":"auto",
                "overflow":"auto"
            });
        });
        $("body").click(function(){
            $("#popup").hide();
            $("body,html").css({
                "height":"auto",
                "overflow":"auto"
            });
        })
    },
    /*产品评论切换*/
    recommendSwitch:function(){
        /*获取导航条距离顶部的距离*/
        var titleTop = $("#introTitle").offset().top;
        /*获取导航条的高度|*/
        var titleHeight = $("#introTitle").outerHeight(true);
        /*获取数组*/
        $(window).scroll(function(){
            var windowScroll = $(this).scrollTop();
            if(windowScroll >= titleTop){
                $("#introTitle").addClass("fixed");
                $(".intro-recommend").css("marginBottom",titleHeight+20);
            }else{
                $("#introTitle").removeClass("fixed");
                $(".intro-recommend").css("marginBottom",20);
            }
        });
        $(".intro-content-group").last().hide().siblings().show();
        for(var i=0;i<$(".intro-content-group").length-1;i++){
            $("#introTitle").find("span").click(function(){
                $(window).scrollTop(titleTop);
                $(this).addClass("intro-title-active").siblings().removeClass("intro-title-active");
                if($(this).index() == 0){
                    $(".intro-content-group").eq(0).show().siblings().hide();
                    $(".intro-content-group").eq(1).show();
                }else{
                    $(".intro-content-group").eq($(this).index()).show().siblings().hide();
                }
            });
        }
    },
    /*星级评价JS*/
    recommendStar:function(){
        for(var i=0;i<$(".star-wrap").length;i++){
            var dataStar =  $(".star-wrap").eq(i).find(".recommend-star05").attr("data-star");
            if(dataStar < 0){
                dataStar = 0;
            }else if(dataStar > 5){
                dataStar = 5;
            }
            var rate = dataStar / 5;
            $(".star-wrap").eq(i).find(".star-mask").width(rate*100 + "%");

        }
    },
    /*预约协议*/
    presellAgreement:function(){
        $(".presell-state-btn").click(function(){
            if($(this).hasClass("active")){
                $(".presell-state-content").stop().slideUp();
                $(this).removeClass("active");
            }else{
                $(".presell-state-content").stop().slideDown();
                $(this).addClass("active");
            }
        });
    },
    /*评论查看大图*/
    recommandImgSwtich:function(){

        $(".recommend-img-list").find("li").on("click", function () {
            var index = $(this).closest(".recommend-img-list").find("li").index($(this));
            var arrlen = $(this).closest(".recommend-img-list").find("li").length;
            console.log(index);
            console.log(arrlen);
            /*重置样式*/
            $(this).siblings("li").removeClass("active");
            /*添加样式*/
            $(this).addClass("active");
            $(this).closest(".recommend-img-list").siblings(".bigImgWrap").show();
            $(this).closest(".recommend-img-list").siblings(".bigImgWrap").find(".imgPrev").show();
            $(this).closest(".recommend-img-list").siblings(".bigImgWrap").find(".imgNext").show();
            var url = $(this).find("img").attr("src");
            $(this).closest(".recommend-img-list").siblings(".bigImgWrap").find(".showImg").attr("src", url);
            if(index == 0){
                $(this).closest(".recommend-img-list").siblings(".bigImgWrap").find(".imgPrev").hide();
            }
            if(index == arrlen - 1){
                $(this).closest(".recommend-img-list").siblings(".bigImgWrap").find(".imgNext").hide();
            }
        })


        $(".imgHide").on("click",function(){
            $(this).closest(".bigImgWrap").hide();
            $(this).closest(".bigImgWrap").siblings(".recommend-img-list").find("li").removeClass("active");
        })

        $(".imgPrev").on("click",function(){
            $(this).siblings(".imgNext").show();
            var activeIndex = PurchaseGoodsDetail.getActiveIndex( $(this) );
            var ImgArrLength = PurchaseGoodsDetail.getImgArrLength( $(this) );
            $(this).closest(".bigImgWrap").siblings(".recommend-img-list").find("li").removeClass("active");
            $(this).closest(".bigImgWrap").siblings(".recommend-img-list").find("li").eq(activeIndex - 1).addClass("active");
            var url = $(this).closest(".bigImgWrap").siblings(".recommend-img-list").find("li").eq(activeIndex - 1).find("img").attr("src");
            $(this).siblings(".showImg").attr("src",url);
            if(activeIndex == 1){
                $(this).hide();
            }
        });

        $(".imgNext").on("click",function(){
            $(this).siblings(".imgPrev").show();
            var activeIndex = PurchaseGoodsDetail.getActiveIndex( $(this) );
            var ImgArrLength = PurchaseGoodsDetail.getImgArrLength( $(this) );
            $(this).closest(".bigImgWrap").siblings(".recommend-img-list").find("li").removeClass("active");
            $(this).closest(".bigImgWrap").siblings(".recommend-img-list").find("li").eq(activeIndex + 1).addClass("active");
            var url = $(this).closest(".bigImgWrap").siblings(".recommend-img-list").find("li").eq(activeIndex + 1).find("img").attr("src");
            $(this).siblings(".showImg").attr("src",url);
            if(activeIndex == ImgArrLength-2){
                $(this).hide();
            }

        });
    },
    /*获取当前激活的图片的索引值*/
    getActiveIndex:function(obj){
        var smallBox = obj.closest(".bigImgWrap").siblings(".recommend-img-list");
        var activeIndex = 0;
        for(var i = 0;i<smallBox.find("li").length;i++){
            if(smallBox.find("li").eq(i).is(".active")){
                activeIndex = smallBox.find("li").eq(i).index();
                break;
            }
        }
        return activeIndex;
    },
    /*获取图片数组的长度*/
    getImgArrLength:function(obj){
        var ImgArrLength = obj.closest(".bigImgWrap").siblings(".recommend-img-list").find("li").length;
        return ImgArrLength;
    }

};

$(function(){
    PurchaseGoodsDetail.init();
});