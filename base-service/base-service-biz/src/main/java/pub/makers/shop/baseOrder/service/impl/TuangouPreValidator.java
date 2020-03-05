package pub.makers.shop.baseOrder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.OrderVerificationResult;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.baseOrder.service.OrderPreValidator;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;

import java.util.List;

@Service("tuangouPreValidator")
public class TuangouPreValidator implements OrderPreValidator{

	
	@Autowired
    private TradeGoodSkuService goodSkuService;
	@Autowired
    private TradeGoodService goodService;
	
	
	@Override
	public OrderVerificationResult validate(List<? extends BaseOrderItem> itemList, OrderBizType bizType,
			OrderType orderType, TradeContext tradeContext) {
		
		OrderVerificationResult result = null;
		// 团购规则只应用于商城
		if (OrderBizType.trade.equals(bizType)){
			
			for (BaseOrderItem item : itemList){
				TradeGoodSku dbSku = goodSkuService.getById(item.getGoodSkuId());
				if (dbSku != null && dbSku.getMinTuanNum() != null && dbSku.getMinTuanNum() > 0 && dbSku.getMinTuanNum() > item.getBuyNum()){
					TradeGood good = goodService.getById(dbSku.getGoodId());
					result = OrderVerificationResult.createError(
	                        String.format("团购商品:商品%s最少需要购买%d份", good.getName(), dbSku.getMinTuanNum()));
					
					break;
					
				}
			}
			
		}
		
		if (result == null) {
			result = OrderVerificationResult.createSuccess();
		}
		
		return result;
	}

}
