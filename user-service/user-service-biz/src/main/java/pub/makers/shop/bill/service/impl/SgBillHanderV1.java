package pub.makers.shop.bill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.account.entity.AccStoreBillChangeRecord;
import pub.makers.shop.account.entity.AccTotalStoreBill;
import pub.makers.shop.account.service.AccStoreBillChangeRecordService;
import pub.makers.shop.account.service.AccTotalStoreBillService;
import pub.makers.shop.bill.service.BillHandler;
import pub.makers.shop.bill.vo.Actor;
import pub.makers.shop.bill.vo.BillItemVo;

@Service
public class SgBillHanderV1 implements BillHandler{


	@Autowired
	private AccTotalStoreBillService accTotalStoreBillService;
	@Autowired
	private AccStoreBillChangeRecordService accStoreBillChangeRecordService;

	@Override
	public void recordBill(Actor actor, BillItemVo billItem) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addTotalBill(AccTotalStoreBill totalBill) {
		accTotalStoreBillService.insert(totalBill);
	}

	@Override
	public void updateTotalBill(AccTotalStoreBill totalBill) {
		accTotalStoreBillService.update(totalBill);
	}

	@Override
	public void addBillChangeRecord(AccStoreBillChangeRecord changeRecord) {
		accStoreBillChangeRecordService.insert(changeRecord);
	}

	@Override
	public AccTotalStoreBill getByStoreId(String storeId) {
		return accTotalStoreBillService.get(Conds.get().eq("store_id", storeId));
	}

}
