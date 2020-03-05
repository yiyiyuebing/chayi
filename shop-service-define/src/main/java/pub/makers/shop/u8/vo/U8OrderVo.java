package pub.makers.shop.u8.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/6/24.
 */
public class U8OrderVo implements Serializable {

    private String id;

    private String name;

    private String tradeHeadStoreId;

    private String subbranchId;

    private String storeName;

    private String storeNum;

    private String storeDeptNum;

    private String storeContactName;

    private String buyerId;

    private Map<String, Object> buyer;

    private String referrerId;

    private Map<String, Object> referrer;

    private String totalAmount;

    private String paymentAmount;

    private Date createTime;

    private Date payTime;

    private Integer number;

    private Integer type;

    private String province;

    private String city;

    private String town;

    private String townCode;

    private String address;

    private String receiverPhone;

    private String buyerRemark;

    private String expressNumber;

    private String expressId;

    private String expressCompany;

    private String weight;

    private String carriage;

    private String buyerCarriage;

    private String shipper;

    private Date shipTime;

    private String receiver;

    private Integer status;

    private Integer payType;

    private String payAccount;

    private Boolean needInvoice = false;

    private String invoiceName;

    private String invoiceContent;

    private String remark;

    private String remarkLevel;

    private String remarkInfo;

    private Date finishTime;

    private Boolean deleteFlag = false;

    private Integer dealStatus;

    private String refundId;

    private String returnId;

    private String refundName;

    private String returnName;

    private String refundRemark;

    private String returnRemark;

    private String rejectRefund;

    private String rejectReturn;

    private String ticketNum;

    private String buyType;


    String shopCode;

    String shopName;

    String departmentCode;

    BigDecimal amount;

    private String orderType;

    private String orderBizType;

    private String goodSkuCode;


    private Integer shipNotice;
    private String roleId;
    private List<U8OrderListVo> indentList;
    private Integer indentStatus;

    public String getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(String orderBizType) {
        this.orderBizType = orderBizType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTradeHeadStoreId() {
        return tradeHeadStoreId;
    }

    public void setTradeHeadStoreId(String tradeHeadStoreId) {
        this.tradeHeadStoreId = tradeHeadStoreId;
    }

    public String getSubbranchId() {
        return subbranchId;
    }

    public void setSubbranchId(String subbranchId) {
        this.subbranchId = subbranchId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getStoreDeptNum() {
        return storeDeptNum;
    }

    public void setStoreDeptNum(String storeDeptNum) {
        this.storeDeptNum = storeDeptNum;
    }

    public String getStoreContactName() {
        return storeContactName;
    }

    public void setStoreContactName(String storeContactName) {
        this.storeContactName = storeContactName;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Map<String, Object> getBuyer() {
        return buyer;
    }

    public void setBuyer(Map<String, Object> buyer) {
        this.buyer = buyer;
    }

    public String getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(String referrerId) {
        this.referrerId = referrerId;
    }

    public Map<String, Object> getReferrer() {
        return referrer;
    }

    public void setReferrer(Map<String, Object> referrer) {
        this.referrer = referrer;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTownCode() {
        return townCode;
    }

    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }

    public String getBuyerCarriage() {
        return buyerCarriage;
    }

    public void setBuyerCarriage(String buyerCarriage) {
        this.buyerCarriage = buyerCarriage;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public Boolean getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Boolean needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemarkLevel() {
        return remarkLevel;
    }

    public void setRemarkLevel(String remarkLevel) {
        this.remarkLevel = remarkLevel;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getRefundName() {
        return refundName;
    }

    public void setRefundName(String refundName) {
        this.refundName = refundName;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark) {
        this.returnRemark = returnRemark;
    }

    public String getRejectRefund() {
        return rejectRefund;
    }

    public void setRejectRefund(String rejectRefund) {
        this.rejectRefund = rejectRefund;
    }

    public String getRejectReturn() {
        return rejectReturn;
    }

    public void setRejectReturn(String rejectReturn) {
        this.rejectReturn = rejectReturn;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getGoodSkuCode() {
        return goodSkuCode;
    }

    public void setGoodSkuCode(String goodSkuCode) {
        this.goodSkuCode = goodSkuCode;
    }

    public Integer getShipNotice() {
        return shipNotice;
    }

    public void setShipNotice(Integer shipNotice) {
        this.shipNotice = shipNotice;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<U8OrderListVo> getIndentList() {
        return indentList;
    }

    public void setIndentList(List<U8OrderListVo> indentList) {
        this.indentList = indentList;
    }

    public Integer getIndentStatus() {
        return indentStatus;
    }

    public void setIndentStatus(Integer indentStatus) {
        this.indentStatus = indentStatus;
    }
}
