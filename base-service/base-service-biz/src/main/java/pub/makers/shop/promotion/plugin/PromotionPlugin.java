package pub.makers.shop.promotion.plugin;

import java.util.List;

import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.baseOrder.pojo.PromotionOrderQuery;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.promotion.vo.PromotionActivityVo;

/**
 * 促销插件接口
 * @author apple
 *
 */
public interface PromotionPlugin {

	
	/**
	 * 下单时应用促销规则
	 * @param query
	 * @param tc
	 * @return
	 */
	BaseOrder applyForCreateOrer(PromotionOrderQuery query, TradeContext tc);
	
	/**
	 * 在购物车查看促销结果的接口
	 * @param query
	 * @param tc
	 * @return
	 */
	BaseOrder applyForPreviewOrder(PromotionOrderQuery query, TradeContext tc);
	
	/**
	 * 在商品列表返回促销信息
	 */
	List<? extends PromotionActivityVo> applyForGoodList(PromotionGoodQuery query);
	
	/**
	 * 在商品详情返回促销信息
	 */
	void applyForGoodDetail();
	
	/**
	 * 获取插件的名称
	 * @return
	 */
	String getName();
}
