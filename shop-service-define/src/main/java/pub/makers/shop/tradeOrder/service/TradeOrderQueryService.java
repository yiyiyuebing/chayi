package pub.makers.shop.tradeOrder.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.pojo.TradeOrderQuery;
import pub.makers.shop.tradeOrder.vo.IndentExtendVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

import java.util.List;

/**
 * Created by kok on 2017/6/16.
 */
public interface TradeOrderQueryService {
    /**
     * 订单
     */
    Indent get(String id);

    /**
     * 订单列表
     */
    List<IndentVo> getOrderList(TradeOrderQuery query);

    /**
     * 查询店铺订单列表
     * @param shopId
     * @return
     */
    List<IndentExtendVo> listShopOrder(String shopId, String buyerId, String status, Paging pi);

    /**
     * 订单详情
     */
    IndentVo getOrderDetail(String id);
    
    /**
     * 查询订单的支付信息
     * @param orderId
     * @return
     */
    IndentVo queryPayInfo(String orderId);

    IndentVo getOrderDetailByOrderNo(String orderNo);
}
