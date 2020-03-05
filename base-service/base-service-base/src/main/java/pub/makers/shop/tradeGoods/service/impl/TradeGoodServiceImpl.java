package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.tradeGoods.dao.TradeGoodDao;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeGoods.entity.TradeGood;

@Service
public class TradeGoodServiceImpl extends BaseCRUDServiceImpl<TradeGood, String, TradeGoodDao>
										implements TradeGoodService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public TradeGood updateSaleNum(Long goodId, int num) {
		
		TradeGood good = getById(goodId);
		Integer saleNum = good.getSaleNum();
		saleNum = saleNum == null ? 0 : saleNum;
		
		update(Update.byId(goodId).set("sale_num", saleNum + num));
		
		return good;
	}

	public String queryGoodImageByCargoId(Long cargoId) {
		
		String image = null;
		try{
			image = jdbcTemplate.queryForObject("select pic_url from image where group_id = (select mobile_album_id from cargo where id = ?) limit 0, 1", String.class, cargoId);
		}
		catch (Exception e){
			
		}
		
		return image;
	}

	@Override
	public TradeGood getBySkuId(String skuId) {
		
		return ListUtils.getSingle(jdbcTemplate.query("select * from trade_good where id = (select good_id from trade_good_sku where id = ?)", new BeanPropertyRowMapper<TradeGood>(TradeGood.class), skuId));
	}
	
}
