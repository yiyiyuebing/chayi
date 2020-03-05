package pub.makers.shop.bill.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.account.entity.AccStoreBillChangeRecord;
import pub.makers.shop.account.entity.AccTotalStoreBill;
import pub.makers.shop.account.service.AccTotalStoreBillService;
import pub.makers.shop.account.service.TotalStoreBillBizService;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.bill.vo.Actor;
import pub.makers.shop.bill.vo.BillItemVo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dy on 2017/10/7.
 */
@Service(version = "1.0.0")
public class TotalStoreBillBizServiceImpl implements TotalStoreBillBizService {


    @Autowired
    private AccTotalStoreBillService accTotalStoreBillService;
    @Autowired
    private BillHandler handler;

    @Override
    public void updateStoreBill(AccStoreBillChangeRecord changeRecord) {
        // 输入检查
        ValidateUtils.notNull(changeRecord.getStoreId(), "用户ID不能为空");
        ValidateUtils.notNull(changeRecord.getRelBillId(), "关联ID不能为空");
        ValidateUtils.notNull(changeRecord.getOperationType(), "操作类型不能为空");


        //获取当前账户余额
        AccTotalStoreBill accTotalStoreBill = handler.getByStoreId(changeRecord.getStoreId());
        if (accTotalStoreBill == null) {
            accTotalStoreBill = createAccTotalStoreBill(changeRecord.getStoreId());
        }
        //历史账户记录
        changeRecord.setHisToSettlementMoney(accTotalStoreBill.getToSettlementMoney());
        changeRecord.setHisTotalIncomeMoney(accTotalStoreBill.getSettlementedMoney());
        changeRecord.setHisTransferableMoney(accTotalStoreBill.getWithdrawMoney());
        changeRecord.setHisWithdrawLockMoney(accTotalStoreBill.getWithdrawLockMoney());


        //账户金额
        accTotalStoreBill.setSettlementedMoney(accTotalStoreBill.getSettlementedMoney().add(changeRecord.getSettlementedMoney()));
        accTotalStoreBill.setToSettlementMoney(accTotalStoreBill.getToSettlementMoney().add(changeRecord.getToSettlementMoney()));
        accTotalStoreBill.setWithdrawMoney(accTotalStoreBill.getWithdrawMoney().add(changeRecord.getTransferableMoney()));
        accTotalStoreBill.setWithdrawLockMoney(accTotalStoreBill.getWithdrawLockMoney().add(changeRecord.getWithdrawLockMoney()));

        //当前变化金额
        changeRecord.setTransferableMoney(accTotalStoreBill.getWithdrawMoney());
        changeRecord.setWithdrawLockMoney(accTotalStoreBill.getWithdrawLockMoney());
        changeRecord.setTotalIncomeMoney(accTotalStoreBill.getWithdrawMoney());
        changeRecord.setToSettlementMoney(accTotalStoreBill.getToSettlementMoney());
        changeRecord.setBalance(accTotalStoreBill.getWithdrawMoney().add(accTotalStoreBill.getWithdrawLockMoney()));
        handler.addBillChangeRecord(changeRecord);


        if (StringUtils.isNotBlank(accTotalStoreBill.getId())) {
            accTotalStoreBill.setLastUpdated(new Date());
            handler.updateTotalBill(accTotalStoreBill);
        } else {
            accTotalStoreBill.setId(IdGenerator.getDefault().nextId() + "");
            handler.addTotalBill(accTotalStoreBill);
        }
    }

    /**
     * 创建店铺账户
     * @param storeId
     * @return
     */
    private AccTotalStoreBill createAccTotalStoreBill(String storeId) {
        AccTotalStoreBill accTotalStoreBill = new AccTotalStoreBill();
        accTotalStoreBill.setStoreId(storeId);
        accTotalStoreBill.setDateCreated(new Date());
        accTotalStoreBill.setToSettlementMoney(BigDecimal.ZERO);
        accTotalStoreBill.setWithdrawLockMoney(BigDecimal.ZERO);
        accTotalStoreBill.setWithdrawMoney(BigDecimal.ZERO);
        accTotalStoreBill.setSettlementedMoney(BigDecimal.ZERO);
        return accTotalStoreBill;
    }


}
