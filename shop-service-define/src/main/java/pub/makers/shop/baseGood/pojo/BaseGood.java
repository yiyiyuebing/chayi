package pub.makers.shop.baseGood.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 基础商品信息
 * @author apple
 *
 */
public class BaseGood implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2450011030927636850L;
	
	/**
	 * 商品SKUID
	 */
	private String goodSkuId;
	
	private String goodId;
	
	/**
	 * 商品原价
	 */
	private BigDecimal amount;

	public String getGoodSkuId() {
		return goodSkuId;
	}

	public void setGoodSkuId(String goodSkuId) {
		this.goodSkuId = goodSkuId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	
}
