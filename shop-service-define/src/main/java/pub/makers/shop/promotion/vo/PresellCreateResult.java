package pub.makers.shop.promotion.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import pub.makers.shop.promotion.entity.PresellActivity;

/**
 * 预售订单创建结果
 * @author apple
 *
 */
public class PresellCreateResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<BigDecimal> paymentAmounts;
	
	private PresellActivity activity;

	public List<BigDecimal> getPaymentAmounts() {
		return paymentAmounts;
	}

	public void setPaymentAmounts(List<BigDecimal> paymentAmounts) {
		this.paymentAmounts = paymentAmounts;
	}

	public PresellActivity getActivity() {
		return activity;
	}

	public void setActivity(PresellActivity activity) {
		this.activity = activity;
	}
	
	

}
