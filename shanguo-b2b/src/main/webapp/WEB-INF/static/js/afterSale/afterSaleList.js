/**
 * Created by Administrator on 2017/7/31.
 */
var AfterSaleList = {
    init:function(){
        AfterSaleList.selectTypeList();
        AfterSaleList.selectStatusList();
        AfterSaleList.topPageSearch();
        AfterSaleList.getRefundOrderList();

        $('input[name=startTime]').datetimepicker({
            language: 'zh-CN',//显示中文
            format: 'yyyy-mm-dd',//显示格式
            initialDate: new Date(),//初始化当前日期
            autoclose: true//选中自动关闭
            ,minView: 'month'

        });
        $('input[name=endTime]').datetimepicker({
            language: 'zh-CN',//显示中文
            format: 'yyyy-mm-dd',//显示格式
            initialDate: new Date(),//初始化当前日期
            autoclose: true//选中自动关闭
            ,minView: 'month'
        });

        //$('input[name=startTime]')
        //    .datetimepicker()
        //    .on('changeDate', function(ev) {
        //        $('input[name=endTime]').val("");
        //        $('input[name=endTime]').datetimepicker('setStartDate', $('input[name=startTime]').val());
        //    }
        //);

    },
    /*下拉列表*/
    /*类型*/
    selectTypeList:function(){
        $("#afterTypeBtn").on("click",function(){
            if($(this).hasClass("active")){
                $("#afterTypeList").slideUp("200");
                $(this).removeClass("active");
            }else{
                $("#afterTypeList").slideDown("200");
                $(this).addClass("active");
            }
        });
        $("#afterTypeList").on("click","li",function(){
            var typeVal = $(this).attr("data-afterType");
            $("#afterTypeBtn").html(typeVal);
            $("input[name=afterType]").val($(this).attr("data-type"));
            $("#afterTypeList").slideUp("200");
            $("#afterTypeBtn").removeClass("active");

            if ($(this).attr("data-type")) {
                $("#afterStatusBtn").removeClass("disabled");
                $("#afterStatusBtn").siblings(".input-select-list").find("li").addClass("hide");
                $("#afterStatusBtn").siblings(".input-select-list").find("li:first").removeClass("hide");
                $("#afterStatusBtn").siblings(".input-select-list").find(".after-sale-" + $(this).attr("data-type")).removeClass("hide");
            } else {
                $("#afterStatusBtn").addClass("disabled");
            }
            $("#afterStatusList").slideUp("200");
            $("#afterStatusBtn").html("全部");

        });
    },
    /*状态*/
    selectStatusList:function(){
        $("#afterStatusBtn").on("click",function(){
            if($(this).hasClass("active")){
                $("#afterStatusList").slideUp("200");
                $(this).removeClass("active");
            }else{
                $("#afterStatusList").slideDown("200");
                $(this).addClass("active");
            }
        });
        $("#afterStatusList").on("click","li",function(){
            var typeVal = $(this).attr("data-afterStatus");
            $("#afterStatusBtn").html(typeVal);
            $("input[name=afterStatus]").val($(this).attr("data-status"));
            $("#afterStatusList").slideUp("200");
            $("#afterStatusBtn").removeClass("active");
        });
    }
    ,getRefundOrderList: function(pageNo) {

        var flowStatusNum = $("input[name=afterStatus]").val() ? $("input[name=afterStatus]").val() : "";
        var flowAsType = $("input[name=afterType]").val() ? $("input[name=afterType]").val() : "";
        var startTime = $('input[name=startTime]').val() ? $('input[name=startTime]').val() + " 00:00:00" : "";
        var endTime = $('input[name=endTime]').val() ? $('input[name=endTime]').val() + " 23:59:59" : "";

        var queryData = {};
        if (flowStatusNum && flowStatusNum != '全部') queryData.flowStatusNum = flowStatusNum;
        if (flowAsType && flowAsType != '全部') queryData.flowAsType = flowAsType;
        if (pageNo) queryData.pageNo = pageNo;
        if (startTime) queryData.startDate = startTime;
        if (endTime) queryData.endDate = endTime;

        var loading = layer.load();
        $.ajax({
            type: "POST",
            data: {orderJson : JSON.stringify(queryData)},
            url: "/afterSale/afterSaleInfoList",
            success: function(result) {
                layer.close(loading);
                if (result.success) {
                    $(".refund-order-list").empty();
                    var pageNo = result.pageNo;
                    var pageSize = result.pageSize;
                    var totalCounts = result.counts;
                    var totalPage = totalCounts %  pageSize == 0 ? totalCounts /  pageSize : parseInt(totalCounts /  pageSize) + 1;

                    //var status = $(".nav-menu").find(".active").attr("data-status") ? $(".nav-menu").find(".active").attr("data-status") : null;

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
                        perOrder.createTime = $.formatDate("yyyy-MM-dd HH:mm:ss", new Date(perOrder.createTime));
                        var tpl_refund_order_list = template("tpl-refund-order-list", perOrder);
                        $(".refund-order-list").append($(tpl_refund_order_list));
                    });
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    }
    ,topPageSearch: function() {




        $(".sales-return-search-btn").click(function() {

            AfterSaleList.getRefundOrderList(1);
        });

        $(".option-wrap").find(".option-btn").click(function() {
            var pageNo = $(this).attr("data-page-no") ? parseInt($(this).attr("data-page-no")) : 0;
            if (pageNo == 0) {
                return;
            }
            AfterSaleList.getRefundOrderList(pageNo);
        });
    }

};
$(function(){
    AfterSaleList.init();
});