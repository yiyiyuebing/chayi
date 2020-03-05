package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.tradeGoods.dao.TradeGoodSkuDao;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;

@Service
public class TradeGoodSkuServiceImpl extends BaseCRUDServiceImpl<TradeGoodSku, String, TradeGoodSkuDao>
										implements TradeGoodSkuService {
	
	public void updateSaleNum(String skuId, int num) {
		
		TradeGoodSku sku = getById(skuId);
		// 当前剩余数量
		Long nowNum = sku.getNums();
		nowNum = nowNum == null ? 0 : nowNum;
		Integer nowSaleNum = sku.getSaleNum();
		nowSaleNum = nowSaleNum == null ? 0 : nowSaleNum;
		
		// 更新销售数量
		update(Update.byId(skuId).set("nums", nowNum - num).set("sale_num", nowSaleNum + num));
	}

	public int getOnSaleNum(String skuId) {
		
		TradeGoodSku sku = getById(skuId);
		Long nowNum = sku.getNums();
		nowNum = nowNum == null ? 0 : nowNum;
		
		return nowNum.intValue();
	}
	
}
