package pub.makers.shop.afterSale.vo;

import java.io.Serializable;
import java.math.BigDecimal;


public class AfterSaleTkResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 最大可退款金额 */
	private BigDecimal maxAmount;
	private BigDecimal freight;
	
	public AfterSaleTkResult(BigDecimal maxAmount, BigDecimal freight){
		this.maxAmount = maxAmount;
		this.freight = freight;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
}
