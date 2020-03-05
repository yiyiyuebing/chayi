package pub.makers.shop.bill.service;

import pub.makers.shop.account.entity.AccStoreBillChangeRecord;
import pub.makers.shop.account.entity.AccTotalStoreBill;
import pub.makers.shop.bill.vo.Actor;
import pub.makers.shop.bill.vo.BillItemVo;

public interface BillHandler {

	/**
	 * 记录账务
	 */
	void recordBill(Actor actor, BillItemVo billItem);

	/**
	 * 新增店铺账户信息
	 * @param totalBill
	 */
	void addTotalBill(AccTotalStoreBill totalBill);

	/**
	 * 更新店铺账户信息
	 * @param totalBill
	 */
	void updateTotalBill(AccTotalStoreBill totalBill);

	/**
	 * 店铺账户变化记录
	 * @param changeRecord
	 */
	void addBillChangeRecord(AccStoreBillChangeRecord changeRecord);

	/**
	 * 获取店铺账户信息
	 * @param storeId
	 * @return
	 */
	AccTotalStoreBill getByStoreId(String storeId);
}
