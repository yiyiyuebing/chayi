/**
 * Created by Administrator on 2017/6/16.
 */
var rateList = {
    init:function(){
        //rateList.getOrderCount();
        rateList.topPageSearch();
        rateList.selectOrderTab();
        rateList.getOrderList("evaluate", 1);
        rateList.initCredenImg();
    },

    getOrderCount: function() {
        $.ajax({
            type: "POST",
            url: "/order/orderCount",
            success: function(result) {
                if (result.success) {
                    $(".right-cont-wrap").find(".evaluation-nav-menu").find(".evaluateCount").html(!result.data.evaluateCount || result.data.evaluateCount == 0 ? "" : result.data.evaluateCount);
                    $(".right-cont-wrap").find(".evaluation-nav-menu").find(".fulfill").html(!result.data.doneCount || result.data.doneCount == 0 ? "" : result.data.doneCount);
                } else {
                    layer.msg(result.msg)
                }
            }
        });
    },

    selectOrderTab: function() {

        $(".evaluation-nav-menu").find("li").click(function() {
            $(".evaluation-nav-menu").find("li").removeClass("active");
            $(this).addClass("active");
            var status = $(this).attr("data-status");
            rateList.getOrderList(status,1);
        });

    },


    topPageSearch: function() {

        $(".option-wrap").find(".option-btn").click(function() {
            var pageNo = $(this).attr("data-page-no") ? parseInt($(this).attr("data-page-no")) : 0;
            if (pageNo == 0) {
                return;
            }
            var status = $(".nav-menu").find(".active").attr("data-status");
            rateList.getOrderList(status ,pageNo);
        });
    },
    getOrderList: function(status ,pageNo) {

        if (!($(".order-list-div").length > 0)) {
            return;
        }

        var queryData = {
            status: status ? status : "",
            name:name,
            pageNo: pageNo
        };
        $.ajax({
            type:"POST",
            url:"/order/orderList",
            data:{orderJson:JSON.stringify(queryData)},
            success:function(result){
                if (result.success) {
                    $(".order-list-div").empty();

                    var pageNo = result.pageNo;
                    var pageSize = result.pageSize;
                    var totalCounts = result.counts;
                    var totalPage = totalCounts %  pageSize == 0 ? totalCounts /  pageSize : parseInt(totalCounts /  pageSize) + 1;

                    var status = $(".evaluation-nav-menu").find(".active").attr("data-status");

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
                        var tpl_order_list = template("tpl-order-list", perOrder);
                        $(".order-list-div").append($(tpl_order_list));
                    });
                } else {
                    layer.msg(result.msg)
                }
            }

        });
    },
    delOrder: function (target) {
        var id =$(target).attr("data-id");
        var orderType =$(target).attr("data-ordertype");
        layer.confirm('是否删除订单?', {title: '提示'}, function (index) {
            rateList.delOrder1(orderType,id);
            layer.close(index);

        });
    },
    delOrder1 : function (orderType,id){
         var orderBizType ="purchase";
         var deleteType="buyer";
        $.ajax({
            type:"POST",
            url:"/order/delOrder",
            data:{orderJsonStr:JSON.stringify({
                orderId:id,
                orderBizType:orderBizType,
                deleteType:deleteType,
                orderType:orderType
            })},
           success : function (result) {
               if (result.success) {
                   layer.msg("删除订单成功！");
                   rateList.getOrderList("done",1);
               }else {
                   layer.msg(result.msg);
               }
           }

        });


    },

    initCredenImg : function () {
        for (var i = 0; i < $(".evaluation-info-list").length; i++) {
            $.createWebUploader({
                viewListId: ".evaluation-photo-list-" + i,
                pickBtn: ".pick-store-photo-" + i,
                replaceModel: false,
                fileNumLimit: 10,
                addImgSuccess: function(options) { //选择图片成功事件
                    var picNum = $(options.viewListId).children().length;
                    $(options.viewListId).siblings(".upload-num").find(".total-num").html(picNum);
                    $(options.viewListId).siblings(".upload-num").find(".last-num").html( 10 - Number(picNum));
                }
            });

            RateManage.star("star-myd-" + i);
        }
    },

    addEvaluation : function () {
/*        var goodCount = 0;
        for(var i=0;i<$("#star-good").find("li").length;i++){
            if($("#star-good").find("li").eq(i).hasClass("on")){
                goodCount++;
            }
        }
        var ServiceCount = 0;
        for(var i=0;i<$("#star-service").find("li").length;i++){
            if($("#star-service").find("li").eq(i).hasClass("on")){
                ServiceCount++;
            }
        }
        var shCount = 0;
        for(var i=0;i<$("#star-sh").find("li").length;i++){
            if($("#star-sh").find("li").eq(i).hasClass("on")){
                shCount++;
            }
        }*/

        var evaluationObjList = [];
        $(".evaluation-info-list").each(function(i, perInfoDiv) {
            var skuId = $(perInfoDiv).find(".skuId").val();
            var orderId = $(perInfoDiv).find(".orderId").val();
            var textarea = $(perInfoDiv).find(".f-textarea").val();
            var picList1 = $.getUploadImageList(".evaluation-photo-list-" + i);
            var addPicList = [];
            $.each(picList1,function(i,pic){
                addPicList.push(pic.url);
            });
            var mydCount = 0;

            if ($(perInfoDiv).find(".star-myd").find("li").hasClass("on")) {
                mydCount = $(perInfoDiv).find(".star-myd").find("li[class=on]").length;
            }

            if (!textarea && !(addPicList.length > 0) && !(mydCount != 0)) {
                return false;
            }

            var evaluationObj = {};
            evaluationObj.orderId = orderId;
            evaluationObj.imageUrlList = addPicList;
            evaluationObj.goodSkuId = skuId;
            evaluationObj.content = textarea;
            evaluationObj.score = mydCount;

            evaluationObjList.push(evaluationObj);

        });

        if (!(evaluationObjList.length > 0)) {
            layer.msg("请评论订单商品");
            return;
        }

        console.log(evaluationObjList);
        $.ajax({
            type:"POST",
            url:"/evaluate/addEvaluate",
            data:{
               addJson:JSON.stringify(evaluationObjList)
            },
            success : function(result) {
                if(result.success){
                    window.location.href="/evaluate/orderEvaluationSuccess/" + orderId;
                }else {
                    layer.msg(result.msg);
                }
            }

        });


    }


};
$(function(){
    rateList.init();
});