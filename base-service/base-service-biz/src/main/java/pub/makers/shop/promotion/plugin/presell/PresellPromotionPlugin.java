package pub.makers.shop.promotion.plugin.presell;

import com.lantu.base.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.baseOrder.pojo.PromotionOrderQuery;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.plugin.PromotionPlugin;
import pub.makers.shop.promotion.service.PresellBizService;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;

import java.util.List;
import java.util.Set;

/**
 * 预售促销插件
 * 该插件只用于商品列表页和商品详情的标签展示使用
 * 对下单后的订单金额没有影响
 * @author apple
 *
 */
@Service
public class PresellPromotionPlugin implements PromotionPlugin{

	@Autowired
	private PresellBizService presellBizService;
	@Autowired
	private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;

	@Override
	public BaseOrder applyForCreateOrer(PromotionOrderQuery query, TradeContext tc) {

		// 预售对普通订单不产生影响
		return query.getOrderInfo();
	}

	@Override
	public BaseOrder applyForPreviewOrder(PromotionOrderQuery query, TradeContext tc) {

		// 预售对普通订单不产生影响
		return query.getOrderInfo();
	}

	@Override
	public List<PresellPromotionActivityVo> applyForGoodList(PromotionGoodQuery query) {

		Set<String> idSet = ListUtils.getIdSet(query.getGoodList(), "goodSkuId");
		List<PurchaseGoodsSku> sanchaList = purchaseGoodsSkuBizService.querySanchaSku(idSet);
		Set<String> sanchaIds = ListUtils.getIdSet(sanchaList, "id");
		idSet.removeAll(sanchaIds);
		List<PresellPromotionActivityVo> resultList = presellBizService.listForGoodsSku(idSet);

        // 对于商品列表,显示金额是预售价
        for (PresellPromotionActivityVo pvo : resultList) {
            pvo.setFinalAmount(pvo.getPresellAmount());
        }

        return resultList;
	}

	@Override
	public void applyForGoodDetail() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {

		return PromotionActivityType.presell.name();
	}

}
