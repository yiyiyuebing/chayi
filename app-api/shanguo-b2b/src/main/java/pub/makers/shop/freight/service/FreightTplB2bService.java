package pub.makers.shop.freight.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.common.util.IpUtil;
import pub.makers.common.util.vo.CityVo;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightTpl;
import pub.makers.shop.logistics.service.FreightTplBizService;
import pub.makers.shop.logistics.vo.FreightResultVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import sun.net.util.IPAddressUtil;

import java.util.List;

/**
 * Created by dy on 2017/6/8.
 */
@Service
public class FreightTplB2bService {

    @Reference(version = "1.0.0")
    private FreightTplBizService freightTplBizService;

    public FreightResultVo showPurchaseOptions(List<PurchaseOrderListVo> orderList, TradeContext tradeContext) {
        PurchaseOrderVo order = new PurchaseOrderVo();
        order.setOrderListVos(orderList);
        return freightTplBizService.queryPurchaseOrderOptions(order, tradeContext);
    }

    public FreightTpl queryPrimaryTpl(String skuId, TradeContext context, String relType){

        return freightTplBizService.queryPrimaryTpl(skuId, context, relType);
    }

    public TradeContext getIpCity(String ip) {
        CityVo cityVo = IpUtil.getIpCity(ip);
        TradeContext tradeContext = new TradeContext();
        if (StringUtils.isNotBlank(cityVo.getCity())) {
            tradeContext.setCity(cityVo.getCity());
        }
        if (StringUtils.isNotBlank(cityVo.getArea())) {
            tradeContext.setCity(cityVo.getArea());
        }
        return tradeContext;
    }
}
