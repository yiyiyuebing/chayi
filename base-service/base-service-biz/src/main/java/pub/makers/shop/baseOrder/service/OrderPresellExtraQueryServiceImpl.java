package pub.makers.shop.baseOrder.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.vo.OrderPresellExtraVo;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.promotion.service.PresellActivityService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/26.
 */
@Service(version = "1.0.0")
public class OrderPresellExtraQueryServiceImpl implements OrderPresellExtraQueryService {
    @Resource(name = "purchaseOrderPresellExtraServiceImpl")
    private OrderPresellExtraService purchaseOrderPresellExtraService;
    @Resource(name = "tradeOrderPresellExtraServiceImpl")
    private OrderPresellExtraService tradeOrderPresellExtraService;
    @Autowired
    private PresellActivityService presellActivityService;

    @Override
    public OrderPresellExtraVo getPresellExtra(String orderId, OrderBizType orderBizType) {
        OrderPresellExtraService orderPresellExtraService = getService(orderBizType);
        OrderPresellExtra extra = orderPresellExtraService.get(Conds.get().eq("order_id", orderId));
        OrderPresellExtraVo extraVo = new OrderPresellExtraVo();
        BeanUtils.copyProperties(extra, extraVo);
        PresellActivity activity = presellActivityService.getById(extra.getPresellActivityId());
        extraVo.setPaymentStart(activity.getPaymentStart());
        extraVo.setPaymentEnd(activity.getPaymentEnd());
        extraVo.setShipTime(activity.getShipTime());
        return extraVo;
    }

    @Override
    public Map<String, OrderPresellExtraVo> getPresellExtraMap(List<String> orderIdList, OrderBizType orderBizType) {
        OrderPresellExtraService orderPresellExtraService = getService(orderBizType);
        List<OrderPresellExtra> extraList = orderPresellExtraService.list(Conds.get().in("order_id", orderIdList));
        List<PresellActivity> activityList = presellActivityService.list(Conds.get().in("id", ListUtils.getIdSet(extraList, "presellActivityId")));
        Map<String, PresellActivity> activityMap = ListUtils.toKeyMap(activityList, "id");
        Map<String, OrderPresellExtraVo> extraVoMap = Maps.newHashMap();
        for (OrderPresellExtra extra : extraList) {
            OrderPresellExtraVo extraVo = new OrderPresellExtraVo();
            BeanUtils.copyProperties(extra, extraVo);
            PresellActivity activity = activityMap.get(extra.getPresellActivityId());
            extraVo.setPaymentStart(activity.getPaymentStart());
            extraVo.setPaymentEnd(activity.getPaymentEnd());
            extraVo.setShipTime(activity.getShipTime());
            extraVoMap.put(extraVo.getOrderId(), extraVo);
        }
        return extraVoMap;
    }

    private OrderPresellExtraService getService(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return purchaseOrderPresellExtraService;
        } else {
            return tradeOrderPresellExtraService;
        }
    }
}
