package pub.makers.shop.tradeGoods.service.impl;

import com.lantu.base.util.ListUtils;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeGoods.dao.TradeGoodExtraDao;
import pub.makers.shop.tradeGoods.service.TradeGoodExtraService;
import pub.makers.shop.tradeGoods.entity.TradeGoodExtra;

import java.util.List;

@Service
public class TradeGoodExtraServiceImpl extends BaseCRUDServiceImpl<TradeGoodExtra, String, TradeGoodExtraDao>
										implements TradeGoodExtraService {

	public TradeGoodExtra getByGoodId(String goodId) {
		
		return get(Conds.get().eq("goodId", goodId));
	}

	public TradeGoodExtra getByGoodSkuId(String goodSkuId) {
		
		List<TradeGoodExtra> dataList = dao.findBySql("select * from trade_good_extra where good_id = (select good_id from trade_good_sku where id = ?)", goodSkuId);
		
		return ListUtils.getSingle(dataList);
	}
	
}
