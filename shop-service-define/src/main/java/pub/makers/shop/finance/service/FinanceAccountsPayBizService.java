package pub.makers.shop.finance.service;

import java.util.List;
import java.util.Map;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.finance.entity.FinanceAccountsPay;
import pub.makers.shop.finance.vo.FinanceAccountsPayParams;
import pub.makers.shop.finance.vo.FinanceAccountspayVo;


public interface FinanceAccountsPayBizService {
	
	/**
	 * 根据查询条件查询列表
	 * @param name
	 * @param u8orderId
	 * @param u8AccountId
	 * @param start
	 * @param limit
	 * @return
	 */
	ResultList<FinanceAccountspayVo> listByParams(FinanceAccountsPayParams params, Paging paging);

	/**
	 * 查找异常的订单
	 * @param params
	 * @param paging
	 * @return
	 */
	ResultList<FinanceAccountspayVo> listFinanceAbnormalParams(FinanceAccountsPayParams params, Paging paging);

	/**
	 * 通过订单ID获取账单
	 * @param orderId
	 * @return
	 */
	FinanceAccountsPay getByOrderId(String orderId);

	/**
	 * 保存对账单
	 * @param financeAccountsPay
	 */
	FinanceAccountsPay saveOrUpdate(FinanceAccountsPay financeAccountsPay);

}
