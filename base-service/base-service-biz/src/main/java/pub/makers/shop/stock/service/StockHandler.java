package pub.makers.shop.stock.service;


import java.util.List;
import java.util.Map;

/**
 * 库存操作接口
 * @author apple
 *
 */
public interface StockHandler {

	
	/**
	 * 更新上架数量
	 */
	void updateOnsalesNo(Long skuId, Integer num);
	
	
	/**
	 * 修改商家数量
	 */
	void appendOnsalesNo(Long skuId, Integer num);

	/**
	 * 已上架未售->未上架
	 */
	void salesToOut(Long skuId, Integer num);

	/**
	 * 已上架未售->已售未付款
	 */
	void salesToPay(Long skuId, Integer num);

	/**
	 * 已售未付款->已上架未售
	 */
	void payToSales(Long skuId, Integer num);
	
	/**
	 * 已售未付款->已售未发货
	 */
	void payToSend(Long skuId, Integer num);

	/**
	 * 已售未发货->已上架未售
	 */
	void sendToSales(Long skuId, Integer num);
	
	
	/**
	 * 已售未发货->已发货
	 */
	void sendToSeller(Long skuId, Integer num);

	/**
	 * 获取已上架未售库存
	 */
	Integer getSalesNum(Long skuId);

	/**
	 * 批量获取已上架未售库存
	 */
	Map<String, Integer> getSalesNum(List<Long> skuIdList);

	/**
	 * 获取货品id
	 */
	Long getCargoSkuId(Long skuId);
}
