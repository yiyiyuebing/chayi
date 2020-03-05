package pub.makers.shop.logistics.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import pub.makers.shop.cargo.entity.Cargo;
import pub.makers.shop.cargo.service.CargoService;
import pub.makers.shop.logistics.entity.FreightTplRel;
import pub.makers.shop.logistics.service.FreightTplRelTypeService;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsService;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeOrder.vo.IndentListVo;


/**
 * 分类规则订单商品选择器
 * @author jlr_6
 *
 */
@Service
public class ClassifyRelTypeServiceImpl implements FreightTplRelTypeService{

	@Autowired
	private CargoService cargoService;
	@Autowired
	private PurchaseGoodsService purchaseGoodsService;
	@Autowired
	private TradeGoodService gradeGoodService;
	
	public List<IndentListVo> selectGood(List<IndentListVo> ivList, FreightTplRel rel) {
		
		List<IndentListVo> resultList = Lists.newArrayList();
		Set<String> classifySet = Sets.newHashSet(rel.getRelIds().split(","));
		
		for (IndentListVo ilv : ivList){
			TradeGood good = gradeGoodService.getBySkuId(ilv.getTradeGoodSkuId());
			Set<String> goodGroups = Sets.newHashSet(good.getGroupId().split(","));
			// 计算交集
			goodGroups.retainAll(classifySet);
			if (goodGroups.size() > 0){
				resultList.add(ilv);
			}
//			Cargo cargo = cargoService.getBySkuId(ilv.getTradeGoodSkuId());
//			if (classifySet.contains(cargo.getClassifyId() + "")){
//				resultList.add(ilv);
//			}
		}
		
		return resultList;
	}

	@Override
	public List<IndentListVo> selectPurchaseGoods(List<IndentListVo> ivList, FreightTplRel rel) {
		
		List<IndentListVo> resultList = Lists.newArrayList();
		Set<String> classifySet = Sets.newHashSet(rel.getRelIds().split(","));
		for (IndentListVo ilv : ivList){
			PurchaseGoods pg = purchaseGoodsService.getByPurGoodsSkuId(ilv.getTradeGoodSkuId());
			// 当提交的商品是样品的情况，pg是空值
			if (pg != null && StringUtils.isNotBlank(pg.getGroupId())){
				Set<String> goodGroups = Sets.newHashSet(pg.getGroupId().split(","));
				// 计算交集
				goodGroups.retainAll(classifySet);
				if (goodGroups.size() > 0){
					resultList.add(ilv);
				}
			}
		}
		
		return resultList;
	}

}
