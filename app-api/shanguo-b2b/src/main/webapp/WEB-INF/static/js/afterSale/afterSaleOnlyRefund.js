/**
 * Created by cg on 17-7-13.
 */

var AfterSaleStatus = {

    submitRefund : function () {
        var submitRefundData = {};

        if($("input[name=returnReason]").val() != "请选择退款原因"){
            submitRefundData.returnReason = $("input[name=returnReason]").val();
        }else{
            layer.msg("请选择换货原因");
            return;
        }

        var attachment = $.getUploadImageList("#photo-list");
        var attachmentList = [];
        $.each(attachment, function(i, pic){
            attachmentList.push(pic.url);
        });

        submitRefundData.asType = $("input[name=afterService]").val();
        submitRefundData.returnAmount = $("mark[name=returnAmount]").val();
        submitRefundData.operDesc = $("textarea[name=operDesc]").val();
        submitRefundData.attachment = attachmentList.join(",");

        submitRefundData.orderId = $("input[name=orderId]").val();
        submitRefundData.orderListIdList = $("input[name=orderListId]").val().split(",");
        submitRefundData.orderType = $("input[name=orderType]").val();

        console.log(submitRefundData);

        $.ajax({
            type: "POST",
            url: "/afterSale/applyAfterSale",
            data:{"modelJson":JSON.stringify(submitRefundData)},
            success:function(result){
                if (result.success){
                    layer.msg("申请成功！");
                    location.reload();
                }else {
                    layer.msg("申请失败！");
                }
            }
        })
    }
    
}
