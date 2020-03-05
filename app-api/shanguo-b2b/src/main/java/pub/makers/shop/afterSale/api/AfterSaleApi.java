package pub.makers.shop.afterSale.api;

import com.dev.base.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.afterSale.enums.FlowTargetType;
import pub.makers.shop.afterSale.service.AfterSaleB2bService;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.afterSale.vo.OrderItemReplyVo;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.store.vo.SalesReturnReasonVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;

/**
 * Created by kok on 2017/7/17.
 */
@Controller
@RequestMapping("weixin/afterSale")
public class AfterSaleApi {
    @Autowired
    private AfterSaleB2bService afterSaleB2bService;

    /**
     * 申请售后
     *
     * @return
     */
    @RequestMapping("applyAfterSale")
    @ResponseBody
    public ResultData applyAfterSale(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        apply.setFlowTargetType(FlowTargetType.list);
        afterSaleB2bService.applyAfterSale(apply);
        return ResultData.createSuccess();
    }

    /**
     * 申请售后
     */
    @RequestMapping("applyOrderAfterSale")
    @ResponseBody
    public ResultData applyOrderAfterSale(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        apply.setFlowTargetType(FlowTargetType.order);
        afterSaleB2bService.applyAfterSale(apply);
        return ResultData.createSuccess();
    }

    /**
     * 查询最大可售后金额
     */
    @RequestMapping("queryTkResult")
    @ResponseBody
    public ResultData queryTkResult(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        ValidateUtils.notNull(apply.getOrderType(), "订单类型不能为空");
        ValidateUtils.notNull(apply.getOrderId(), "订单ID不能为空");

        AfterSaleTkResult result = afterSaleB2bService.queryTkResult(apply);

        return ResultData.createSuccess(result);
    }

    /**
     * 退款理由
     */
    @RequestMapping("getReturnReason")
    @ResponseBody
    public ResultData getReturnReason() {
        List<SalesReturnReasonVo> list = afterSaleB2bService.getReturnReason();
        return ResultData.createSuccess("reasonList", list);
    }

    /**
     * 修改申请
     */
    @RequestMapping("updateAfterSale")
    @ResponseBody
    public ResultData updateAfterSale(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        afterSaleB2bService.updateAfterSale(apply);
        return ResultData.createSuccess();
    }

    /**
     * 取消申请
     */
    @RequestMapping("cancelApply")
    @ResponseBody
    public ResultData cancelApply(String flowId) {

        afterSaleB2bService.cancelApply(flowId);
        return ResultData.createSuccess();
    }

    /**
     * 修改用户物流信息
     *
     * @return
     */
    @RequestMapping("editUserShipping")
    @ResponseBody
    public ResultData editUserShipping(String flowId, String freightNo, String freightCompany) {
        ValidateUtils.notNull(flowId, "请输入售后id");
        ValidateUtils.notNull(freightNo, "请输入物流单号");
        ValidateUtils.notNull(freightCompany, "请输入物流公司");

        AfterSaleApply apply = new AfterSaleApply();
        apply.setFlowId(flowId);
        apply.setFreightNo(freightNo);
        apply.setFreightCompany(freightCompany);
        afterSaleB2bService.editUserShipping(apply);
        return ResultData.createSuccess();
    }

    /**
     * 协商信息列表
     */
    @RequestMapping("getOrderReplyList")
    @ResponseBody
    public ResultData getOrderReplyList(String orderId, String skuId) {
        List<OrderItemReplyVo> voList = afterSaleB2bService.getOrderReplyList(orderId, skuId);
        return ResultData.createSuccess("replyList", voList);
    }

    /**
     * 售后订单数量
     *
     * @return
     */
    @RequestMapping("getOrderNum")
    @ResponseBody
    public ResultData afterSaleInfoList() {
        //    PurchaseOrderQuery orderQuery = JsonUtils.toObject(orderJson, PurchaseOrderQuery.class);
        PurchaseOrderQuery orderQuery = new PurchaseOrderQuery();
        orderQuery.setUserId(AccountUtils.getCurrShopId());
        return afterSaleB2bService.afterSaleInfoList(orderQuery);
    }

}
