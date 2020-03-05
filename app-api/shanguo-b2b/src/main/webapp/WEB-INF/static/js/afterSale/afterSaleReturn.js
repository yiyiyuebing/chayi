/**
 * Created by dy on 2017/7/13.
 */
var afterSaleReturn = {
    init: function() {
        afterSaleReturn.initComponent();
        afterSaleReturn.submitAfterSaleApply();
    },
    initComponent: function() {
        $("input[name=returnAmount]").on("change", function() {
            var obj = $(this);
            if (!$.validateMoney(obj.val())) {
                //obj.val(0);
                //layer.msg("请输入正确的价格！");
            }
            var maxPrice = obj.attr("data-max-price") ? Number(obj.attr("data-max-price")) : 0;
            if (Number(obj.val()) > maxPrice) {
                //obj.val(0);
                //layer.msg("退款金额不能超过商品金额！");
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
            setInterval(afterSaleReturn.setLastTime, 0);
        }
    },

    submitAfterSaleApply: function() {
        $(".submit-btn").on("click", function() {  //提交售后申请


            var attachment = $.getUploadImageList("#photo-list");
            var attachmentList = [];
            $.each(attachment, function(i, pic){
                attachmentList.push(pic.url);
            });

            if (!$.validateMoney($("input[name=returnAmount]").val())) {
                //$("input[name=returnAmount]").val(0);
                layer.msg("请输入正确的价格！");
                return;
            }
            var maxPrice = $("input[name=returnAmount]").attr("data-max-price") ? Number($("input[name=returnAmount]").attr("data-max-price")) : 0;
            if (!(Number($("input[name=returnAmount]").val()) > 0)) {
                //$("input[name=returnAmount]").val(0);
                layer.msg("退款金额不能等于0！");
                return;
            }
            if (Number($("input[name=returnAmount]").val()) > maxPrice) {
                //$("input[name=returnAmount]").val(0);
                layer.msg("退款金额不能超过商品金额！");
                return;
            }

            var applyData = {};
            applyData.asType = $("input[name=afterService]").attr("data-service");
            if ($("input[name=returnSeason]").val() != "请选择退款退货原因") {
                applyData.returnReason = $("input[name=returnSeason]").val();
            } else {
                layer.msg("请选择退款退货原因！");
                return;
            }
            applyData.returnAmount = $("input[name=returnAmount]").val();
            applyData.orderId = $("input[name=orderId]").val();
            applyData.orderType = $("input[name=orderType]").val();
            applyData.orderListIds = $("input[name=orderListId]").val().split(",");
            applyData.orderListIdList = $("input[name=orderListId]").val().split(",");
            applyData.operDesc = $("textarea[name=operDesc]").val();
            applyData.flowTargetType = "list";
            applyData.attachment = attachmentList.join(",");

            console.log(applyData);


            var operType = $("input[name=operType]").val();

            switch (operType) {
                case 'modify': //修改售后
                    var flowId = $("input[name=flowId]").val();
                    applyData.flowId = flowId;
                    $.ajax({
                        type:"POST",
                        url:"/afterSale/updateAfterSale",
                        data:{"modelJson":JSON.stringify(applyData)},
                        success:function(result){
                            if (result.success) {
                                layer.msg("修改成功！");
                                window.location.href = "/afterSale/afterSaleReturn/"+ applyData.orderId +"/"+ $("input[name=goodSkuId]").val() +"";
                            }else {
                                layer.msg(result.msg);
                            }
                        }
                    });
                    break;
                case 'reject':  //拒绝后，再次提交申请
                    $.ajax({
                        type:"POST",
                        url:"/afterSale/applyAfterSale",
                        data:{"modelJson":JSON.stringify(applyData)},
                        success:function(result){
                            if (result.success){
                                layer.msg("再次申请成功！");
                                window.location.href = "/afterSale/afterSaleReturn/"+ applyData.orderId +"/"+ $("input[name=goodSkuId]").val() +"";
                            }else {
                                layer.msg(result.msg);
                            }
                        }
                    });
                    break;
                default : //申请售后
                    $.ajax({
                        type:"POST",
                        url:"/afterSale/applyAfterSale",
                        data:{"modelJson":JSON.stringify(applyData)},
                        success:function(result){
                            if (result.success){
                                layer.msg("申请成功！");
                                location.reload();
                            }else {
                                layer.msg(result.msg);
                            }
                        }
                    });
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

        $(".again-apply-after-sale").on("click", function() { //再次申请

        });

        $(".logistic-popup .submit").on("click", function() { //保存快递信息

            if(!$(".logistic-popup .company").val()) {
                layer.msg("请输入快递公司");
                return;
            }
            if(!$(".logistic-popup .logistic-number").val()) {
                layer.msg("请输入物流编号");
                return;
            }
            var flowId = $(this).attr("data-flow-id");
            var apply = {};
            apply.flowId = flowId;
            apply.freightNo = $(".logistic-popup .logistic-number").val();
            apply.freightCompany = $(".logistic-popup .company").val();
            $.ajax({
                type:"POST",
                url:"/afterSale/editUserShipping",
                data:{"modelJson":JSON.stringify(apply)},
                success:function(result){
                    if (result.success){
                        layer.msg("提交成功！");
                        $("#logisticPopup").hide();
                        location.reload();
                    }else {
                        layer.msg(result.msg);
                    }
                }
            });

        });

        $(".logistic-popup .cancel").on("click", function() { //取消保存快递信息窗口
            $("#logisticPopup").hide();
        });

    },
    setLastTime : function() {
        var endTimeStr = $("#ipt-endTime").val();
        var EndTime= new Date(endTimeStr);
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
        $("#return-day").html(d);
        $("#return-hour").html(h);
        $("#return-min").html(m);
    }
}
$(function() {
    afterSaleReturn.init();
})