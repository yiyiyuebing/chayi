/**
 * Created by dy on 2017/6/21.
 */
var orderPresellList = {
    init: function() {
        if (!$.checkLogin()) { //验证登录
            return;
        }
        orderPresellList.getOrderList(1);
    },

    doDelOrder: function(target) {
        layer.confirm('是否删除该订单?', {title: '提示'}, function (index) {
            layer.close(index);
            var orderJson = {};
            orderJson.orderId = $(target).attr("data-id");
            orderJson.deleteType = "buyer";
            orderJson.orderBizType = "purchase";
            orderJson.orderType = "presell";
            $.ajax({
                type: "POST",
                data: {orderJsonStr : JSON.stringify(orderJson)},
                url: "/order/delOrder",
                success: function(result) {
                    if (result.success) {
                        //边缘弹出
                        layer.open({
                            closeBtn: 0
                            ,type: 1
                            ,content: '<div style="padding: 20px 80px;">删除成功</div>'
                            ,btn: '确定'
                            ,btnAlign: 'c' //按钮居中
                            ,shade: 0 //不显示遮罩
                            ,yes: function(){
                                orderPresellList.getOrderList(1);
                                layer.closeAll();

                            }
                        });
                    } else {
                        layer.msg(result.msg);
                    }
                }
            });
        });
    },

    getOrderList: function(pageNo) {
        var queryData = {
            pageNo: pageNo,
            orderType: 'presell'
        };
        var ii = layer.load();
        $.ajax({
            type: "POST",
            data: {orderJson : JSON.stringify(queryData)},
            url: "/order/orderList",
            success: function(result) {
                $(".check-all").removeAttr("checked");
                layer.close(ii);
                if (result.success) {
                    $(".order-list-div").empty();
                    var pageNo = result.pageNo;
                    var pageSize = result.pageSize;
                    var totalCounts = result.counts;
                    var totalPage = totalCounts %  pageSize == 0 ? totalCounts /  pageSize : parseInt(totalCounts /  pageSize) + 1;

                    $.each(result.data, function(i, perOrder) {
                        perOrder.createTime = $.formatDate("yyyy-MM-dd", new Date(perOrder.createTime));
                        var tpl_order_list_table = template("tpl-list-table", perOrder);
                        $(".order-list-div").append($(tpl_order_list_table));
                    });


                    if (totalPage > 1) {

                        if (pageNo == 1) {
                            $(".check-box").find(".pre").addClass("disable");
                            $(".check-box").find(".pre").attr("data-page-no", 0);
                        } else {
                            $(".check-box").find(".pre").removeClass("disable");
                            $(".check-box").find(".pre").attr("data-page-no", pageNo - 1);
                        }

                        if (totalPage == pageNo) {
                            $(".check-box").find(".next").addClass("disable");
                            $(".check-box").find(".next").attr("data-page-no", 0);
                        } else {
                            $(".check-box").find(".next").removeClass("disable");
                            $(".check-box").find(".next").attr("data-page-no", pageNo + 1);
                        }
                    } else {
                        $(".check-box").find(".pre").addClass("disable");
                        $(".check-box").find(".pre").attr("data-page-no", 0);
                        $(".check-box").find(".next").addClass("disable");
                        $(".check-box").find(".next").attr("data-page-no", 0);
                    }

                    /*$.each(result.data, function(i, perOrder) {
                        perOrder.createTime = $.formatDate("yyyy-MM-dd", new Date(perOrder.createTime));
                        var tpl_order_list_table = template("tpl-order-list-table", perOrder);
                        $(".order-list-div").append($(tpl_order_list_table));
                    });*/
                } else {
                    layer.msg(result.msg);
                }
            }
        });

    },

    topPageSearch: function() {

        $(".check-box").find(".option-btn").click(function() {
            var pageNo = $(this).attr("data-page-no") ? parseInt($(this).attr("data-page-no")) : 0;
            if (pageNo == 0) {
                return;
            }
            orderPresellList.getOrderList(pageNo);
        });
    }

};
$(function() {
    orderPresellList.init();
})
