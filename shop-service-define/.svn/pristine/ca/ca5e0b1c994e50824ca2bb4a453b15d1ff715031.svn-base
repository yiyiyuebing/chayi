package pub.makers.shop.bill.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dy on 2017/9/6.
 */
public class OrderBillRecord implements Serializable {

    /** ID */
    private Long id;

    /** 用户ID */
    private Long buyerId;

    /** 买家ID */
    private Long subbranchId;

    /**
     * 是否是主账号
     */
    private String isSubAccount;

    /** 订单号ID */
    private Long orderId;

    /** 支付金额 */
    private BigDecimal payAmount;

    /** 结算金额 */
    private BigDecimal settlementAmount;

    /** 订单类型 */
    private String orderType;

    /** 订单号 */
    private String orderNo;

    /** 状态(待结算:1,已结算待付款:2,已结算已付款:3) */
    private Integer status;

    /** 备注 */
    private String remark;

    /**
     * 结算周期
     */
    private String timeCycle;

    /**
     * 结算日期
     */
    private Date settlementDate;

    /** 更新时间 */
    private Date lastUpdated;

    /** 创建时间 */
    private Date dateCreated;

    private BigDecimal catchAmount;//实际利润

    public BigDecimal getCatchAmount() {
        return catchAmount;
    }

    public void setCatchAmount(BigDecimal catchAmount) {
        this.catchAmount = catchAmount;
    }

    @Override
    public String toString() {
        return "OrderBillRecord{" +
                "id=" + id +
                ", buyerId=" + buyerId +
                ", subbranchId=" + subbranchId +
                ", isSubAccount='" + isSubAccount + '\'' +
                ", orderId=" + orderId +
                ", payAmount=" + payAmount +
                ", settlementAmount=" + settlementAmount +
                ", orderType='" + orderType + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", timeCycle='" + timeCycle + '\'' +
                ", settlementDate=" + settlementDate +
                ", lastUpdated=" + lastUpdated +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public String getIsSubAccount() {
        return isSubAccount;
    }

    public void setIsSubAccount(String isSubAccount) {
        this.isSubAccount = isSubAccount;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getTimeCycle() {
        return timeCycle;
    }

    public void setTimeCycle(String timeCycle) {
        this.timeCycle = timeCycle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSubbranchId() {
        return subbranchId;
    }

    public void setSubbranchId(Long subbranchId) {
        this.subbranchId = subbranchId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
