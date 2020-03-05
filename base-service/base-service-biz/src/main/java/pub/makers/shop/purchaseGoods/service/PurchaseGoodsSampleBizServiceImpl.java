package pub.makers.shop.purchaseGoods.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSample;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSampleVo;

/**
 * Created by dy on 2017/6/24.
 */
@Service(version = "1.0.0")
public class PurchaseGoodsSampleBizServiceImpl implements PurchaseGoodsSampleBizService {

    @Autowired
    private PurchaseGoodsSampleService purchaseGoodsSampleService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PurchaseGoodsSample getPurGoodsSampleByGoodsId(String goodsId) {
        return purchaseGoodsSampleService.get(Conds.get().eq("pur_goods_id", goodsId));
    }

	@Override
	public Map<String, PurchaseGoodsSampleVo> findBySkus(Set<String> skuIds) {

		if (skuIds.isEmpty()){
			skuIds.add("-1");
		}
		Map<String, Object> dataModel = Maps.newHashMap();
		dataModel.put("skuIds", StringUtils.join(skuIds, ","));
		String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGoodsSample/findBySkus.sql", dataModel);
		List<PurchaseGoodsSampleVo> skuList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoodsSampleVo.class));
		
		return ListUtils.toKeyMap(skuList, "goodSkuId");
	}
}
