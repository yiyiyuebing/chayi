package pub.makers.shop.purchaseOrder.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderExport;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderParams;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.IndentExport;
import pub.makers.shop.tradeOrder.vo.IndentInvoiceVo;
import pub.makers.shop.tradeOrder.vo.IndentParams;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/5/17.
 */
public interface PurchaseOrderMgrBizService {
    /**
     * 获取今日订单数量
     * @return
     */
    Long countTodayOrderNum();

    /**
     * 获取待付款数量
     * @return
     */
    Long countUnpayOrderNum();


    /**
     * 待发货数量
     * @return
     */
    Long countUnpostOrderNum();

    /**
     * 待处理售后数量
     * @return
     */
    Long countUnDealOrderNum();

    /**
     * 商品总金额
     * @return
     */
    BigDecimal indentTotalAmount();

    /**
     * 实收总金额金额
     * @return
     */
    BigDecimal indentPaymentAmount();

    /**
     * 实收总金额金额
     * @param todayDateStart
     * @param todayDateEnd
     * @return
     */
    BigDecimal indentPaymentAmountByDateRange(Date todayDateStart, Date todayDateEnd);

    /**
     * 获取订单列表
     * @param indentParams
     * @param pg
     * @return
     */
    ResultList<PurchaseOrderVo> purIndentList(PurchaseOrderParams indentParams, Paging pg);

    /**
     * 获取订单详情
     * @param id
     * @return
     */
    PurchaseOrderVo getDetailInfo(Long id);

    /**
     * 修改收货相关信息
     * @param indentParams
     * @return
     */
    Map<String,Object> modifyIndentReceiverInfo(PurchaseOrderParams indentParams);

    /**
     * 导出表格
     * @param params
     * @return
     */
    List<PurchaseOrderExport> exportExcel(PurchaseOrderParams params);

    /**
     * 售后详情
     * @param orderNo
     * @param goodSkuId
     * @param flowStatus
     * @param afterSaleService
     * @return
     */
    Map<String,Object> afterSaleIndentDetail(String orderNo, String goodSkuId, String flowStatus, String afterSaleService);

    /**
     * 修改备注
     * @param l
     * @param remark
     * @param remarkLevel
     */
    void updateRemark(long l, String remark, String remarkLevel);

    /**
     * 协商回复
     * @param orderFlowInfo
     */
    void addOrderFlowReply(Map<String, Object> orderFlowInfo);

    /**
     * 批量免运费
     * @param indentParams
     * @return
     */
    Map<String,Object> updateIndentCarriage(PurchaseOrderParams indentParams);

    /**
     * 获取发票导出列表
     * @param indentParams
     * @return
     */
    List<IndentInvoiceVo> getPurIndentInvoiceList(PurchaseOrderParams indentParams);


    /**
     * 发货
     * @param ids
     * @param shippingInfo
     * @return
     */
    ResultData shipIndent(String ids, ShippingInfo shippingInfo);

    PurchaseOrder findPurchaseOrderById(long id);

    /**
     * 批量取消订单
     * @param paramsList
     * @param orderBizType
     * @param userId
     * @return
     */
    ResultData batchCancelOrder(List<PurchaseOrderParams> paramsList, OrderBizType orderBizType, String userId);
}
