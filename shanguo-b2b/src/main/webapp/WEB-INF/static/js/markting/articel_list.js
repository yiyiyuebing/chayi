/**
 * Created by Administrator on 2017/5/11.
 */
var  MarketingList = {
    memoryType: null,
    init:function() {
        initNavBar("markting");
        MarketingList.initMargketingTabs();
    },

    initMargketingTabs: function() {

        $(".article .article-aside").find("li[data-value="+ articleType +"]").addClass("active");

        if($(".article .article-aside").find("li").hasClass("active")) {
            var type = $(".article .article-aside").find(".active").attr("data-value");
            MarketingList.marktingArticleList(1, 20, type);
        }
        $(".article .article-aside").find("li").click(function() {
            $(".article .article-aside").find("li").removeClass("active");
            $(this).addClass("active");
            $("#kkpager").addClass("hide");
            MarketingList.marktingArticleList(1, 20, $(this).attr("data-value"));
        });
    },

    marktingArticleList: function(pageNum, pageSize, type) {
        var queryParam = {
            pageNum: pageNum,
            pageSize: pageSize,
            type: type
        };
        $.ajax({
            type: "POST",
            data: queryParam,
            url: "/news/marktingArticleList",
            success: function(result) {
                $("#kkpager").empty();
                if (result.success && result.data.baseArticleInfo) {
                    $(".article-list-content-right").empty();
                    $(".article-list-content-left").empty();
                    if (result.data.baseArticleInfo.baseArticleInfos.length == 0) {
                        return;
                    }
                    $("#kkpager").removeClass("hide");

                    var leftBaseArticleInfoList = [];
                    $.each(result.data.baseArticleInfo.baseArticleInfos, function(i, baseArticleInfo) {
                        if (i < 10) {
                            baseArticleInfo.articleType = type;
                            leftBaseArticleInfoList.push(baseArticleInfo);
                        }
                    });
                    var rightBaseArticleInfoList = [];
                    $.each(result.data.baseArticleInfo.baseArticleInfos, function(i, baseArticleInfo) {
                        if (i >= 10) {
                            baseArticleInfo.articleType = type;
                            rightBaseArticleInfoList.push(baseArticleInfo);
                        }
                    });

                    if (leftBaseArticleInfoList.length > 0) {

                        var tplArticleListContentLeft = template("tpl-article-list-content-left", {leftArticleInfoList: leftBaseArticleInfoList});
                        $(".article-list-content-left").html(tplArticleListContentLeft);
                    }
                    if (rightBaseArticleInfoList.length > 0) {

                        var tplArticleListContentRight = template("tpl-article-list-content-right", {rightArticleInfoList: rightBaseArticleInfoList});
                        $(".article-list-content-right").html(tplArticleListContentRight);
                    }

                    MarketingList.initMarktingPage(result.data.baseArticleInfo.pageNum,
                        result.data.baseArticleInfo.totalPage,
                        result.data.baseArticleInfo.totalRecods, pageSize, type);
                }

            }
        });

    },

    initMarktingPage: function(pageNum, totalPage, totalRecods, pageSize, type) {
        //if (!$(".infoTextAndGoPageBtnWrap").hasClass("hide")) {
        //    $(".infoTextAndGoPageBtnWrap").addClass("hide");
        //}
        //生成分页控件
        kkpager.generPageHtml({
            pno : pageNum,

            mode : 'click', //设置为click模式
            //总页码
            total : totalPage,
            //总数据条数
            totalRecords : totalRecods,
            isGoPage: false,
            isShowTotalPage: false,
            isShowTotalRecords: false,
            //点击页码、页码输入框跳转、以及首页、下一页等按钮都会调用click
            //适用于不刷新页面，比如ajax
            click : function(n){
                //这里可以做自已的处理
                MarketingList.marktingArticleList(n, pageSize, type);
                //处理完后可以手动条用selectPage进行页码选中切换
                this.selectPage(n);
            }
        }, true);


    }




};

$(function(){
    MarketingList.init();
});