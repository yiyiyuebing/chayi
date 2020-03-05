package pub.makers.shop.tradeOrder.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeOrder.entity.IndentList;

@Repository
public class IndentListDao extends BaseCRUDDaoImpl<IndentList, String> {
	
	@Override
	protected String getTableName() {
		
		return "indent_list";
	}
	
	@Override
	protected String getKeyName() {
		
		return "id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
