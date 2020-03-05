package pub.makers.shop.tradeGoods.service;

import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.tradeGoods.entity.TradeGiftRule;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodExtra;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.vo.GoodSearchInfo;
import pub.makers.shop.tradeGoods.vo.TradeGoodVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;

import java.util.List;
import java.util.Map;

/**
 * 商品业务服务
 * @author apple
 *
 */
public interface TradeGoodBizService {

	
	ResultList<Map<String, Object>> listByParams(TradeGood params, Paging paging);
	
	/**
	 * 根据商品ID查询商品扩展信息
	 * @param goodId
	 * @return
	 */
	TradeGoodExtra getByGoodId(String goodId);
	
	/**
	 * 根据商品ID查询商品信息
	 * @param goodId
	 * @return
	 */
	TradeGood getGoodById(String goodId);

	/**
	 * 根据商品id查询商品详细信息
	 * @param goodId
	 * @return
	 */
	TradeGoodVo getGoodVoById(String goodId, ClientType clientType);

	/**
	 * 根据商品id查询商品详细信息
	 * @param goodId
	 * @return
	 */
	TradeGoodVo getGoodVoById(String goodId, String storeLevelId, ClientType clientType);
	
	/**
	 * 锁定SKU库存
	 * @param skuId
	 * @param num
	 * @return
	 */
	TradeGoodSku lockStore(String skuId, int num);
	
	/**
	 * 应用赠品规则
	 * @param indentList
	 * @return
	 */
	List<IndentListVo> applyGiftRule(List<IndentListVo> indentList);
	
	/**
	 * 保存商品的关联赠品
	 * @param tradeGiftRule
	 * @return
	 */
	TradeGiftRule save(TradeGiftRule tradeGiftRule);
	
	/**
	 * 根据商品ID删除赠品
	 * @param tradeGoodId
	 * @return
	 */
	void deleteById(String tradeGoodId);
	
	/**
	 * 根据商品id查询该商品是否已有关联赠品
	 * @param tradeGoodId
	 * @return
	 */
	List<TradeGiftRule> queryGiftByGoodId(String tradeGoodId);

	/**
	 * 商品列表
	 * @param goodSearchInfo
	 * @return
	 */
	List<BaseGoodVo> getSearchGoodListSer(GoodSearchInfo goodSearchInfo);

	/**
	 * 新保存赠品
	 * @param tradeGiftRuleList
	 * @param skuId
	 */
	void saveOrUpdateGift(List<TradeGiftRule> tradeGiftRuleList, String skuId);

	/**
	 * 通过skuid查询赠品信息
	 * @param skuId
	 * @return
	 */
	List<TradeGiftRule> getGiftData(String skuId);

	/**
	 * 更新分类启用标识
	 */
	void updateClassifyValid();
}
