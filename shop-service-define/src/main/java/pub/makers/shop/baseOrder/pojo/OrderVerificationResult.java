package pub.makers.shop.baseOrder.pojo;

import java.io.Serializable;


/**
 * 订单商品检查结果
 * @author apple
 *
 */
public class OrderVerificationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -562178646366916265L;

	/** 是否成功 */
	private boolean success;

	/** 错误消息 */
	private String message;

	/** 商品ID */
	private String goodId;

	/** SKUID */
	private String skuId;

	public static OrderVerificationResult createError(String msg) {

		OrderVerificationResult result = new OrderVerificationResult();
		result.setMessage(msg);
		result.setSuccess(false);

		return result;
	}

	public static OrderVerificationResult createSuccess() {

		OrderVerificationResult result = new OrderVerificationResult();
		result.setSuccess(true);

		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	
}
