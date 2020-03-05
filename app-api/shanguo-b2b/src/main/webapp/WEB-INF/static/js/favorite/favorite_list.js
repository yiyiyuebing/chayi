$(function() {
    if (!$.checkLogin()) { //验证登录
        return;
    }
    Collect.init();
});

var Collect = {
    init : function() {
        Collect.showBatchBox();
        Collect.hideBatchBox();
        Collect.prePage();
        Collect.nextPage();
        Collect.initCheck();
        Collect.getFavoriteList(1);
        Collect.initFavoritePage();
    },

    initCheck: function() {
        $(".check-all").change(function() {
            if ($(this).attr("checked") == "checked") {
                $(".list-item").addClass("active");
            } else {
                $(".list-item").removeClass("active");
            }

        });
    },


    initFavoritePage: function() {

        var pageNum = 1;
        var pageSize = 8;

        var queryData = {
            countType: "list",
            pageNum: pageNum,
            pageSize: pageSize
        };
        $.ajax({
            type: "POST",
            data: {modelJsonStr : JSON.stringify(queryData)},
            url: "/favorite/getFavoriteAllCount",
            success: function(result) {
                if (result.success) {
                    var counts = result.counts;
                    var totalPage = counts %  pageSize == 0 ? counts /  pageSize : parseInt(counts /  pageSize) + 1;
                    $("#total-page").html(totalPage);
                    if (totalPage > 1) {
                        $("#page-next").removeClass("disable");
                    }
                    Collect.initKKpage(1, totalPage, counts);
                } else {
                    layer.msg(result.msg)
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
                $("#current-page").text(n);
                if (n==1 || n < totalPageNo) {
                    $("#page-pre").addClass("disable");
                    $("#page-next").removeClass("disable");
                } else if(n == totalPageNo || n > 1) {
                    $("#page-next").addClass("disable");
                    $("#page-pre").removeClass("disable");
                } else {
                    $("#page-next").removeClass("disable");
                    $("#page-pre").removeClass("disable");
                }
                Collect.getFavoriteList(n);
                //处理完后可以手动条用selectPage进行页码选中切换
                this.selectPage(n);
            }
        }, true);

    },

    cancelFavorite: function(target) {
        var queryData = [];
        if (!target) {
            $(".collect-list-wrap").find(".list-item.active").each(function(i, perFavorite) {
                queryData.push($(perFavorite).attr("data-id"));
            });
            if (queryData.length <= 0) {
                return;
            }
            $.ajax({
                type: "POST",
                data: {modelJsonStr : JSON.stringify(queryData)},
                url: "/favorite/batchCancleFavorite",
                success: function(result) {
                    if (result.success) {
                        //边缘弹出
                        layer.open({
                            closeBtn: 0
                            ,type: 1
                            ,content: '<div style="padding: 20px 80px;">取消收藏成功</div>'
                            ,btn: '确定'
                            ,btnAlign: 'c' //按钮居中
                            ,shade: 0 //不显示遮罩
                            ,yes: function(){
                                Collect.initFavoritePage();
                                Collect.getFavoriteList(1);
                                layer.closeAll();

                            }
                        });
                    } else {
                        layer.msg(result.msg);
                    }
                }
            });

        } else {
            var data = {
                goodsId:$(target).attr("data-good-id"),
                goodsCount: 1,
                orderBizType: 'purchase'
            }
            $.ajax({
                type: "POST",
                data: {modelJsonStr: JSON.stringify(data)},
                url: "/favorite/cancelFavorite",
                success: function(result) {
                    if (result.success) {
                        //边缘弹出
                        layer.open({
                            closeBtn: 0
                            ,type: 1
                            ,content: '<div style="padding: 20px 80px;">取消收藏成功</div>'
                            ,btn: '确定'
                            ,btnAlign: 'c' //按钮居中
                            ,shade: 0 //不显示遮罩
                            ,yes: function(){
                                Collect.initFavoritePage();
                                Collect.getFavoriteList(1);
                                layer.closeAll();

                            }
                        });
                    } else {
                        layer.msg(result.msg);
                    }
                }
            });
        }
    },

    addToCartFromFavorite: function(target){
        //var cartQueryList = [];

        var cartQueryObj = {};
        cartQueryObj.goodsId = $(target).attr("data-id");
        cartQueryObj.orderBizType = "purchase";
        //cartQueryList.push(cartQueryObj);

        $.ajax({
            type: "POST",
            data: {modelJsonStr : JSON.stringify(cartQueryObj)},
            url: "/favorite/addToCartFromFavorite",
            success: function(result){
                if(result.success){
                    AsideBar.cartCount();
                    layer.msg("已加入购物车")
                }else{
                    layer.msg(result.msg);
                }
            },
            error:function(){
                layer.msg("加入购物车失败");
            }
        })
    },

    getFavoriteList: function(pageNo) {
        var ii = layer.load();
        var queryData = {
            pageNum: pageNo
        };
        $.ajax({
            type: "POST",
            data: {modelJsonStr : JSON.stringify(queryData)},
            url: "/favorite/getFavoriteList",
            success: function(result) {
                //此处用setTimeout演示ajax的回调
                layer.close(ii);
                if (result.success) {
                    $("#collect-list-box").empty();
                    $.each(result.data, function(i, perGoods) {
                        var tpl_list_item = template("tpl-list-item", perGoods);
                        $("#collect-list-box").append($(tpl_list_item));
                    });
                } else {
                    layer.msg(result.msg);
                }

            }
        });
    },

    showBatchBox : function() {
        $("#batch-btn").click(function() {
            $("#title-batch-box").addClass("check");
            $("#collect-list-box").addClass("check");

            Collect.checkActive();
        });
    },

    hideBatchBox :function() {
        $("#complete-btn").click(function () {
            $("#title-batch-box").removeClass("check");
            $("#collect-list-box").removeClass("check");
            $("#collect-list-box .list-item").removeClass("active");
            $(".check-all").removeAttr("checked");
            Collect.unBindCheckActive();
        });
    },

    checkActive : function() {
        $("#collect-list-box .list-item").click(function() {
           $(this).toggleClass("active");
        });
    },

    unBindCheckActive : function() {
        $("#collect-list-box .list-item").unbind("click");
    },

    prePage : function() {
        $("#page-pre").click(function() {

            var totalPage = $("#total-page").text() ? parseInt($("#total-page").text()) : 1;


            var currentTagt = $("#current-page"),
                currentPage = parseInt(currentTagt.text());

            if ((currentPage-1) == 0 || currentPage == totalPage) {
                $(this).addClass("disable");
                $("#page-next").removeClass("disable");
                return;
            } else {
                currentTagt.text(currentPage - 1);
                Collect.getFavoriteList(currentPage -1);
                Collect.initKKpage(currentPage -1, totalPage, 8);
            }
        });
    },

    nextPage : function() {
        $("#page-next").click(function() {
            var totalPage = $("#total-page").text() ? parseInt($("#total-page").text()) : 1;
            var currentTagt = $("#current-page"),
                currentPage = parseInt(currentTagt.text());

            if (totalPage > 1) {
                $("#page-pre").removeClass("disable");
            }

            if (currentPage == totalPage) {
                $(this).addClass("disable");
            } else if (currentPage <= totalPage) {
                currentTagt.text(currentPage + 1);
                Collect.getFavoriteList(currentPage + 1);
                Collect.initKKpage(currentPage + 1, totalPage, 8);
                return;
            }


        });
    }
}