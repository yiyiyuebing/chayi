package pub.makers.shop.bill.service;

import pub.makers.shop.account.pojo.WithdrawApplyParam;
import pub.makers.shop.bill.entity.IndentBill;
import pub.makers.shop.bill.vo.BillVo;

import java.util.List;
import java.util.Map;

public interface BillBizService {

	/**
	 * 记录账务
	 * @param billVo
	 */
	void recordBill(BillVo billVo);

	IndentBill getBill(String indentId);

	Map<String, IndentBill> getBill(List<String> indentIdList);

	/**
	 * 记录账单
	 * @param orderId
	 */
	void addOrderBillRecord(String orderId);

	/**
	 * 结算付款后，操作账户余额
	 */
	void doSettlementMoneyBill(String recordIds);

	/**
	 * 提现申请
	 * userId：店铺ID
	 * withdrawAmount：提现金额
	 * bankCardId：绑定银行卡ID
	 */
	void applyWithDraw(WithdrawApplyParam apply);

	/**
	 * 同意提现
	 * applyId：提现记录ID
	 * reviewMan：审核人
	 * reviewReason：审核原因
	 */
	void agreeWithDrawApply(WithdrawApplyParam apply);

	/**
	 * 拒绝提现
	 * applyId：提现记录ID
	 * reviewMan：审核人
	 * reviewReason：拒绝审核原因
	 */
	void refuseWithDrawApply(WithdrawApplyParam apply);

	/**
	 * 打款操作
	 * applyId：提现记录ID
	 * operMan：操作人
	 * operReason：操作原因
	 */
	void transferWithDraw(WithdrawApplyParam apply);

	/**
	 * 拒绝打款
	 * applyId：提现记录ID
	 * operMan：操作人
	 * operReason：拒绝操作原因
	 */
	void refuseWithDraw(WithdrawApplyParam apply);
}
