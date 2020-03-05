/**
 * Created by dy on 2017/6/18.
 */
var orderList = {

    init: function() {
        if (!$.checkLogin()) { //验证登录
            return;
        }
        orderList.selectOrderTab();
        orderList.doBatchConfReceipt();
        orderList.getOrderCount();
        orderList.getOrderList(null, null, 1);
        orderList.topPageSearch();
        orderList.checkAllOrder();
    },
    getOrderCount: function() {

        var queryData = {
            orderType :"normal"
        };

        $.ajax({
            type: "POST",
            url: "/order/orderCount",
            data: {orderJson : JSON.stringify(queryData)},
            success: function(result) {
                console.log(result);
                if (result.success) {
                    $(".right-cont-wrap").find(".nav-menu").find(".allCount").html(!result.data.allCount || result.data.allCount == 0 ? "" : result.data.allCount);
                    $(".right-cont-wrap").find(".nav-menu").find(".evaluateCount").html(!result.data.evaluateCount || result.data.evaluateCount == 0 ? "" : result.data.evaluateCount);
                    $(".right-cont-wrap").find(".nav-menu").find(".payCount").html(!result.data.payCount || result.data.payCount == 0 ? "" : result.data.payCount);
                    $(".right-cont-wrap").find(".nav-menu").find(".receiveCount").html(!result.data.receiveCount || result.data.receiveCount == 0 ? "" : result.data.receiveCount);
                    $(".right-cont-wrap").find(".nav-menu").find(".shipCount").html(!result.data.shipCount || result.data.shipCount == 0 ? "" : result.data.shipCount);
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    },

    doDelOrder: function(target) {
        var orderJson = {};
        orderJson.orderId = $(target).attr("data-id");
        orderJson.deleteType = "buyer";
        orderJson.orderBizType = "purchase";
        orderJson.orderType = $(target).attr("data-order-type") ? $(target).attr("data-order-type") : "normal";
        layer.confirm('是否删除该订单?', {title: '提示'}, function (index) {
            layer.close(index);
            $.ajax({
                type: "POST",
                data: {orderJsonStr : JSON.stringify(orderJson)},
                url: "/order/delOrder",
                success: function(result) {
                    $(".check-all").removeAttr("checked");
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
                                var name = $(this).siblings(".search-ipt").val();
                                var status = $(".nav-menu").find(".active").attr("data-status") ? $(".nav-menu").find(".active").attr("data-status") : null;
                                orderList.getOrderCount(); //重新获取订单数量
                                orderList.getOrderList(status, name, 1);
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

    doBatchConfReceipt: function() {
        $(".batch-confirm-receive").click(function() {

            if($("input[name=ordercheck]").length <= 0) {
                layer.msg("请选择待收货状态的订单！");
                return;
            }

            var orderList = [];
            $("input[name=ordercheck]").each(function(i, perIpt) {
                if ($(perIpt).attr("data-status") != "5") {
                    layer.msg("请选择待收货状态的订单！");
                    return false;
                }
                var orderObj = {};
                orderObj.orderId = $(perIpt).val();
                orderObj.orderBizType = "purchase";
                orderObj.confirmType = "user";
                orderObj.orderType = $(perIpt).attr("data-order-type") ? $(perIpt).attr("data-order-type") : "normal";
                orderList.push(orderObj);
            });

            if (orderList.length > 0) {

                layer.confirm('是否批量确认收货?', {title: '提示'}, function (index) {
                    $.ajax({
                        type: "POST",
                        data: {confirmInfoListJson : JSON.stringify(orderList)},
                        url: "/order/confReceipt",
                        success: function(result) {
                            $(".check-all").removeAttr("checked");
                            if (result.success) {
                                layer.msg(result.msg);
                                var name = $(this).siblings(".search-ipt").val();
                                if (!name) {
                                    return;
                                }
                                var status = $(".nav-menu").find(".active").attr("data-status") ? $(".nav-menu").find(".active").attr("data-status") : null;
                                orderList.getOrderCount(); //重新获取订单数量
                                orderList.getOrderList(status, name, 1);
                            } else {
                                layer.msg(result.msg);
                            }
                        },
                        error: function(error) {
                            layer.close(ii);
                            layer.msg(error);
                        }
                    });
                    layer.close(index);
                });

            }
        });
    },

    checkAllOrder: function() {
        $(".check-all").change(function() {
            if ($(this).attr("checked") == "checked") {
                $(".order-list-div").find("input[name=ordercheck]").attr("checked", "checked");
            } else {
                $(".order-list-div").find("input[name=ordercheck]").removeAttr("checked");
            }
        });
    },

    selectOrderTab: function() {

        $(".status-select").change(function() {
            var status = $(this).attr("data-status") ? $(this).attr("data-status") : null;
            if (status) {
                return;
            }
            status = $(this).val();
            orderList.getOrderList(status, null, 1);
        });

        $(".nav-menu").find("li").click(function() {
            $(".nav-menu").find("li").removeClass("active");
            $(this).addClass("active");
            $(".search-ipt").val(""); //清空查询条件
            var status = $(this).attr("data-status") ? $(this).attr("data-status") : null;
            orderList.getOrderList(status, null, 1);
        });
    },
    getOrderList: function(status, name, pageNo) {
        var queryData = {
            status: status ? status : "",
            name: name,
            pageNo: pageNo,
            orderType :"normal"
        };
        var ii = layer.load();
        $.ajax({
            type: "POST",
            data: {orderJson : JSON.stringify(queryData)},
            url: "/order/orderList",
            success: function(result) {
                layer.close(ii);
                $(".check-all").removeAttr("checked");
                if (result.success) {
                    $(".order-list-div").empty();
                    var pageNo = result.pageNo;
                    var pageSize = result.pageSize;
                    var totalCounts = result.counts;
                    var totalPage = totalCounts %  pageSize == 0 ? totalCounts /  pageSize : parseInt(totalCounts /  pageSize) + 1;

                    var status = $(".nav-menu").find(".active").attr("data-status") ? $(".nav-menu").find(".active").attr("data-status") : null;

                    if (status == "receive" || status == null) {
                        $(".batch-confirm-receive").show();
                    } else {
                        $(".batch-confirm-receive").hide();
                    }

                    if (totalPage > 1) {

                        if (pageNo == 1) {
                            $(".option-wrap").find(".pre").addClass("disable");
                            $(".option-wrap").find(".pre").attr("data-page-no", 0);
                        } else {
                            $(".option-wrap").find(".pre").removeClass("disable");
                            $(".option-wrap").find(".pre").attr("data-page-no", pageNo - 1);
                        }

                        if (totalPage == pageNo) {
                            $(".option-wrap").find(".next").addClass("disable");
                            $(".option-wrap").find(".next").attr("data-page-no", 0);
                        } else {
                            $(".option-wrap").find(".next").removeClass("disable");
                            $(".option-wrap").find(".next").attr("data-page-no", pageNo + 1);
                        }
                    } else {
                        $(".option-wrap").find(".pre").addClass("disable");
                        $(".option-wrap").find(".pre").attr("data-page-no", 0);
                        $(".option-wrap").find(".next").addClass("disable");
                        $(".option-wrap").find(".next").attr("data-page-no", 0);
                    }

                    $.each(result.data, function(i, perOrder) {
                        perOrder.createTime = $.formatDate("yyyy-MM-dd", new Date(perOrder.createTime));
                        var tpl_order_list_table = template("tpl-order-list-table", perOrder);
                        $(".order-list-div").append($(tpl_order_list_table));
                    });
                } else {
                    layer.msg(result.msg);
                }
            }
        });

    },

    topPageSearch: function() {

        $(".search-label").click(function() {
            var name = $(this).siblings(".search-ipt").val();
            if (!name) {
                return;
            }
            var status = $(".nav-menu").find(".active").attr("data-status") ? $(".nav-menu").find(".active").attr("data-status") : null;
            orderList.getOrderList(status, name, 1);
        });

        $(".option-wrap").find(".option-btn").click(function() {
            var pageNo = $(this).attr("data-page-no") ? parseInt($(this).attr("data-page-no")) : 0;
            if (pageNo == 0) {
                return;
            }
            var status = $(".nav-menu").find(".active").attr("data-status") ? $(".nav-menu").find(".active").attr("data-status") : null;
            var name = $(".search-ipt").val() ? $(".search-ipt").val() : null;
            orderList.getOrderList(status, name, pageNo);
        });
    }

}
$(function() {
    orderList.init();
})
