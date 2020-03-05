package pub.makers.shop.afterSale.controller;

import com.dev.base.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.afterSale.enums.FlowTargetType;
import pub.makers.shop.afterSale.service.WeixinTradeAfterSaleAppService;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.afterSale.vo.OrderItemReplyVo;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.vo.SalesReturnReasonVo;

import java.util.List;

/**
 * Created by kok on 2017/6/16.
 */
@Controller
@RequestMapping("weixin/afterSale")
public class WeixinTradeAfterSaleController {
    @Autowired
    private WeixinTradeAfterSaleAppService weixinTradeAfterSaleAppService;

    /**
     * 申请售后
     */
    @RequestMapping("applyAfterSale")
    @ResponseBody
    public ResultData applyAfterSale(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        apply.setFlowTargetType(FlowTargetType.list);
        weixinTradeAfterSaleAppService.applyAfterSale(apply);
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
        weixinTradeAfterSaleAppService.applyAfterSale(apply);
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
        weixinTradeAfterSaleAppService.updateAfterSale(apply);
        return ResultData.createSuccess();
    }

    /**
     * 撤销申请
     */
    @RequestMapping("cancelApply")
    @ResponseBody
    public ResultData cancelApply(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        weixinTradeAfterSaleAppService.cancelApply(apply);
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
        
        AfterSaleTkResult result = weixinTradeAfterSaleAppService.queryTkResult(apply);
        
        return ResultData.createSuccess(result);
    }

    /**
     * 修改用户物流信息
     */
    @RequestMapping("editUserShipping")
    @ResponseBody
    public ResultData editUserShipping(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        weixinTradeAfterSaleAppService.editUserShipping(apply);
        return ResultData.createSuccess();
    }

    /**
     * 协商信息列表
     */
    @RequestMapping("getOrderReplyList")
    @ResponseBody
    public ResultData getOrderReplyList(String orderId, String skuId) {
        List<OrderItemReplyVo> voList = weixinTradeAfterSaleAppService.getOrderReplyList(orderId, skuId);
        return ResultData.createSuccess("replyList", voList);
    }

    /**
     * 退款理由
     */
    @RequestMapping("getReturnReason")
    @ResponseBody
    public ResultData getReturnReason() {
        List<SalesReturnReasonVo> list = weixinTradeAfterSaleAppService.getReturnReason();
        return ResultData.createSuccess("reasonList", list);
    }
}
