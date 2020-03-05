package pub.makers.shop.tradeOrder.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeOrder.dao.IndentListDao;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.service.IndentListService;
import pub.makers.shop.tradeOrder.vo.IndentListVo;

@Service
public class IndentListServiceImpl extends BaseCRUDServiceImpl<IndentList, String, IndentListDao>
										implements IndentListService{

	public void saveIndentListVo(List<IndentListVo> voList) {
		
		List<IndentList> indentList = Lists.newArrayList();
		for (IndentListVo vo : voList){
			indentList.add(IndentList.fromIndentListVo(vo));
		}
		
		dao.batchInsert(indentList);
	}

	@Override
	public List<IndentList> getIndentListNotGift(List<Object> ids) {
		String sql = "select i.indent_id, i.trade_good_img_url from indent_list i where 1 = 1 and (i.gift_flag <> 'T' or i.gift_flag is null) and i.indent_id in (%s)";
		String strIds = ids.size() == 0 ? "-1" : Joiner.on(",").join(ids);
		String stmt = String.format(sql, strIds);
		
//		for (int i = 0; i < ids.size(); i++) {
//			if (i > 0) {
//				sql += ",";
//			}
//			sql += ids.get(i);
//		}
//		sql += ")";
		return dao.findBySql(stmt);
	}

	@Override
	public List<IndentList> listByOrderId(String orderId) {
		
		return list(Conds.get().eq("indentId", orderId));
	}
	
	
}
