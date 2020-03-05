package pub.makers.shop.baseOrder.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.baseOrder.entity.OrderListPresellExtra;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.vo.OrderListPresellExtraVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/7/31.
 */
@Service(version = "1.0.0")
public class OrderListPresellExtraQueryServiceImpl implements OrderListPresellExtraQueryService {
    @Resource(name = "purchaseOrderListPresellExtraServiceImpl")
    private OrderListPresellExtraService purchaseOrderListPresellExtraService;
    @Resource(name = "tradeOrderListPresellExtraServiceImpl")
    private OrderListPresellExtraService tradeOrderListPresellExtraService;

    @Override
    public Map<String, Map<String, OrderListPresellExtraVo>> getListPresellExtra(List<String> orderIdList, OrderBizType orderBizType) {
        OrderListPresellExtraService orderListPresellExtraService = getService(orderBizType);
        List<OrderListPresellExtra> extraList = orderListPresellExtraService.list(Conds.get().in("order_id", orderIdList));
        Map<String, Map<String, OrderListPresellExtraVo>> extraVoMap = Maps.newHashMap();
        for (OrderListPresellExtra extra : extraList) {
            Map<String, OrderListPresellExtraVo> skuExtraVoMap = extraVoMap.get(extra.getOrderId()) == null ? Maps.newHashMap() : extraVoMap.get(extra.getOrderId());
            OrderListPresellExtraVo extraVo = new OrderListPresellExtraVo();
            BeanUtils.copyProperties(extra, extraVo);
            skuExtraVoMap.put(extra.getGoodSkuId(), extraVo);
            extraVoMap.put(extra.getOrderId(), skuExtraVoMap);
        }
        return extraVoMap;
    }

    private OrderListPresellExtraService getService(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return purchaseOrderListPresellExtraService;
        } else {
            return tradeOrderListPresellExtraService;
        }
    }
}
