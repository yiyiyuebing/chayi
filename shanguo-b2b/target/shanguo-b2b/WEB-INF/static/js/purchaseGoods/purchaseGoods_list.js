/**
 * Created by Administrator on 2017/5/11.
 */
var  ProductList = {
    main: $(".product-list-body"),
    dataArray: [],
    init:function(){
        initNavBar("goods");
        ProductList.inputFn();
        ProductList.initSearchInput();
        ProductList.productSwitch($(".recommend-product-content-title").find("span"),$(".recommend-product-content-list-group"),"recommend-product-content-title-active");
        ProductList.productSwitch($(".recommend-product-news-title").find("span"),$(".recommend-product-news-content-group"),"news-title-active");
        ProductList.sectionSwitch();

        ProductList.plusFunction();
        ProductList.imgSelectFunction();
        ProductList.cancleFunction();
        ProductList.confirmFunction();
        ProductList.moreFunction();
        ProductList.footerBtnHover();
        ProductList.collapseFn($(".footer-more"),$(".footer-less"),$(".classic-body"));
        ProductList.initSearchBar();
        ProductList.brandIconHover();
        ProductList.initTopPage();
        ProductList.productListSort();
        ProductList.productListPriceRange();
        ProductList.initKKpage(purchaseGoodsQuery.pageNum, purchaseGoodsQuery.totalPageNo, purchaseGoodsQuery.totalRecods);
        ProductList.initCuXiaoInfo();
        //ProductList.getPurchaseGoodsAjaxList(); //加载数据
        ProductList.initPageTpl();


        var data = {
            goodList:[{goodSkuId:386507444951650304, amount: 3}],
            orderBizType:"purchase",
            orderType:"normal"
        }
        //ProductList.getGoodsPromotioinInfo();
        ProductList.initGoodsFavorite();
    },



    initGoodsFavorite:function() {
        var goodSkuIdList = [];
        ProductList.main.find(".product-list-content").find(".collect").each(function(i, perA) {
            goodSkuIdList.push($(perA).attr("data-id"));
        });

        $.ajax({
            type: "POST",
            data: {goodsSkuIdListStr: goodSkuIdList.join(",")},
            url: "/favorite/getFavoriteListByGoodsSkuId",
            success: function(result) {
                if (result.success && result.data) {
                    $.each(result.data, function(i, perFavorite) {
                        $(".collect-" + perFavorite.goodsId).addClass("active");
                    })
                }
            }
        });
    },

    addToFavorite: function(obj) {

        if (!$.checkLogin()) { //验证登录
            return;
        }

        var data = {
            goodsId:$(obj).attr("data-id"),
            goodsCount: 1,
            orderBizType: 'purchase'
        }
        if ($(obj).hasClass("active")) {
            $.ajax({    //取消收藏
                type: "POST",
                data: {modelJsonStr: JSON.stringify(data)},
                url: "/favorite/cancelFavorite",
                success: function(result) {
                    if (result.success) {
                        layer.msg("取消收藏");
                        $(obj).removeClass("active");
                        $(obj).attr("data-flag", "0");
                    } else {
                        layer.msg(result.msg);
                    }
                },
                error: function() {
                    layer.msg("收藏失败");
                }
            });
        } else { //添加收藏
            $.ajax({
                type: "POST",
                data: {modelJsonStr: JSON.stringify(data)},
                url: "/favorite/addToFavorite",
                success: function(result) {
                    if (result.success) {
                        layer.msg("收藏成功");
                        $(obj).attr("data-flag", "1");
                        $(obj).addClass("active");
                    } else {
                        layer.msg(result.msg);

                    }
                },
                error: function() {
                    layer.msg("收藏失败");
                }
            });
        }

    },

    getGoodsPromotioinInfo: function() {
        var goodList = [];
        ProductList.main.find(".product-list-content").find(".package").each(function(i, perA) {
            var baseGoods = {};
            baseGoods.goodId = $(perA).attr("data-id");
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
                if (result.success && result.data) {
                    $.each(result.data, function(i, perSkuInfo) {
                        if (perSkuInfo.activityType == "presell") {
                            $(".label-sku-"+ perSkuInfo.goodsSkuId +"").removeClass("hide");
                            $(".label-sku-"+ perSkuInfo.goodsSkuId +"").html("预售");
                            $(".package-sku-"+ perSkuInfo.goodsSkuId +"").hide();
                        }
                        if (perSkuInfo.activityType != "zengpin") {
                            if ($("input[name=shopId]").val()) {
                                $(".price-sku-"+ perSkuInfo.goodsSkuId +"").html("<i>供货价：</i>￥" + perSkuInfo.price);
                            } else {
                                $(".price-sku-"+ perSkuInfo.goodsSkuId +"").html("<i>供货价：</i>￥询价");
                            }

                        }
                    });
                }
            }
        });
    },

    /**
     * 页面模版初始化
     */
    initPageTpl: function() {
        var data = {
            classifyId: purchaseGoodsQuery.classifyId,
            orderBizType: "purchase",
            pageTplClassify: "pclist"
        };
        $.ajax({
            type: "POST",
            data: {modelJsonStr: JSON.stringify(data)},
            url: "/purchase/getPageTpl",
            success: function(result) {
                if (result.success && result.data && result.data.modelList) {
                    $.each(result.data.modelList, function(i, perModel) {

                        perModel.shopId = $("input[name=shopId]").val();

                        if (perModel.postCode == "ad") {

                            var adObj = $.parseJSON(perModel.adUrl);
                            perModel['adImgUrl'] = perModel.adImgList && perModel.adImgList.length > 0 ? perModel.adImgList[0].url : "";
                            if (adObj.type == "good") {
                                perModel.adUrl = "/purchase/detail/" + adObj.goodId + ".html";
                            } else {
                                perModel.adUrl = "/purchase/list?classifyId=" + adObj.id;;
                            }
                            if (perModel.adImgUrl) {
                                var tpl_img_ad = template("tpl-img-ad", perModel);
                                $(".product-aside").append($(tpl_img_ad));
                            }
                        }

                        if (perModel.postCode == "tsp") {
                            var tpl_top_page_tpl = template("tpl-top-page-tpl", perModel);
                            $(".product-hot").append($(tpl_top_page_tpl));
                        }

                        if (perModel.postCode == "sp") {
                            //if ($(".product-aside-wrap").length > 0) {
                            //
                            //} else {
                            var tpl_left_tpl = template("tpl-left-tpl", perModel);
                            $(".product-aside").append($(tpl_left_tpl));
                            //}
                        }
                        if (perModel.postCode == "dsp") {
                            var tpl_bottom_tpl_dsp = template("tpl-bottom-tpl-dsp", perModel);
                            $(".bottom-product-list").append($(tpl_bottom_tpl_dsp));
                        }
                    });
                }
            }
        });

    },

    /**
     * 初始化搜索框
     */
    initSearchInput: function() {
        if (purchaseGoodsQuery.purGoodsName) {
            $(".search-input").find("input").val(purchaseGoodsQuery.purGoodsName);
        }
    },

    /**
     * 初始化促销信息
     */
    initCuXiaoInfo: function() {
        $.ajax({
            type: "POST",
            async: true,
            url: "/purchase/cuxiaoList",
            success: function(result) {
                if (result.success) {
                    $(".promote-list").empty();
                    var tpl_promote_list_item = template("tpl-promote-list-item", result.data.baseArticleInfo);
                    $(".promote-list").append($(tpl_promote_list_item));
                }
            }
        });
    },

    /**
     * 初始化列表分页
     * @param pageNum
     * @param totalPageNo
     * @param totalRecods
     */
    initKKpage: function(pageNum, totalPageNo, totalRecods) {
        //生成分页控件
        kkpager.generPageHtml({
            pno : pageNum,
            mode : 'click', //可选，默认就是link
            //总页码
            total : totalPageNo,
            //总数据条数
            totalRecords : totalRecods,
            click : function(n){
                ProductList.kkScroll($(".product-wrap"));
                var queryParam = ProductList.getQueryParam();
                queryParam.pageNum = n;
                queryParam.pageSize = purchaseGoodsQuery.pageSize;
                console.log(ProductList.getQueryParam());
                var queryParamStr = ProductList.parseParam(queryParam);
                var pageHref = queryParamStr&& queryParamStr.length > 0 ? "/purchase/list?" + queryParamStr : "";
                history.pushState(history.state, "", pageHref);
                $(".product-header-paging").find(".this-page-num").html(n);
                ProductList.getPurchaseGoodsAjaxList(queryParam); //加载数据
                //处理完后可以手动条用selectPage进行页码选中切换
                this.selectPage(n);
            }
        }, true);

    },

    /**
     * 初始化头部分页
     */
    initTopPage: function() {
        var pageNum = $(".product-header-paging").find(".this-page-num").html();
        var preTopPageParam = purchaseGoodsQuery;


        $(".product-header-paging").find(".prev").click(function() {
            pageNum = $(".product-header-paging").find(".this-page-num").html();
            preTopPageParam.pageNum = Number(pageNum) - 1;
            if (nextTopPageParam.pageNum >= 1) {
                $(".product-header-paging").find(".this-page-num").html(preTopPageParam.pageNum);
                ProductList.getPurchaseGoodsAjaxList(preTopPageParam); //加载数据
                ProductList.initKKpage(preTopPageParam.pageNum, preTopPageParam.totalPageNo, preTopPageParam.totalRecods);
            }
            if (preTopPageParam.pageNum > 1) {
                $(".product-header-paging").find(".prev").removeClass("disable");

            } else {
                $(".product-header-paging").find(".prev").addClass("disable");
            }
            if (preTopPageParam.pageNum < purchaseGoodsQuery.totalPageNo) {
                $(".product-header-paging").find(".next").removeClass("disable");
            } else {
                $(".product-header-paging").find(".next").addClass("disable");
            }
        });

        if (preTopPageParam.pageNum > 1) {
            $(".product-header-paging").find(".prev").removeClass("disable");

        } else {
            $(".product-header-paging").find(".prev").addClass("disable");
        }

        var nextTopPageParam = purchaseGoodsQuery;
        nextTopPageParam.pageNum = Number(nextTopPageParam.pageNum);
        nextTopPageParam.totalPageNo = Number(nextTopPageParam.totalPageNo);

        $(".product-header-paging").find(".next").click(function() {
            pageNum = $(".product-header-paging").find(".this-page-num").html();
            nextTopPageParam.pageNum = Number(pageNum) + 1;

            if (nextTopPageParam.pageNum <= purchaseGoodsQuery.totalPageNo) {
                $(".product-header-paging").find(".this-page-num").html(nextTopPageParam.pageNum);
                ProductList.getPurchaseGoodsAjaxList(nextTopPageParam); //加载数据
                ProductList.initKKpage(nextTopPageParam.pageNum, nextTopPageParam.totalPageNo, nextTopPageParam.totalRecods);
            }

            if (nextTopPageParam.pageNum > 1) {
                $(".product-header-paging").find(".prev").removeClass("disable");

            } else {
                $(".product-header-paging").find(".prev").addClass("disable");
            }

            if (nextTopPageParam.pageNum < purchaseGoodsQuery.totalPageNo) {
                $(".product-header-paging").find(".next").removeClass("disable");
            } else {
                $(".product-header-paging").find(".next").addClass("disable");
            }
        });

        if (nextTopPageParam.pageNum < purchaseGoodsQuery.totalPageNo) {
            $(".product-header-paging").find(".next").removeClass("disable");
        } else {
            $(".product-header-paging").find(".next").addClass("disable");
        }

    },

    /**
     * 初始化查询栏头部按钮
     */
    initSearchBar: function() {
        var searchParam = purchaseGoodsQuery;
        $(".price-input-form").find("input:button").click(function() {
            if ($(".price-input-form").find("input[name=minPrice]").val()) {
                searchParam['minPrice'] = Number($(".price-input-form").find("input[name=minPrice]").val());
            } else {
                searchParam['minPrice'] = "";
            }
            if ($(".price-input-form").find("input[name=maxPrice]").val()) {
                searchParam['maxPrice'] = Number($(".price-input-form").find("input[name=maxPrice]").val());
            } else {
                searchParam['maxPrice'] = "";
            }
            ProductList.getPurchaseGoodsList(searchParam);
        });

        if (searchParam.classifyIds && searchParam.classifyIds.length > 0) {
            var classifyIdArr = searchParam.classifyIds.split(",");
            var classifyBar = "分类：";
            $.each(classifyIdArr, function(i, perClassifyId) {
                classifyBar += $(".classify-div").find("li").find("a[data-id="+ perClassifyId +"]").html() + ",";
            });
            if (classifyBar.indexOf(",") >= 0) {
                classifyBar = classifyBar.substring(0, classifyBar.length - 1);
            }
            $(".classic-header").find(".clear-condition").before($('<a class="pull-left select-condition" href="javascript:void(0);">'+ classifyBar +' <i data-type="classify"></i></a>'));
            $(".classic-header").find(".clear-condition").show();
        }

        if (searchParam.brandIds && searchParam.brandIds.length > 0) {
            var brandIdArr = searchParam.brandIds.split(",");
            var brandBar = "品牌：";
            $.each(brandIdArr, function(i, perBrandId) {
                brandBar += $(".brand-div").find("li").find("a[data-id="+ perBrandId +"]").find("span").html() + ",";
            });
            if (brandBar.indexOf(",") >= 0) {
                brandBar = brandBar.substring(0, brandBar.length - 1);
            }
            $(".classic-header").find(".clear-condition").before($('<a class="pull-left select-condition" href="javascript:void(0);">'+ brandBar +' <i data-type="brand"></i></a>'));
            $(".classic-header").find(".clear-condition").show();
        }

        if (searchParam.searchEv && searchParam.searchEv.length > 0) {
            var classifyAttrIdArr = searchParam.searchEv.split("|");
            $.each(classifyAttrIdArr, function(i, perAttrType) {
                var typeId = perAttrType.substring(0, perAttrType.indexOf(":"));
                var typeName = $(".attr-div").find("li").find("a[data-type-id="+ typeId +"]:first").attr("data-type-name");
                var typeArrs = perAttrType.substring(perAttrType.indexOf(":") + 1, perAttrType.length).split(",");
                var attrBar = "";
                $.each(typeArrs, function(j, perArr) {
                    attrBar += $(".attr-div").find("li").find("a[data-id="+ perArr +"]").html() + ",";
                });
                if (attrBar.length > 0) {
                    attrBar = attrBar.substring(0, attrBar.length - 1);
                }
                $(".classic-header").find(".clear-condition").before($('<a class="pull-left select-condition" href="javascript:void(0);">'+ typeName +'：'+ attrBar +' <i data-type="attr" data-id="'+ typeId +'"></i></a>'));
            });
            $(".classic-header").find(".clear-condition").show();
        }

        if (searchParam.minPrice || searchParam.maxPrice) {
            var priceBar = "价格：";
            if (searchParam.minPrice && searchParam.maxPrice) {
                priceBar += searchParam.minPrice + "-" + searchParam.maxPrice;
            } else if (searchParam.minPrice && !searchParam.maxPrice) {
                priceBar += searchParam.minPrice + "+";
            }
            $(".classic-header").find(".clear-condition").before($('<a class="pull-left select-condition" href="javascript:void(0);">'+ priceBar +' <i data-type="price"></i></a>'));
            $(".classic-header").find(".clear-condition").show();
        }


        $(".classic-header").find(".select-condition").find("i").click(function() {
            if ($(this).attr("data-type") == "classify") {
                searchParam['classifyIds'] = "";
            }
            if ($(this).attr("data-type") == "brand") {
                searchParam['brandIds'] = "";
            }

            if ($(this).attr("data-type") == "price") {
                searchParam['minPrice'] = "";
                searchParam['maxPrice'] = "";
            }

            if ($(this).attr("data-type") == "attr") {
                var classifyAttrIdArr = searchParam.searchEv.split("|");
                var searchEvStr = "";
                var thisObj = $(this);
                $.each(classifyAttrIdArr, function(i, perAttrType) {
                    var typeId = perAttrType.substring(0, perAttrType.indexOf(":"));
                    if (typeId != thisObj.attr("data-id")) {
                        searchEvStr += perAttrType + "|";
                    }
                });

                if (searchEvStr.length > 0) {
                    searchEvStr = searchEvStr.substring(0, searchEvStr.length - 1);
                }
                searchParam['searchEv'] = searchEvStr;
                searchParam['classifyAttrIds'] = "";
            }
            ProductList.getPurchaseGoodsList(searchParam);
        });
    },

    /**
     * 清空筛选
     */
    clearSearchParam: function() {

        var searchParam = purchaseGoodsQuery;
        searchParam.searchEv = "";
        searchParam.brandIds = "";
        searchParam.classifyIds = "";
        searchParam.classifyAttrIds = "";
        searchParam.minPrice = "";
        searchParam.maxPrice = "";
        $(".classic-header").find(".select-condition").remove();
        $(".classic-header").find(".clear-condition").hide();
        searchParam.pageNum = 1;
        ProductList.getPurchaseGoodsList(searchParam);
    },

    /**
     * 筛选条件-单选
     * @param obj
     */
    singleSelectSearchParam: function(obj) {
        var position = "#searchForm";
        var searchParam = purchaseGoodsQuery;
        if ($(obj).attr("data-type") == "attr") {

            var searchEv = searchParam.searchEv ? searchParam.searchEv : "";
            var searchAttrsArrys = searchEv.split("|");
            var searchAttrStr = "";
            //3434343:353423,5354334|756445:532534|23232:64534,7756
            $.each(searchAttrsArrys, function(i, perAttrs) {
                if (perAttrs.length > 0) {
                    if ($(obj).attr("data-type-id") != perAttrs.substring(0, perAttrs.indexOf(":"))) {
                        searchAttrStr += perAttrs + "|";
                    }
                }
            });
            searchAttrStr += $(obj).attr("data-type-id") + ":" + $(obj).attr("data-id");
            searchParam['searchEv'] = searchAttrStr;
        } else if($(obj).attr("data-type") == "price") {
            if ($(obj).attr("data-min-price")) {
                searchParam['minPrice'] = $(obj).attr("data-min-price");
            } else {
                searchParam['minPrice'] = "";
            }
            if ($(obj).attr("data-max-price")) {
                searchParam['maxPrice'] = $(obj).attr("data-max-price");
            } else {
                searchParam['maxPrice'] = "";
            }
        } else {
            searchParam[$(obj).attr("data-name")] = $(obj).attr("data-id");
        }
        searchParam.pageNum = 1;
        ProductList.getPurchaseGoodsList(searchParam, position);
    },

    /**
     * 筛选条件-多选
     * @param obj
     */
    multiSelectSearchParam: function(obj) {
        var position = "#searchForm";
        var searchParam = purchaseGoodsQuery;
        var type = $(obj).attr("data-type");
        if (type == "classsify") {
            var checkClassicInputArr = $(".classic-body").find(".classic-select-checkbox").find("input[name=classic]:checked");
            var classicIds = "";
            if (checkClassicInputArr.length > 1) {
                checkClassicInputArr.each(function(i, perClassicInput) {
                    classicIds += $(perClassicInput).val() + ",";
                });
                if (classicIds.length > 0) {
                    classicIds = classicIds.substring(0, classicIds.length -1);
                }
            } else {
                classicIds = checkClassicInputArr.val();
            }
            searchParam['classifyIds'] = classicIds;
            ProductList.getPurchaseGoodsList(searchParam, position);
        } else if (type == "brand") {

            var checkBrandInputArr = $(".classic-body").find(".classic-select-checkbox").find("input[name=brand]:checked");
            var brandIds = "";
            if (checkBrandInputArr.length > 1) {
                checkBrandInputArr.each(function(i, perBrandInput) {
                    brandIds += $(perBrandInput).val() + ",";
                });
                if (brandIds.length > 0) {
                    brandIds = brandIds.substring(0, brandIds.length -1);
                }
            } else {
                brandIds = checkBrandInputArr.val();
            }
            searchParam['brandIds'] = brandIds;
            searchParam.pageNum = 1;
            ProductList.getPurchaseGoodsList(searchParam, position);

        } else {

            var searchEv = searchParam.searchEv ? searchParam.searchEv : "";
            var searchAttrsArrys = searchEv.split("|");
            var searchAttrStr = "";
            //3434343:353423,5354334|756445:532534|23232:64534,7756
            $.each(searchAttrsArrys, function(i, perAttrs) {
                if (perAttrs.length > 0) {
                    if ($(obj).attr("data-type-id") != perAttrs.substring(0, perAttrs.indexOf(":"))) {
                        searchAttrStr += perAttrs + "|";
                    }
                }
            });

            var classifyAttrInputArr = $("."+ type).find(".classic-select-checkbox").find("input[name=attr]:checked");
            var classifyAttrIds = "";
            if (classifyAttrInputArr.length > 1) {
                classifyAttrInputArr.each(function(i, perClassicInput) {
                    classifyAttrIds += $(perClassicInput).val() + ",";
                });
                if (classifyAttrIds.length > 0) {
                    classifyAttrIds = classifyAttrIds.substring(0, classifyAttrIds.length -1);
                }
            } else {
                classifyAttrIds = classifyAttrInputArr.val();
            }
            //if (searchAttrStr.length > 0) {
            //    searchAttrStr += "|" + $(obj).attr("data-type-id") + ":" + classifyAttrIds;
            //} else {
                searchAttrStr += $(obj).attr("data-type-id") + ":" + classifyAttrIds;
            //}

            searchParam['searchEv'] = searchAttrStr;
            searchParam.pageNum = 1;
            ProductList.getPurchaseGoodsList(searchParam, position);
        }

    },





    getQueryParam: function() {
        var queryParam = ProductList.getProductListPriceRange();
        if (!queryParam) {
            queryParam = {};
        }
        $.extend(true, queryParam, ProductList.getProductListSort());



        return queryParam;
    },

    /**
     * 排序查询
     */
    productListSort: function() {

        if (purchaseGoodsQuery.orderIndex) {
            $(".product-list-order").find(".pull-left").removeClass("active");
            $(".product-list-order").find(".pull-left").find("i").removeClass("desc");
            $(".product-list-order").find(".pull-left").find("i").removeClass("asc");
            $(".product-list-order").find(".pull-left[data-order-type=orderIndex]").addClass("active");
            if (purchaseGoodsQuery.orderIndex == "1") {
                $(".product-list-order").find(".pull-left[data-order-type=orderIndex]").find("i").addClass("asc");
                $(".product-list-order").find(".pull-left[data-order-type=orderIndex]").attr("data-order", "asc");
            } else {
                $(".product-list-order").find(".pull-left[data-order-type=orderIndex]").find("i").addClass("desc");
                $(".product-list-order").find(".pull-left[data-order-type=orderIndex]").attr("data-order", "desc");
            }
        }
        if (purchaseGoodsQuery.saleNumSort) {
            $(".product-list-order").find(".pull-left").removeClass("active");
            $(".product-list-order").find(".pull-left").find("i").removeClass("desc");
            $(".product-list-order").find(".pull-left").find("i").removeClass("asc");
            $(".product-list-order").find(".pull-left[data-order-type=saleNumSort]").addClass("active");
            if (purchaseGoodsQuery.saleNumSort == "1") {
                $(".product-list-order").find(".pull-left[data-order-type=saleNumSort]").find("i").addClass("asc");
                $(".product-list-order").find(".pull-left[data-order-type=saleNumSort]").attr("data-order", "asc");
            } else {
                $(".product-list-order").find(".pull-left[data-order-type=saleNumSort]").find("i").addClass("desc");
                $(".product-list-order").find(".pull-left[data-order-type=saleNumSort]").attr("data-order", "desc");
            }
        }
        if (purchaseGoodsQuery.createTimeSort) {
            $(".product-list-order").find(".pull-left").removeClass("active");
            $(".product-list-order").find(".pull-left").find("i").removeClass("desc");
            $(".product-list-order").find(".pull-left").find("i").removeClass("asc");
            $(".product-list-order").find(".pull-left[data-order-type=createTimeSort]").addClass("active");
            if (purchaseGoodsQuery.createTimeSort == "1") {
                $(".product-list-order").find(".pull-left[data-order-type=createTimeSort]").find("i").addClass("asc");
                $(".product-list-order").find(".pull-left[data-order-type=createTimeSort]").attr("data-order", "asc");
            } else {
                $(".product-list-order").find(".pull-left[data-order-type=createTimeSort]").find("i").addClass("desc");
                $(".product-list-order").find(".pull-left[data-order-type=createTimeSort]").attr("data-order", "desc");
            }
        }
        if (purchaseGoodsQuery.priceSort) {
            $(".product-list-order").find(".pull-left").removeClass("active");
            $(".product-list-order").find(".pull-left").find("i").removeClass("desc");
            $(".product-list-order").find(".pull-left").find("i").removeClass("asc");
            $(".product-list-order").find(".pull-left[data-order-type=priceSort]").addClass("active");
            if (purchaseGoodsQuery.priceSort == "1") {
                $(".product-list-order").find(".pull-left[data-order-type=priceSort]").find("i").addClass("asc");
                $(".product-list-order").find(".pull-left[data-order-type=priceSort]").attr("data-order", "asc");
            } else {
                $(".product-list-order").find(".pull-left[data-order-type=priceSort]").find("i").addClass("desc");
                $(".product-list-order").find(".pull-left[data-order-type=priceSort]").attr("data-order", "desc");
            }
        }

        $(".product-list-order").find(".pull-left").click(function() {

            var queryParam = purchaseGoodsQuery;

            var aObj = $(this);
            if (aObj.hasClass("active")) {
                if (aObj.attr("data-order") == "asc") {
                    aObj.find("i").removeClass("asc");
                    aObj.find("i").addClass("desc");
                    aObj.attr("data-order", "desc");
                } else {
                    aObj.find("i").removeClass("desc");
                    aObj.find("i").addClass("asc");
                    aObj.attr("data-order", "asc");
                }
            } else {
                $(".product-list-order").find(".pull-left").each(function(i, perA) {
                    $(perA).attr("data-order", "");
                });
                $(".product-list-order").find(".pull-left").removeClass("active");
                $(".product-list-order").find(".pull-left").find("i").removeClass("asc");
                $(".product-list-order").find(".pull-left").find("i").removeClass("desc");
                aObj.addClass("active");
                aObj.find("i").addClass("asc");
                aObj.attr("data-order", "asc");
            }

            queryParam.orderIndex = "";
            queryParam.saleNumSort = "";
            queryParam.createTimeSort = "";
            queryParam.priceSort = "";

            if (aObj.attr("data-order-type") == "orderIndex") {
                queryParam.orderIndex = aObj.attr("data-order") != "desc"? "1" : "0"
            }
            if (aObj.attr("data-order-type") == "saleNumSort") {
                queryParam.saleNumSort = aObj.attr("data-order") != "desc"? "1" : "0"
            }
            if (aObj.attr("data-order-type") == "createTimeSort") {
                queryParam.createTimeSort = aObj.attr("data-order") != "desc"? "1" : "0"
            }
            if (aObj.attr("data-order-type") == "priceSort") {
                queryParam.priceSort = aObj.attr("data-order") != "desc"? "1" : "0"
            }
            queryParam.pageNum = 1;
            queryParam.pageSize = 32;

            ProductList.getPurchaseGoodsAjaxList(queryParam);
        });
    },

    getProductListSort: function() {
        var queryParam = purchaseGoodsQuery;
        if($(".product-list-order").find(".pull-left[data-order=desc]")) {
            if ($(".product-list-order").find(".pull-left[data-order=desc]").attr("data-order-type") == "orderIndex") {
                queryParam.orderIndex = "1";
            }
            if ($(".product-list-order").find(".pull-left[data-order=desc]").attr("data-order-type") == "saleNumSort") {
                queryParam.saleNumSort = "1";
            }
            if ($(".product-list-order").find(".pull-left[data-order=desc]").attr("data-order-type") == "createTimeSort") {
                queryParam.createTimeSort = "1";
            }
            if ($(".product-list-order").find(".pull-left[data-order=desc]").attr("data-order-type") == "priceSort") {
                queryParam.priceSort = "1";
            }
        }
        if($(".product-list-order").find(".pull-left[data-order=asc]")) {
            if ($(".product-list-order").find(".pull-left[data-order=asc]").attr("data-order-type") == "orderIndex") {
                queryParam.orderIndex = "0";
            }
            if ($(".product-list-order").find(".pull-left[data-order=asc]").attr("data-order-type") == "saleNumSort") {
                queryParam.saleNumSort = "0";
            }
            if ($(".product-list-order").find(".pull-left[data-order=asc]").attr("data-order-type") == "createTimeSort") {
                queryParam.createTimeSort = "0";
            }
            if ($(".product-list-order").find(".pull-left[data-order=asc]").attr("data-order-type") == "priceSort") {
                queryParam.priceSort = "0";
            }
        }
        return queryParam;
    },

    /**
     * 查询价格范围
     */
    productListPriceRange: function() {

        if (purchaseGoodsQuery.minPrice) {
            $(".price-range-form").find("input[name=minPrice]").val(purchaseGoodsQuery.minPrice);
        }
        if (purchaseGoodsQuery.maxPrice) {
            $(".price-range-form").find("input[name=maxPrice]").val(purchaseGoodsQuery.maxPrice);
        }

        $(".price-btn").click(function() {
            var queryParam = ProductList.getProductListSort();
            var minPrice = $(".price-range-form").find("input[name=minPrice]").val();
            var maxPrice = $(".price-range-form").find("input[name=maxPrice]").val();
            if (minPrice && minPrice.length > 0) {
                queryParam.minPrice = minPrice;
            }
            if (maxPrice && maxPrice.length > 0) {
                queryParam.maxPrice = maxPrice;
            }
            queryParam.pageNum = 1;
            queryParam.pageSize = 32;
            var position = "#J_main";
            ProductList.getPurchaseGoodsList(queryParam, position);
        });
    },
    /**
     * 获取查询价格范围
     */
    getProductListPriceRange: function() {
        var queryParam = {};
        var minPrice = $(".price-range-form").find("input[name=minPrice]").val();
        var maxPrice = $(".price-range-form").find("input[name=maxPrice]").val();
        if (minPrice && minPrice.length > 0) {
            queryParam.minPrice = minPrice;
        }
        if (maxPrice && maxPrice.length > 0) {
            queryParam.maxPrice = maxPrice;
        }
        return queryParam;
    },

    getPurchaseGoodsList: function(queryParam, position) {
        if (!position) {
            position = "";
        }

        var queryParamStr = $.jsonToGetParamstr(queryParam);
        window.location.href = queryParamStr&& queryParamStr.length > 0 ? "/purchase/list?" + queryParamStr + position : "";
    },


    parseParam: function(param, key) {
        var paramStr = "";
        if (param instanceof String || param instanceof Number || param instanceof Boolean) {
            if (encodeURIComponent(param) && encodeURIComponent(param).length > 0) {
                paramStr += "&" + key + "=" + encodeURIComponent(param);
            }
        } else {
            $.each(param, function(i) {
                var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
                if (this && (this instanceof String && this.length > 0) || this instanceof Number || this instanceof Boolean) {
                    paramStr += '&' + ProductList.parseParam(this, k);
                }
            });
        }
        return paramStr.substr(1);
    },

    getPurchaseGoodsAjaxList: function(queryParam) {
        var ii = layer.load();
        var queryParamStr = $.jsonToGetParamstr(queryParam);
        var href = queryParamStr&& queryParamStr.length > 0 ? "/purchase/list?" + queryParamStr : "";
        history.pushState(history.state, "", href);
        $.ajax({
            type: "POST",
            data: queryParam,
            async: true,
            url: "/purchase/purchaseGoodsList",
            success: function(result) {
                layer.close(ii);
                if (result.success && result.data.purchaseGoods.purchaseGoodsVos) {
                    $(".product-list-content").empty();
                    if (result.data.purchaseGoods.purchaseGoodsVos.length > 0) {
                        $("#kkpager").show();
                        $.each(result.data.purchaseGoods.purchaseGoodsVos, function(i, purchaseGoods) {
                            if (!purchaseGoods.saleNum) {
                                purchaseGoods.saleNum = 0;
                            }
                            if (purchaseGoods.saleNum >= 10000 && purchaseGoods.saleNum < 100000) {
                                purchaseGoods.saleNum = (Number(purchaseGoods.saleNum) / 10000).toFixed(1) + "万+";
                            } else if (purchaseGoods.saleNum >= 100000) {
                                purchaseGoods.saleNum = parseInt(Number(purchaseGoods.saleNum) / 10000) + "万+";
                            }
                            purchaseGoods.shopId = $("input[name=shopId]").val();
                            var nav_product_aside_group = template("product-list-pull-left", purchaseGoods);
                            $(".product-list-content").append($(nav_product_aside_group));
                        });
                    } else {
                        $("#kkpager").hide();
                    }
                } else {
                    layer.msg("加载失败");
                }
                ProductList.initGoodsFavorite();
                //ProductList.getGoodsPromotioinInfo() //异步查询活动信息
            }
        });


    },

    /**
     * 加入购物车
     */
    addGoodToCart: function(obj) {

        if (!$.checkLogin()) { //验证登录
            return;
        }
        var data = {
            goodsId:$(obj).attr("data-id"),
            goodsCount: 1,
            orderBizType: 'purchase'
        }
        var goods = [data];
        $.ajax({
            type: "POST",
            data: {modelJsonStr: JSON.stringify(goods)},
            url: "/cart/addGoodsToCart",
            success: function(result) {
                if (result.success) {
                    layer.msg("加入购物车成功");
                    AsideBar.cartCount();
                } else {
                    layer.msg(result.msg);
                }
            },
            error: function() {
                layer.msg("加入购物车失败");
            }
        });
    },

    /*商品类别切换*/
    productSwitch:function(obj,target,className){
        target.eq(0).show().siblings().hide();
        obj.hover(function(){
            $(this).addClass(className).siblings().removeClass(className);
            target.eq($(this).index()).show().siblings().hide();
            target.eq($(this).index()).hover(function(){
                $(this).show();
                obj.eq($(this).index()).addClass(className).siblings().removeClass(className);
            },function(){
                $(this).hide();
                target.eq(0).show().siblings().hide();
                obj.eq(0).addClass(className).siblings().removeClass(className);
            });
        },function(){
            $(this).removeClass(className);
            obj.eq(0).addClass(className).siblings().removeClass(className);
        })
    },
    /*section商品切换*/
    sectionSwitch:function(){
        for(var i = 0;i<$(".section").length;i++){
            Index.productSwitch($(".section").eq(i).find(".section-right-title").find("span"),$(".section").eq(i).find(".section-right-content-group"),"section-right-active");
        }
    },
    inputFn:function() {
        $("input[type=checkbox]").change(function(){
            if($(this).prop("checked")){
                $(this).closest('i').addClass("active");
            }else{
                $(this).closest('i').removeClass("active");
            }
        });
    },
    /*多选按钮切换*/
    plusFunction:function(){
        $(".plus").click(function(){

            ProductList.dataArray = [];

            $(".classic-body-group").removeClass("classic-body-group-active"); //移除其他多选的激活状态
            $(".classic-btn-group").siblings(".classic-content-select").find(".classic-select-checkbox-wrap").hide().siblings(".classic-select-radio").show();
            $(".classic-btn-group").find(".plus").show();

            $(".classic-select-radio").each(function(i, perRadio) {
                if ($(perRadio).find("li").length > 10) {
                    $(perRadio).closest(".classic-content-select").siblings(".classic-btn-group").find(".more").show();
                }
            });

            $(".classic-select-checkbox-wrap").find("input:checked").each(function(i, perCheckedInput) {
                $(perCheckedInput).parent("i").removeClass("active");
                $(perCheckedInput).removeAttr("checked");
            });
            $(".classic-select-checkbox-btn").find("input:button").attr("disabled", "disabled");
            $(".classic-select-checkbox-btn").find("input:button").removeClass("active");
            $(".classic-select-checkbox-imgBox").find("li").removeClass("active");
            $(".classic-select-checkbox-imgBox").find("li").removeAttr("style");
            $(".classic-select-checkbox-imgBox").find("li").find("img").removeAttr("style");
            $(".classic-select-checkbox-imgBox").siblings("form").find(".classic-select-checkbox").empty();

            $(this).siblings(".more").hide();
            $(this).closest(".classic-btn-group").siblings(".classic-content-select").find(".classic-select-checkbox-wrap").show().siblings(".classic-select-radio").hide();
            $(this).closest(".classic-body-group").addClass("classic-body-group-active");
            $(this).hide();

        });
    },
    /*更多按钮功能*/
    moreFunction:function(){
        /*初始化UL的高度*/
        for(var i = 0 ;i < $(".classic-select-radio").length;i++){
            $(".classic-select-radio").eq(i).height($(".classic-select-radio").eq(i).find("li").outerHeight(true));
        };
        for(var i = 0;i<$(".classic-btn-group").length;i++){
            /*获取数据长度*/
            var n = $(".classic-btn-group").eq(i).siblings('.classic-content-select').find(".classic-select-radio").find("li").length;
            console.log(n);
            if(n <= 10){
                $(".classic-btn-group").eq(i).find(".more").hide();
            }
        };
        $(".more").click(function(){
            /*获取单个li高度*/
            var oLi = $(this).closest(".classic-btn-group").siblings(".classic-content-select").find(".classic-select-radio").find("li");
            var oUl = $(this).closest(".classic-btn-group").siblings(".classic-content-select").find(".classic-select-radio");
            /*计算行数*/
            var n = Math.ceil( oLi.length / 10 );
            if(parseInt( oLi.outerHeight(true) ) == parseInt(oUl.outerHeight(true)) ){
                oUl.height(n*oLi.outerHeight(true));
                $(this).html("<i class='up'></i>收起");
            }else{
                oUl.height(oLi.outerHeight(true));
                $(this).html("<i></i>更多");
            }
        });
    },
    /*图片多选*/
    imgSelectFunction:function(){

        var html;
        $(".classic-select-checkbox-imgBox").find("li").click(function(){
            if($(this).hasClass("active")){
                $(this).removeClass("active");
                /*获取关联的input-name数据(这个字段一定不一样)*/
                var inputValue = $(this).find(".mask-img").attr('data-input-value');
                for(var i = 0;i<ProductList.dataArray.length;i++){
                    if(ProductList.dataArray[i].inputValue == inputValue){
                        /*找出已经存在的json,从数组中移除*/
                        ProductList.dataArray.splice(i,1);
                    }
                };
                /*重置HTML*/
                if(ProductList.dataArray.length > 0){
                    html = '<li class="pull-left">已经选条件：</li>';
                }else{
                    html='';
                    $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox-btn").find('input').removeClass("active");
                    $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox-btn").find('input').attr("disabled",true);
                }
                for(var i=0;i<ProductList.dataArray.length;i++){
                    html +=  '<li class="pull-left"><label><i class="active"><input checked="checked" type="checkbox" name="'+ProductList.dataArray[i].category+'" value="'+ProductList.dataArray[i].inputValue+'"></i>'+ProductList.dataArray[i].name+'</label></li>';
                };
                $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox").html($(html));
            }else{
                /*重置HTML*/
                html = '<li class="pull-left">已经选条件：</li>';
                $(this).addClass("active");
                /*获取关联name数据*/
                var name = $(this).find(".mask-img").attr("data-name");
                /*获取关联的category数据*/
                var category = $(this).find(".mask-img").attr('data-category');
                /*获取关联的input-name数据*/
                var inputValue = $(this).find(".mask-img").attr('data-input-value');
                /*获取初始状态状态下ul的innerHTML*/
                /*存到对象中*/
                var json = {name:name, category:category, inputValue:inputValue};
                /*添加到数组中*/
                ProductList.dataArray.push(json);
                for(var i=0; i < ProductList.dataArray.length; i++) {
                    html +=  '<li class="pull-left"><label><i class="active"><input checked="checked" type="checkbox" name="'+ProductList.dataArray[i].category+'" value="'+ProductList.dataArray[i].inputValue+'"></i>'+ProductList.dataArray[i].name+'</label></li>';
                };
                $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox").html($(html));
                $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox-btn").find('input').addClass("active");
                $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox-btn").find('input').attr("disabled",false);
            }
        });
    },
    /*取消按钮的事件*/
    cancleFunction:function(){
        $(".classic-select-checkbox-btn").find("span").click(function() {
            ProductList.dataArray = [];
            $(this).closest(".classic-select-checkbox-wrap").find("input:checked").each(function(i, perChecked) {
                $(perChecked).parent("i").removeClass("active");
                $(perChecked).removeAttr("checked");
            });

            $(this).closest(".classic-select-checkbox-wrap").hide().siblings(".classic-select-radio").show();
            var n = $(this).closest(".classic-select-checkbox-wrap").siblings('.classic-select-radio').find('li').length;
            if(n>10){
                $(this).closest(".classic-content-select").siblings(".classic-btn-group").find(".more").show();
            }
            $(this).closest(".classic-body-group").removeClass('classic-body-group-active');
            $(".plus").show();
        });
    },
    /*多选中确认按钮事件*/
    confirmFunction:function(){
        $(".classic-select-checkbox").find('input').change(function(){
            var li = $(this).closest('.classic-select-checkbox').find('li');
            var onOff = false;
            for(var i=0;i<li.length;i++){
                if( li.eq(i).find("i").hasClass('active') ){
                    onOff = true;
                    break;
                }else{
                    onOff = false;
                }
            };
            console.log(onOff);
            if(onOff){
                $(this).closest('.classic-select-checkbox').siblings('.classic-select-checkbox-btn').find('input').addClass('active');
                $(this).closest('.classic-select-checkbox').siblings('.classic-select-checkbox-btn').find('input').attr("disabled",false);
            }else{
                $(this).closest('.classic-select-checkbox').siblings('.classic-select-checkbox-btn').find('input').removeClass('active');
                $(this).closest('.classic-select-checkbox').siblings('.classic-select-checkbox-btn').find('input').attr("disabled",true);
            }
        });
    },
    /*品牌鼠标进过效果*/
    brandIconHover:function(){
        $("#brandCategory").find(".img-box").hover(function(){
            $(this).find("img").hide();
            $(this).closest("li").css("border","2px solid #cc9b4b");
        },function(){
            $(this).find("img").show();
            $(this).closest("li").css("border","1px solid #dbdbdb");
        });
        $("#brandCategory").find(".link-img").hover(function(){
            $(this).find("img").hide();
            $(this).closest("li").css("border","2px solid #cc9b4b");
        },function(){
            $(this).find("img").show();
            $(this).closest("li").css("border","1px solid #dbdbdb");
        });
    },
    /*更多选项样式变化*/
    footerBtnHover:function(){
        $(".classic-footer-btn").hover(function(){
            $(this).closest(".classic-footer").addClass("classic-footer-active");
        },function(){
            $(this).closest(".classic-footer").removeClass("classic-footer-active");
        });
    },
    /*更多选项  和收起交互*/
    collapseFn:function(obj1,obj2,target){
        /*设置开关*/
        var onOff = true;
        /*初始化高度*/
        var height = 0;
        /*只显示4组*/
        for(var i=0;i<4;i++){
            height += parseInt( target.find(".classic-body-group").eq(i).outerHeight(true) );
        };
        target.css("height",height);

        $(".more").click(function(){
            if(onOff){
                /*重置height的值*/
                height = 0;
                for(var i=0;i<4;i++){
                    height += parseInt( target.find(".classic-body-group").eq(i).outerHeight(true) );
                };
                target.css("height",height);
            }
        });
        $(".plus").click(function(){
            if(onOff){
                /*重置height的值*/
                height = 0;
                for(var i=0;i<4;i++){
                    height += parseInt( target.find(".classic-body-group").eq(i).outerHeight(true) );
                };
                target.css("height",height);
            }
        });

        obj1.click(function(){
            target.css("height","auto");
            $(this).hide();
            obj2.show();
            onOff = false;
        });
        obj2.click(function(){
            /*重置height的值*/
            height = 0;
            /*获取target子集的长度*/
            var len = target.find(".classic-body-group").length;
            for(var i=0;i<4;i++){
                height += parseInt( target.find(".classic-body-group").eq(i).outerHeight(true) );
            };
            target.css("height",height);
            $(this).hide();
            obj1.show();
            onOff = true;
        });
    },
    /*点击分页按钮  页面滚动*/
    kkScroll:function(target){
        var scrollTop = target.offset().top;
        $(window).scrollTop(scrollTop);
    }
};

$(function(){
    ProductList.init();
});