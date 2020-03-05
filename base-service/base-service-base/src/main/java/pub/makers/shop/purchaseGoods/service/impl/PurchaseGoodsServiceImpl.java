package pub.makers.shop.purchaseGoods.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseGoodsDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;

@Service
public class PurchaseGoodsServiceImpl extends BaseCRUDServiceImpl<PurchaseGoods, String, PurchaseGoodsDao>
										implements PurchaseGoodsService{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public PurchaseGoods getByMaterialSkuId(String skuId) {
		
		List<PurchaseGoods> dataList = dao.findBySql("select * from purchase_goods where id in (select pur_goods_id from purchase_goods_sku where material_sku_id = ?)", skuId);
		
		return ListUtils.getSingle(dataList);
	}

	@Override
	public String queryGoodImageByCargoId(Long cargoId) {

		String image = jdbcTemplate.queryForObject("select pic_url from image where id in (select small_image_id from cargo where id = ?) ", String.class, cargoId);

		return image;
	}

	@Override
	public PurchaseGoods getByPurGoodsSkuId(String purGoodsSkuId) {
		
		List<PurchaseGoods> dataList = dao.findBySql("select * from purchase_goods where id in (select pur_goods_id from purchase_goods_sku where id = ?)", purGoodsSkuId);
		
		return ListUtils.getSingle(dataList);
	}

	@Override
	public Map<String, PurchaseGoodsVo> listBySkuId(Set<String> skuIds) {
		
		String stmt = String.format("select a.*, b.id sku_id from purchase_goods a, purchase_goods_sku b where a.id = b.pur_goods_id and b.id in (%s)", Joiner.on(",").join(skuIds));
		
		List<PurchaseGoodsVo> dataList = jdbcTemplate.query(stmt, new BeanPropertyRowMapper<PurchaseGoodsVo>(PurchaseGoodsVo.class));
		
		return ListUtils.toKeyMap(dataList, "skuId");
	}

}
