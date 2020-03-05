package pub.makers.shop.promotion.plugin.sale;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.promotion.entity.SaleActivityGood;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.plugin.PromotionPlugin;
import pub.makers.shop.promotion.service.SaleActivityService;
import pub.makers.shop.promotion.service.SaleBizService;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 促销活动插件
 * @author apple
 *
 */
@Service
public class SalePromotionPlugin implements PromotionPlugin{

	@Autowired
	private SaleActivityService saleActivityService;
	@Autowired
	private SaleBizService saleBizService;
	@Autowired
	private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
	
	@Override
	public BaseOrder applyForCreateOrer(PromotionOrderQuery query, TradeContext tc) {
		
		BaseOrder order = query.getOrderInfo();
		// 克隆一份数据
		List<BaseOrderItem> itemList = Lists.newArrayList(order.getItemList());
		
		// 查询当前商品可用的促销活动
		Set<String> skuIdSet = ListUtils.getIdSet(itemList, "goodSkuId");
		List<PurchaseGoodsSku> sanchaList = purchaseGoodsSkuBizService.querySanchaSku(skuIdSet);
		Set<String> sanchaIds = ListUtils.getIdSet(sanchaList, "id");
		skuIdSet.removeAll(sanchaIds);
		List<SaleActivityGood> goodList = saleActivityService.filterAvailableSaleGood(skuIdSet);
		
		// 根据SKUID 转换成MAP
		Map<String, SaleActivityGood> goodMap = Maps.newHashMap();
		for (SaleActivityGood good : goodList){
			goodMap.put(good.getGoodSkuId(), good);
		}
		
		// 对订单商品根据原价进行打折
		for (BaseOrderItem item : itemList){
			SaleActivityGood saleGood = goodMap.get(item.getGoodSkuId());
			if (saleGood != null){
				item.setGoodPrice(saleGood.getActivityPrice());
				item.setSumAmount(saleGood.getActivityPrice().multiply(BigDecimal.valueOf(item.getBuyNum())));
				item.setWaitPayAmont(saleGood.getActivityPrice().multiply(BigDecimal.valueOf(item.getBuyNum())));
			}
		}
		
		order.setItemList(itemList);
		
		return order;
	}

	@Override
	public BaseOrder applyForPreviewOrder(PromotionOrderQuery query, TradeContext tc) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void applyForGoodDetail() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SalePromotionActivityVo> applyForGoodList(PromotionGoodQuery query) {
		Set<String> idSet = ListUtils.getIdSet(query.getGoodList(), "goodSkuId");
		List<PurchaseGoodsSku> sanchaList = purchaseGoodsSkuBizService.querySanchaSku(idSet);
		Set<String> sanchaIds = ListUtils.getIdSet(sanchaList, "id");
		idSet.removeAll(sanchaIds);
		List<SalePromotionActivityVo> resultList = saleBizService.listForGoodsSku(idSet);
		return resultList;
	}

	@Override
	public String getName() {
		
		return PromotionActivityType.sale.name();
	}

}
