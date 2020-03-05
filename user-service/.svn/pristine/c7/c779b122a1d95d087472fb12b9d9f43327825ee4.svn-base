package pub.makers.shop.bill.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.bill.entity.IndentBill;

@Repository
public class IndentBillDao extends BaseCRUDDaoImpl<IndentBill, String> {
	
	@Override
	protected String getTableName() {
		
		return "indent_bill";
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
