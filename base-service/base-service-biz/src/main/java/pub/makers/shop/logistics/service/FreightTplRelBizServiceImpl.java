package pub.makers.shop.logistics.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.logistics.entity.FreightTplRel;
import pub.makers.shop.logistics.vo.FreightTplRelVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

import java.util.List;

@Service(version="1.0.0")
public class FreightTplRelBizServiceImpl implements FreightTplRelBizService{

	@Autowired
	private FreightTplRelService tplRelService;
	@Autowired
	private RelTypeServiceManager relServiceManager;
	
	public List<FreightTplRelVo> queryTradeGoodRel(IndentVo iv) {
		
		List<FreightTplRelVo> resultList = Lists.newArrayList();
		
		// 查询现有关联列表
		List<FreightTplRel> relList = tplRelService.list(Conds.get().eq("orderType", OrderBizType.trade.name()).eq("isValid", BoolType.T.name()).eq("delFlag", BoolType.F.name()).order("sort desc"));
		List<IndentListVo> indentList = Lists.newArrayList(iv.getIndentList());
		
		for (FreightTplRel rel : relList){
			FreightTplRelTypeService relTypeService = relServiceManager.getServiceByType(rel.getRelType());
			List<IndentListVo> ilvList = relTypeService.selectGood(indentList, rel);
			if (ilvList.size() <= 0){
				continue;
			}
			
			// 已经匹配到的商品不在重复应用规则
			indentList.removeAll(ilvList);
			
			FreightTplRelVo vo = new FreightTplRelVo();
			vo.setIndentList(ilvList);
			vo.setTplId(rel.getTplId());
			
			resultList.add(vo);
		}
		
		return resultList;
		
	}

	@Override
	public List<FreightTplRelVo> queryPurchaseGoodRel(PurchaseOrderVo pvo) {
		
		List<FreightTplRelVo> resultList = Lists.newArrayList();
		
		// 查询现有关联列表
		List<FreightTplRel> relList = tplRelService.list(Conds.get().eq("orderType", OrderBizType.purchase.name()).eq("delFlag", BoolType.F.name()).eq("isValid", BoolType.T.name()).order("sort desc"));
		List<IndentListVo> indentList = Lists.newArrayList();
		for (PurchaseOrderListVo plvo : pvo.getOrderListVos()){
			
			IndentListVo ilvo = plvo.toIndentListVo();
			
			indentList.add(ilvo);
		}
		
		for (FreightTplRel rel : relList){
			FreightTplRelTypeService relTypeService = relServiceManager.getServiceByType(rel.getRelType());
			List<IndentListVo> ilvList = relTypeService.selectPurchaseGoods(indentList, rel);
			if (ilvList.size() <= 0){
				continue;
			}
			
			// 已经匹配到的商品不在重复应用规则
			indentList.removeAll(ilvList);
			
			FreightTplRelVo vo = new FreightTplRelVo();
			vo.setIndentList(ilvList);
			vo.setTplId(rel.getTplId());
			
			resultList.add(vo);
		}
		
		return resultList;
	}

}
