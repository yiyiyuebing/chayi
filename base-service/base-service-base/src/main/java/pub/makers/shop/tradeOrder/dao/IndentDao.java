package pub.makers.shop.tradeOrder.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeOrder.entity.Indent;

@Repository
public class IndentDao extends BaseCRUDDaoImpl<Indent, String> {
	
	@Override
	protected String getTableName() {
		
		return "indent";
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
