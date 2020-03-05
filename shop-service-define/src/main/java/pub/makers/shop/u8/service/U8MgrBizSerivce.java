package pub.makers.shop.u8.service;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.u8.vo.U8OrderListVo;
import pub.makers.shop.u8.vo.U8OrderVo;

import java.util.List;

/**
 * Created by dy on 2017/6/24.
 */
public interface U8MgrBizSerivce {

    void u8OrderSync(String orderId, OrderType orderType, OrderBizType orderBizType, Integer stage);

    /**
     * 查询订单
     * @param orderId
     * @param orderBizType
     * @return
     */
    U8OrderVo getU8OrderVo(String orderId, OrderBizType orderBizType);

    /**
     * 查询订单明细
     * @param u8OrderVo
     * @param orderType
     * @return
     */
    List<U8OrderListVo> getU8OrderList(U8OrderVo u8OrderVo, String subbranchId, String orderType, String orderBizType);

    /**
     * 同步销售单
     * @return
     */
    String addU8Order();

    /**
     * 同步收款单
     * @return
     */
    String addU8Accept();


    String asychStockFromU8(String cInvCode) throws Exception;


}
