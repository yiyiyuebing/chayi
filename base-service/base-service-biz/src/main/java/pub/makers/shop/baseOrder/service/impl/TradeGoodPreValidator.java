package pub.makers.shop.baseOrder.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.OrderVerificationResult;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.baseOrder.service.OrderPreValidator;
import pub.makers.shop.tradeGoods.entity.GoodPackage;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.service.GoodPackageService;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;

/**
 * 商城商品的前置验证
 * 上架状态检查
 * @author apple
 *
 */
public class TradeGoodPreValidator implements OrderPreValidator{

	@Autowired
    private GoodPackageService goodPackageService;
	@Autowired
    private TradeGoodSkuService tradeGoodSkuService;
	@Autowired
    private TradeGoodService tradeGoodService;
	
	@Override
	public OrderVerificationResult validate(List<? extends BaseOrderItem> itemList, OrderBizType bizType,
			OrderType orderType, TradeContext tradeContext) {
		
		OrderVerificationResult result = null;
		if (OrderBizType.trade.equals(bizType)){
			
			for (BaseOrderItem item : itemList){
				if (isPackage(item.getGoodSkuId())) {
					result = checkPackage(item.getGoodSkuId());
		        } else {
		        	result = checkSingle(item);
		        }
				
				if (!result.isSuccess()){
					break;
				}
			}
		}
		
		return result == null ? OrderVerificationResult.createSuccess() : result;
	}

	private boolean isPackage(String skuId) {
        return Long.valueOf(skuId) <= 100000000000L;
    }
	
	private OrderVerificationResult checkPackage(String skuId) {

        GoodPackage p = goodPackageService.getByBoomId(skuId);
        if (p == null){
        	return OrderVerificationResult.createError("商品已下架");
        }
        return OrderVerificationResult.createSuccess();
        
    }
	
	/**
     * 检查单品
     *
     * @param detail
     */
    private OrderVerificationResult checkSingle(BaseOrderItem item) {

        Long skuId = Long.valueOf(item.getGoodSkuId());
        // 检查商品的库存
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        if (sku == null){
        	return OrderVerificationResult.createError("商品已下架");
        }

        TradeGood good = tradeGoodService.getById(sku.getGoodId());
        if (good == null){
        	return OrderVerificationResult.createError("商品已下架");
        }
        
        Long nowTs = new Date().getTime();

        if (good.getBeginTime() != null && good.getEndTime() != null && good.getEndTime().getTime() < nowTs) {

        	return OrderVerificationResult.createError("商品活动时间已结束，无法购买！");
        }
        
        return OrderVerificationResult.createSuccess();

    }
}
