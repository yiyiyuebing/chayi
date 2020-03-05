/**
 * Created by Administrator on 2017/5/9.
 */
var Index = {
    init:function(){
        Index.swiper();
        Index.initIndexArticle();
        Index.initRecommendProductContent();
        Index.initIndexShowLanmu();
    },

    initIndexArticle: function() {
        $.ajax({
            type: "POST",
            url: "/index/indexOnlineInfoList",
            success: function(result) {
                if (result.success && result.data.baseArticleInfos) {
                    var baseArticleInfos = {baseArticleInfos: result.data.baseArticleInfos};
                    $(".recommend-product-news-title").empty();
                    var newsTitleTpl = template("tpl-news-title", baseArticleInfos);
                    $(".recommend-product-news-title").append($(newsTitleTpl));


                    $(".recommend-product-news-content").empty();
                    var newsContentTpl = template("tpl-recommend-product-news-content-group", baseArticleInfos);
                    $(".recommend-product-news-content").append($(newsContentTpl));

                    Index.productSwitch($(".recommend-product-news-title").find("span"),$(".recommend-product-news-content-group"),"news-title-active");
                    Index.productSwitch($(".recommend-product-content-title").find("span"),$(".recommend-product-content-list-group"),"recommend-product-content-title-active");

                }
                console.log(result);
            }
        });

    },

    initRecommendProductContent: function() {
        $.ajax({
            type: "POST",
            url: "/index/indexModuleInfo",
            data: {type: "re", limit: 5},
            success: function(result) {
                if (result.success && result.data) {
                    console.log($("input[name=shopId]").val());

                    var reIndexModelList = {reIndexModelList: result.data, shopId: $("input[name=shopId]").val()};
                    $(".recommend-product-content-title").empty();
                    var tpl_recommend_product_content_title = template("tpl-recommend-product-content-title", reIndexModelList);
                    $(".recommend-product-content-title").append($(tpl_recommend_product_content_title));

                    $(".recommend-product-content-list-wrap").empty();
                    var tpl_recommend_product_content_list_wrap = template("tpl-recommend-product-content-list-wrap", reIndexModelList);
                    $(".recommend-product-content-list-wrap").append($(tpl_recommend_product_content_list_wrap));

                    Index.productSwitch($(".recommend-product-news-title").find("span"),$(".recommend-product-news-content-group"),"news-title-active");
                    Index.productSwitch($(".recommend-product-content-title").find("span"),$(".recommend-product-content-list-group"),"recommend-product-content-title-active");
                    Index.sectionSwitch();
                }
            }
        });
    },

    initIndexShowLanmu: function() {
        $.ajax({
            type: "POST",
            url: "/index/indexModuleInfo",
            data: {type: "floor", limit: 10},
            success: function(result) {
                if (result.success && result.data) {

                    var floorIndexModelList = [];

                    $.each(result.data, function(i, perIndexModel) {
                        var leftAdUrl = perIndexModel.leftAdUrl ? $.parseJSON(perIndexModel.leftAdUrl) : null;
                        if (leftAdUrl && leftAdUrl.type === 'good') {
                            perIndexModel.adType = leftAdUrl.type;
                            perIndexModel.adRelId = leftAdUrl.goodId;
                        } else if (leftAdUrl && leftAdUrl.type === 'classify') {
                            perIndexModel.adType = leftAdUrl.type;
                            perIndexModel.adRelId = leftAdUrl.id;
                        } else {
                            perIndexModel.leftAdUrl = "#";
                        }



                        var keywords = [];
                        $.each(perIndexModel.floorKeywords, function(i, perKeyword) {

                            var keywordUrl = perKeyword.keywordUrl ? $.parseJSON(perKeyword.keywordUrl) : null;
                            if (keywordUrl && (keywordUrl.type === "good")) {
                                perKeyword.keywordType = keywordUrl.type;
                                perKeyword.keywordRelId = keywordUrl.goodId;
                            } else if (keywordUrl && keywordUrl.type === "classify") {
                                perKeyword.keywordType = keywordUrl.type;
                                perKeyword.keywordRelId = keywordUrl.id;
                            } else if (keywordUrl && keywordUrl.type === "diy") {
                                perKeyword.keywordType = keywordUrl.type;
                                perKeyword.keywordRelId = perKeyword.keywordUrlDescribe;
                            } else {
                                perKeyword.keywordRelId = "#";
                            }
                            keywords.push(perKeyword);
                        });
                        perIndexModel.floorKeywords = keywords;

                        var topkeywords = [];
                        $.each(perIndexModel.floorTopKeywords, function(i, perKeyword) {

                            var keywordUrl = perKeyword.keywordUrl ? $.parseJSON(perKeyword.keywordUrl) : null;
                            if (keywordUrl && (keywordUrl.type === "good")) {
                                perKeyword.keywordType = keywordUrl.type;
                                perKeyword.keywordRelId = keywordUrl.goodId;
                            } else if (keywordUrl && keywordUrl.type === "classify") {
                                perKeyword.keywordType = keywordUrl.type;
                                perKeyword.keywordRelId = keywordUrl.id;
                            } else if (keywordUrl && keywordUrl.type === "diy") {
                                perKeyword.keywordType = keywordUrl.type;
                                perKeyword.keywordRelId = perKeyword.keywordUrlDescribe;
                            } else {
                                perKeyword.keywordRelId = "#";
                            }
                            topkeywords.push(perKeyword);
                        });
                        perIndexModel.floorTopKeywords = topkeywords;

                        floorIndexModelList.push(perIndexModel);
                    });

                    var floorIndexModelList = {floorIndexModelList: floorIndexModelList, shopId: $("input[name=shopId]").val()};

                    console.log(floorIndexModelList);

                    $(".index-show-lanmu").empty();
                    var tpl_index_show_lanmu = template("tpl-index-show-lanmu", floorIndexModelList);
                    $(".index-show-lanmu").append($(tpl_index_show_lanmu));
                }
            }
        });
    },

    /*轮播*/
    swiper:function(){
        var mySwiper = new Swiper('.swiper-container',{
            autoplay : 3000,
            pagination : '.pagination',
            paginationClickable :true,
            loop : true
        })
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
    }
};

$(function(){
   Index.init();
});