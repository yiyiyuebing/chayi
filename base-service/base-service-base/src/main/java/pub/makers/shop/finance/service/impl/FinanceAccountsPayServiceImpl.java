package pub.makers.shop.finance.service.impl;

import com.lantu.base.util.ListUtils;
import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.finance.dao.FinanceAccountsPayDao;
import pub.makers.shop.finance.entity.FinanceAccountsPay;
import pub.makers.shop.finance.service.FinanceAccountsPayService;
import pub.makers.shop.finance.vo.FinanceAccountspayVo;

import java.util.List;

@Service
public class FinanceAccountsPayServiceImpl extends BaseCRUDServiceImpl<FinanceAccountsPay, String, FinanceAccountsPayDao>
											implements FinanceAccountsPayService{

	@Override
	public FinanceAccountsPay getVoByOrderId(String orderId) {
		List<FinanceAccountsPay> financeAccountspays = dao.findBySql("select * from finance_accountspay where order_id = ?", orderId);
		return ListUtils.getSingle(financeAccountspays);
	}
}

