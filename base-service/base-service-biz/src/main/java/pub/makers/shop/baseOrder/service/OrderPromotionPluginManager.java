package pub.makers.shop.baseOrder.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.promotion.plugin.PromotionPlugin;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 订单促销插件管理器
 * 
 * @author apple
 *
 */
@Service
public class OrderPromotionPluginManager {

	private final Multimap<String, PromotionPlugin> giftPluginMap = ArrayListMultimap.create();
	private final Multimap<String, PromotionPlugin> pricePluginMap = ArrayListMultimap.create();
	private final Multimap<String, PromotionPlugin> totalPricePluginMap = ArrayListMultimap.create();
	private final Multimap<String, PromotionPlugin> allPluginMap = ArrayListMultimap.create();
	@Resource(name = "salePromotionPlugin")
	private PromotionPlugin salePromotionPlugin;
	@Resource(name = "giftPromotionPlugin")
	private PromotionPlugin giftPromotionPlugin;
	@Resource(name = "presellPromotionPlugin")
	private PromotionPlugin presellPromotionPlugin;
	@Resource(name = "zengpinPromotionPlugin")
	private PromotionPlugin zengpinPromotionPlugin;

	public synchronized Collection<PromotionPlugin> getGiftPlungins(OrderBizType orderBizType, OrderType orderType) {
		if (giftPluginMap.size() ==0l){
			initGift();
		}
		
		return giftPluginMap.get(getKey(orderBizType, orderType));
	}
	
	public synchronized Collection<PromotionPlugin> getPricePlungins(OrderBizType orderBizType, OrderType orderType) {
		if (pricePluginMap.size() ==0l){
			initPrice();
		}
		
		return pricePluginMap.get(getKey(orderBizType, orderType));
	}
	
	public synchronized Collection<PromotionPlugin> getTotalPricePlungins(OrderBizType orderBizType, OrderType orderType) {
		if (totalPricePluginMap.size() == 0) {
			initTotalPrice();
		}

		return totalPricePluginMap.get(getKey(orderBizType, orderType));
	}
	
	public synchronized Collection<PromotionPlugin> getAlllplungins(OrderBizType orderBizType, OrderType orderType) {
		if (allPluginMap.size() ==0l){
			initAll();
		}
		
		return allPluginMap.get(getKey(orderBizType, orderType));
	}

	private void initGift() {

		// 赠品规则应用商城和采购普通订单
		giftPluginMap.put(getKey(OrderBizType.trade, OrderType.normal), giftPromotionPlugin);
		giftPluginMap.put(getKey(OrderBizType.purchase, OrderType.normal), giftPromotionPlugin);
		giftPluginMap.put(getKey(OrderBizType.trade, OrderType.presell), giftPromotionPlugin);
		giftPluginMap.put(getKey(OrderBizType.purchase, OrderType.presell), giftPromotionPlugin);

		// 满赠规则应用于商城和采购普通订单
//
//		// 预售规则
//		giftPluginMap.put(getKey(OrderBizType.trade, OrderType.normal), presellPromotionPlugin);
//		giftPluginMap.put(getKey(OrderBizType.purchase, OrderType.normal), presellPromotionPlugin);
	}
	
	/**
	 * 初始化价格插件
	 */
	private void initPrice() {
		
		// 限时打折插件应用于采购和商城
		pricePluginMap.put(getKey(OrderBizType.trade, OrderType.normal), salePromotionPlugin);
		pricePluginMap.put(getKey(OrderBizType.purchase, OrderType.normal), salePromotionPlugin);
	}

	/**
	 * 初始化订单总价插件
	 */
	private void initTotalPrice() {
		// 满减满赠插件应用雨采购和商城，普通和预售订单
		totalPricePluginMap.put(getKey(OrderBizType.trade, OrderType.normal), zengpinPromotionPlugin);
		totalPricePluginMap.put(getKey(OrderBizType.purchase, OrderType.normal), zengpinPromotionPlugin);
		totalPricePluginMap.put(getKey(OrderBizType.trade, OrderType.presell), zengpinPromotionPlugin);
		totalPricePluginMap.put(getKey(OrderBizType.purchase, OrderType.presell), zengpinPromotionPlugin);
	}
	
	/**
	 * 初始化全部插件
	 */
	private void initAll(){
		
		// 赠品规则应用商城和采购普通订单
//		allPluginMap.put(getKey(OrderBizType.trade, OrderType.normal), giftPromotionPlugin);
//		allPluginMap.put(getKey(OrderBizType.purchase, OrderType.normal), giftPromotionPlugin);
		
		// 满赠规则应用于商城和采购普通订单

		// 预售规则
		allPluginMap.put(getKey(OrderBizType.trade, OrderType.normal), presellPromotionPlugin);
		allPluginMap.put(getKey(OrderBizType.purchase, OrderType.normal), presellPromotionPlugin);
		
		// 限时打折插件应用于采购和商城
		allPluginMap.put(getKey(OrderBizType.trade, OrderType.normal), salePromotionPlugin);
		allPluginMap.put(getKey(OrderBizType.purchase, OrderType.normal), salePromotionPlugin);

		// 满减满赠规则应用于采购和商城
		allPluginMap.put(getKey(OrderBizType.trade, OrderType.normal), zengpinPromotionPlugin);
		allPluginMap.put(getKey(OrderBizType.purchase, OrderType.normal), zengpinPromotionPlugin);
	}

	private String getKey(OrderBizType orderBizType, OrderType orderType) {

		return String.format("%s_%s", orderBizType.name(), orderType.name());
	}
}
