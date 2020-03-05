package pub.makers.shop.baseGood.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuService;
import pub.makers.shop.tradeGoods.entity.TradeGiftRule;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.service.TradeGiftRuleService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;
@Service(version = "1.0.0")
public class GiftBizServiceImpl implements GiftBizService{

	@Autowired
	private TradeGoodSkuService tradeSkuService;
	
	@Autowired
	private PurchaseGoodsSkuService purchaseSkuService;
	
	@Autowired
    private TradeGiftRuleService giftRuleService;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<TradeGiftRule> queryGiftRules(OrderBizType orderBizType, String goodSkuId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("orderBizType", orderBizType);
		queryMap.put("goodSkuId", goodSkuId);

		String getGiftRuleListStmt = FreeMarkerHelper.getValueFromTpl("sql/gift/getGiftRuleList.sql", queryMap);
		// 优先应用采购/商城的赠品规则
		List<TradeGiftRule> ruleList = jdbcTemplate.query(getGiftRuleListStmt, ParameterizedBeanPropertyRowMapper.newInstance(TradeGiftRule.class));
		
		// 如果没有设置，则应用货品的赠品规则
		if (ruleList.size() == 0){
			String cargoSkuId = null;
			
			// 查询货品SKUID
			if (OrderBizType.trade.equals(orderBizType)){
				TradeGoodSku sku = tradeSkuService.getById(goodSkuId);
				if (sku != null){
					cargoSkuId = sku.getCargoSkuId() + "";
				}
			}
			else {
				PurchaseGoodsSku sku = purchaseSkuService.getById(goodSkuId);
				if (sku != null){
					cargoSkuId = sku.getCargoSkuId() + "";
				}
			}
			
			if (StringUtils.isNotBlank(cargoSkuId)){
				ruleList = giftRuleService.list(Conds.get().eq("goodSkuId", goodSkuId));
				
				// 将关联的SKUID设置为商品的SKUID，方便后续使用
				for (TradeGiftRule rule : ruleList){
					rule.setGoodSkuId(Long.valueOf(goodSkuId));
				}
			}
		}
		
		return ruleList;
	}

}
