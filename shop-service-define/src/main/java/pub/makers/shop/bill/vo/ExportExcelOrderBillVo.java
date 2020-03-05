package pub.makers.shop.bill.vo;

import org.apache.commons.lang.StringUtils;
import pub.makers.shop.bill.enums.OrderBillStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**   
* @Title: ExportExcelBillVo.java
* @Package com.club.web.store.vo
* @Description: 导出Excel账单VO,导出结算账单excel专用，非excel的内容，请勿增删属性
* @author hqLin 
* @date 2016/05/09
* @version V1.0   
*/
public class ExportExcelOrderBillVo implements Serializable{
	
	private String shopName;//店铺名称
	private String shopNumber;//店铺编号
	private String deptNum;//部门编码
	private String buyerName;//客户名称
	private String accountType; //帐号分类
	private String indentName ;
	private String name;//姓名
	private String bankCard;//银行卡号
	private String bankName;//银行名称
	private String bankAddress;//开户行
	private String goodName;//商品名称
	private int number;//商品个数
    private Date payTime;	//支付时间
    private String cycle;	//结算周期
    private Date settlementTime;//可结算时间
    private BigDecimal paymentAmount;//支付金额
    private BigDecimal carriage;//运费
    private BigDecimal supplyPrice;//结算金额
    private String payStatus;	//付款状态

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(String shopNumber) {
		this.shopNumber = shopNumber;
	}

	public String getDeptNum() {
		return deptNum;
	}

	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getIndentName() {
		return indentName;
	}

	public void setIndentName(String indentName) {
		this.indentName = indentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getCarriage() {
		return carriage;
	}

	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}

	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {

		if (StringUtils.isNotBlank(payStatus)) {
			this.payStatus = OrderBillStatus.getDbDataByName(Integer.parseInt(payStatus));
		} else {
			this.payStatus = payStatus;
		}
	}

}
