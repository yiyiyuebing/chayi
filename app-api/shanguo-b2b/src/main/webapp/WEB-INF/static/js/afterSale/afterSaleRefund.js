/**
 * Created by dy on 2017/7/17.
 */
var afterSaleRefund = {
    init: function() {
        afterSaleRefund.initComponent();
        afterSaleRefund.btnEvents();
    },

    initComponent: function() {
        $("input[name=refundAmount]").on("change", function() {
            var obj = $(this);
            var maxPrice = obj.attr("data-max-price") ? Number(obj.attr("data-max-price")) : 0;
            if (!$.validateMoney(obj.val())) {
                //obj.val(0);
                //layer.msg("请输入正确的价格！");
            }

            if (Number(obj.val()) > maxPrice) {
                //obj.val(0);
                //layer.msg("退款金额不能超过商品金额！");
            }
            if (!(Number(obj.val()) > 0)) {
                //obj.val(0);
                //layer.msg("退款金额不能小于0！");
            }
        });

        if ($("#selectImgBtn").length > 0) {
            var imageList = [];
            if ($("input[name=ipt-photo-list]").length > 0 && $("input[name=ipt-photo-list]").val()) {
                var attachment = $("input[name=ipt-photo-list]").val();
                var attachmentList = attachment.split(",");
                $.each(attachmentList, function(i, per) {
                    var imgObj = {};
                    imgObj.id = i;
                    imgObj.url = per;
                    imgObj.name = "";
                    imageList.push(imgObj);
                });
            }

            $.createWebUploader({
                viewListId: "#photo-list",
                pickBtn: "#selectImgBtn",
                replaceModel: false,
                isShow: true,
                thumbnailWidth: 66,
                thumbnailHeight: 66,
                fileNumLimit: 3,
                addImgSuccess: function() { //选择图片成功事件
                },
                imageList: imageList
            });
        }


        if ($("#ipt-endTime").length > 0) {
            setInterval(afterSaleRefund.setLastTime, 0);
        }
    },

    btnEvents : function () {

        $(".submit-btn").on("click", function() {
            var submitRefundData = {};

            if ($("input[name=returnSeason]").val() && $("input[name=returnSeason]").val() != "请选择退款原因") {
                submitRefundData.returnReason = $("input[name=returnSeason]").val();
            } else {
                layer.msg("请选择退款原因");
                return;
            }
            var maxPrice = $("input[name=refundAmount]").attr("data-max-price") ? Number($("input[name=refundAmount]").attr("data-max-price")) : 0;
            if (!$.validateMoney($("input[name=refundAmount]").val())) {
                //$("input[name=refundAmount]").val(0);
                layer.msg("请输入正确的价格！");
                return;
            }

            if (!(Number($("input[name=refundAmount]").val()) > 0)) {
                //$("input[name=refundAmount]").val(0);
                layer.msg("退款金额不能等于0！");
                return;
            }

            if (Number($("input[name=refundAmount]").val()) > maxPrice) {
                //$("input[name=refundAmount]").val(0);
                layer.msg("退款金额不能超过商品金额！");
                return;
            }


            var attachment = $.getUploadImageList("#photo-list");
            var attachmentList = [];
            $.each(attachment, function(i, pic){
                attachmentList.push(pic.url);
            });
            submitRefundData.asType = $("input[name=afterService]").attr("data-service");
            submitRefundData.returnAmount = $("input[name=refundAmount]").val();
            submitRefundData.operDesc = $("textarea[name=operDesc]").val();
            submitRefundData.flowTargetType = "list";
            submitRefundData.attachment = attachmentList.join(",");

            submitRefundData.orderId = $("input[name=orderId]").val();
            submitRefundData.orderListIdList = $("input[name=orderListId]").val().split(",");
            submitRefundData.orderType = $("input[name=orderType]").val();

            console.log(submitRefundData);

            var operType = $("input[name=operType]").val();

            switch (operType) {
                case 'modify': //修改售后
                    var flowId = $("input[name=flowId]").val();
                    submitRefundData.flowId = flowId;
                    $.ajax({
                        type:"POST",
                        url:"/afterSale/updateAfterSale",
                        data:{"modelJson":JSON.stringify(submitRefundData)},
                        success:function(result){
                            if (result.success) {
                                layer.msg("修改成功！");
                                window.location.href = "/afterSale/afterSaleRefund/"+ submitRefundData.orderId +"/"+ $("input[name=goodSkuId]").val() +"";
                            }else {
                                layer.msg(result.msg);
                            }
                        }
                    });
                    break;
                case 'reject':  //拒绝后，再次提交申请
                    $.ajax({
                        type: "POST",
                        url: "/afterSale/applyAfterSale",
                        data:{"modelJson":JSON.stringify(submitRefundData)},
                        success:function(result){
                            if (result.success){
                                layer.msg("再次申请成功！");
                                window.location.href = "/afterSale/afterSaleRefund/"+ submitRefundData.orderId +"/"+ $("input[name=goodSkuId]").val() +"";
                            }else {
                                layer.msg(result.msg);
                            }
                        }
                    })
                    break;
                default : //申请售后
                    $.ajax({
                        type: "POST",
                        url: "/afterSale/applyAfterSale",
                        data:{"modelJson":JSON.stringify(submitRefundData)},
                        success:function(result){
                            if (result.success){
                                layer.msg("申请成功！");
                                location.reload();
                            }else {
                                layer.msg(result.msg);
                            }
                        }
                    })
                    break;
            }



        });


        $(".cancel-after-sale").on("click", function() { //撤销申请
            var me = this;
            layer.confirm('撤销申请后，不能再发起申请，是否确认撤销？', {title: '提示'}, function (index) {
                var flowId = $(me).attr("data-flow-id");
                $.ajax({
                    type:"POST",
                    url:"/afterSale/cancelAfterSale",
                    data:{"flowId":flowId},
                    success:function(result){
                        if (result.success){
                            layer.msg("撤销成功！");
                            location.reload();
                        }else {
                            layer.msg(result.msg);
                        }
                    }
                });
                layer.close(index);
            });

        });

    },
    setLastTime : function() {
        var endTimeStr = $("#ipt-endTime").val();
        var EndTime= new Date(endTimeStr);
        var NowTime = new Date();
        var t = EndTime.getTime() - NowTime.getTime();
        var d = 0;
        var h = 0;
        var m = 0;
        var s = 0;
        if( t >= 0) {
            d = Math.floor(t/1000/60/60/24);
            h = Math.floor(t/1000/60/60%24);
            m = Math.floor(t/1000/60%60);
            s = Math.floor(t/1000%60);
        }
        $("#return-day").html(d);
        $("#return-hour").html(h);
        $("#return-min").html(m);
    }
}

$(function() {
    afterSaleRefund.init();
})