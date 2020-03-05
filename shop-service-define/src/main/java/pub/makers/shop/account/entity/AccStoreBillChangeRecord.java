package pub.makers.shop.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccStoreBillChangeRecord implements Serializable {


    private String Id;

    private String storeId;

    private String relBillId;

    private BigDecimal balance;

    private BigDecimal totalIncomeMoney;

    private BigDecimal transferableMoney;

    private BigDecimal settlementedMoney;

    private BigDecimal toSettlementMoney;

    private BigDecimal withdrawLockMoney;

    private BigDecimal hisTotalIncomeMoney;

    private BigDecimal hisTransferableMoney;

    private BigDecimal hisToSettlementMoney;

    private BigDecimal hisWithdrawLockMoney;

    private String operationType;

    private String operationDesc;

    private Date lastUpdated;

    private Date dateCreated;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRelBillId() {
        return relBillId;
    }

    public void setRelBillId(String relBillId) {
        this.relBillId = relBillId == null ? null : relBillId.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalIncomeMoney() {
        return totalIncomeMoney;
    }

    public void setTotalIncomeMoney(BigDecimal totalIncomeMoney) {
        this.totalIncomeMoney = totalIncomeMoney;
    }

    public BigDecimal getTransferableMoney() {
        return transferableMoney;
    }

    public void setTransferableMoney(BigDecimal transferableMoney) {
        this.transferableMoney = transferableMoney;
    }

    public BigDecimal getSettlementedMoney() {
        return settlementedMoney;
    }

    public void setSettlementedMoney(BigDecimal settlementedMoney) {
        this.settlementedMoney = settlementedMoney;
    }

    public BigDecimal getToSettlementMoney() {
        return toSettlementMoney;
    }

    public void setToSettlementMoney(BigDecimal toSettlementMoney) {
        this.toSettlementMoney = toSettlementMoney;
    }

    public BigDecimal getWithdrawLockMoney() {
        return withdrawLockMoney;
    }

    public void setWithdrawLockMoney(BigDecimal withdrawLockMoney) {
        this.withdrawLockMoney = withdrawLockMoney;
    }

    public BigDecimal getHisTotalIncomeMoney() {
        return hisTotalIncomeMoney;
    }

    public void setHisTotalIncomeMoney(BigDecimal hisTotalIncomeMoney) {
        this.hisTotalIncomeMoney = hisTotalIncomeMoney;
    }

    public BigDecimal getHisTransferableMoney() {
        return hisTransferableMoney;
    }

    public void setHisTransferableMoney(BigDecimal hisTransferableMoney) {
        this.hisTransferableMoney = hisTransferableMoney;
    }

    public BigDecimal getHisToSettlementMoney() {
        return hisToSettlementMoney;
    }

    public void setHisToSettlementMoney(BigDecimal hisToSettlementMoney) {
        this.hisToSettlementMoney = hisToSettlementMoney;
    }

    public BigDecimal getHisWithdrawLockMoney() {
        return hisWithdrawLockMoney;
    }

    public void setHisWithdrawLockMoney(BigDecimal hisWithdrawLockMoney) {
        this.hisWithdrawLockMoney = hisWithdrawLockMoney;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc == null ? null : operationDesc.trim();
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}