package pub.makers.shop.afterSale.controller;

import com.dev.base.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.afterSale.enums.FlowTargetType;
import pub.makers.shop.afterSale.enums.OrderAsType;
import pub.makers.shop.afterSale.service.AfterSaleAppService;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.afterSale.vo.OrderItemReplyVo;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.vo.SalesReturnReasonVo;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/5/18.
 */
@Controller
@RequestMapping("afterSale")
public class AfterSaleController {
    @Autowired
    private AfterSaleAppService afterSaleAppService;

    /**
     * 退款
     */
    @RequestMapping("refund")
    @ResponseBody
    public ResultData refund(String ids, String mapStr, String username) {
        AfterSaleApply apply = new AfterSaleApply();
        apply.setOrderId(ids);
        apply.setAsType(OrderAsType.refund);
        apply.setFlowTargetType(FlowTargetType.order);
        Map<String, String> map = JsonUtils.toObject(mapStr, Map.class);
        apply.setReturnReason(map.get("content"));
        afterSaleAppService.applyAfterSale(apply);
        return ResultData.createSuccess();
    }

    /**
     * 退货
     */
    @RequestMapping("return")
    @ResponseBody
    public ResultData returnApply(String ids, String mapStr, String username) {
        AfterSaleApply apply = new AfterSaleApply();
        apply.setOrderId(ids);
        apply.setAsType(OrderAsType.refund_return);
        apply.setFlowTargetType(FlowTargetType.order);
        Map<String, String> map = JsonUtils.toObject(mapStr, Map.class);
        apply.setReturnReason(map.get("content"));
        afterSaleAppService.applyAfterSale(apply);
        return ResultData.createSuccess();
    }

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
        afterSaleAppService.applyAfterSale(apply);
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
        afterSaleAppService.applyAfterSale(apply);
        return ResultData.createSuccess();
    }

    /**
     * 修改申请
     */
    @RequestMapping("updateAfterSale")
    @ResponseBody
    public ResultData updateAfterSale(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        afterSaleAppService.updateAfterSale(apply);
        return ResultData.createSuccess();
    }

    /**
     * 取消申请
     */
    @RequestMapping("cancelApply")
    @ResponseBody
    public ResultData cancelApply(String flowId) {

        afterSaleAppService.cancelApply(flowId);
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
        afterSaleAppService.editUserShipping(apply);
        return ResultData.createSuccess();
    }

    /**
     * 协商信息列表
     */
    @RequestMapping("getOrderReplyList")
    @ResponseBody
    public ResultData getOrderReplyList(String orderId, String skuId) {
        List<OrderItemReplyVo> voList = afterSaleAppService.getOrderReplyList(orderId, skuId);
        return ResultData.createSuccess("replyList", voList);
    }

    /**
     * 退款
     */
    @RequestMapping("submitRefund")
    @ResponseBody
    public ResultData submitRefund(String modelJson) {
        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        afterSaleAppService.submitRefund(apply);
        return ResultData.createSuccess();
    }

    /**
     * 退款理由
     */
    @RequestMapping("getReturnReason")
    @ResponseBody
    public ResultData getReturnReason() {
        List<SalesReturnReasonVo> list = afterSaleAppService.getReturnReason();
        return ResultData.createSuccess("reasonList", list);
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

        AfterSaleTkResult result = afterSaleAppService.queryTkResult(apply);

        return ResultData.createSuccess(result);
    }
}
