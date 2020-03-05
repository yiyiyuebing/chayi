package pub.makers.shop.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccWithdrawApply implements Serializable {
    private String id;

    private String storeId;

    private String storeType;

    private BigDecimal amount;

    private String withdrawType;

    private String applyMan;

    private String applyPhone;

    private Date applyTime;

    private String applyStatus;

    private String reviewMan;

    private String reviewReason;

    private Date reviewTime;

    private String operMan;

    private Date operTime;

    private String tradeNo;

    private String enterCardId;

    private String enterCardNo;

    private String bankAddr;

    private String bankCategory;

    private String ownerName;

    private Date lastUpdated;

    private Date dateCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        this.withdrawType = withdrawType == null ? null : withdrawType.trim();
    }

    public String getApplyMan() {
        return applyMan;
    }

    public void setApplyMan(String applyMan) {
        this.applyMan = applyMan == null ? null : applyMan.trim();
    }

    public String getApplyPhone() {
        return applyPhone;
    }

    public void setApplyPhone(String applyPhone) {
        this.applyPhone = applyPhone == null ? null : applyPhone.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public String getReviewMan() {
        return reviewMan;
    }

    public void setReviewMan(String reviewMan) {
        this.reviewMan = reviewMan == null ? null : reviewMan.trim();
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason == null ? null : reviewReason.trim();
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getOperMan() {
        return operMan;
    }

    public void setOperMan(String operMan) {
        this.operMan = operMan == null ? null : operMan.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public String getEnterCardId() {
        return enterCardId;
    }

    public void setEnterCardId(String enterCardId) {
        this.enterCardId = enterCardId == null ? null : enterCardId.trim();
    }

    public String getEnterCardNo() {
        return enterCardNo;
    }

    public void setEnterCardNo(String enterCardNo) {
        this.enterCardNo = enterCardNo == null ? null : enterCardNo.trim();
    }

    public String getBankAddr() {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr == null ? null : bankAddr.trim();
    }

    public String getBankCategory() {
        return bankCategory;
    }

    public void setBankCategory(String bankCategory) {
        this.bankCategory = bankCategory == null ? null : bankCategory.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
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